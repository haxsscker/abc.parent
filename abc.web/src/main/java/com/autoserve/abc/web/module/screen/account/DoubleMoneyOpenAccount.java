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
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPlainVO;

public class DoubleMoneyOpenAccount {
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

    public JsonPlainVO<Map<String, String>> execute(@Param("empId") int empId, Context context, Navigator nav,
                                                    ParameterParser params) {
        PlainResult<EmployeeDO> employeeResult = employeeService.findById(empId);
        PlainResult<Map<String, String>> jsonObject = new PlainResult<Map<String, String>>();
        if (employeeResult.getData().getEmpOrgId() == null) {
            jsonObject.setSuccess(false);
            jsonObject.setMessage("未查到该用户的机构信息！");
        }
        PlainResult<GovernmentDO> government = governmentService.findById(employeeResult.getData().getEmpOrgId());
        GovernmentDO governmentDO = government.getData();
        Map<String, String> paramsMap = new HashMap<String, String>();
       
        AccountInfoDO account =  accountInfoService.queryByAccountMark(empId, UserType.PARTNER.type);
        if (account!=null && account.getAccountNo() != null) {
            paramsMap.put("AccountMark","1");
            paramsMap.put("AccountNo",account.getAccountUserAccount());
            paramsMap.put("AccountName",account.getAccountUserAccountName());
            paramsMap.put("AccountBk",account.getAccountUserAccountBk());
            paramsMap.put("ChargeAccount",account.getAccountUserChargeAccount());
            paramsMap.put("ChargeName",account.getAccountUserChargeName());
        	Map<String, String> chargeAccountMap =doubleDryService.queryGuarAccountResult(account.getAccountUserAccount(),account.getAccountMark());
        	paramsMap.put("RealNameFlg",chargeAccountMap.get("RealNameFlg"));
        	paramsMap.put("ChargeAmt",chargeAccountMap.get("ChargeAmt"));
        } else {
        	paramsMap.put("AccountMark","2");
        }

        jsonObject.setData(paramsMap);
        return ResultMapper.toPlainVO(jsonObject);

    }
}
