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
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 修改机构账户信息
 * 
 * @author J.YL 2014年12月5日 上午10:34:59
 */
public class ModifyOrgAccount {

    @Resource
    private AccountInfoService accountService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private OperateLogService  operateLogService;

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
        aid.setAccountUserId(params.getInt("act_user_id"));
        aid.setAccountUserName(params.getString("act_user_name"));
        aid.setAccountUserPhone(params.getString("act_user_phone"));
        aid.setAccountUserType(params.getInt("act_user_type"));
        BaseResult result = accountService.modifyAccountInfo(aid);
        returnResult.setMessage(result.getMessage());
        returnResult.setSuccess(result.isSuccess());
        //记录操作日志
        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
        operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
        operateLogDO.setOlModule("机构账户维护");//操作模块
        operateLogDO.setOlOperateType("修改");//操作类型：添加/修改/删除
        operateLogDO.setOlContent("修改机构用户id为：" + aid.getAccountUserId() + "的资金账户信息");
        operateLogService.createOperateLog(operateLogDO);
        return returnResult;
    }
}
