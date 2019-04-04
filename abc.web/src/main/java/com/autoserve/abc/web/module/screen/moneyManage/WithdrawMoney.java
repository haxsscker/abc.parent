package com.autoserve.abc.web.module.screen.moneyManage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class WithdrawMoney {
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

    public void execute(@Param("empId") int empId, Context context, Navigator nav, ParameterParser params) {
        PlainResult<EmployeeDO> employeeResult = employeeService.findById(empId);
        String PlatformId = "";
        Integer empType = employeeResult.getData().getEmpType();
        PlainResult<Map<String, String>> result = new PlainResult<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
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
        Double money = params.getDouble("money");
        String mey = params.getString("money");
        String CardType = params.getString("CardType");
        String BankCode = params.getString("BankCode");
        String province = params.getString("province");
        String city = params.getString("city");
        String CardNo = params.getString("CardNo");
        map.put("WithdrawMoneymoremore", PlatformId);
        map.put("Amount", mey);
        map.put("FeePercent", "0");
        map.put("CardNo", CardNo);
        map.put("CardType", CardType);
        map.put("BankCode", BankCode);
        map.put("Province", province);
        map.put("City", city);
        BigDecimal bigdecimal = new BigDecimal(mey);
        PlainResult<DealReturn> paramMap = tocashservice.toCashOther(empId, userType, bigdecimal, map);
        JSONObject jo = JSON.parseObject(paramMap.getData().getParams());
        context.put("jo", jo);
    }

}
