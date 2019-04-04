package com.autoserve.abc.web.module.screen.webnotify;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.dataobject.RechargeRecordDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.CardType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.cash.TiedCardService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public class RechargeRetUrl {
    private final static Logger logger = LoggerFactory.getLogger(RechargeRetUrl.class);
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
    private RechargeService     rechargeservice;
    @Resource
    HuifuPayService             huifuPayService;
    @Resource
    private AccountInfoService  accountInfoSer;
    @Resource
    private TiedCardService     tiedCardService;
    @Resource
    private UserService         userService;
    @Resource
    private BankInfoService     bankInfoService;

    public void execute(Context context, Navigator nav, ParameterParser params) {
        HuiFuData data = new HuiFuData();
        data = huifuPayService.rechargeResult(resq);
        String resultCode = data.getRespCode();
        String resultDesc = data.getRespDesc();
        String orderNo = data.getMerPriv();
        String ordId = data.getOrdId();
        String trxId = data.getTrxId();
        String recvOrdId = "RECV_ORD_ID_" + trxId;
        Map notifyMap = new HashMap();
        notifyMap.put("data", data);
        System.out.println(notifyMap.get("data"));
        String crResponse = JSON.toJSONString(data);

        String CardId = data.getCardId();
        String GateBusiId = data.getGateBusiId();
        String GateBankId = data.getGateBankId();
        if (GateBusiId != null && ("QP").equals(GateBusiId)) {
            AccountInfoDO infoDO = accountInfoSer.queryByAccountNo(data.getUsrCustId());
            // 绑定快捷卡成功后，之前所绑定的银行卡都不可用
            BankInfo updateBank = new BankInfo();
            updateBank.setBankUserId(infoDO.getAccountUserId());
            updateBank.setCardStatus(CardStatus.STATE_DISABLE);
            bankInfoService.modifyBankInfoByUserId(updateBank);

            BankInfo bankInfo = new BankInfo();
            bankInfo.setBankUserId(infoDO.getAccountUserId());
            bankInfo.setBankLawer(infoDO.getAccountUserName());
            bankInfo.setBankUserType(infoDO.getAccountUserType());
            bankInfo.setCardType(CardType.DEBIT_CARD);
            bankInfo.setBankNo(CardId);
            bankInfo.setBankCode(GateBankId);
            bankInfo.setCardStatus(CardStatus.STATE_ENABLE);
            bankInfo.setBindDate(new Date());
            tiedCardService.tiedCard(bankInfo);

            //修改用户是否绑定银行卡标识
            UserDO userDo = new UserDO();
            userDo.setUserId(infoDO.getAccountUserId());
            userDo.setUserBankcardIsbinded(1);
            userService.modifyUserSelective(userDo);
        }

        System.out.println(crResponse);
        BaseResult result = dealRecord.modifyDealRecordStateWithDouble(notifyMap);
        System.out.println("修改资金交易记录：" + result.isSuccess() + result.getMessage());
        PlainResult<CashRecordDO> cashrecorddo = cashrecordservice.queryCashRecordBySeqNo(orderNo);
        CashRecordDO cashrecord = cashrecorddo.getData();
        cashrecord.setCrResponse(crResponse);
        cashrecord.setCrResponseState(Integer.valueOf(resultCode));
        BaseResult cashresult = cashrecordservice.modifyCashRecordState(cashrecord);
        System.out.println("修改资金交易记录：" + cashresult.isSuccess() + cashresult.getMessage());
        RechargeRecordDO rechargeDo = new RechargeRecordDO();
        rechargeDo.setRechargeSeqNo(orderNo);
        rechargeDo.setRechargeOutSeqNo(ordId);
        if (resultCode.equals("000")) {
            rechargeDo.setRechargeState(1);
            nav.redirectToLocation("/account/myAccount/transactionInform");
        } else {
            rechargeDo.setRechargeState(2);
            context.put("ResultCode", resultCode);
            context.put("Message", resultDesc);
            nav.forwardTo("/error");
        }
        BaseResult rechargeresult = rechargeservice.updateBackStatus(rechargeDo);

    }
}
