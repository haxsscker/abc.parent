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
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPlainVO;

public class AuthorizeView {
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
        Integer empType = queryUserTypeByUserId(empId);
        UserType userType = UserType.PARTNER;
        if (empType == 1) {
            userType = UserType.ENTERPRISE;
        }
        AccountInfoDO account = accountInfoService.queryByAccountMark(empId, userType.getType());
        Map<String, String> paramsMap = new HashMap<String, String>();
        PlainResult<Map<String, String>> jsonObject = new PlainResult<Map<String, String>>();
        if (account.getAccountMark() != null) {
            paramsMap.put("accountMark", account.getAccountMark());
        } else {
            jsonObject.setSuccess(false);
            jsonObject.setMessage("钱多多标示信息不正确，请补全后重新操作！");
        }

        jsonObject.setData(paramsMap);
        return ResultMapper.toPlainVO(jsonObject);

    }

    // TODO 优化
    private Integer queryUserTypeByUserId(int userId) {
        // PlainResult<User> userResult = employeeService.findEntityById(userId);
        PlainResult<EmployeeDO> userResult = employeeService.findById(userId);
        if (!userResult.isSuccess() || userResult.getData() == null) {
            throw new BusinessException("用户类型查询失败");
        }

        return userResult.getData().getEmpType();
    }
}
