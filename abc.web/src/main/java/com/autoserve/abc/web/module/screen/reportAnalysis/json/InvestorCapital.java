/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashInvesterViewDO;
import com.autoserve.abc.dao.dataobject.UserAccountDO;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashInvesterService;
import com.autoserve.abc.service.biz.intf.cash.UserAccountService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.reportAnalysis.InvestorCaptialVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 投资人资金对账表
 * 
 * @author J.YL 2014年12月19日 上午10:46:58
 */
public class InvestorCapital {
    @Resource
    private CashInvesterService cashInvesterService;

    @Resource
    private UserAccountService  userAccountService;

    @Resource
    private AccountInfoService  accountInfoService;
    
    @Resource
    private InvestService investService;

    public JsonPageVO<InvestorCaptialVO> execute(ParameterParser params) {
        String model = params.getString("modelAction");
        String investorName = null;
        String investorRealName = null;
        if (model != null) {
            //investorName = params.getString("investorName");
            investorRealName = params.getString("investorRealName");
        }
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        JsonPageVO<InvestorCaptialVO> resultVO = new JsonPageVO<InvestorCaptialVO>();
        PageResult<CashInvesterViewDO> queryResult = cashInvesterService.queryCashInvester(investorName,
                investorRealName, pageCondition);
        if (!queryResult.isSuccess()) {
            resultVO.setMessage(queryResult.getMessage());
            resultVO.setTotal(0);
            resultVO.setRows(new ArrayList<InvestorCaptialVO>());
            return resultVO;
        }
        List<CashInvesterViewDO> queryData = queryResult.getData();
        List<UserIdentity> userIds = new ArrayList<UserIdentity>();
        for (CashInvesterViewDO civd : queryData) {
            UserIdentity ui = new UserIdentity();
            ui.setUserId(civd.getAccountUserId());
            ui.setUserType(UserType.valueOf(civd.getAccountUserType()));
            userIds.add(ui);
        }
        List<String> accountNos = Lists.transform(queryData, new Function<CashInvesterViewDO, String>() {
            @Override
            public String apply(CashInvesterViewDO ac) {
                return ac.getAccountNo();
            }

        });
        ListResult<UserAccountDO> ua = userAccountService.queryByAccountNo(accountNos);
        if (!ua.isSuccess()) {
            resultVO.setMessage(ua.getMessage());
            resultVO.setTotal(0);
            resultVO.setRows(new ArrayList<InvestorCaptialVO>());
            return resultVO;
        }
        List<UserAccountDO> userAccounts = ua.getData();
        Map<String, UserAccountDO> userAccountMap = Maps.uniqueIndex(userAccounts,
                new Function<UserAccountDO, String>() {

                    @Override
                    public String apply(UserAccountDO ua) {
                        return ua.getUaAccountNo();
                    }

                });
        List<InvestorCaptialVO> resultList = new ArrayList<InvestorCaptialVO>();
        for (CashInvesterViewDO civd : queryData) {
//            if (userAccountMap.get(civd.getAccountNo()) == null) {
//                continue;
//            }
            InvestorCaptialVO temp = new InvestorCaptialVO();
            temp.setCustomer_name(civd.getAccountUserName());
            temp.setReal_name(civd.getUserRealName());
//            BigDecimal aomountFrozen = userAccountMap.get(civd.getAccountNo()).getUaFrozen();
//            BigDecimal assets_total = userAccountMap.get(civd.getAccountNo()).getUaTotalMoney();
//            BigDecimal availableBalance = userAccountMap.get(civd.getAccountNo()).getUaUseableMoney();
//            temp.setAmountFrozen(aomountFrozen);
//            temp.setAssets_total(assets_total);
//            temp.setAvailableBalance(availableBalance);

            temp.setPurchase_money(civd.getBlBuyTotal()); //收购债权
            temp.setPurchasefee(civd.getBlFee()); //收购手续费
            temp.setPro_collect_money(civd.getPiCollectCapital());//回收本金
            temp.setPro_collect_over_rate(civd.getPiCollectFine());//回收罚息
            temp.setPro_collect_rate(civd.getPiCollectInterest());//回收利息
            temp.setPro_invest_money(civd.getInValidInvestMoney());//投资金额
//            BigDecimal trasferMoney = BigDecimal.ZERO;
//            if (civd.getTlTransferTotal() != null) {
//                trasferMoney = trasferMoney.add(civd.getTlTransferTotal());
//            }
//            if (civd.getBlsTransferMoney() != null) {
//                trasferMoney = trasferMoney.add(civd.getBlsTransferMoney());
//            }
//            temp.setTransfer_money(trasferMoney);//转让债权
            temp.setTransfer_money(investService.zrzq(civd.getAccountUserId()));//转让债权
            temp.setTransfer_fee(civd.getTlTransferFee());//转让手续费
//            BigDecimal buyMoney = BigDecimal.ZERO;
//            //            if (civd.getBlBuyTotal() != null) {
//            //                buyMoney = buyMoney.add(civd.getBlBuyTotal());
//            //            }
//            if (civd.getInValidInvestMoneyTransfer() != null) {
//                buyMoney = buyMoney.add(civd.getInValidInvestMoneyTransfer());
//            }
            temp.setBuy_money(investService.mrzq(civd.getAccountUserId()));//买入债权
            resultList.add(temp);
        }
        resultVO.setTotal(queryResult.getTotalCount());
        resultVO.setRows(resultList);
        return resultVO;
    }
}
