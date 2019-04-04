/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.account.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 添加机构用户
 * 
 * @author J.YL 2014年12月4日 下午5:22:10
 */
public class AddOrgAccount {
    @Resource
    private AccountInfoService accountService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private OperateLogService  operateLogService;
    @Resource
    private EmployeeService    employeeService;

    public JsonBaseVO execute(ParameterParser params) {
        JsonBaseVO returnResult = new JsonBaseVO();
        AccountInfoDO aid = new AccountInfoDO();
        aid.setAccountBankArea(params.getString("act_bank_area_no"));
        aid.setAccountBankBranchName(params.getString("act_bank_name"));
        aid.setAccountBankName(params.getString("act_bank_level"));
        aid.setAccountCashPwd(params.getString("act_cash_pwd"));
        aid.setAccountId(params.getInt("act_account_id"));
        aid.setAccountLegalName(params.getString("act_legal_name"));
        aid.setAccountMark(params.getString("act_account_mark"));
        aid.setAccountNo(params.getString("act_account_no"));
        aid.setAccountUserAccount(params.getString("act_user_account"));
        aid.setAccountUserCard(params.getString("act_user_card"));
        aid.setAccountUserEmail(params.getString("act_user_email"));
        //添加机构用户时返回的act_user_id 为governmentId，需查询employeeId
        int governmentId = params.getInt("act_user_id");
        aid.setAccountUserName(params.getString("act_user_name"));
        aid.setAccountUserPhone(params.getString("act_user_phone"));
        aid.setAccountUserType(params.getInt("act_user_type"));
        if (governmentId <= 0) {
            returnResult.setMessage("机构信息错误,添加失败");
            returnResult.setSuccess(false);
            return returnResult;
        }
        PlainResult<Employee> queryRes = employeeService.findByGovId(governmentId);
        if (!queryRes.isSuccess()) {
            returnResult.setMessage("机构信息错误,添加失败");
            returnResult.setSuccess(false);
            return returnResult;
        }
        Employee data = queryRes.getData();

        aid.setAccountUserId(data.getEmpId());
        UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserId(data.getEmpId());
        userIdentity.setUserType(UserType.PARTNER);
        PlainResult<AccountInfoDO> accountRes = accountService.queryByUserIdentity(userIdentity);
        if (!accountRes.isSuccess()) {
            returnResult.setMessage("账户信息查询失败,添加失败");
            returnResult.setSuccess(false);
            return returnResult;
        }
        AccountInfoDO accountData = accountRes.getData();
        if (accountData.getAccountId() != null) {
            returnResult.setMessage("机构已开户,账户添加失败");
            returnResult.setSuccess(false);
            return returnResult;
        }
        BaseResult result = accountService.addNewOrgAccountInfo(aid);
        returnResult.setMessage(result.getMessage());
        returnResult.setSuccess(result.isSuccess());

        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
        operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
        operateLogDO.setOlModule("机构账户维护");//操作模块
        operateLogDO.setOlOperateType("添加");//操作类型：添加/修改/删除
        operateLogDO.setOlContent("为机构id为：" + aid.getAccountUserId() + "添加了一个账户，账户号为：" + aid.getAccountNo());//具体操作内容
        operateLogService.createOperateLog(operateLogDO);
        return returnResult;
    }
}
