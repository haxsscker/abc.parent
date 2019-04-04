package com.autoserve.abc.service.message.mail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.exportpdf.ExportPdfService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserAssessService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.message.deposit.bean.CertificateBean;
import com.autoserve.abc.service.message.deposit.constant.SceneConfig;
import com.autoserve.abc.service.message.deposit.service.ContractGenerationService;
import com.autoserve.abc.service.message.deposit.service.SceneDeposit;
import com.autoserve.abc.service.message.sms.SendMsgServiceImpl;
import com.autoserve.abc.service.message.tsign.SignManager;
import com.timevale.esign.sdk.tech.bean.OrganizeBean;
import com.timevale.esign.sdk.tech.bean.PersonBean;
import com.timevale.esign.sdk.tech.bean.result.FileDigestSignResult;
import com.timevale.esign.sdk.tech.impl.constants.OrganRegType;

public class SendMailServiceImpl implements SendMailService {
//    @Resource
//    private ExportPdfService        exportPdfService;
//    @Resource
//    private InvestQueryService      investQueryService;
//    @Resource
//    private UserService             userService;
    @Resource
    private SimpleMailSenderService simpleMailSender;
//    @Resource
//    private LoanDao                 loanDao;
//    @Resource
//    private TransferLoanDao         transferLoanDao;
    @Resource
    private SysConfigService        sysConfigService;
//    @Resource
//    private CompanyCustomerService  companyCustomerService;
//    @Resource
//    private SignManager             signManager;
//    @Resource
//    private UserAssessService       userAssessService;  
//    @Resource
//    private InvestDao               investDao;
//    @Resource
//    private SceneDeposit             sceneDeposit;
    @Resource
    private ContractGenerationService             contractGenerationService;
    
    private static final Logger     logger          = LoggerFactory.getLogger(SendMsgServiceImpl.class);
    private static final Properties emptyProperties = new Properties();

    /**
     *
     */
    private String                  user            = "tiuwer@163.com";
    private String                  host            = "smtp.163.com";
    private String                  password        = "shaoye5334";
    private String                  from            = "tiuwer@qq.com";
    private String                  outFile         = "D:/";
    /**
     * 使用加密的方式,利用465端口进行传输邮件,开启ssl
     * @param toAddress    为收件人邮箱
     * @param mailContent    发送的消息
     * @param theme    发送的消息主题
     */
    private boolean sendSslEmil(String toAddress, String mailContent, String theme) {
        try {
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            //设置邮件会话参数
            Properties props = new Properties();
            //邮箱的发送服务器地址
            props.setProperty("mail.smtp.host", host);
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            //邮箱发送服务器端口,这里设置为465端口
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");
            //获取到邮箱会话,利用匿名内部类的方式,将发送者邮箱用户名和密码授权给jvm
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            });
            //通过会话,得到一个邮件,用于发送
            Message msg = new MimeMessage(session);
            //设置发件人
            msg.setFrom(new InternetAddress(from));
            //设置收件人,to为收件人,cc为抄送,bcc为密送
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress, false));
//            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(toAddress, false));
//            msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(toAddress, false));
            msg.setSubject(theme);
            //设置邮件消息
            msg.setText(mailContent);
            //设置发送的日期
            msg.setSentDate(new Date());
            
            //调用Transport的send方法去发送邮件
            Transport.send(msg);

        } catch (Exception e) {
        	logger.info("邮箱发送失败！", e);
            return false;
        }
    	return true;

    }
    @Override
    public boolean sendYzm2Mail(String toAddress, String mailContent, String theme) {
    	return sendSslEmil(toAddress, mailContent, theme);
        /*try {
            Session session = Session.getInstance(emptyProperties);
            Message email = new MimeMessage(session);
            // 发件人在配置文件里面配置
            Address fromAddress = new InternetAddress(user, from);
            email.setFrom(fromAddress);
            email.setSubject(theme);
            // 设置邮件的具体内容
            email.setContent(mailContent, "text/html;charset=UTF-8");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, user, password);
            Address[] addresses = { new InternetAddress(toAddress) };
            email.setRecipients(Message.RecipientType.TO, addresses);
            transport.sendMessage(email, addresses);// 发送给多个人
        } catch (Exception e) {
            logger.info("邮箱发送失败！", e);
            return false;
        }
        return true;*/
    }

	@Override
	public BaseResult sendMailToCreditoUser(int loanId, String loanName) {
		return contractGenerationService.transContractGeneration(loanId, loanName, true);
		/*BaseResult result = new BaseResult();
		try {
			String dir = outFile+ new SimpleDateFormat("yyyyMMdd").format(new Date())+ File.separatorChar;

			// 判断目录是否存在，不存在创建
			File tempFile = new File(dir);
			if (!tempFile.exists()) {
				tempFile.mkdir();
			}

			InvestSearchDO searchDO = new InvestSearchDO();
			searchDO.setBidId(loanId);
			searchDO.setBidType(BidType.TRANSFER_LOAN.getType());
			searchDO.setInvestStates(Arrays.asList(InvestState.EARNING.getState()));
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
			String accountId = "";//签署账号
			String sealData = "";//个人印章
			// loan user
			TransferLoanDO loanDo = transferLoanDao.findById(loanId);
			PlainResult<UserDO> loanUser = userService.findById(loanDo.getTlUserId());

			PlainResult<CompanyCustomerDO> enterprise = companyCustomerService
					.findByUserId(loanDo.getTlUserId());
			String loanUserEmail;
			if (UserType.ENTERPRISE.getType() == loanUser.getData()
					.getUserType()) {
				if (null == loanUser.getData().getTsignAccountId()
						|| loanUser.getData().getTsignAccountId().isEmpty()) {
					OrganizeBean organizeBean = new OrganizeBean();
					organizeBean.setName(enterprise.getData().getCcCompanyName());
					organizeBean.setAgentName(enterprise.getData().getCcCorporate());
					organizeBean.setAgentIdNo(enterprise.getData().getCcDocNo());
					organizeBean.setRegType(OrganRegType.MERGE);
					organizeBean.setOrganCode(enterprise.getData().getCcLicenseNo());
					accountId = signManager.addOrganizeAccount(organizeBean);
					if (null != accountId && !accountId.isEmpty())
					{
						loanUser.getData().setTsignAccountId(accountId);
						userService.modifyInfo(loanUser.getData());
//            		userAssessService.updateUserTsignId(accountId, loanDo.getLoanUserId());
					}
				}
				//生成企业印章
				if (null == loanUser.getData().getSealData() || loanUser.getData().getSealData().isEmpty())
				{
					if (null != accountId && !accountId.isEmpty())
					{
						sealData = signManager.addOrganizeTemplateSeal(accountId);
						if (null != sealData && !sealData.isEmpty())
						{
							loanUser.getData().setSealData(sealData);
							userService.modifyInfo(loanUser.getData());
						}
					}
				}
				loanUserEmail = enterprise.getData().getCcContactEmail();
			} else {
				if (null == loanUser.getData().getTsignAccountId()|| loanUser.getData().getTsignAccountId().isEmpty()) {
					PersonBean p = new PersonBean();
					p.setName(loanUser.getData().getUserRealName());
					p.setIdNo(loanUser.getData().getUserDocNo());
					accountId = signManager.addPersonAccount(p);
					if (null != accountId && !accountId.isEmpty()) {
						loanUser.getData().setTsignAccountId(accountId);
						userService.modifyInfo(loanUser.getData());
					}
				}
				if (null == loanUser.getData().getSealData() || loanUser.getData().getSealData().isEmpty())
				{
					if (null != accountId && !accountId.isEmpty())
					{
						sealData = signManager.addPersonTemplateSeal(accountId);
						if (null != sealData && !sealData.isEmpty())
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
			boolean isSignSuccess = false;
			MailSenderInfo mailInfo = new MailSenderInfo();
			for (Integer invId : investIdList) {
				int invUserId = investUserMap.get(invId);
				String loanUserSignFile = dir + loanName + "_00_" + invId+ ".pdf";// 原始洁净版pdf文件路径
				out = new FileOutputStream(loanUserSignFile);
				try {
					if (invUserId == loanDo.getTlUserId()) {// 如果是出让人合同则特殊处理，只有平台和出让人签章，并且显示所有投资人列表
						BaseResult exportResu = exportPdfService.exportObligatoryRight(loanId, 0,invId, out,0);
						if (exportResu.isSuccess()) {
							StringBuffer signServiceIds = new StringBuffer();
							userSignFileEnd = dir + loanName + "_02_"+ invId + ".pdf";// 出让人签章后文件路径
							platformSignFileEnd = dir + loanName + "_xh99d.pdf";// 平台签章后文件路径
							// 出让人签章
							fileResult = signManager.userPersonSignByFile(loanUserSignFile, userSignFileEnd, 
									loanUser.getData().getTsignAccountId(),"甲方（出让人）", loanUser.getData().getSealData());
							if (0 == fileResult.getErrCode()) {
								signServiceIds.append(fileResult.getSignServiceId()).append(",");
								// 平台签章
								fileResult = signManager.platformSignByFile(userSignFileEnd, platformSignFileEnd,"丙方（下称居间方）");
								if (0 == fileResult.getErrCode()) {
									isSignSuccess = true;
									signServiceIds.append(fileResult.getSignServiceId()).append(",");
									// 更新数据库
									transferLoanDao.updateContractPath(loanId,platformSignFileEnd);
									// 设置给借款人发送邮件
									// 企业用户邮箱保存在abc_company_customer中
									mailInfo.setToAddress(loanUserEmail);
									mailInfo.setSubject("给债权出让人");
									mailInfo.setContent("尊敬的客户：<br>您好，感谢您对新华久久贷的信任与支持。您在新华久久贷（www.xh99d.com）转让的债权已成功转出，回款资金已转入您的银行存管账户。具体明细请查看网站-“我的账户”内相关信息。<br>若有疑问可咨询网站在线客服，也可电话联系网站客服。（附件：债权转让合同）<br>新华久久贷");
									
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
//		  	               				FutureTask<String> futureTask = new FutureTask<String>(new SceneDepositThread<String>(param,sceneDeposit));
//		  	               				ExecutorService service = Executors.newSingleThreadExecutor();
//		  	           			        service.submit(futureTask);
//		  	        			        if (futureTask.isDone() && !futureTask.isCancelled()) {
//		  	        			        	C_Evid = futureTask.get();
//		  	        			        }
		  	        			        if(!StringUtil.isEmpty(C_Evid)){
		  	        			        	// 更新数据库
		  	        			        	transferLoanDao.updateContractDepositNumber(loanId,C_Evid);
		  	        			        }
//		  	           			        service.shutdown();
		  	        			        logger.info("**************************************出让人合同存证结束**************************************");
								}
							}
						} 
					} else if(investMap.get(invId).getInTransContract()==null || "".equals(investMap.get(invId).getInTransContract())){
						BaseResult exportResu = exportPdfService.exportObligatoryRight(loanId, invUserId,invId, out,i);
						if (exportResu.isSuccess()) {
							loanUserSignFileNew = dir + loanName+ "_01_" + invId + ".pdf";// 投资人签章后文件路径
							userSignFileEnd = dir + loanName + "_02_"+ invId + ".pdf";// 出让人签章后文件路径
							platformSignFileEnd = dir + loanName + "_"+ invId + "_xh99d.pdf";// 平台签章后文件路径
							StringBuffer signServiceIds = new StringBuffer();
							invUser = userService.findById(invUserId);
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
	                				invUser.getData().setSealData(sealData);
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
							fileResult = signManager.userPersonSignByFile(loanUserSignFile,loanUserSignFileNew,
									accountId, "乙方（受让人）", sealData);
							if (0 == fileResult.getErrCode()) {
								signServiceIds.append(fileResult.getSignServiceId()).append(",");
								// 出让人签章
								fileResult = signManager.userPersonSignByFile(loanUserSignFileNew,userSignFileEnd,
											loanUser.getData().getTsignAccountId(),"甲方（出让人）", loanUser.getData().getSealData());

								if (0 == fileResult.getErrCode()) {
									signServiceIds.append(fileResult.getSignServiceId()).append(",");
									// 平台签章
									fileResult = signManager.platformSignByFile(userSignFileEnd,platformSignFileEnd,"丙方（下称居间方）");

									if (0 == fileResult.getErrCode()) {
										isSignSuccess = true;
										signServiceIds.append(fileResult.getSignServiceId()).append(",");
										// 更新数据库
										investDao.updateTransContractPath(invId,platformSignFileEnd);
										// 设置给债权受让人发送邮件
										mailInfo.setToAddress(invUser.getData().getUserEmail());
										mailInfo.setSubject("给债权受让人");
										mailInfo.setContent("尊敬的客户：<br>您好，感谢您对新华久久贷的信任与支持。您在新华久久贷（www.xh99d.com）受让的债权已经成功买入，具体明细请通过网站-“我的账户”内相关信息进行查看。<br> 若有疑问可咨询网站在线客服，也可电话联系网站客服。（附件：债权转让合同）   <br>新华久久贷 ");
									}
								}
							}
							if(isSignSuccess){
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
//  	               				FutureTask<String> futureTask = new FutureTask<String>(new SceneDepositThread<String>(param,sceneDeposit));
//  	               				ExecutorService service = Executors.newSingleThreadExecutor();
//  	           			        service.submit(futureTask);
//  	        			        if (futureTask.isDone() && !futureTask.isCancelled()) {
//  	        			        	C_Evid = futureTask.get();
//  	        			        }
  	        			        if(!StringUtil.isEmpty(C_Evid)){
  	        			        	// 更新数据库
  	        			        	investDao.updateTransDepositNumber(invId,C_Evid);
  	        			        }
//  	           			        service.shutdown();
  	        			        logger.info("**************************************投资人合同存证结束**************************************");
							}
						}
						i=i+1;
					}
					if(isSignSuccess){
						logger.info("**************************************发送邮件**************************************");
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
				} catch (Exception ex) {
					continue;
				} finally {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;*/
	}

    public void existsDelete(String dirPath,String fileName) {
		File pathFile = new File(dirPath);
		if(!pathFile.exists() || pathFile.isFile()) {
			return;
		}
		for(File file:pathFile.listFiles()) {
			if(file.isFile() && fileName.equals(file.getName())) {
				file.delete();
				break;
			}
		}
	}
    
    @Override
    public BaseResult sendMailToInvestUser(int loanId, String loanName) {
    	return contractGenerationService.investContractGeneration(loanId, loanName, true);
        /*BaseResult result = new BaseResult();
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
//            searchDO.setInvestStates(Arrays.asList(InvestState.EARNING.getState()));
            ListResult<Invest> investResult = investQueryService.queryInvestList(searchDO);
            List<Invest> investList = investResult.getData();
            if (!investResult.isSuccess() || CollectionUtils.isEmpty(investList)) {
                return result.setError(CommonResultCode.BIZ_ERROR, "投资记录查询失败");
            }
            List<Integer> investIdList = new ArrayList<Integer>();
			Map<Integer,Integer> investUserMap = new HashMap<Integer,Integer>();
			for (Invest invest : investList) {
				investIdList.add(invest.getId());
				investUserMap.put(invest.getId(), invest.getUserId());
			}
            
            //loan user
			String accountId = "";//签署账号
			String sealData = "";//个人印章
            PlainResult<UserDO> userDo = userService.findById(loanDo.getLoanUserId());
        	PlainResult<CompanyCustomerDO> enterprise = companyCustomerService.findByUserId(loanDo.getLoanUserId());
        	String loanUserEmail;
        	if (UserType.ENTERPRISE.getType() == userDo.getData().getUserType()){
                if (null == userDo.getData().getTsignAccountId() || userDo.getData().getTsignAccountId().isEmpty())
                {
            		OrganizeBean organizeBean = new OrganizeBean();
            		organizeBean.setName(enterprise.getData().getCcCompanyName());
            		organizeBean.setAgentName(enterprise.getData().getCcCorporate());
            		organizeBean.setAgentIdNo(enterprise.getData().getCcDocNo());
            		organizeBean.setRegType(OrganRegType.MERGE);
            		organizeBean.setOrganCode(enterprise.getData().getCcLicenseNo());
                	accountId = signManager.addOrganizeAccount(organizeBean);
                	if (null != accountId && !accountId.isEmpty())
                	{
                		userDo.getData().setTsignAccountId(accountId);
                		userService.modifyInfo(userDo.getData());
//                		userAssessService.updateUserTsignId(accountId, loanDo.getLoanUserId());
                	}
                }
                //生成企业印章
                if (null == userDo.getData().getSealData() || userDo.getData().getSealData().isEmpty())
                {
                	if (null != accountId && !accountId.isEmpty())
                	{
                		sealData = signManager.addOrganizeTemplateSeal(accountId);
                    	if (null != sealData && !sealData.isEmpty())
                    	{
                    		userDo.getData().setSealData(sealData);
                    		userService.modifyInfo(userDo.getData());
                    	}
                	}
                }
                loanUserEmail = enterprise.getData().getCcContactEmail();
        	}else{
            	if (null == userDo.getData().getTsignAccountId() || userDo.getData().getTsignAccountId().isEmpty())
                {
                	PersonBean p = new PersonBean();
            		p.setName(userDo.getData().getUserRealName());
                	p.setIdNo(userDo.getData().getUserDocNo());
                	accountId = signManager.addPersonAccount(p);
                	if (null != accountId && !accountId.isEmpty())
                	{
                		userDo.getData().setTsignAccountId(accountId);
                		userService.modifyInfo(userDo.getData());
//                		userAssessService.updateUserTsignId(accountId, loanDo.getLoanUserId());
                	}
                }
            	//生成个人印章
            	if (null == userDo.getData().getSealData() || userDo.getData().getSealData().isEmpty())
                {
                	if (null != accountId && !accountId.isEmpty())
                	{
                		sealData = signManager.addPersonTemplateSeal(accountId);
                    	if (null != sealData && !sealData.isEmpty())
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
        			if(invUserId==loanDo.getLoanUserId()){//如果是借款人合同则特殊处理，只有平台和借款人签章，并且显示所有投资人列表
        				BaseResult exportResu = exportPdfService.exportBorrowMoney(loanId,0,invId, out,0);
        				if(exportResu.isSuccess()){
        					StringBuffer signServiceIds = new StringBuffer();
	        				userSignFileEnd = dir + loanName + "_02_" + invId + ".pdf";//借款人签章后文件路径
	                		platformSignFileEnd = dir + loanName + ".pdf";//平台签章后文件路径
	                		//借款人签章
	                    	fileResult = signManager.userPersonSignByFile(loanUserSignFile, userSignFileEnd, 
	                    			userDo.getData().getTsignAccountId(), "乙方：（盖章）", userDo.getData().getSealData());
	                    	 if (0 == fileResult.getErrCode()){
	                    		 signServiceIds.append(fileResult.getSignServiceId()).append(",");
	                     		//平台签章
	                          	fileResult = signManager.platformSignByFile(userSignFileEnd, platformSignFileEnd, "丙方：（盖章）");
	                          	if (0 == fileResult.getErrCode()){
	                          		isSignSuccess = true;
	                          		signServiceIds.append(fileResult.getSignServiceId()).append(",");
	                          		  // 更新数据库
	                                  loanDao.updateContractPath(loanId, platformSignFileEnd);
	                              	
	                                  // 设置给借款人发送邮件
	                                  //企业用户邮箱保存在abc_company_customer中
	                                  mailInfo.setToAddress(loanUserEmail);
	                                  mailInfo.setSubject("致客户的一封信");
	                                  mailInfo
	                                          .setContent("尊敬的客户：<br/>您好，感谢您对新华久久贷的信任与支持。您通过新华久久贷网站（www.xh99d.com）融资项目已经完成融资，所筹集款项已转入您的银行存管账户，请您查收并可以提现。具体还款明细可通过网站-“我的账户”-“我的借款”-“还款计划”查询，请按时还款。若有疑问可咨询网站在线客服，也可电话联系网站客服。（附件：借款合同）<br/>新华久久贷");
	                                  
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
//		  	               				FutureTask<String> futureTask = new FutureTask<String>(new SceneDepositThread<String>(param,sceneDeposit));
//		  	               				ExecutorService service = Executors.newSingleThreadExecutor();
//		  	           			        service.submit(futureTask);
//		  	        			        if (futureTask.isDone() && !futureTask.isCancelled()) {
//		  	        			        	C_Evid = futureTask.get();
//		  	        			        }
		  	        			        if(!StringUtil.isEmpty(C_Evid)){
		  	        			        	// 更新数据库
		  	        			        	loanDao.updateDepositNumber(loanId, C_Evid);
		  	        			        }
//		  	           			        service.shutdown();
		  	        			        logger.info("**************************************借款人合同存证结束**************************************");
	                          	}
	                    	 }
        				  }
        			}else{
        				BaseResult exportResu = exportPdfService.exportBorrowMoney(loanId,invUserId,invId, out,i);
        				if(exportResu.isSuccess()){
        					loanUserSignFileNew = dir + loanName + "_01_" + invId + ".pdf";//投资人签章后文件路径
                    		userSignFileEnd = dir + loanName + "_02_" + invId + ".pdf";//借款人签章后文件路径
                    		platformSignFileEnd = dir + loanName + "_"+invId+"_xh99d.pdf";//平台签章后文件路径
        					StringBuffer signServiceIds = new StringBuffer();
        					invUser = userService.findById(invUserId);
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
	                				invUser.getData().setSealData(sealData);
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
                        	fileResult = signManager.userPersonSignByFile(loanUserSignFile, loanUserSignFileNew, 
                        			accountId, "甲方：", sealData);
                        	if (0 == fileResult.getErrCode())
                    		{
                        		signServiceIds.append(fileResult.getSignServiceId()).append(",");
                        		//借款人签章
                            	fileResult = signManager.userPersonSignByFile(loanUserSignFileNew, userSignFileEnd, 
                            			userDo.getData().getTsignAccountId(), "乙方：（盖章）", userDo.getData().getSealData());
                            	
                            	if (0 == fileResult.getErrCode()){
                            		signServiceIds.append(fileResult.getSignServiceId()).append(",");
                            		//平台签章
                                 	fileResult = signManager.platformSignByFile(userSignFileEnd, platformSignFileEnd, "丙方：（盖章）");
                                 	
                                 	if (0 == fileResult.getErrCode()){
                                 		isSignSuccess = true;
                                 		signServiceIds.append(fileResult.getSignServiceId()).append(",");
                                 		
                                 		// 更新数据库
                                 		investDao.updateCommContractPath(invId, platformSignFileEnd);
                                 		
                                         // 设置给投资人发送邮件
                                         mailInfo.setSubject("致客户的一封信");
                                         mailInfo.setContent("尊敬的客户：<br/>您好，感谢您对新华久久贷的信任与支持。您在新华久久贷投资的项目已经成交并开始计息，收益以实际投资期限为准，具体明细请查看新华久久贷网站（www.xh99d.com）-“我的账户”内相关信息。若有疑问可咨询网站在线客服，也可电话联系网站客服。（附件：投资合同）  <br/>新华久久贷 ");
                                         mailInfo.setToAddress(invUser.getData().getUserEmail());
                                 	}
                                 }
                    		}
                			if(isSignSuccess){
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
//	               				FutureTask<String> futureTask = new FutureTask<String>(new SceneDepositThread<String>(param,sceneDeposit));
//	               				ExecutorService service = Executors.newSingleThreadExecutor();
//	           			        service.submit(futureTask);
//	        			        if (futureTask.isDone() && !futureTask.isCancelled()) {
//	        			        	C_Evid = futureTask.get();
//	        			        }
	        			        if(!StringUtil.isEmpty(C_Evid)){
	        			        	// 更新数据库
	        			        	investDao.updateCommDepositNumber(invId, C_Evid);
	        			        }
//	           			        service.shutdown();
	        			        logger.info("**************************************投资人合同存证结束**************************************");
                			}
        				}
        				i=i+1;
        			}
        			if(isSignSuccess){
        				logger.info("**************************************发送邮件**************************************");
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
        }

        
        return result;*/
    }
    /**
     * 场景式存证
     * @author Administrator
     *
     * @param <T>
     */
    static class SceneDepositThread<T> implements java.util.concurrent.Callable<T> {
        private Map <String,String>      param;
        private SceneDeposit             sceneDeposit;

        public SceneDepositThread(Map <String,String> param, SceneDeposit sceneDeposit) {
            this.param = param;
            this.sceneDeposit = sceneDeposit;
        }


		@Override
		public T call() throws Exception {
			return (T) sceneDeposit.sceneDeposit(param);
		}
    }
    /**
     * 发送邮件线程类
     * 
     * @author zhangkang 2015年6月11日 下午5:36:35
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

    @Override
    public BaseResult sendMailToManager(LoanIntentApply loanIntentApply) {
        BaseResult result = new BaseResult();
        String toAddress = "";

        List<String> list = new ArrayList<String>();
        list.add("MAIL_SMTP_SERVER");
        list.add("MAIL_PORT");
        list.add("MAIL_ADDRESS");
        list.add("MAIL_PASSWORD");
        list.add("MAIL_SENDER_NAME");

        // 设置给平台管理者发送邮件，取出系统配置的邮件参数
        ListResult<SysConfig> mailConf = sysConfigService.queryListByParam(list);

        MailSenderInfo mailInfo = new MailSenderInfo();

        for (SysConfig SysConfig : mailConf.getData()) {
            if (SysConfig.getConf() == SysConfigEntry.MAIL_SMTP_SERVER) {
                mailInfo.setMailServerHost(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.MAIL_PORT) {
                mailInfo.setMailServerPort(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.MAIL_ADDRESS) {
                toAddress = SysConfig.getConfValue();
                mailInfo.setFromAddress(SysConfig.getConfValue());
                mailInfo.setUserName(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.MAIL_PASSWORD) {
                mailInfo.setPassword(SysConfig.getConfValue());
            }
        }
        mailInfo.setValidate(true);
        mailInfo.setToAddress(toAddress);

        mailInfo.setSubject("给平台管理者的邮件");
        mailInfo.setContent("尊敬的平台管理者：<br/>您好，现有一条新的前台借款申请<br/><br/>产品类型 ："
                + loanIntentApply.getIntentState().getPrompt() + "<br/><br/>融资金额 ：" + loanIntentApply.getIntentMoney()
                + "<br/><br/>借款人名称 ：" + loanIntentApply.getUserName() + "<br/><br/>手机号码 ：" + loanIntentApply.getPhone()
                + "<br/><br/>详细信息请您登陆新华久久贷后台在  融资意向管理-意向审核  中查看  <br/>新华久久贷 ");

        // 给投资人发送邮件
        result = simpleMailSender.sendTextMail(mailInfo);// 发送h格式
        return result;
    }
    
    @Override
    public BaseResult sendMailToOrgLoanUser(String content,String toAddress) {
        BaseResult result = new BaseResult();

        // 设置给原始标借款人发送邮件
 		MailSenderInfo orgBorrowMailInfo = new MailSenderInfo();
 		orgBorrowMailInfo.setMailServerHost(host);
 		orgBorrowMailInfo.setMailServerPort("587");
 		orgBorrowMailInfo.setValidate(true);
 		orgBorrowMailInfo.setUserName(user);
 		orgBorrowMailInfo.setPassword(password);// 您的邮箱密码
 		orgBorrowMailInfo.setFromAddress(from);

 		// 企业用户邮箱保存在abc_company_customer中
 		StringBuffer orgBorrowUserMaile = new StringBuffer();
 		orgBorrowUserMaile = orgBorrowUserMaile.append(toAddress);
 		orgBorrowMailInfo.setToAddress(orgBorrowUserMaile.toString());
 		try {
 			InternetAddress[] address = new InternetAddress[1];
 			address[0] = new InternetAddress(orgBorrowUserMaile.toString());
 			orgBorrowMailInfo.setTos(address);
 		} catch (AddressException e) {
 			e.printStackTrace();
 		}

 		orgBorrowMailInfo.setSubject("给原始标的借款人");
 		orgBorrowMailInfo.setContent(content);
 		simpleMailSender.sendHTMLMail(orgBorrowMailInfo);// 发送html格式
        return result;
    }

    @Override
    public BaseResult sendMail(String toAddress, String content, String subject) {
        BaseResult result = new BaseResult();

        List<String> list = new ArrayList<String>();
        list.add("MAIL_SMTP_SERVER");
        list.add("MAIL_PORT");
        list.add("MAIL_ADDRESS");
        list.add("MAIL_PASSWORD");
        list.add("MAIL_SENDER_NAME");

        // 设置给平台管理者发送邮件，取出系统配置的邮件参数
        ListResult<SysConfig> mailConf = sysConfigService.queryListByParam(list);

        MailSenderInfo mailInfo = new MailSenderInfo();

        for (SysConfig SysConfig : mailConf.getData()) {
            if (SysConfig.getConf() == SysConfigEntry.MAIL_SMTP_SERVER) {
                mailInfo.setMailServerHost(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.MAIL_PORT) {
                mailInfo.setMailServerPort(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.MAIL_ADDRESS) {
                mailInfo.setFromAddress(SysConfig.getConfValue());
                mailInfo.setUserName(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.MAIL_PASSWORD) {
                mailInfo.setPassword(SysConfig.getConfValue());
            }
        }
        mailInfo.setValidate(true);
        mailInfo.setToAddress(toAddress);

        mailInfo.setSubject(subject);
        mailInfo.setContent(content);

        result = simpleMailSender.sendHTMLMail(mailInfo);// 发送html格式
        return result;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    public void setFrom(String from) {
        this.from = from;
    }

//	public void setSceneDeposit(SceneDeposit sceneDeposit) {
//		this.sceneDeposit = sceneDeposit;
//	}

}
