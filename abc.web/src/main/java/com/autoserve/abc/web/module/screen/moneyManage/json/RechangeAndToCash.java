package com.autoserve.abc.web.module.screen.moneyManage.json;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPlainVO;

public class RechangeAndToCash {

    @Resource
    private DoubleDryService   doubleDryService;
    @Resource
    private AccountInfoService accountInfoService;
    @Resource
    private UserService        userService;
    @Autowired
    private EmployeeService    employeeService;
    @Autowired
    private GovernmentService  governmentService;

    public JsonPlainVO<Map<String, String>> execute(@Param("empId") int empId, ParameterParser params) {
        //根据用户ID获取到该用户的钱多多标示
        PlainResult<EmployeeDO> employeeResult = employeeService.findById(empId);
        Integer empType = employeeResult.getData().getEmpType();
        PlainResult<Map<String, String>> result = new PlainResult<Map<String, String>>();
        Map<String, String> param = new HashMap<String, String>();
        String PlatformId = "";
        switch (empType) {
            case 1: {
                PlainResult<UserDO> plainResult = userService.findById(empId);
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

        //查询该用于在钱多多平台的余额
        Double[] resultDouble = doubleDryService.queryBalance(PlatformId, "2");
        param.put("mount", resultDouble[1].toString());
        result.setSuccess(true);
        result.setData(param);

        return ResultMapper.toPlainVO(result);
    }

}
