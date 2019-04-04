package com.autoserve.abc.web.module.screen.webnotify;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public class OpenAcctRetUrl {
    private final static Logger logger = LoggerFactory.getLogger(OpenAcctRetUrl.class);
    @Resource
    private AccountInfoService  accountInfoService;
    @Resource
    private HttpServletResponse resp;
    @Resource
    private HttpServletRequest  request;
    @Resource
    HuifuPayService             huifuPayService;
    @Autowired
    private UserService         userService;

    public void execute(Context context, Navigator nav, ParameterParser params) {
        HuiFuData resultData = new HuiFuData();
        AccountInfoDO account = new AccountInfoDO();
        resultData = huifuPayService.openAccountResult(params);
        System.out.println(resultData.getRespCode());
        System.out.println(resultData.getRespDesc());
        BaseResult result = new BaseResult();
        Integer userId = Integer.parseInt(resultData.getMerPriv());
        if (resultData.getRespCode().equals("000")) {
            PlainResult<UserDO> userResult1 = userService.findById(userId);
            account.setAccountUserId(userId); //商户私有域对应数据库字段userId
            account.setAccountMark(resultData.getUsrId()); //用户号
            account.setAccountNo(resultData.getUsrCustId()); //用户客户号
            account.setAccountUserEmail(resultData.getUsrEmail());
            account.setAccountUserPhone(resultData.getUsrMp());
            account.setAccountUserCard(resultData.getIdNo()); //身份证号  
            account.setAccountLegalName(userResult1.getData().getUserRealName()); //真实名称
            account.setAccountUserName(userResult1.getData().getUserName());
            account.setAccountUserType(1);
            result = accountInfoService.openAccount(account);
            System.out.println(result.getMessage());
            nav.redirectToLocation("/account/myAccount/bindAccount");
        } else {
            System.out.println("开户失败！");
            logger.error("[OpenAccount] error: ", "开户失败");
            context.put("ResultCode", resultData.getRespCode());
            context.put("Message", resultData.getRespDesc());
            nav.forwardTo("/error");
        }

    }
}
