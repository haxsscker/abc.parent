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
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 后台用户开户 userType为平台加盟商
 * 
 * @author J.YL 2014年12月17日 下午2:23:19
 */
public class OpenAccount {
    @Resource
    private AccountInfoService accountService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private OperateLogService  operateLogService;

    public JsonBaseVO execute(ParameterParser params) {
        JsonBaseVO returnResult = new JsonBaseVO();
        AccountInfoDO aid = new AccountInfoDO();
        LoginUserInfo user = LoginUserInfoHelper.getLoginUserInfo();
        aid.setAccountBankArea(params.getString("act_bank_area_no"));
        aid.setAccountBankBranchName(params.getString("act_bank_name"));
        aid.setAccountBankName(params.getString("act_bank_level"));
        aid.setAccountCashPwd(params.getString("act_cash_pwd"));
        aid.setAccountLoginPwd(params.getString("act_login_pwd"));
        aid.setAccountMark(params.getString("act_account_mark"));
        aid.setAccountNo(params.getString("act_account_no"));
        aid.setAccountUserAccount(params.getString("act_user_account"));
        aid.setAccountUserCard(params.getString("act_user_card"));
        aid.setAccountUserEmail(params.getString("act_user_email"));
        aid.setAccountUserName(params.getString("act_user_name"));
        aid.setAccountUserPhone(params.getString("act_user_phone"));
        //设置用户id和用户类型 现为写死：平台加盟商
        aid.setAccountUserId(user.getEmpId());
        aid.setAccountUserType(UserType.PARTNER.getType());
        BaseResult result = accountService.openAccount(aid);
        if (!result.isSuccess()) {
            returnResult.setMessage("开户失败");
        }
        returnResult.setSuccess(result.isSuccess());
        //记录操作日志
        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
        operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
        operateLogDO.setOlModule("后台账户开户");//操作模块
        operateLogDO.setOlOperateType("添加");//操作类型：添加/修改/删除
        operateLogDO.setOlContent("为用户id为：" + aid.getAccountUserId() + "的后台用户添加资金账户");
        operateLogService.createOperateLog(operateLogDO);
        return returnResult;
    }
}
