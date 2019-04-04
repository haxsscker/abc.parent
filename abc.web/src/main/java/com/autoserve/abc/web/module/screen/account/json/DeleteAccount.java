/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.account.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 删除用户账户
 * 
 * @author J.YL 2014年12月27日 下午8:06:48
 */
public class DeleteAccount {
    private static Logger      logger = LoggerFactory.getLogger(DeleteAccount.class);

    @Resource
    private HttpServletRequest request;

    @Resource
    private OperateLogService  operateLogService;

    @Resource
    private AccountInfoService accountInfoService;

    public JsonBaseVO execute(@Param("accountId") int accountId, @Param("govId") int govId) {
        logger.info("员工：{} 删除账户：{}", LoginUserUtil.getEmpId(), accountId);
        BaseResult delRes = accountInfoService.deleteAccountById(accountId);
        JsonBaseVO result = new JsonBaseVO();
        if (!delRes.isSuccess()) {
            result.setSuccess(false);
            result.setMessage(delRes.getMessage());
        }
        //记录操作日志
        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
        operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
        operateLogDO.setOlModule("机构账户维护");//操作模块
        operateLogDO.setOlOperateType("删除");//操作类型：添加/修改/删除
        operateLogDO.setOlContent("删除机构id为：" + govId + "的资金账户");//具体操作内容
        operateLogService.createOperateLog(operateLogDO);
        return result;
    }
}
