/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.InvestSet;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestSetOpenState;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.invest.InvestSetService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 类实现描述 自动投标
 * 
 * @author Tiuwer 2015年3月30日 上午9:49:11
 */
public class AutomaticInvestJob implements BaseJob {
    private static final Logger logger = LoggerFactory.getLogger(AutomaticInvestJob.class);

    @Resource
    private LoanQueryService    loanQueryService;
    @Resource
    private InvestSetService    investSetService;
    @Resource
    private DoubleDryService    DoubleDryService;
    @Resource
    private InvestService       investService;
    @Resource
    private AccountInfoService  accountInfoService;
    @Resource
    private InvestQueryService  investQueryService;
    @Resource
    private DealRecordService   dealRecordService;

    @Override
    public void run() {
        //查询开启自动投资的设置
    	//这个定时器不要了
//        InvestSet investSet = new InvestSet();
//        investSet.setIsOpen(InvestSetOpenState.STATE_ENABLE);
//        ListResult<InvestSet> resultInvestSet = this.investSetService.queryInvest(investSet);
//        checkAutomaticInvest(resultInvestSet);
//        //重新查一次，防止有更新
//        resultInvestSet = this.investSetService.queryInvest(investSet);
//        automaticInvest(resultInvestSet);
    }

    //判断是否停用自动投标设置
    private void checkAutomaticInvest(ListResult<InvestSet> resultInvestSet) {
        for (InvestSet investSetResult : resultInvestSet.getData()) {
            UserIdentity userIdentity = new UserIdentity();
            userIdentity.setUserId(investSetResult.getUserId());
            userIdentity.setUserType(UserType.PERSONAL);
            PlainResult<AccountInfoDO> result = accountInfoService.queryByUserIdentity(userIdentity);
            if (result.getData() != null) {
//                Double[] userMonerys = this.DoubleDryService.queryBalance(result.getData().getAccountNo(), "2");
            	Double[] userMonerys = this.DoubleDryService.queryBalance(result.getData().getAccountMark(), "1");
                Double userMonery = userMonerys[1];
                if (BigDecimal.valueOf(userMonery).subtract(investSetResult.getSettMoney())
                        .compareTo(investSetResult.getInvestMoney()) == -1) {
                    investSetResult.setIsOpen(InvestSetOpenState.STATE_DISABLE);
                    this.investSetService.modifyInvest(investSetResult);
                }
                
            }
        }
    }

    /**
     * 自动投标
     * @param resultInvestSet
     */
    public void automaticInvest(ListResult<InvestSet> resultInvestSet) {

        //查询招标中的项目
        LoanSearchDO loanSearchDO = new LoanSearchDO();
        List<Integer> stateList = new ArrayList<Integer>();
        stateList.add(LoanState.BID_INVITING.state);
        loanSearchDO.setLoanState(stateList);
        //开始投标时间
        loanSearchDO.setLoanInvestStarttime(new Date());
        //开始结束时间
        loanSearchDO.setLoanInvestEndtime(new Date());
        ListResult<Loan> resultLoan = loanQueryService.queryLoanListBySearchParam(loanSearchDO);
        for (InvestSet investSetResult : resultInvestSet.getData()) {

            for (Loan loan : resultLoan.getData()) {
            	//判断标是否是自己的
            	if(loan.getLoanUserId()==investSetResult.getUserId()){
            		continue;
            	}
            	//判断该用户是否投资过此标，如果投资跳过
            	ListResult<Invest> invests=investService.findListByParam(loan.getLoanId());
            	if(invests.getData().size()>0){
            		continue;
            	}
            	
                boolean flag = this.checkLoan(loan, investSetResult);
                if (flag) {
                    Invest inv = new Invest();
                    inv.setUserId(investSetResult.getUserId());
                    inv.setBidType(BidType.COMMON_LOAN);
                    inv.setBidId(loan.getLoanId());
                    inv.setOriginId(loan.getLoanId());
                    if (loan.getLoanMaxInvest().compareTo(investSetResult.getInvestMoney()) == 1 || 
                    		loan.getLoanMaxInvest().compareTo(investSetResult.getInvestMoney()) == 0) {
                        inv.setInvestMoney(investSetResult.getInvestMoney());
                    } else {
                        inv.setInvestMoney(loan.getLoanMaxInvest());
                    }
                    //调用投资接口
                    investService.createInvest(inv,null);
                }
            }
        }
    }

    /**
     * 检查项目是否符合自动投标设置的条件
     * @param loan
     * @param investSetResult
     * @return
     */
    private boolean checkLoan(Loan loan, InvestSet investSetResult) {
        int flag;
        if (loan == null || investSetResult == null) {
            logger.error("项目或自动投标设置没有找到！");
            return false;
        }
        //判断项目最大金额
        flag = loan.getLoanMoney().compareTo(investSetResult.getMaxMoney());

        if (flag == -1 || flag == 0) {
            //判断项目最小金额
            flag = loan.getLoanMoney().compareTo(investSetResult.getMinMoney());

            if (flag == 1 || flag == 0) {
                //判断项目类型
                if (loan.getLoanCategory().equals(investSetResult.getLoanCategory())) {
                    //判断项目年利率
                    flag = loan.getLoanRate().compareTo(investSetResult.getMaxRate());

                    if (flag == -1 || flag == 0) {
                        //判断最小年利率
                        flag = loan.getLoanRate().compareTo(investSetResult.getMinRate());
                        if (flag == 1 || flag == 0) {
                            //判断借款期限
                            int loanPeriod = loan.getLoanPeriod();
                            if (loan.getLoanPeriodUnit().equals(LoanPeriodUnit.YEAR)) {
                                loanPeriod = loan.getLoanPeriod() * 12;
                            }
                            if (investSetResult.getMinLoanPeriod() <= loanPeriod) {
                                if (loanPeriod <= investSetResult.getMaxLoanPeriod()) {
                                    if (loan.getLoanPayType().equals(investSetResult.getLoanType())
                                            && (loan.getLoanMinInvest().compareTo(investSetResult.getInvestMoney()) == -1
                                            || loan.getLoanMinInvest().compareTo(investSetResult.getInvestMoney()) ==0 )) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
