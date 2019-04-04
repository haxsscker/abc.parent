package com.autoserve.abc.service.message.deposit.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.exportpdf.ExportPdfService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.message.deposit.bean.CertificateBean;
import com.autoserve.abc.service.message.deposit.constant.SceneConfig;
import com.autoserve.abc.service.message.mail.MailSenderInfo;
import com.autoserve.abc.service.message.mail.SimpleMailSenderService;
import com.autoserve.abc.service.message.tsign.SignManager;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.google.common.collect.Lists;
import com.timevale.esign.sdk.tech.bean.OrganizeBean;
import com.timevale.esign.sdk.tech.bean.PersonBean;
import com.timevale.esign.sdk.tech.bean.result.FileDigestSignResult;
import com.timevale.esign.sdk.tech.impl.constants.OrganRegType;

/**
 * 合同及存证生成
 * @author Administrator
 *
 */
public class ContractGenerationService {
	private static final Logger     logger          = LoggerFactory.getLogger(ContractGenerationService.class);
	private String                  user            = SystemGetPropeties.getBossString("abc.message.mail.user");
    private String                  host            = SystemGetPropeties.getBossString("abc.message.mail.smtp.host");
    private String                  password        = SystemGetPropeties.getBossString("abc.message.mail.password");
    private String                  from            = SystemGetPropeties.getBossString("abc.message.mail.from");
	private String                  outFile			= SystemGetPropeties.getBossString("abc.message.mail.outFile");
	@Resource
    private ExportPdfService        exportPdfService;
    @Resource
    private InvestQueryService      investQueryService;
    @Resource
    private UserService             userService;
    @Resource
    private SimpleMailSenderService simpleMailSender;
    @Resource
    private LoanDao                 loanDao;
    @Resource
    private TransferLoanDao         transferLoanDao;
    @Resource
    private CompanyCustomerService  companyCustomerService;
    @Resource
    private SignManager             signManager;
    @Resource
    private InvestDao               investDao;
    @Resource
    private SceneDeposit             sceneDeposit;
    
	/**
	 * 普通标合同生成
	 * @param loanId
	 * @param loanName
	 * @param isSendMail
	 * @return
	 */
	 public BaseResult investContractGeneration(int loanId, String loanName, boolean isSendMail) {
	        BaseResult result = new BaseResult();
	        OutputStream out;
	        try {
	            String dir = outFile + new SimpleDateFormat("yyyyMMdd").format(new Date()) + File.separatorChar;

	            // 判断目录是否存在，不存在创建
	            File tempFile = new File(dir);
	            if (!tempFile.exists()) {
	                tempFile.mkdir();
	            }
	            LoanDO loanDo = loanDao.findByLoanId(loanId);
	            
	            InvestSearchDO searchDO = new InvestSearchDO();
	            searchDO.setBidId(loanId);
	            searchDO.setBidType(BidType.COMMON_LOAN.getType());
	            if(LoanState.REPAY_COMPLETED.state==loanDo.getLoanState()){
	            	searchDO.setInvestStates(Arrays.asList(InvestState.EARN_COMPLETED.getState()));
	            }else{
	            	searchDO.setInvestStates(Arrays.asList(InvestState.EARNING.getState(),InvestState.TRANSFERED.getState()));
	            }
	            ListResult<Invest> investResult = investQueryService.queryInvestList(searchDO);
	            List<Invest> investList = investResult.getData();
	            if (!investResult.isSuccess() || CollectionUtils.isEmpty(investList)) {
	                return result.setError(CommonResultCode.BIZ_ERROR, "投资记录查询失败");
	            }
	            List<Integer> investIdList = new ArrayList<Integer>();
				Map<Integer,Integer> investUserMap = new HashMap<Integer,Integer>();
				Map<Integer,Invest> investMap = new HashMap<Integer,Invest>();
				for (Invest invest : investList) {
					investIdList.add(invest.getId());
					investUserMap.put(invest.getId(), invest.getUserId());
					investMap.put(invest.getId(), invest);
				}
				String reciveEmaiUser = "";
	            //loan user
				String accountId = "";//签署账号
				String sealData = "";//个人印章
	            PlainResult<UserDO> userDo = userService.findById(loanDo.getLoanUserId());
	        	PlainResult<CompanyCustomerDO> enterprise = companyCustomerService.findByUserId(loanDo.getLoanUserId());
	        	String loanUserEmail;
	        	accountId = userDo.getData().getTsignAccountId();
	        	sealData = userDo.getData().getSealData();
	        	if (UserType.ENTERPRISE.getType() == userDo.getData().getUserType()){
	                if (StringUtil.isEmpty(accountId))
	                {
	            		OrganizeBean organizeBean = new OrganizeBean();
	            		organizeBean.setName(enterprise.getData().getCcCompanyName());
	            		organizeBean.setAgentName(enterprise.getData().getCcCorporate());
	            		organizeBean.setAgentIdNo(enterprise.getData().getCcDocNo());
	            		organizeBean.setRegType(OrganRegType.MERGE);
	            		organizeBean.setOrganCode(enterprise.getData().getCcLicenseNo());
	                	accountId = signManager.addOrganizeAccount(organizeBean);
	                	if (!StringUtil.isEmpty(accountId))
	                	{
	                		userDo.getData().setTsignAccountId(accountId);
	                		userService.modifyInfo(userDo.getData());
	                	}
	                }
	                //生成企业印章
	                if (StringUtil.isEmpty(sealData))
	                {
	                	if (!StringUtil.isEmpty(accountId))
	                	{
	                		sealData = signManager.addOrganizeTemplateSeal(accountId);
	                		if (!StringUtil.isEmpty(sealData))
	                    	{
	                    		userDo.getData().setSealData(sealData);
	                    		userService.modifyInfo(userDo.getData());
	                    	}
	                	}
	                }
	                loanUserEmail = enterprise.getData().getCcContactEmail();
	        	}else{
	        		if (StringUtil.isEmpty(accountId))
	                {
	                	PersonBean p = new PersonBean();
	            		p.setName(userDo.getData().getUserRealName());
	                	p.setIdNo(userDo.getData().getUserDocNo());
	                	accountId = signManager.addPersonAccount(p);
	                	if (!StringUtil.isEmpty(accountId))
	                	{
	                		userDo.getData().setTsignAccountId(accountId);
	                		userService.modifyInfo(userDo.getData());
	                	}
	                }
	            	//生成个人印章
	        		if (StringUtil.isEmpty(sealData))
	                {
	        			if (!StringUtil.isEmpty(accountId))
	                	{
	                		sealData = signManager.addPersonTemplateSeal(accountId);
	                		if (!StringUtil.isEmpty(sealData))
	                    	{
	                    		userDo.getData().setSealData(sealData);
	                    		userService.modifyInfo(userDo.getData());
	                    	}
	                	}
	                }
	            	loanUserEmail = userDo.getData().getUserEmail();
	            }
	        	userDo = userService.findById(loanDo.getLoanUserId());//重新查询借款人
	            
	        	investIdList.add(0);//设定几款人对应为0
				investUserMap.put(0, loanDo.getLoanUserId());
	            //userSet.add(loanDo.getLoanUserId());
	            // tsign process
	            FileDigestSignResult fileResult = new FileDigestSignResult();
	            fileResult.setErrCode(99);
	            MailSenderInfo mailInfo = new MailSenderInfo();
	            //invest user sign
	            PersonBean pu;
	            PlainResult<UserDO> invUser;
	        	String loanUserSignFileNew = "";
	        	String userSignFileEnd = "";
	        	String platformSignFileEnd = "";
	        	boolean isSignSuccess = false;
	        	int i=1;
	        	for (Integer invId : investIdList) {
					int invUserId = investUserMap.get(invId);
	        		String loanUserSignFile = dir + loanName +"_00_"+invId+ ".pdf";//原始洁净版pdf文件路径
	                out = new FileOutputStream(loanUserSignFile);
	        		try
	        		{
	        			StringBuffer signServiceIds = new StringBuffer();
	        			if(invUserId==loanDo.getLoanUserId()){//如果是借款人合同则特殊处理，只有平台和借款人签章，并且显示所有投资人列表
	        				if(StringUtil.isEmpty(loanDo.getContractPath())){
		        				BaseResult exportResu = exportPdfService.exportBorrowMoney(loanId,0,invId, out,0);
		        				if(exportResu.isSuccess()){
			        				userSignFileEnd = dir + loanName + "_02_" + invId + ".pdf";//借款人签章后文件路径
			                		platformSignFileEnd = dir + loanName + ".pdf";//平台签章后文件路径
			                		//借款人签章
			                    	fileResult = signManager.userPersonSignByFileXY(loanUserSignFile, userSignFileEnd, 
			                    			userDo.getData().getTsignAccountId(), "乙方：（盖章）", userDo.getData().getSealData(),350,-20,100);
			                    	if (0 == fileResult.getErrCode()){
			                    		signServiceIds.append(fileResult.getSignServiceId()).append(",");
			                    		//平台签章
			                    		 fileResult = signManager.platformSignByFileXY(userSignFileEnd, platformSignFileEnd, "丙方：（盖章）",300,0,150);
			                    		 if (0 == fileResult.getErrCode()){
			                          		isSignSuccess = true;
			                          		signServiceIds.append(fileResult.getSignServiceId()).append(",");
			                          		//所有投资全部签署
			                          		Map<String, String> resultMap = this.multiInvestorElectronicSign(investMap, dir, loanName, platformSignFileEnd);
			                          		if(!StringUtil.isEmpty(resultMap.get("signedPdf"))){
			                          			platformSignFileEnd = resultMap.get("signedPdf");
			                          		}
				                       		 if(!StringUtil.isEmpty(resultMap.get("signServiceIds"))){
				                       			signServiceIds.append(resultMap.get("signServiceIds"));
				                       		 }
				                       		 
			                          		// 更新数据库
			                          		loanDao.updateContractPath(loanId, platformSignFileEnd);
		                                  }
			                    	}
		        				}
	        				}else{
	        					isSignSuccess = true;
	        					platformSignFileEnd = loanDo.getContractPath();
	        				}
                            // 设置给借款人发送邮件
	        				reciveEmaiUser=userDo.getData().getUserName();
                            //企业用户邮箱保存在abc_company_customer中
                            mailInfo.setToAddress(loanUserEmail);
                            mailInfo.setSubject("致客户的一封信");
                            mailInfo.setContent("尊敬的客户：<br/>您好，感谢您对新华久久贷的信任与支持。您通过新华久久贷网站（www.xh99d.com）融资项目已经完成融资，所筹集款项已转入您的银行存管账户，请您查收并可以提现。具体还款明细可通过网站-“我的账户”-“我的借款”-“还款计划”查询，请按时还款。若有疑问可咨询网站在线客服，也可电话联系网站客服。（附件：借款合同）<br/>新华久久贷");
	        				if(isSignSuccess && StringUtil.isEmpty(loanDo.getLoanDepositNumber())){
	        					logger.info("**************************************借款人合同存证开始**************************************");
                                Map <String,String> param=new HashMap<String, String>();
                                param.put(SceneConfig.CONTRACT_TYPE, SceneConfig.LOAN_CONTRACT);
                                param.put(SceneConfig.CONTRACT_PATH, platformSignFileEnd);
                                List<Integer> ids = new ArrayList<Integer>();
                                for (Invest invest : investList) {
                              	  ids.add(invest.getUserId());
                                }
                                ListResult<UserDO> usersResult = userService.findByList(ids);
                                StringBuffer userRealNames = new StringBuffer();
                                for(UserDO user : usersResult.getData()){
                              	  userRealNames.append(user.getUserRealName()).append(",");
                                }
                                param.put(SceneConfig.PARTYA_PARAM_NAME, userRealNames.toString().substring(0, userRealNames.toString().length()-1));
                                if (UserType.ENTERPRISE.getType() == userDo.getData().getUserType()){
                               	 param.put(SceneConfig.PARTYB_PARAM_NAME, enterprise.getData().getCcCompanyName());
                                }else{
                               	 param.put(SceneConfig.PARTYB_PARAM_NAME, userDo.getData().getUserRealName());
                                }
                                param.put(SceneConfig.PARTYC_PARAM_NAME, SceneConfig.PARTYC_NAME);
                                param.put(SceneConfig.CONTRACT_NAME, loanDo.getLoanNo());
                                param.put(SceneConfig.SIGN_SERVICE_IDS, signServiceIds.toString());
                                List<CertificateBean> certificates = new ArrayList<CertificateBean>();
                                CertificateBean personBean = null;
                                //投资人 一对多
                                for(UserDO user : usersResult.getData()){
                              	  personBean = new CertificateBean();
                              	  personBean.setName(user.getUserRealName());
                              	  personBean.setType("ID_CARD");
                              	  personBean.setNumber(user.getUserDocNo());
                              	  certificates.add(personBean);
                                }
                        		//借款人 一对多
                        		personBean = new CertificateBean();
                        		if (UserType.ENTERPRISE.getType() == userDo.getData().getUserType()){
                        			personBean.setName(enterprise.getData().getCcCompanyName());
                        			personBean.setType("CODE_USC");
                        			personBean.setNumber(enterprise.getData().getCcLicenseNo());
                        		}else{
                        			personBean.setName(userDo.getData().getUserRealName());
                            		personBean.setType("ID_CARD");
                            		personBean.setNumber(userDo.getData().getUserDocNo());
                        		}
                        		certificates.add(personBean);
                        		JSONArray cer=JSONArray.fromObject(certificates);
                        		param.put(SceneConfig.CERTIFICATES, cer.toString());
                        		// 场景式存证编号（证据链编号）
                        		String C_Evid = sceneDeposit.sceneDeposit(param);
                        		logger.info("场景式存证编号==========================="+C_Evid);
	        			        if(!StringUtil.isEmpty(C_Evid)){
	        			        	// 更新数据库
	        			        	loanDao.updateDepositNumber(loanId, C_Evid);
	        			        }
	        			        logger.info("**************************************借款人合同存证结束**************************************");
	        				}
	        			}else{
	        				invUser = userService.findById(invUserId);
	        				if(StringUtil.isEmpty(investMap.get(invId).getInCommContract())){
		        				BaseResult exportResu = exportPdfService.exportBorrowMoney(loanId,invUserId,invId, out,i);
		        				if(exportResu.isSuccess()){
		        					loanUserSignFileNew = dir + loanName + "_01_" + invId + ".pdf";//投资人签章后文件路径
		                    		userSignFileEnd = dir + loanName + "_02_" + invId + ".pdf";//借款人签章后文件路径
		                    		platformSignFileEnd = dir + loanName + "_"+invId+"_xh99d.pdf";//平台签章后文件路径
		        					accountId = invUser.getData().getTsignAccountId();//签署账号
		        					sealData = invUser.getData().getSealData();//个人印章
		        					if (StringUtil.isEmpty(accountId))
			                        {
			                			pu = new PersonBean();
			                			pu.setName(invUser.getData().getUserRealName());
			                			pu.setIdNo(invUser.getData().getUserDocNo());
			                        	
			                			accountId = signManager.addPersonAccount(pu);
			                			if (!StringUtil.isEmpty(accountId))
			                        	{
			                				invUser.getData().setTsignAccountId(accountId);
		    	                    		userService.modifyInfo(invUser.getData());
			                        	}
			                        }
		        					if (StringUtil.isEmpty(sealData))
		        	                {
		        						if (!StringUtil.isEmpty(accountId))
		        	                	{
		        	                		sealData = signManager.addPersonTemplateSeal(accountId);
		        	                		if (!StringUtil.isEmpty(sealData))
		        	                    	{
		        	                    		invUser.getData().setSealData(sealData);
		        	                    		userService.modifyInfo(invUser.getData());
		        	                    	}
		        	                	}
		        	                }
		                    		//投资人签章
		                        	fileResult = signManager.userPersonSignByFileXY(loanUserSignFile, loanUserSignFileNew, 
		                        			accountId, "甲方：（盖章）", sealData,260,0,100);
		                        	if (0 == fileResult.getErrCode())
		                    		{
		                        		signServiceIds.append(fileResult.getSignServiceId()).append(",");
		                        		//借款人签章
		                            	fileResult = signManager.userPersonSignByFileXY(loanUserSignFileNew, userSignFileEnd, 
		                            			userDo.getData().getTsignAccountId(), "乙方：（盖章）", userDo.getData().getSealData(),350,-30,100);
		                            	
		                            	if (0 == fileResult.getErrCode()){
		                            		signServiceIds.append(fileResult.getSignServiceId()).append(",");
		                            		//平台签章
		                                 	fileResult = signManager.platformSignByFileXY(userSignFileEnd, platformSignFileEnd, "丙方：（盖章）",300,0,150);
		                                 	
		                                 	if (0 == fileResult.getErrCode()){
		                                 		isSignSuccess = true;
		                                 		signServiceIds.append(fileResult.getSignServiceId()).append(",");
		                                 		// 更新数据库
		                                 		investDao.updateCommContractPath(invId, platformSignFileEnd);
		                                 	}
		                                 }
		                    		}
		        				}
	        				}else{
	        					isSignSuccess = true;
	        					platformSignFileEnd = investMap.get(invId).getInCommContract();
	        				}
	        				// 设置给投资人发送邮件
	        				reciveEmaiUser=invUser.getData().getUserName();
                            mailInfo.setSubject("致客户的一封信");
                            mailInfo.setContent("尊敬的客户：<br/>您好，感谢您对新华久久贷的信任与支持。您在新华久久贷投资的项目已经成交并开始计息，收益以实际投资期限为准，具体明细请查看新华久久贷网站（www.xh99d.com）-“我的账户”内相关信息。若有疑问可咨询网站在线客服，也可电话联系网站客服。（附件：投资合同）  <br/>新华久久贷 ");
                            mailInfo.setToAddress(invUser.getData().getUserEmail());
	        				if(isSignSuccess && StringUtil.isEmpty(investMap.get(invId).getCommDepositNumber())){
                                logger.info("**************************************投资人合同存证开始**************************************");
                                Map <String,String> param=new HashMap<String, String>();
                                param.put(SceneConfig.CONTRACT_TYPE, SceneConfig.LOAN_CONTRACT);
                                param.put(SceneConfig.CONTRACT_PATH, platformSignFileEnd);
                                param.put(SceneConfig.PARTYA_PARAM_NAME, invUser.getData().getUserRealName());
                                if (UserType.ENTERPRISE.getType() == userDo.getData().getUserType()){
                               	 param.put(SceneConfig.PARTYB_PARAM_NAME, enterprise.getData().getCcCompanyName());
                                }else{
                               	 param.put(SceneConfig.PARTYB_PARAM_NAME, userDo.getData().getUserRealName());
                                }
                                param.put(SceneConfig.PARTYC_PARAM_NAME, SceneConfig.PARTYC_NAME);
                                param.put(SceneConfig.CONTRACT_NAME, loanDo.getLoanNo() + "-0"+i);
                                param.put(SceneConfig.SIGN_SERVICE_IDS, signServiceIds.toString());
                                List<CertificateBean> certificates = new ArrayList<CertificateBean>();
                        		CertificateBean personBean = new CertificateBean();
                        		//投资人 一对一
                        		personBean.setName(invUser.getData().getUserRealName());
                        		personBean.setType("ID_CARD");
                        		personBean.setNumber(invUser.getData().getUserDocNo());
                        		certificates.add(personBean);
                        		//借款人 一对一
                        		personBean = new CertificateBean();
                        		if (UserType.ENTERPRISE.getType() == userDo.getData().getUserType()){
                        			personBean.setName(enterprise.getData().getCcCompanyName());
                        			personBean.setType("CODE_USC");
                        			personBean.setNumber(enterprise.getData().getCcLicenseNo());
                        		}else{
                        			personBean.setName(userDo.getData().getUserRealName());
                            		personBean.setType("ID_CARD");
                            		personBean.setNumber(userDo.getData().getUserDocNo());
                        		}
                        		certificates.add(personBean);
                        		JSONArray cer=JSONArray.fromObject(certificates);
                                param.put(SceneConfig.CERTIFICATES, cer.toString());
                        		// 场景式存证编号（证据链编号）
	                       		String C_Evid = sceneDeposit.sceneDeposit(param);
	                       		logger.info("场景式存证编号==========================="+C_Evid);
	        			        if(!StringUtil.isEmpty(C_Evid)){
	        			        	// 更新数据库
	        			        	investDao.updateCommDepositNumber(invId, C_Evid);
	        			        }
	        			        logger.info("**************************************投资人合同存证结束**************************************");
                			}
	        				i=i+1;
	        			}
	        			if(isSignSuccess && isSendMail && !StringUtil.isEmpty(mailInfo.getToAddress()) && !StringUtil.isEmpty(platformSignFileEnd)){
	        				logger.info("**************************************发送邮件**************************************");
	        				logger.info(reciveEmaiUser+"==========="+invId+"==========="+mailInfo.getToAddress()+"============"+platformSignFileEnd);
		                    mailInfo.setMailServerHost(host);
		                    mailInfo.setMailServerPort("587");
		                    mailInfo.setValidate(true);
		                    mailInfo.setUserName(user);
		                    mailInfo.setPassword(password);// 您的邮箱密码
		                    mailInfo.setFromAddress(from);
		                    try {
		                   	 InternetAddress[] address = new InternetAddress[1];
		                        address[0] = new InternetAddress(mailInfo.getToAddress());
		                        mailInfo.setTos(address);
		            		 } catch (AddressException e) {
		            			e.printStackTrace();
		            		 }
		                    
		                    // 给每个人单独发送
		                    new SendMailThread(mailInfo, platformSignFileEnd, simpleMailSender).start();
		                    logger.debug("email:" + mailInfo.getToAddress());
	        			} 
	        		}
	        		catch (Exception ex)
	        		{
	        			continue;
	        		}finally{
	        			out.close();
	        		}
	        	}
	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.error("生成电子合同及存证异常");
	            return result.setError(CommonResultCode.BIZ_ERROR, "生成电子合同及存证异常");
	        }
	        return result;
	    }
	 public static void main(String[] args) {
		Map<Integer,Integer> investUserMap = new HashMap<Integer,Integer>();
		investUserMap.put(23703, 2056);
		investUserMap.put(23704, 1002);
		investUserMap.put(23705, 404);
		investUserMap.put(23706, 404);
		investUserMap.put(23707, 404);
		investUserMap.put(23709, 735);
		investUserMap.put(23710, 9);
		investUserMap.put(0, 1843);
		Map<Integer,String> investUserEmailMap = new HashMap<Integer,String>();
		investUserEmailMap.put(2056,"xiawuqun@163.com");
		investUserEmailMap.put(1002, "441355405@qq.com");
		investUserEmailMap.put(404, "190445882@qq.com");
		investUserEmailMap.put(735, "706911570@qq.com");
		investUserEmailMap.put(9, "1921907773@qq.com");
		Map<Integer,Invest> investMap = new HashMap<Integer,Invest>();
		Invest invest = new Invest();
		invest.setId(23703);
		invest.setInTransContract("");//home/pdf/20190222/新华20190222字第0008号_23703_xh99d.pdf
		investMap.put(23703, invest);
		
		invest = new Invest();
		invest.setId(23704);
		invest.setInTransContract("");///home/pdf/20190222/新华20190222字第0008号_23704_xh99d.pdf
		investMap.put(23704, invest);
		
		invest = new Invest();
		invest.setId(23705);
		invest.setInTransContract("");///home/pdf/20190222/新华20190222字第0008号_23705_xh99d.pdf
		investMap.put(23705, invest);
		
		invest = new Invest();
		invest.setId(23706);
		invest.setInTransContract("");///home/pdf/20190222/新华20190222字第0008号_23706_xh99d.pdf
		investMap.put(23706, invest);
		
		invest = new Invest();
		invest.setId(23707);
		invest.setInTransContract("");///home/pdf/20190222/新华20190222字第0008号_23707_xh99d.pdf
		investMap.put(23707, invest);
		
		invest = new Invest();
		invest.setId(23709);
		invest.setInTransContract("");///home/pdf/20190222/新华20190222字第0008号_23709_xh99d.pdf
		investMap.put(23709, invest);
		
		invest = new Invest();
		invest.setId(23710);
		invest.setInTransContract("");///home/pdf/20190222/新华20190222字第0008号_23710_xh99d.pdf
		investMap.put(23710, invest);
		
		String dir = new SimpleDateFormat("yyyyMMdd").format(new Date())+ File.separatorChar;
		String loanName="新华20190222字第0008号";
		String platformSignFileEnd = "";
		boolean isSignSuccess = true;
		boolean isSendMail = true;
		MailSenderInfo mailInfo = new MailSenderInfo();
		Integer[] investIdArr = {23703,23704,23705,23706,23707,23709,23710};
		List<Integer> investIdList = Lists.newArrayList(0);
		for(int i=0;i<7;i++){//模拟投资7次
			investIdList.add(investIdArr[i]);
			System.out.println(investIdArr[i]+"-------------过来投资了---------------");
			for (Integer invId : investIdList) {
				int invUserId = investUserMap.get(invId);
				try {
					if (invUserId == 1843) {// 如果是出让人合同则特殊处理，只有平台和出让人签章，并且显示所有投资人列表
							platformSignFileEnd = dir + loanName + "_xh99d.pdf";// 平台签章后文件路径
							// 设置给借款人发送邮件
							// 企业用户邮箱保存在abc_company_customer中
							mailInfo.setToAddress("768802490@qq.com");
							mailInfo.setSubject("给债权出让人");
							mailInfo.setContent("尊敬的客户：<br>您好，感谢您对新华久久贷的信任与支持。您在新华久久贷（www.xh99d.com）转让的债权已成功转出，回款资金已转入您的银行存管账户。具体明细请查看网站-“我的账户”内相关信息。<br>若有疑问可咨询网站在线客服，也可电话联系网站客服。（附件：债权转让合同）<br>新华久久贷");
					} else{
						if(investMap.get(invId).getInTransContract()==null || "".equals(investMap.get(invId).getInTransContract())){
							System.out.println(invId+"--------------已经生成合同了---------------");
								platformSignFileEnd = dir + loanName + "_"+ invId + "_xh99d.pdf";// 平台签章后文件路径
								investMap.get(invId).setInTransContract(platformSignFileEnd);
						}else{
							platformSignFileEnd = investMap.get(invId).getInTransContract();
						}
						// 设置给债权受让人发送邮件
						mailInfo.setToAddress(investUserEmailMap.get(invUserId));
						mailInfo.setSubject("给债权受让人");
						mailInfo.setContent("尊敬的客户：<br>您好，感谢您对新华久久贷的信任与支持。您在新华久久贷（www.xh99d.com）受让的债权已经成功买入，具体明细请通过网站-“我的账户”内相关信息进行查看。<br> 若有疑问可咨询网站在线客服，也可电话联系网站客服。（附件：债权转让合同）   <br>新华久久贷 ");
					}
					if(isSignSuccess && isSendMail && !StringUtil.isEmpty(mailInfo.getToAddress()) && !StringUtil.isEmpty(platformSignFileEnd)){
						System.out.println("**************************************发送邮件**************************************");
						System.out.println(invId+"========="+mailInfo.getToAddress()+"======="+platformSignFileEnd);
					}
				} catch (Exception ex) {
					continue;
				}
			}
			
		}
		
	}
	 
	 /**
	  * 转让合同生成
	  * @param loanId
	  * @param loanName
	  * @param isSendMail
	  * @return
	  */
	 public BaseResult transContractGeneration(int loanId, String loanName,boolean isSendMail) {
			BaseResult result = new BaseResult();
			try {
				String dir = outFile+ new SimpleDateFormat("yyyyMMdd").format(new Date())+ File.separatorChar;

				// 判断目录是否存在，不存在创建
				File tempFile = new File(dir);
				if (!tempFile.exists()) {
					tempFile.mkdir();
				}
				TransferLoanDO loanDo = transferLoanDao.findById(loanId);
				LoanDO originLoan = loanDao.findById(loanDo.getTlOriginId());//原始项目
				InvestSearchDO searchDO = new InvestSearchDO();
				searchDO.setBidId(loanId);
				searchDO.setBidType(BidType.TRANSFER_LOAN.getType());
				if(LoanState.REPAY_COMPLETED.state==originLoan.getLoanState()){
	            	searchDO.setInvestStates(Arrays.asList(InvestState.EARN_COMPLETED.getState()));
	            }else{
	            	searchDO.setInvestStates(Arrays.asList(InvestState.EARNING.getState(),InvestState.TRANSFERED.getState()));
	            }
//				searchDO.setInvestStates(Arrays.asList(InvestState.EARNING.getState()));
				ListResult<Invest> investResult = investQueryService.queryInvestList(searchDO);
				List<Invest> investList = investResult.getData();
				if (!investResult.isSuccess() || CollectionUtils.isEmpty(investList)) {
					return result.setError(CommonResultCode.BIZ_ERROR, "投资记录查询失败");
				}
				List<Integer> investIdList = new ArrayList<Integer>();
				Map<Integer,Integer> investUserMap = new HashMap<Integer,Integer>();
				Map<Integer,Invest> investMap = new HashMap<Integer,Invest>();
				int count=1;
				for (Invest invest : investList) {
					investIdList.add(invest.getId());
					investUserMap.put(invest.getId(), invest.getUserId());
					investMap.put(invest.getId(), invest);
					if(invest.getInTransContract()!=null && !"".equals(invest.getInTransContract())){
						count=count+1;
					}
				}
				String reciveEmaiUser = "";
				String accountId = "";//签署账号
				String sealData = "";//个人印章
				// loan user
				PlainResult<UserDO> loanUser = userService.findById(loanDo.getTlUserId());
				PlainResult<CompanyCustomerDO> enterprise = companyCustomerService
						.findByUserId(loanDo.getTlUserId());
				String loanUserEmail;
				accountId = loanUser.getData().getTsignAccountId();
				sealData = loanUser.getData().getSealData();
				if (UserType.ENTERPRISE.getType() == loanUser.getData()
						.getUserType()) {
					if (StringUtil.isEmpty(accountId)) {
						OrganizeBean organizeBean = new OrganizeBean();
						organizeBean.setName(enterprise.getData().getCcCompanyName());
						organizeBean.setAgentName(enterprise.getData().getCcCorporate());
						organizeBean.setAgentIdNo(enterprise.getData().getCcDocNo());
						organizeBean.setRegType(OrganRegType.MERGE);
						organizeBean.setOrganCode(enterprise.getData().getCcLicenseNo());
						accountId = signManager.addOrganizeAccount(organizeBean);
						if (!StringUtil.isEmpty(accountId))
						{
							loanUser.getData().setTsignAccountId(accountId);
							userService.modifyInfo(loanUser.getData());
						}
					}
					//生成企业印章
					if (StringUtil.isEmpty(sealData)) 
					{
						if (!StringUtil.isEmpty(accountId))
						{
							sealData = signManager.addOrganizeTemplateSeal(accountId);
							if (!StringUtil.isEmpty(sealData)) 
							{
								loanUser.getData().setSealData(sealData);
								userService.modifyInfo(loanUser.getData());
							}
						}
					}
					loanUserEmail = enterprise.getData().getCcContactEmail();
				} else {
					if (StringUtil.isEmpty(accountId)) {
						PersonBean p = new PersonBean();
						p.setName(loanUser.getData().getUserRealName());
						p.setIdNo(loanUser.getData().getUserDocNo());
						accountId = signManager.addPersonAccount(p);
						if (!StringUtil.isEmpty(accountId)) {
							loanUser.getData().setTsignAccountId(accountId);
							userService.modifyInfo(loanUser.getData());
						}
					}
					if (StringUtil.isEmpty(sealData))
					{
						if (!StringUtil.isEmpty(accountId))
						{
							sealData = signManager.addPersonTemplateSeal(accountId);
							if (!StringUtil.isEmpty(sealData))
							{
								loanUser.getData().setSealData(sealData);
								userService.modifyInfo(loanUser.getData());
							}
						}
					}
					loanUserEmail = loanUser.getData().getUserEmail();
				}
				loanUser = userService.findById(loanDo.getTlUserId());// 重新查询出让人
				//userSet.add(loanDo.getTlUserId());
				investIdList.add(0);//设定几款人对应为0
				investUserMap.put(0, loanDo.getTlUserId());
				// tsign process
				FileDigestSignResult fileResult = new FileDigestSignResult();
				fileResult.setErrCode(99);
				
				// invest user sign
				// String loanUserSignFile = contractPath;
				PersonBean pu;
				PlainResult<UserDO> invUser;
				String loanUserSignFileNew = "";
				String userSignFileEnd = "";
				String platformSignFileEnd = "";
				OutputStream out;
				int i = count;
				boolean isSignSuccess = false;//是否签名成功
				boolean isSendMailed = false;//是否发送过邮件，避免重复发送邮件
				MailSenderInfo mailInfo = new MailSenderInfo();
				for (Integer invId : investIdList) {
					int invUserId = investUserMap.get(invId);
					String loanUserSignFile = dir + loanName + "_00_" + invId+ ".pdf";// 原始洁净版pdf文件路径
					out = new FileOutputStream(loanUserSignFile);
					StringBuffer signServiceIds = new StringBuffer();
					try {
						if (invUserId == loanDo.getTlUserId()) {// 如果是出让人合同则特殊处理，只有平台和出让人签章，并且显示所有投资人列表
							BaseResult exportResu = exportPdfService.exportObligatoryRight(loanId, 0,invId, out,0);
							if (exportResu.isSuccess()) {
								userSignFileEnd = dir + loanName + "_02_"+ invId + ".pdf";// 出让人签章后文件路径
								platformSignFileEnd = dir + loanName + "_xh99d.pdf";// 平台签章后文件路径
								// 出让人签章
								fileResult = signManager.userPersonSignByFileXY(loanUserSignFile, userSignFileEnd, 
										loanUser.getData().getTsignAccountId(),"甲方（出让人）", loanUser.getData().getSealData(),270,0,100);
								if (0 == fileResult.getErrCode()) {
									signServiceIds.append(fileResult.getSignServiceId()).append(",");
									// 平台签章
									fileResult = signManager.platformSignByFileXY(userSignFileEnd, platformSignFileEnd,"丙方（下称居间方）",300,-30,120);
									if (0 == fileResult.getErrCode()) {
										isSignSuccess = true;
										signServiceIds.append(fileResult.getSignServiceId()).append(",");
										//所有投资全部签署
		                          		Map<String, String> resultMap = this.multiInvestorElectronicSign(investMap, dir, loanName, platformSignFileEnd);
		                          		if(!StringUtil.isEmpty(resultMap.get("signedPdf"))){
		                          			platformSignFileEnd = resultMap.get("signedPdf");
		                          		}
			                       		 if(!StringUtil.isEmpty(resultMap.get("signServiceIds"))){
			                       			signServiceIds.append(resultMap.get("signServiceIds"));
			                       		 }
										// 更新数据库
										transferLoanDao.updateContractPath(loanId,platformSignFileEnd);
										// 设置给借款人发送邮件
										reciveEmaiUser = loanUser.getData().getUserName();
										// 企业用户邮箱保存在abc_company_customer中
										mailInfo.setToAddress(loanUserEmail);
										mailInfo.setSubject("给债权出让人");
										mailInfo.setContent("尊敬的客户：<br>您好，感谢您对新华久久贷的信任与支持。您在新华久久贷（www.xh99d.com）转让的债权已成功转出，回款资金已转入您的银行存管账户。具体明细请查看网站-“我的账户”内相关信息。<br>若有疑问可咨询网站在线客服，也可电话联系网站客服。（附件：债权转让合同）<br>新华久久贷");
									}
								}
							} 
							if(isSignSuccess && StringUtil.isEmpty(loanDo.getContractDepositNumber())){
								 logger.info("**************************************出让人合同存证开始**************************************");
	                             Map <String,String> param=new HashMap<String, String>();
	                             param.put(SceneConfig.CONTRACT_TYPE, SceneConfig.TRANS_CONTRACT);
	                             param.put(SceneConfig.CONTRACT_PATH, platformSignFileEnd);
	                             List<Integer> ids = new ArrayList<Integer>();
	                             for (Invest invest : investList) {
	                           	  ids.add(invest.getUserId());
	                             }
	                             ListResult<UserDO> usersResult = userService.findByList(ids);
	                             StringBuffer userRealNames = new StringBuffer();
	                             for(UserDO user : usersResult.getData()){
	                           	  userRealNames.append(user.getUserRealName()).append(",");
	                             }
	                             param.put(SceneConfig.PARTYB_PARAM_NAME, userRealNames.toString().substring(0, userRealNames.toString().length()-1));
	                             if (UserType.ENTERPRISE.getType() == loanUser.getData().getUserType()){
	                            	 param.put(SceneConfig.PARTYA_PARAM_NAME, enterprise.getData().getCcCompanyName());
	                             }else{
	                            	 param.put(SceneConfig.PARTYA_PARAM_NAME, loanUser.getData().getUserRealName());
	                             }
	                             param.put(SceneConfig.PARTYC_PARAM_NAME, SceneConfig.PARTYC_NAME);
	                             param.put(SceneConfig.CONTRACT_NAME, loanDo.getTlLoanNo());
	                             param.put(SceneConfig.SIGN_SERVICE_IDS, signServiceIds.toString());
	                             List<CertificateBean> certificates = new ArrayList<CertificateBean>();
                         		CertificateBean personBean = null;
                         		//投资人 一对多
                         		for(UserDO user : usersResult.getData()){
                         			personBean = new CertificateBean();
                         			personBean.setName(user.getUserRealName());
                         			personBean.setType("ID_CARD");
                         			personBean.setNumber(user.getUserDocNo());
                         			certificates.add(personBean);
                         		}
                         		//出让人 一对多
                         		personBean = new CertificateBean();
                         		if (UserType.ENTERPRISE.getType() == loanUser.getData().getUserType()){
                         			personBean.setName(enterprise.getData().getCcCompanyName());
                         			personBean.setType("CODE_USC");
                         			personBean.setNumber(enterprise.getData().getCcLicenseNo());
                         		}else{
                         			personBean.setName(loanUser.getData().getUserRealName());
                             		personBean.setType("ID_CARD");
                             		personBean.setNumber(loanUser.getData().getUserDocNo());
                         		}
                         		certificates.add(personBean);
                         		JSONArray cer=JSONArray.fromObject(certificates);
                         		param.put(SceneConfig.CERTIFICATES, cer.toString());
                         		// 场景式存证编号（证据链编号）
                         		String C_Evid = sceneDeposit.sceneDeposit(param);
                         		logger.info("场景式存证编号==========================="+C_Evid);
 	        			        if(!StringUtil.isEmpty(C_Evid)){
 	        			        	// 更新数据库
 	        			        	transferLoanDao.updateContractDepositNumber(loanId,C_Evid);
 	        			        }
 	        			        logger.info("**************************************出让人合同存证结束**************************************");
							}
						} else{
							invUser = userService.findById(invUserId);
							if(investMap.get(invId).getInTransContract()==null || "".equals(investMap.get(invId).getInTransContract())){
								BaseResult exportResu = exportPdfService.exportObligatoryRight(loanId, invUserId,invId, out,i);
								if (exportResu.isSuccess()) {
									loanUserSignFileNew = dir + loanName+ "_01_" + invId + ".pdf";// 投资人签章后文件路径
									userSignFileEnd = dir + loanName + "_02_"+ invId + ".pdf";// 出让人签章后文件路径
									platformSignFileEnd = dir + loanName + "_"+ invId + "_xh99d.pdf";// 平台签章后文件路径
									accountId = invUser.getData().getTsignAccountId();//签署账号
		        					sealData = invUser.getData().getSealData();//个人印章
		        					if (StringUtil.isEmpty(accountId))
			                        {
			                			pu = new PersonBean();
			                			pu.setName(invUser.getData().getUserRealName());
			                			pu.setIdNo(invUser.getData().getUserDocNo());
			                        	
			                			accountId = signManager.addPersonAccount(pu);
			                			if (!StringUtil.isEmpty(accountId))
			                        	{
			                				invUser.getData().setTsignAccountId(accountId);
		    	                    		userService.modifyInfo(invUser.getData());
			                        	}
			                        }
		        					if (StringUtil.isEmpty(sealData))
		        	                {
		        						if (!StringUtil.isEmpty(accountId))
		        	                	{
		        	                		sealData = signManager.addPersonTemplateSeal(accountId);
		        	                		if (!StringUtil.isEmpty(sealData))
		        	                    	{
		        	                    		invUser.getData().setSealData(sealData);
		        	                    		userService.modifyInfo(invUser.getData());
		        	                    	}
		        	                	}
		        	                }
									// 投资人签章
									fileResult = signManager.userPersonSignByFileXY(loanUserSignFile,loanUserSignFileNew,
											accountId, "乙方（受让人）", sealData,270,0,100);
									if (0 == fileResult.getErrCode()) {
										signServiceIds.append(fileResult.getSignServiceId()).append(",");
										// 出让人签章
										fileResult = signManager.userPersonSignByFileXY(loanUserSignFileNew,userSignFileEnd,
													loanUser.getData().getTsignAccountId(),"甲方（出让人）", loanUser.getData().getSealData(),270,0,100);
	
										if (0 == fileResult.getErrCode()) {
											signServiceIds.append(fileResult.getSignServiceId()).append(",");
											// 平台签章
											fileResult = signManager.platformSignByFileXY(userSignFileEnd,platformSignFileEnd,"丙方（下称居间方）",320,-40,120);
	
											if (0 == fileResult.getErrCode()) {
												isSignSuccess = true;
												signServiceIds.append(fileResult.getSignServiceId()).append(",");
												// 更新数据库
												investDao.updateTransContractPath(invId,platformSignFileEnd);
											}
										}
									}
								}
							}else{
								logger.info("====================受让人合同已经生成过了=======================");
								isSignSuccess = true;
								isSendMailed = true;//已经发送过
								platformSignFileEnd = investMap.get(invId).getInTransContract();
								logger.info(invUser.getData().getUserName()+"==========="+invId+"============"+platformSignFileEnd);
							}
							// 设置给债权受让人发送邮件
							reciveEmaiUser = invUser.getData().getUserName();
							mailInfo.setToAddress(invUser.getData().getUserEmail());
							mailInfo.setSubject("给债权受让人");
							mailInfo.setContent("尊敬的客户：<br>您好，感谢您对新华久久贷的信任与支持。您在新华久久贷（www.xh99d.com）受让的债权已经成功买入，具体明细请通过网站-“我的账户”内相关信息进行查看。<br> 若有疑问可咨询网站在线客服，也可电话联系网站客服。（附件：债权转让合同）   <br>新华久久贷 ");
							if(isSignSuccess && StringUtil.isEmpty(investMap.get(invId).getTransDepositNumber())){
								logger.info("**************************************投资人合同存证开始**************************************");
                                Map <String,String> param=new HashMap<String, String>();
                                param.put(SceneConfig.CONTRACT_TYPE, SceneConfig.TRANS_CONTRACT);
                                param.put(SceneConfig.CONTRACT_PATH, platformSignFileEnd);
                                param.put(SceneConfig.PARTYB_PARAM_NAME, invUser.getData().getUserRealName());
                                if (UserType.ENTERPRISE.getType() == loanUser.getData().getUserType()){
                               	 param.put(SceneConfig.PARTYA_PARAM_NAME, enterprise.getData().getCcCompanyName());
                                }else{
                               	 param.put(SceneConfig.PARTYA_PARAM_NAME, loanUser.getData().getUserRealName());
                                }
                                param.put(SceneConfig.PARTYC_PARAM_NAME, SceneConfig.PARTYC_NAME);
                                param.put(SceneConfig.CONTRACT_NAME, loanDo.getTlLoanNo()+"-0"+i);
                                param.put(SceneConfig.SIGN_SERVICE_IDS, signServiceIds.toString());
                                List<CertificateBean> certificates = new ArrayList<CertificateBean>();
                          		CertificateBean personBean = new CertificateBean();
                          		//投资人 一对一
                      			personBean.setName(invUser.getData().getUserRealName());
                      			personBean.setType("ID_CARD");
                      			personBean.setNumber(invUser.getData().getUserDocNo());
                      			certificates.add(personBean);
                          		//出让人 一对一
                          		personBean = new CertificateBean();
                          		if (UserType.ENTERPRISE.getType() == loanUser.getData().getUserType()){
                          			personBean.setName(enterprise.getData().getCcCompanyName());
                          			personBean.setType("CODE_USC");
                          			personBean.setNumber(enterprise.getData().getCcLicenseNo());
                          		}else{
                          			personBean.setName(loanUser.getData().getUserRealName());
                              		personBean.setType("ID_CARD");
                              		personBean.setNumber(loanUser.getData().getUserDocNo());
                          		}
                          		certificates.add(personBean);
                          		JSONArray cer=JSONArray.fromObject(certificates);
                          		param.put(SceneConfig.CERTIFICATES, cer.toString());
                          		// 场景式存证编号（证据链编号）
                          		String C_Evid = sceneDeposit.sceneDeposit(param);
                          		logger.info("场景式存证编号==========================="+C_Evid);
  	        			        if(!StringUtil.isEmpty(C_Evid)){
  	        			        	// 更新数据库
  	        			        	investDao.updateTransDepositNumber(invId,C_Evid);
  	        			        }
  	        			        logger.info("**************************************投资人合同存证结束**************************************");
							}
							i=i+1;
						}
						if(!isSendMailed && isSignSuccess && isSendMail && !StringUtil.isEmpty(mailInfo.getToAddress()) && !StringUtil.isEmpty(platformSignFileEnd)){
							logger.info("**************************************发送邮件**************************************");
							logger.info(reciveEmaiUser+"==========="+invId+"==========="+mailInfo.getToAddress()+"============"+platformSignFileEnd);
							mailInfo.setMailServerHost(host);
							mailInfo.setMailServerPort("587");
							mailInfo.setValidate(true);
							mailInfo.setUserName(user);
							mailInfo.setPassword(password);// 您的邮箱密码
							mailInfo.setFromAddress(from);
							try {
								InternetAddress[] address = new InternetAddress[1];
								address[0] = new InternetAddress(
										mailInfo.getToAddress());
								mailInfo.setTos(address);
							} catch (AddressException e) {
								e.printStackTrace();
							}
							new SendMailThread(mailInfo,platformSignFileEnd,simpleMailSender).start();
						}
						isSendMailed=true;
					} catch (Exception ex) {
						continue;
					} finally {
						out.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("生成电子合同及存证异常");
	            return result.setError(CommonResultCode.BIZ_ERROR, "生成电子合同及存证异常");
			}

			return result;
		}
 	/**
 	 * 多个投资人循环签章（以投资人的用户名作为关键字）
 	 * @param investMap 投资集合key--investid value---invest
 	 * @param dir 文件目录
 	 * @param loanName 项目名称
 	 * @param srcpdf 平台和借款人签署后的pdf
 	 * @return
 	 */
	 private Map<String,String> multiInvestorElectronicSign (Map<Integer,Invest> investMap,String dir,String loanName,String srcpdf){
		 Map<String,String> map = new HashMap<String, String>();
		 StringBuffer signServiceIds = new StringBuffer();
		 Set <Integer> userNameSet = new LinkedHashSet<Integer>();
		 String signedPdf = "";
		 PlainResult<UserDO> invUser;
		 PersonBean pu;
		 String accountId="";
		 String sealData="";
		 FileDigestSignResult fileResult = new FileDigestSignResult();
         fileResult.setErrCode(99);
		 for (Map.Entry<Integer, Invest> entry : investMap.entrySet()) {
			 invUser = userService.findById(entry.getValue().getUserId());
			 boolean flag = userNameSet.add(invUser.getData().getUserId());
			 if(flag){
				 signedPdf = dir+loanName+"_for_"+entry.getKey()+".pdf";
				 accountId = invUser.getData().getTsignAccountId();//签署账号
				 sealData = invUser.getData().getSealData();//个人印章
				 if (StringUtil.isEmpty(accountId))
				 {
					 pu = new PersonBean();
					 pu.setName(invUser.getData().getUserRealName());
					 pu.setIdNo(invUser.getData().getUserDocNo());
					 
					 accountId = signManager.addPersonAccount(pu);
					 if (!StringUtil.isEmpty(accountId))
					 {
						 invUser.getData().setTsignAccountId(sealData);
						 userService.modifyInfo(invUser.getData());
					 }
				 }
				 if (StringUtil.isEmpty(sealData))
				 {
					 if (!StringUtil.isEmpty(accountId))
					 {
						 sealData = signManager.addPersonTemplateSeal(accountId);
						 if (!StringUtil.isEmpty(sealData))
						 {
							 invUser.getData().setSealData(sealData);
							 userService.modifyInfo(invUser.getData());
						 }
					 }
				 }
				 fileResult = signManager.userPersonSignByFileXY(srcpdf, signedPdf, 
						 accountId, invUser.getData().getUserName(), sealData,0,0,60);
				 if (0 == fileResult.getErrCode()){
					 signServiceIds.append(fileResult.getSignServiceId()).append(",");
				 }
				 srcpdf = signedPdf;
			 }
		 }
		 map.put("signedPdf", signedPdf);
		 if(!StringUtil.isEmpty(signServiceIds.toString())){
			 map.put("signServiceIds", signServiceIds.toString().substring(0, signServiceIds.toString().length()-1));
		 }
		 return map;
	 }
	 /**
	     * 发送邮件线程类
	     * 
	     */
	    static class SendMailThread extends Thread {
	        private MailSenderInfo          mailInfo;
	        private String                  contractPath;
	        private SimpleMailSenderService simpleMailSender;

	        public SendMailThread(MailSenderInfo mailInfo, String contractPath, SimpleMailSenderService simpleMailSender) {
	            this.mailInfo = mailInfo;
	            this.contractPath = contractPath;
	            this.simpleMailSender = simpleMailSender;
	        }

	        @Override
	        public void run() {
	        	simpleMailSender.sendHtmlMail(mailInfo, contractPath);// 发送html格式
	        }
	    }
}
