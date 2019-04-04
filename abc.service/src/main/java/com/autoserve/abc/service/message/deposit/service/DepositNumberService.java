package com.autoserve.abc.service.message.deposit.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.message.deposit.bean.CertificateBean;
import com.autoserve.abc.service.message.deposit.constant.SceneConfig;

public class DepositNumberService {
	private static final Logger logger = LoggerFactory.getLogger(DepositNumberService.class);
	@Resource
	private SceneDeposit sceneDeposit;
	@Resource
	private LoanDao loanDao;
	@Resource
	private InvestDao investDao;
	@Resource
	private TransferLoanDao transferDao;
	@Resource
	private UserService userService;
	@Resource
    private CompanyCustomerService  companyCustomerService;
	/**
     * 获取存证编号
     * @param type comm_jk：普通标借款人合同，comm_tz：普通标投资人合同，trans_zr：转让人合同，trans_tz：受让人合同
     * @param loanId 普通标的id或者转让标id
     * @param investId 投资id
     * @param userDO 当前登录人
     * @return
     */
    public String getDepositNumber(String type,Integer loanId,Integer investId,UserDO userDO){
		String depositNumber = "";//场景式存证编号（证据链编号）
    	 Map <String,String> param=new HashMap<String, String>();
    	 List<CertificateBean> certificates = new ArrayList<CertificateBean>();
    	 InvestDO invest = investDao.findById(investId);
    	 LoanDO loan = loanDao.findById(loanId);
    	 PlainResult<UserDO> loanUserDo = userService.findById(loan.getLoanUserId());//借款人
    	 PlainResult<CompanyCustomerDO> enterprise = companyCustomerService.findByUserId(loan.getLoanUserId());
    	 TransferLoanDO transferLoan = transferDao.findById(loanId);
    	 PlainResult<UserDO> transUser = null;
    	 if(null != transferLoan){
    		 transUser = userService.findById(transferLoan.getTlUserId());//出让人
    	 }
 		if("comm_jk".equals(type)) {
 			param.put(SceneConfig.CONTRACT_TYPE, SceneConfig.LOAN_CONTRACT);
 			param.put(SceneConfig.CONTRACT_PATH, loan.getContractPath());
 			param.put(SceneConfig.CONTRACT_NAME, loan.getLoanNo());
 			InvestSearchDO searchDO = new InvestSearchDO();
            searchDO.setBidId(loanId);
            searchDO.setBidType(BidType.COMMON_LOAN.getType());
            if(LoanState.REPAY_COMPLETED.state==loan.getLoanState()){
            	searchDO.setInvestStates(Arrays.asList(InvestState.EARN_COMPLETED.getState()));
            }else{
            	searchDO.setInvestStates(Arrays.asList(InvestState.EARNING.getState(),InvestState.TRANSFERED.getState()));
            }
            List<InvestDO> investList = investDao.findListBySearchParam(searchDO, null);
            if (CollectionUtils.isEmpty(investList)) {
            	logger.error("投资记录查询失败");
                throw new RuntimeException("投资记录查询失败");
            }
            List<Integer> ids = new ArrayList<Integer>();
            for (InvestDO investDO : investList) {
          	  ids.add(investDO.getInId());
            }
            ListResult<UserDO> usersResult = userService.findByList(ids);
            StringBuffer userRealNames = new StringBuffer();
            for(UserDO user : usersResult.getData()){
          	  userRealNames.append(user.getUserRealName()).append(",");
            }
            param.put(SceneConfig.PARTYA_PARAM_NAME, userRealNames.toString().substring(0, userRealNames.toString().length()-1));
            if (UserType.ENTERPRISE.getType() == userDO.getUserType()){
           	 param.put(SceneConfig.PARTYB_PARAM_NAME, enterprise.getData().getCcCompanyName());
            }else{
           	 param.put(SceneConfig.PARTYB_PARAM_NAME, userDO.getUserRealName());
            }
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
       		if (UserType.ENTERPRISE.getType() == userDO.getUserType()){
       			personBean.setName(enterprise.getData().getCcCompanyName());
       			personBean.setType("CODE_USC");
       			personBean.setNumber(enterprise.getData().getCcLicenseNo());
       		}else{
       			personBean.setName(userDO.getUserRealName());
           		personBean.setType("ID_CARD");
           		personBean.setNumber(userDO.getUserDocNo());
       		}
       		certificates.add(personBean);
 		}else if("comm_tz".equals(type)) {
// 			InvestSearchDO searchDO = new InvestSearchDO();
//            searchDO.setBidId(loanId);
//            searchDO.setBidType(BidType.COMMON_LOAN.getType());
//            if(LoanState.REPAY_COMPLETED.state==loan.getLoanState()){
//            	searchDO.setInvestStates(Arrays.asList(InvestState.EARN_COMPLETED.getState()));
//            }else{
//            	searchDO.setInvestStates(Arrays.asList(InvestState.EARNING.getState(),InvestState.TRANSFERED.getState()));
//            }
//            int xh = investDao.findXhBySearchParam(searchDO, investId);
 			param.put(SceneConfig.CONTRACT_TYPE, SceneConfig.LOAN_CONTRACT);
 			param.put(SceneConfig.CONTRACT_PATH, invest.getInCommContract());
// 			param.put(SceneConfig.CONTRACT_NAME, loan.getLoanNo()+"-"+xh);
 			param.put(SceneConfig.CONTRACT_NAME, loan.getLoanNo());
 			param.put(SceneConfig.PARTYA_PARAM_NAME, userDO.getUserRealName());
            if (UserType.ENTERPRISE.getType() == loanUserDo.getData().getUserType()){
           	 param.put(SceneConfig.PARTYB_PARAM_NAME, enterprise.getData().getCcCompanyName());
            }else{
           	 param.put(SceneConfig.PARTYB_PARAM_NAME, loanUserDo.getData().getUserRealName());
            }
    		CertificateBean personBean = new CertificateBean();
    		//投资人 一对一
    		personBean.setName(userDO.getUserRealName());
    		personBean.setType("ID_CARD");
    		personBean.setNumber(userDO.getUserDocNo());
    		certificates.add(personBean);
    		//借款人 一对一
    		personBean = new CertificateBean();
    		if (UserType.ENTERPRISE.getType() == loanUserDo.getData().getUserType()){
    			personBean.setName(enterprise.getData().getCcCompanyName());
    			personBean.setType("CODE_USC");
    			personBean.setNumber(enterprise.getData().getCcLicenseNo());
    		}else{
    			personBean.setName(loanUserDo.getData().getUserRealName());
        		personBean.setType("ID_CARD");
        		personBean.setNumber(loanUserDo.getData().getUserDocNo());
    		}
    		certificates.add(personBean);
 		}else if("trans_tz".equals(type)) {
 			loan = loanDao.findById(transferLoan.getTlOriginId());//原始项目
// 			InvestSearchDO searchDO = new InvestSearchDO();
//            searchDO.setBidId(loanId);
//            searchDO.setBidType(BidType.TRANSFER_LOAN.getType());
//            if(LoanState.REPAY_COMPLETED.state==loan.getLoanState()){
//            	searchDO.setInvestStates(Arrays.asList(InvestState.EARN_COMPLETED.getState()));
//            }else{
//            	searchDO.setInvestStates(Arrays.asList(InvestState.EARNING.getState(),InvestState.TRANSFERED.getState()));
//            }
//            int xh = investDao.findXhBySearchParam(searchDO, investId);
 			param.put(SceneConfig.CONTRACT_TYPE, SceneConfig.TRANS_CONTRACT);
 			param.put(SceneConfig.CONTRACT_PATH, invest.getInTransContract());
// 			param.put(SceneConfig.CONTRACT_NAME, transferLoan.getTlLoanNo()+"-"+xh);
 			param.put(SceneConfig.CONTRACT_NAME, transferLoan.getTlLoanNo());
 			param.put(SceneConfig.PARTYB_PARAM_NAME, userDO.getUserRealName());
 			param.put(SceneConfig.PARTYA_PARAM_NAME, transUser.getData().getUserRealName());
            CertificateBean personBean = new CertificateBean();
      		//投资人 一对一
  			personBean.setName(userDO.getUserRealName());
  			personBean.setType("ID_CARD");
  			personBean.setNumber(userDO.getUserDocNo());
  			certificates.add(personBean);
      		//出让人 一对一
      		personBean = new CertificateBean();
  			personBean.setName(transUser.getData().getUserRealName());
      		personBean.setType("ID_CARD");
      		personBean.setNumber(transUser.getData().getUserDocNo());
      		certificates.add(personBean);
 		}else if("trans_zr".equals(type)) {
 			loan = loanDao.findById(transferLoan.getTlOriginId());//原始项目
 			InvestSearchDO searchDO = new InvestSearchDO();
            searchDO.setBidId(loanId);
            searchDO.setBidType(BidType.TRANSFER_LOAN.getType());
            if(LoanState.REPAY_COMPLETED.state==loan.getLoanState()){
            	searchDO.setInvestStates(Arrays.asList(InvestState.EARN_COMPLETED.getState()));
            }else{
            	searchDO.setInvestStates(Arrays.asList(InvestState.EARNING.getState(),InvestState.TRANSFERED.getState()));
            }
            List<InvestDO> investList = investDao.findListBySearchParam(searchDO, null);
            if (CollectionUtils.isEmpty(investList)) {
            	logger.error("投资记录查询失败");
                throw new RuntimeException("投资记录查询失败");
            }
 			param.put(SceneConfig.CONTRACT_TYPE, SceneConfig.TRANS_CONTRACT);
 			param.put(SceneConfig.CONTRACT_PATH, transferLoan.getContractPath());
 			param.put(SceneConfig.CONTRACT_NAME, transferLoan.getTlLoanNo());
 			List<Integer> ids = new ArrayList<Integer>();
 			for (InvestDO investDO : investList) {
 	          	  ids.add(investDO.getInId());
            }
            ListResult<UserDO> usersResult = userService.findByList(ids);
            StringBuffer userRealNames = new StringBuffer();
            for(UserDO user : usersResult.getData()){
          	  userRealNames.append(user.getUserRealName()).append(",");
            }
            param.put(SceneConfig.PARTYB_PARAM_NAME, userRealNames.toString().substring(0, userRealNames.toString().length()-1));
           	param.put(SceneConfig.PARTYA_PARAM_NAME, transUser.getData().getUserRealName());
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
  			personBean.setName(transUser.getData().getUserRealName());
      		personBean.setType("ID_CARD");
      		personBean.setNumber(transUser.getData().getUserDocNo());
      		certificates.add(personBean);
 		}
 		JSONArray cer=JSONArray.fromObject(certificates);
   		param.put(SceneConfig.CERTIFICATES, cer.toString());
        param.put(SceneConfig.PARTYC_PARAM_NAME, SceneConfig.PARTYC_NAME);
        param.put(SceneConfig.SIGN_SERVICE_IDS, "");
        if(StringUtil.isEmpty(param.get(SceneConfig.CONTRACT_PATH))){
        	logger.error("借款合同未生成");
        	throw new RuntimeException("借款合同未生成，请联系管理员");
        }
   		depositNumber = sceneDeposit.sceneDeposit(param);
   		// 更新数据库
   		if(!StringUtil.isEmpty(depositNumber)){
   			if("comm_jk".equals(type)) {
	        	loanDao.updateDepositNumber(loanId, depositNumber);
	        }else if("comm_tz".equals(type)) {
	        	investDao.updateCommDepositNumber(investId, depositNumber);
	        }else if("trans_tz".equals(type)) {
	        	investDao.updateTransDepositNumber(investId,depositNumber);
	        }else if("trans_zr".equals(type)) {
	        	transferDao.updateContractDepositNumber(loanId,depositNumber);
	        }
   		}
   		return depositNumber;
    }
}
