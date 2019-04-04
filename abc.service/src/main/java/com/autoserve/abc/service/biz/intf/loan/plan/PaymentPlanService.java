/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.loan.plan;

import java.math.BigDecimal;
import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.dao.dataobject.PaymentPlanJDO;
import com.autoserve.abc.dao.dataobject.SmsNotifyDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.search.PaymentPlanSearchDO;
import com.autoserve.abc.dao.dataobject.summary.LoanPaymentSummaryDO;
import com.autoserve.abc.dao.dataobject.summary.PaymentPlanSummaryDO;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 还款计划服务
 * 
 * @author segen189 2014年11月29日 下午12:06:45
 */
public interface PaymentPlanService {
    /**
     * ［内部］批量生成借款人的还款计划
     * 
     * @param incomePlanList 待添加的还款计划
     * @return PlainResult<Integer> 新增的条数
     */
    PlainResult<Integer> batchCreatePaymentPlanList(List<PaymentPlan> paymentPlanList);

    /**
     * ［内部］批量更改满标资金划转记录id对应的还款计划的状态
     * 
     * @param fullTransRecordId 满标资金划转记录id, 必选
     * @param oldState 原来的状态, 必选
     * @param newState 要更改为的新状态, 必选
     * @return PlainResult<Integer> 更改的条数
     */
    PlainResult<Integer> batchModifyStateByFullTransRecordId(int fullTransRecordId, PayState oldState, PayState newState);

    /**
     * ［内部］批量更改指定内部交易流水号对应的还款计划的状态
     * 
     * @param innerSeqNo 内部交易流水号, 必选
     * @param oldState 原来的状态, 必选
     * @param newState 要更改为的新状态, 必选
     * @param newReplaceState 要更改为的平台是否已经代还标识, 可选
     * @param paymentPlan 修改已还信息
     * @return PlainResult<Integer> 更改的条数
     */
    PlainResult<Integer> modifyPaymentPlan(String innerSeqNo, PayState oldState, PayState newState,
                                           Boolean newReplaceState, PaymentPlan paymentPlan);

    /**
     * 修改还款计划应还罚息、应还总额、实还的罚息、实还总额
     * 夏同同   新增更新违约罚金 pulishBreachMoney
     * @param paymentPlanId 还款计划id，必选
     * @param punishMoneyIncreasing 罚息，可选
     * @param newState 新的还款计划状态，必选
     * @return BaseResult 如果没有还款计划被修改，则返回错误结果
     */
    BaseResult modifyPaymentPlan(int paymentPlanId, BigDecimal punishMoneyIncreasing,
    		BigDecimal pulishBreachMoney, PayState newState);

    /**
     * 修改还款计划表
     * 
     * @param paymentPlanId 还款计划id，必选
     * @param oldState 原来的还款状态, 必选
     * @param newState 要更改为的新还款状态, 必选
     * @param newPayType 要更改为的新还款类型, 必选
     * @param newInnerSeqNo 要更改为的内部交易流水号, 必选
     * @return BaseResult 如果没有还款计划被修改，则返回错误结果
     */
    BaseResult modifyPaymentPlan(int paymentPlanId, PayState oldState, PayState newState, PayType newPayType,
                                 String newInnerSeqNo);

    /**
     * 查询借款人loaneeId在loanId借款中的还款计划状态为payPlanState的累计债权总额
     * 
     * @param loaneeId 借款人id，必选
     * @param loanId 借款标id，必选
     * @param payPlanState 还款计划状态，可选
     * @return PlainResult<BigDecimal> 满足条件的债权总额
     */
    PlainResult<BigDecimal> sumCapitalByLoaneeIdAndLoanId(int loaneeId, int loanId, PayState payPlanState);

    /**
     * 根据内部交易流水号查询还款计划
     * 
     * @param innerSeqNo 还款时创建的内部交易流水号，必选
     * @return PlainResult<PaymentPlan>
     */
    PlainResult<PaymentPlan> queryPaymentPlanByInnerSeqNo(String innerSeqNo);

    /**
     * 查询借款标要进行还款的还款计划
     * 
     * @param loanId 普通借款标id
     * @return PlainResult<PaymentPlan> 如果借款已还清则PlainResult的data为null
     */
    PlainResult<PaymentPlan> queryNextPaymentPlan(int loanId);
    
    /**
     * 查询下一条未还清且不是平台待还的还款计划
     * add by 夏同同 20160507
     * @param loanId 普通借款标id
     * @return PlainResult<PaymentPlan> 如果借款已还清则PlainResult的data为null
     */
    PlainResult<PaymentPlan> queryNextPaymentNoReplacePlan(int loanId);

    /**
     * 查询分页的还款计划列表
     * 
     * @param pojo 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<PaymentPlanJDO>
     */
    PageResult<PaymentPlanJDO> queryPlanList(PageCondition pageCondition);
    /**
     * 查询分页的还款计划列表
     * @param pp
     * @param pageCondition
     * @return
     */
	PageResult<PaymentPlanJDO> queryPlanList2(PaymentPlanSearchDO pp,
			PageCondition pageCondition);

    /**
     * 根据参数分页查询还款计划
     * 
     * @param pojo 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<PaymentPlanJDO>
     */
    PageResult<PaymentPlan> queryPaymentPlanList(PaymentPlan paymentPlan, PageCondition pageCondition);

    /**
     * 根据满标资金划转记录id，查询对应的还款计划列表
     * 
     * @param fullTransferRecordId 满标资金划转记录
     * @return ListResult<PaymentPlan>
     */
    ListResult<PaymentPlan> queryPaymentPlanList(int fullTransferRecordId);

    /**
     * 查询loanIds列表对应的还款计划概要信息
     * 
     * @param loanIds 借款id列表，必选
     * @return ListResult<PaymentPlanSummary>
     */
    ListResult<LoanPaymentSummaryDO> querySummaryByIds(List<Integer> loanIds);

    /**
     * 查询借款标要进行还款的还款计划(添加查找条件)
     * 
     * @param loanId 普通借款标id
     * @return PlainResult<PaymentPlan> 如果借款已还清则PlainResult的data为null
     */
    PlainResult<PaymentPlan> queryNextPaymentPlan(int loanId, InvestSearchJDO InvestSearchJDO);

    /**
     * 根据loanid查询所有标的总费用
     * 
     * @param loanId 普通借款标id
     * @return PlainResult<PaymentPlan> 如果借款已还清则PlainResult的data为null
     */
    PlainResult<PaymentPlanSummaryDO> queryTotalByLoanId(int loanId, InvestSearchJDO InvestSearchJDO);

    /**
     * 根据ID查询费用
     */
    PlainResult<PaymentPlan> queryById(int planId);

    /**
     * 根据ID查询费用
     */
    ListResult<PaymentPlanDO> queryByLoanId(int loanId);

    /**
     * 查询当月待付本金
     * 
     * @param userId
     * @return
     */
    PlainResult<BigDecimal> queryCurMonthWaitPayCapital(int userId);

    /**
     * 查询当月待付利息
     * 
     * @param userId
     * @return
     */
    PlainResult<BigDecimal> queryCurMonthWaitPayInterest(int userId);
    /**
     * 查询本标本期未还款的本金
     * @param loanId 原始id
     * @param period 最近一期还款的期数
     * @return
     */
    PlainResult<BigDecimal> queryRemainPrincipalByLoanidAndPeriod(int loanId,int period);
    
	/**
     * @content:查询本标本期未还款的利息
     * @param loanId 原始id
     * @param period 最近一期还款的期数
     * @author:夏同同
     * @date:2016年4月10日 上午11:01:23
     */
    PlainResult<BigDecimal> queryRemainInterestByLoanidAndPeriod(int loanId,int period);
    
    /**
     * 查询某用户的待付本金
     * 
     * @param userId
     * @return
     */
    PlainResult<BigDecimal> queryWaitPayCapital(int userId);
    
    /**
     * 查询用户按天汇总的还款计划
     * @param userId
     * @param pageCondition
     * @return
     */
    PageResult<PaymentPlan> queryPaymentPlanByDay(Integer userId, PageCondition pageCondition);
    
    /**
     * 查询当前日期之前的还款计划数目
     * add by 夏同同 20160518
     */
    int queryCountListBeforeToday(Integer userId);
    
    /**
     * 将还款短信插入数据库
     * @param planPaymentId
     */
	BaseResult repaymentSmsNotify(Integer planPaymentId);
	/**
	 * 生成还款短信
	 * @param paymentPlanId
	 * @return
	 */
	SmsNotifyDO createSmsNotify(Integer paymentPlanId);

}
