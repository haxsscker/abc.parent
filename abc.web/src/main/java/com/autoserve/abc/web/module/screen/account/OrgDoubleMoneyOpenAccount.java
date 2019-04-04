package com.autoserve.abc.web.module.screen.account;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.AuthorizeUtil;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPlainVO;

public class OrgDoubleMoneyOpenAccount {
    @Autowired
    private UserService        userservice;
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private DealRecordService  dealrecordservice;
    @Resource
    private DoubleDryService   doubleDryService;
    @Resource
    private BankInfoService    bankinfoservice;
    @Resource
    private ToCashService      tocashservice;
    @Autowired
    private EmployeeService    employeeService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private GovernmentService  governmentService;

    public void execute(Context context, Navigator nav, ParameterParser params) {
    	Integer empId = LoginUserUtil.getEmpId();
        PlainResult<EmployeeDO> employeeResult = employeeService.findById(empId);
        EmployeeDO emp = employeeResult.getData();
    	context.put("emp", emp);
        PlainResult<Map<String, String>> jsonObject = new PlainResult<Map<String, String>>();
        if (employeeResult.getData().getEmpOrgId() == null) {
            jsonObject.setSuccess(false);
            jsonObject.setMessage("未查到该用户的机构信息！");
        }
//        PlainResult<GovernmentDO> government = governmentService.findById(employeeResult.getData().getEmpOrgId());
//        GovernmentDO governmentDO = government.getData();
//        Map<String, String> paramsMap = new HashMap<String, String>();
       
        AccountInfoDO account =  accountInfoService.queryByAccountMark(empId, UserType.PARTNER.type);
//        if (account!=null && account.getAccountNo() != null) {
    	if (account!=null) {
        	context.put("AccountMark","1");
//        	context.put("BhAccountNo",account.getAccountNo());
//        	context.put("AccountNo",account.getAccountUserAccount());
//        	context.put("AccountName",account.getAccountUserAccountName());
//        	context.put("AccountBk",account.getAccountUserAccountBk());
//        	context.put("ChargeAccount",account.getAccountUserChargeAccount());
//        	context.put("ChargeName",account.getAccountUserChargeName());
        	Map<String, String> chargeAccountMap =doubleDryService.queryGuarAccountResult(account.getAccountUserAccount(),account.getAccountMark());
        	String ChargeAccount =chargeAccountMap.get("ChargeAccount");
    		String AccountName =chargeAccountMap.get("AccountName");
    		String PlaCustId =chargeAccountMap.get("PlaCustId");
    		PlainResult<GovPlainJDO> plainResult = governmentService.findGovPlainByEmpId(account.getAccountUserId());
            Integer govId = plainResult.getData().getGovId();
            governmentService.updateGovernmentOutSeq(govId,PlaCustId);
            governmentService.updateGovName(govId,account.getAccountUpdateName());
    		context.put("BhAccountNo",PlaCustId);//账户存管平台客户号
    		context.put("AccountNo",account.getAccountUserAccount());//对公账号
        	context.put("AccountName",account.getAccountUserAccountName());//对公账户名
        	context.put("AccountBk",account.getAccountUserAccountBk());//清算行号
        	context.put("ChargeAccount",ChargeAccount);//大额充值账号
        	context.put("ChargeName",AccountName);//大额充值账户户名
        	context.put("RealNameFlg",chargeAccountMap.get("RealNameFlg"));//实名状态01-未实名 03-已实名
        	context.put("ChargeAmt",chargeAccountMap.get("ChargeAmt"));//01 未实名时打款金额
        	Double[] accountBacance = { 0.00, 0.00, 0.00 };
            if (PlaCustId != null && !"".equals(PlaCustId)&&"03".equals(chargeAccountMap.get("RealNameFlg"))) {
                accountBacance = this.doubleDryService.queryBalance(PlaCustId, "1");
            }
            context.put("accountBacance", accountBacance);
        	//授权类型  59、缴费 60、还款
        	String authorizeFee="";
        	String authorizeRepay="";
        	if("59".equals(emp.getAuthorizeFeeType())){
        		authorizeFee=AuthorizeUtil.isAuthorize(emp.getAuthorizeFeeStartDate(),emp.getAuthorizeFeeEndDate());
        	}
        	if("60".equals(emp.getAuthorizeRepayType())){
        		authorizeRepay=AuthorizeUtil.isAuthorize(emp.getAuthorizeRepayStartDate(),emp.getAuthorizeRepayEndDate());
        	}
        	context.put("authorizeFee", authorizeFee);
        	context.put("authorizeRepay",authorizeRepay);
        } else {
        	context.put("AccountMark","2");
        }
    }
}
