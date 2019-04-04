package com.autoserve.abc.web.module.screen.moneyManage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;

public class RechargeMoney {
    @Autowired
    private UserService         userservice;
    @Autowired
    private HttpSession         session;
    @Autowired
    private DealRecordService   dealrecordservice;
    @Autowired
    private AccountInfoService  accountInfoService;
    @Autowired
    private RechargeService     rechargeservice;
    @Autowired
    private DeployConfigService deployConfigService;
    @Autowired
    private HttpServletRequest  request;
    @Autowired
    private EmployeeService     employeeService;
    @Autowired
    private GovernmentService   governmentService;

    public void execute(@Param("empId") int empId, Context context, Navigator nav, ParameterParser params) {
        //根据用户ID获取到该用户的钱多多标示
        //根据用户ID获取到该用户的钱多多标示
        PlainResult<EmployeeDO> employeeResult = employeeService.findById(empId);
        Integer empType = employeeResult.getData().getEmpType();
        PlainResult<Map<String, String>> result = new PlainResult<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        String PlatformId = "";
        UserType userType = UserType.PARTNER;
        switch (empType) {
            case 1: {
                PlainResult<UserDO> plainResult = userservice.findById(empId);
                userType = UserType.valueOf(plainResult.getData().getUserType());
                AccountInfoDO accountDo = accountInfoService.queryByAccountMark(empId, plainResult.getData()
                        .getUserType());
                if (accountDo.getAccountMark() != null) {
                    PlatformId = accountDo.getAccountMark();
                }
                break;
            }
            case 2: {
                PlainResult<GovernmentDO> government = governmentService.findById(employeeResult.getData()
                        .getEmpOrgId());
                GovernmentDO governmentDO = government.getData();
                PlatformId = governmentDO.getGovOutSeqNo();
                break;
            }
        }

        String mey = params.getString("money");
        map.put("RechargeMoneymoremore", PlatformId);
        map.put("Amount", mey);
        BigDecimal bigdecimal = new BigDecimal(mey);
        PlainResult<DealReturn> paramMap = rechargeservice.recharge(empId, userType, bigdecimal, map);
        JSONObject jo = JSON.parseObject(paramMap.getData().getParams());
        context.put("jo", jo);
    }
}
