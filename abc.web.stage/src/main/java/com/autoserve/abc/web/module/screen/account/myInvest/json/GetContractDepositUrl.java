package com.autoserve.abc.web.module.screen.account.myInvest.json;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.message.deposit.constant.SceneConfig;
import com.autoserve.abc.service.message.deposit.service.DepositNumberService;
import com.autoserve.abc.service.message.deposit.service.SceneDeposit;
import com.autoserve.abc.web.vo.JsonBaseVO;
/**
 * 
 * @author DS
 *
 * 2015上午10:13:43
 */
public class GetContractDepositUrl {
	private static final Logger logger = LoggerFactory.getLogger(GetContractDepositUrl.class);
	@Autowired
    private HttpSession session;
	@Resource
	private SceneDeposit sceneDeposit;
	@Resource
	private DepositNumberService depositNumberService;
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
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
	
    public JsonBaseVO execute(Context context, ParameterParser params,Navigator nav) {
    	JsonBaseVO result = new JsonBaseVO();
    	//登录URL
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		result.setSuccess(false);
    		result.setMessage("您还没有登录,请先登录");
			result.setRedirectUrl("/login/login");
    		return result;
    	}
    	String depositNumber = "";//场景式存证编号（证据链编号）
		String ViewInfoUrl = "";//存证证明页面查看完整Url
    	try {
    		Map <String,String> param = new HashMap<String, String>();
    		//comm_jk：普通标借款人合同，comm_tz：普通标投资人合同，trans_zr：转让人合同，trans_tz：受让人合同
    		String type = params.getString("type");
    		Integer loanId = params.getInt("loanId");
    		Integer investId = params.getInt("investId");
    		PlainResult<UserDO> userDoResult=userService.findById(user.getUserId());
    		UserDO userDO = userDoResult.getData();
    		PlainResult<CompanyCustomerDO> enterprise = companyCustomerService.findByUserId(userDO.getUserId());
    		if (UserType.ENTERPRISE.getType() == userDO.getUserType()){
    			param.put(SceneConfig.CERTIFICATE_TYPE, "CODE_ORG");
            	 param.put(SceneConfig.CERTIFICATE_NUMBER, enterprise.getData().getCcNo());
             }else{
            	 param.put(SceneConfig.CERTIFICATE_TYPE, "ID_CARD");
            	 param.put(SceneConfig.CERTIFICATE_NUMBER, userDO.getUserDocNo());
             }
    		InvestDO invest = investDao.findById(investId);
    		if("comm_jk".equals(type)) {
    			LoanDO loan = loanDao.findById(loanId);
    			depositNumber = loan.getLoanDepositNumber();
    		}else if("comm_tz".equals(type)) {
    			depositNumber = invest.getCommDepositNumber();
    		}else if("trans_tz".equals(type)) {
    			depositNumber = invest.getTransDepositNumber();
    		}else if("trans_zr".equals(type)) {
    			TransferLoanDO transferLoan = transferDao.findById(loanId);
    			depositNumber = transferLoan.getContractDepositNumber();
    		}
    		if(StringUtil.isEmpty(depositNumber)){
    			depositNumber=depositNumberService.getDepositNumber(type, loanId, investId, userDO);
    		}
    		param.put(SceneConfig.DEPOSIT_ID, depositNumber);
    		ViewInfoUrl = sceneDeposit.getSceneViewInfoUrl(param);
    		result.setRedirectUrl(ViewInfoUrl);
    		return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setSuccess(false);
    		result.setMessage("系统异常，请联系管理员");
			return result;
		}
    }
}
