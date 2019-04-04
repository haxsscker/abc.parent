/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashBorrowerViewDO;
import com.autoserve.abc.dao.intf.CashBorrowerViewDao;
import com.autoserve.abc.service.biz.intf.cash.CashBorrowerService;
import com.autoserve.abc.service.biz.intf.cash.UserAccountService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 类CashBorrowerServiceImpl.java的实现描述：借款人资金对账表(视图)
 * 
 * @author J.YL 2014年11月18日 上午11:36:18
 */
@Service
public class CashBorrowerServiceImpl implements CashBorrowerService {
    @Resource
    private UserAccountService  userAccount;

    @Resource
    private CashBorrowerViewDao cashBorrowerViewDao;

    /**
     * 通过借款人的用户名进行统计借款人借款信息详情
     */
    @Override
    public PageResult<CashBorrowerViewDO> queryCashBorrower(String borrowerName, PageCondition pageCondition) {
        int borrowerNum = cashBorrowerViewDao.getBorrowerNum(borrowerName);
        //查询已经生效的还款记录总和
        List<CashBorrowerViewDO> resultList = cashBorrowerViewDao.queryBorrowerDetail(borrowerName, pageCondition);
        if (CollectionUtils.isEmpty(resultList)) {
            resultList = new ArrayList<CashBorrowerViewDO>();
        }
        //PageResult<CashBorrowerViewDO> result = process(resultList, pageCondition);
        PageResult<CashBorrowerViewDO> result = new PageResult<CashBorrowerViewDO>(pageCondition.getPage(),
                pageCondition.getPageSize());        
        result.setData(resultList);
        result.setTotalCount(borrowerNum);
        return result;
    }

    /**
     * 通过借款人的用户名进行统计借款人借款信息详情
     */
    @Override
    public PlainResult<CashBorrowerViewDO> queryCashBorrower(int userId) {
        //查询已经生效的还款记录总和
        List<CashBorrowerViewDO> resultList = cashBorrowerViewDao.queryBorrowerDetailByUserId(userId);
        if (CollectionUtils.isEmpty(resultList)) {
            resultList = new ArrayList<CashBorrowerViewDO>();
        }
       // PageResult<CashBorrowerViewDO> result = process(resultList, new PageCondition());

        PlainResult<CashBorrowerViewDO> plainResult = new PlainResult<CashBorrowerViewDO>();
        if (resultList != null && resultList.size() > 0) {
            plainResult.setData(resultList.get(0));
        }
        return plainResult;
    }
    /**
     * 计算的借款人的借款详情
     */
    private PageResult<CashBorrowerViewDO> process(List<CashBorrowerViewDO> resultList, PageCondition pageCondition) {
        PageResult<CashBorrowerViewDO> result = new PageResult<CashBorrowerViewDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        List<Integer> loanees = Lists.transform(resultList, new Function<CashBorrowerViewDO, Integer>() {
            @Override
            public Integer apply(CashBorrowerViewDO cashBorrowerView) {
                return cashBorrowerView.getPpLoanee();
            }
        });
        //代还总和
        List<CashBorrowerViewDO> platform = new ArrayList<CashBorrowerViewDO>();
        if (!CollectionUtils.isEmpty(loanees)) {
            platform = cashBorrowerViewDao.queryPlatformPay(loanees);
        }
        Map<Integer, CashBorrowerViewDO> platformMap = Maps.uniqueIndex(platform,
                new Function<CashBorrowerViewDO, Integer>() {
                    @Override
                    public Integer apply(CashBorrowerViewDO cashBorrower) {
                        return cashBorrower.getPpLoanee();
                    }

                });
        //对代还的进行处理
        for (CashBorrowerViewDO cashBorrower : resultList) {
            if (CollectionUtils.isEmpty(platformMap)) {
                break;
            }
            CashBorrowerViewDO temp = platformMap.get(cashBorrower.getPpLoanee());
            if (temp != null) {
                //实还担保费
                BigDecimal collectGuarFee = cashBorrower.getPpCollectGuarFee();
                //实还服务费
                BigDecimal collectServiceFee = cashBorrower.getPpCollectServiceFee();
                //实还总额
                BigDecimal collectTotal = cashBorrower.getPpCollectTotal();
                //实还本金
                BigDecimal payCapital = cashBorrower.getPpPayCollectCapital();
                //实还利息
                BigDecimal payInterest = cashBorrower.getPpPayCollectInterest();
                //实还逾期罚金
                BigDecimal payFine = cashBorrower.getPpPayCollectFine();
                //实还违约罚金 add by 夏同同  20160419
                BigDecimal payBreachFine = cashBorrower.getPpPayCollectBreachFine();
                cashBorrower.setPpCollectGuarFee(collectGuarFee.subtract(temp.getPpCollectGuarFee()));
                cashBorrower.setPpCollectServiceFee(collectServiceFee.subtract(temp.getPpCollectServiceFee()));
                cashBorrower.setPpCollectTotal(collectTotal.subtract(temp.getPpCollectTotal()));
                cashBorrower.setPpPayCollectCapital(payCapital.subtract(temp.getPpPayCollectCapital()));
                cashBorrower.setPpPayCollectInterest(payInterest.subtract(temp.getPpPayCollectInterest()));
                cashBorrower.setPpPayCollectFine(payFine.subtract(temp.getPpPayCollectFine()));
                //实还违约罚金 add by 夏同同  20160419
                cashBorrower.setPpPayCollectBreachFine(payBreachFine.subtract(temp.getPpPayCollectBreachFine()));
            }
        }
        result.setData(resultList);
        return result;
    }
}
