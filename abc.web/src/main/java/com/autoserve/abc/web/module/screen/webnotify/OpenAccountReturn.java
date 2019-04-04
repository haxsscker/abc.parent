package com.autoserve.abc.web.module.screen.webnotify;

import java.util.Map;

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
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.EasyPayUtils;

public class OpenAccountReturn {
    private final static Logger logger = LoggerFactory.getLogger(RechargeReturn.class);
    @Resource
    private AccountInfoService  accountInfoService;
    @Resource
    private HttpServletResponse resp;
    @Resource
    private HttpServletRequest  resq;
    @Resource
    private InvestService       investService;
    @Resource
    private DealRecordService   dealRecord;
    @Resource
    private CashRecordService   cashrecordservice;
    @Resource
    private BankInfoService     bankinfoservice;
    @Autowired
    private EmployeeService     employeeService;
    @Autowired
    private GovernmentService   governmentService;
    @Autowired
    private DoubleDryService    doubleDryService;

    public void execute(Context context, Navigator nav, ParameterParser params) {
        Map paramterMap = resq.getParameterMap();
        Map notifyMap = EasyPayUtils.transformRequestMap(paramterMap);
        String ResultCode = (String) notifyMap.get("ResultCode");
        String Message = params.getString("Message");
        try {
            if (ResultCode.equals("88")) {
                PlainResult<EmployeeDO> pResult = employeeService.findById(Integer.valueOf(notifyMap.get(
                        "Remark1").toString()));
                Integer govId = pResult.getData().getEmpOrgId();
                BaseResult baseResult = governmentService.updateGovernmentOutSeq(govId, notifyMap
                        .get("MoneymoremoreId").toString());
                if (baseResult.isSuccess()) {
                    AccountInfoDO account = new AccountInfoDO();
                    account.setAccountUserId(pResult.getData().getEmpId());
                    account.setAccountUserType(UserType.PARTNER.getType());
                    account.setAccountMark(notifyMap.get("MoneymoremoreId").toString());
                    account.setAccountNo(notifyMap.get("AccountNumber").toString());
                    account.setAccountUserPhone(notifyMap.get("Mobile").toString());
                    account.setAccountUserEmail(notifyMap.get("Email").toString());
                    account.setAccountLegalName(notifyMap.get("RealName").toString());
                    account.setAccountUserCard(notifyMap.get("IdentificationNo").toString());
                    account.setAccountUserName(pResult.getData().getEmpName());
                    BaseResult reseResult = accountInfoService.openAccount(account);
                    context.put("Message", Message);
                    if (reseResult.isSuccess()) {
                    	nav.redirectToLocation("/moneyReturnManage/rechangeReturn?Message=88&status=kh");
                    }
                }
            } else {
            	context.put("ResultCode", ResultCode);
            	context.put("Message", Message);
            	nav.forwardTo("/moneyReturnManage/error").end();
//                nav.redirectToLocation("/moneyReturnManage/rechangeReturn?Message=77&status=kh");
            }
        } catch (Exception e) {
            logger.error("[Recharge] error: ", e);
        }
    }
}
