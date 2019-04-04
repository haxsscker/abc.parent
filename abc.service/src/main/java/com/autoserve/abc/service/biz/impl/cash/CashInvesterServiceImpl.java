/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashInvesterViewDO;
import com.autoserve.abc.dao.intf.CashInvesterViewDao;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashInvesterService;
import com.autoserve.abc.service.biz.intf.cash.UserAccountService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 类CashInvesterServiceImpl.java的实现描述：投资人资金对账表(视图)
 * 
 * @author J.YL 2014年11月18日 上午11:37:52
 */
@Service
public class CashInvesterServiceImpl implements CashInvesterService {

    @Resource
    private UserAccountService  userAccountService;
    @Resource
    private AccountInfoService  account;
    @Resource
    private UserAccountService  userAccount;
    @Resource
    private CashInvesterViewDao cashInvesterViewDao;
    @Resource
    private InvestDao investDao;

    @Override
    public PageResult<CashInvesterViewDO> queryCashInvester(String investorName, String investorRealName,
                                                            PageCondition pageCondition) {
        PageResult<CashInvesterViewDO> result = new PageResult<CashInvesterViewDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        int total = cashInvesterViewDao.countInvesterNum(investorName, investorRealName);
        List<CashInvesterViewDO> investers = cashInvesterViewDao.findInvesters(investorName, investorRealName,
                pageCondition);
        if (CollectionUtils.isEmpty(investers)) {
            result.setTotalCount(0);
            result.setData(new ArrayList<CashInvesterViewDO>());
            return result;
        }
        return process(investers, total, result);
    }

    @Override
    public PlainResult<CashInvesterViewDO> queryCashInvester(int userId) {

        PlainResult<CashInvesterViewDO> result = new PlainResult<CashInvesterViewDO>();

        CashInvesterViewDO investers = cashInvesterViewDao.findInvestersByUserId(userId);

        List<CashInvesterViewDO> list = new ArrayList<CashInvesterViewDO>();

        PageResult<CashInvesterViewDO> pageResult = new PageResult<CashInvesterViewDO>(new PageCondition());

        list.add(investers);

        pageResult = process(list, 1, pageResult);

        if (pageResult.getData() != null && pageResult.getData().size() > 0)
            result.setData(pageResult.getData().get(0));

        return result;
    }

    private PageResult<CashInvesterViewDO> process(List<CashInvesterViewDO> investers, int total,
                                                   PageResult<CashInvesterViewDO> result) {
        List<Integer> userIds = Lists.transform(investers, new Function<CashInvesterViewDO, Integer>() {

            @Override
            public Integer apply(CashInvesterViewDO input) {
                return input.getAccountUserId();
            }
        });
        //查询投资人投资收益
        List<CashInvesterViewDO> income = cashInvesterViewDao.findInvesterIncome(userIds);
        //查询投资人投资详情
        List<CashInvesterViewDO> invest = cashInvesterViewDao.findInvesterInvester(userIds);
        //查询投资人收购详情
        List<CashInvesterViewDO> purchase = cashInvesterViewDao.findInvesterPurchase(userIds);
        //查询投资人转让详情
        List<CashInvesterViewDO> transfer = cashInvesterViewDao.findInvesterTransfer(userIds);
        //查询投资人被收购详情
        List<CashInvesterViewDO> transferByPurchase = cashInvesterViewDao.findInvesterTransferByPurchase(userIds);
        if (CollectionUtils.isEmpty(income) || income.get(0) == null) {
            income = new ArrayList<CashInvesterViewDO>();
        }
        if (CollectionUtils.isEmpty(invest) || invest.get(0) == null) {
            invest = new ArrayList<CashInvesterViewDO>();
        }
        if (CollectionUtils.isEmpty(transfer) || transfer.get(0) == null) {
            transfer = new ArrayList<CashInvesterViewDO>();
        }
        if (CollectionUtils.isEmpty(purchase) || purchase.get(0) == null) {
            purchase = new ArrayList<CashInvesterViewDO>();
        }
        if (CollectionUtils.isEmpty(transferByPurchase) || transferByPurchase.get(0) == null) {
            transferByPurchase = new ArrayList<CashInvesterViewDO>();
        }
        Map<Integer, CashInvesterViewDO> incomeMap = Maps.uniqueIndex(income,
                new Function<CashInvesterViewDO, Integer>() {

                    @Override
                    public Integer apply(CashInvesterViewDO cashInvester) {
                        if (cashInvester == null) {
                            return null;
                        }
                        return cashInvester.getAccountUserId();
                    }
                });
        Map<Integer, CashInvesterViewDO> investMap = Maps.uniqueIndex(invest,
                new Function<CashInvesterViewDO, Integer>() {

                    @Override
                    public Integer apply(CashInvesterViewDO cashInvester) {
                        if (cashInvester == null) {
                            return null;
                        }
                        return cashInvester.getAccountUserId();
                    }
                });
        Map<Integer, CashInvesterViewDO> purchaseMap = Maps.uniqueIndex(purchase,
                new Function<CashInvesterViewDO, Integer>() {

                    @Override
                    public Integer apply(CashInvesterViewDO cashInvester) {
                        if (cashInvester == null) {
                            return null;
                        }
                        return cashInvester.getAccountUserId();
                    }
                });
        Map<Integer, CashInvesterViewDO> transferMap = Maps.uniqueIndex(transfer,
                new Function<CashInvesterViewDO, Integer>() {

                    @Override
                    public Integer apply(CashInvesterViewDO cashInvester) {
                        if (cashInvester == null) {
                            return null;
                        }
                        return cashInvester.getAccountUserId();
                    }
                });
        Map<Integer, CashInvesterViewDO> transferByPurchaseMap = Maps.uniqueIndex(transferByPurchase,
                new Function<CashInvesterViewDO, Integer>() {

                    @Override
                    public Integer apply(CashInvesterViewDO cashInvester) {
                        if (cashInvester == null) {
                            return null;
                        }
                        return cashInvester.getAccountUserId();
                    }
                });
        List<CashInvesterViewDO> resultList = new ArrayList<CashInvesterViewDO>();
        for (CashInvesterViewDO civd : investers) {
            Integer userId = civd.getAccountUserId();
            CashInvesterViewDO inc = incomeMap.get(userId);
            CashInvesterViewDO pur = purchaseMap.get(userId);
            CashInvesterViewDO inv = investMap.get(userId);
            CashInvesterViewDO tra = transferMap.get(userId);
            CashInvesterViewDO traPur = transferByPurchaseMap.get(userId);
            if (inc != null) {
                civd.setPiCollectCapital(inc.getPiCollectCapital());//回收本金
                civd.setPiCollectFine(inc.getPiCollectFine());//回收罚息
                civd.setPiCollectInterest(inc.getPiCollectInterest());//回收利息
                civd.setPiCollectTotal(inc.getPiCollectTotal());
                civd.setPiPayCapital(inc.getPiPayCapital());
                civd.setPiPayInterest(inc.getPiPayInterest());
            }
            if (pur != null) {
                civd.setBlFee(pur.getBlFee());
                civd.setBlBuyTotal(pur.getBlBuyTotal());
            }
            if (inv != null) {
                //债权
            	//TODO 投资金额
//                civd.setInInvestMoney(inv.getInInvestMoney());
                civd.setInInvestMoney(investDao.tzze(userId));
                civd.setInValidInvestMoney(inv.getInValidInvestMoney());//投资金额
                civd.setInValidInvestMoneyTransfer(inv.getInValidInvestMoneyTransfer());
            }
//            if (tra != null) {
//                civd.setTlTransferFee(tra.getTlTransferFee());
                civd.setTlTransferFee(investDao.zrsxf(userId));
//            }
            if (traPur != null) {
                civd.setBlsTransferMoney(traPur.getBlsTransferMoney());
            }
            resultList.add(civd);
        }
        result.setTotalCount(total);
        result.setData(resultList);
        return result;
    }
}
