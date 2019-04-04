package com.autoserve.abc.dao.intf;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.dao.dataobject.PaymentPlanJDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.search.PaymentPlanSearchDO;
import com.autoserve.abc.dao.dataobject.summary.LoanPaymentSummaryDO;
import com.autoserve.abc.dao.dataobject.summary.PaymentPlanSummaryDO;

public interface PaymentPlanDao extends BaseDao<PaymentPlanDO, Integer> {

    /**
     * 根据还款计划id查询单条还款计划，加行级独占锁
     * 
     * @param loanId
     * @return LoanDO
     */
    PaymentPlanDO findWithLock(int paymentPlanId);

    /**
     * ［内部］批量更改还款计划的状态
     * 
     * @param fullTransRecordId 满标资金划转记录id
     * @param oldState 原来的状态, 必选
     * @param newState 要更改为的新状态, 必选
     * @return int 更改的条数
     */
    int batchUpdateStateByFullTransRecordId(@Param("fullTransRecordId") Integer fullTransRecordId,
                                            @Param("oldState") int oldState, @Param("newState") int newState);

    /**
     * 更改指定内部交易流水号对应的还款计划的状态
     *
     * @param innerSeqNo 内部交易流水号，必选
     * @param oldState 旧的状态，必选
     * @param newState 新的状态，必选
     * @param newReplaceState 新的平台是否已经代还状态，可选
     * @return int
     */
    int updateStateByInnerSeqNo(@Param("innerSeqNo") String innerSeqNo, @Param("oldState") int oldState,
                                @Param("newState") int newState, @Param("newReplaceState") Boolean newReplaceState);

    /**
     * 还款计划应还罚息、应还总额、实还的罚息、实还总额字段增加罚息
     * 夏同同   新增更新违约罚金 pulishBreachMoney
     * @param paymentPlanId 还款计划id，必选
     * @param punishMoney 罚息，必选
     * @param newState 新的还款计划状态，必选
     * @return int
     */
    int updateByAddPunishMoney(@Param("paymentPlanId") int paymentPlanId, @Param("punishMoney") BigDecimal punishMoney,
    		 @Param("pulishBreachMoney") BigDecimal pulishBreachMoney,@Param("newState") int newState);

    /**
     * 修改还款计划表的状态，还款类型，内部交易流水号，实还日期
     * 
     * @param paymentPlanId 还款计划id，必选
     * @param oldState 原来的还款状态, 必选
     * @param newState 要更改为的新还款状态, 必选
     * @param newType 要更改为的新还款类型, 必选
     * @param newInnerSeqNo 要更改为的内部交易流水号, 必选
     * @return int
     */
    int updateStateTypeInneSeq(@Param("paymentPlanId") int paymentPlanId, @Param("oldState") int oldState,
                               @Param("newState") int newState, @Param("newType") int newType,
                               @Param("newInnerSeqNo") String newInnerSeqNo);

    /**
     * 查询列表
     * 
     * @param plan 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<PaymentPlanDO>
     */
    List<PaymentPlanJDO> findPlanList(@Param("pageCondition") PageCondition pageCondition);
    
    /**
     * 查询列表2
     * @param pp
     * @param pageCondition
     * @return
     */
    List<PaymentPlanJDO> findPlanList2(@Param("pp") PaymentPlanSearchDO pp, @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询总数
     * 
     * @param int
     */
    int countPlanList();
    
    /**
     * 查询总数2
     * @param pp
     * @return
     */
    int countPlanList2(PaymentPlanSearchDO pp);

    /**
     * 统计按条件查询的分页结果总数
     * 
     * @param plan 查询条件，可选
     * @return int 符合条件的总数
     */
    int countListByParam(@Param("plan") PaymentPlanDO plan);

    /**
     * 查询借款人loaneeId在loanId借款中的还款计划状态为payPlanState的累计债权总额
     *
     * @param loaneeId 借款人id，必选
     * @param loanId 借款标id，可选
     * @param payPlanState 还款计划状态，可选
     * @return BigDecimal 满足条件的债权总额
     */
    BigDecimal sumCapitalByLoaneeId(@Param("loaneeId") int loaneeId, @Param("loanId") Integer loanId,
                                    @Param("paymentPlanState") Integer paymentPlanState);

    /**
     * 根据内部交易流水号查询还款计划
     *
     * @param innerSeqNo 内部交易流水号，必选
     * @return PaymentPlanDO
     */
    PaymentPlanDO findPaymentPlanByInnerSeqNo(@Param("innerSeqNo") String innerSeqNo);

    /**
     * 查询借款标要进行的下一期还款计划
     * 
     * @param loanId 普通借款标id
     * @param paymentState 下一期还款计划状态
     * @return PaymentPlanDO
     */
    PaymentPlanDO findNextPaymentPlan(@Param("loanId") int loanId, @Param("paymentState") int paymentState);
    
    /**
     * 查询借款标未还清且不是平台代还的还款计划
     * add by 夏同同 20160507
     * @param loanId 普通借款标id
     * @param paymentState 下一期还款计划状态
     * @return PaymentPlanDO
     */
    PaymentPlanDO findNextPaymentNoReplacePlan(@Param("loanId") int loanId, @Param("paymentState") int paymentState);

    /**
     * 按条件查询分页结果
     * 
     * @param loan 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<T>
     */
    List<PaymentPlanDO> findListByParam(@Param("plan") PaymentPlanDO plan,
                                        @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询loanIds列表对应的还款计划概要信息
     * 
     * @param loanIds 借款id列表，必选
     * @param paymentPlanStates 还款计划状态查询条件，可选
     * @return ListResult<PaymentPlanSummary>
     */
    List<LoanPaymentSummaryDO> findSummaryListByIds(@Param("loanIds") List<Integer> loanIds,
                                                    @Param("paymentPlanStates") List<Integer> paymentPlanStates);

    /**
     * 根据满标资金划转记录id查找对应的还款计划列表
     * 
     * @param fullTransferRecordId 满标资金划转记录id
     * @return List<PaymentPlanDO>
     */
    List<PaymentPlanDO> findByFullTransferRecordId(int fullTransferRecordId);

    /**
     * 添加查询条件
     * 
     * @param loanId 普通借款标id
     * @param paymentState 下一期还款计划状态
     * @param investSearchJDO
     * @return PaymentPlanDO
     */
    PaymentPlanDO findNextPaymentPlanBySearch(@Param("loanId") int loanId, @Param("paymentState") int paymentState,
                                              @Param("searchJDO") InvestSearchJDO investSearchJDO);

    /**
     * 查詢所有計劃總和錢數
     * 
     * @param loanId 普通借款标id
     * @param paymentState 下一期还款计划状态
     * @param investSearchJDO
     * @return PaymentPlanDO
     */
    PaymentPlanSummaryDO findTotalByLoanId(@Param("loanId") int loanId,
                                           @Param("searchJDO") InvestSearchJDO investSearchJDO);

    /**
     * 根据原始标号查询还款计划
     * 
     * @param loanId
     * @return LoanDO
     */
    List<PaymentPlanDO> findByLoanId(int loanId);

    /**
     * 查询某个用户的所有还款金额
     * 
     * @param userId
     * @return
     */
    BigDecimal queryTotalPayMoneyByUserId(@Param("userId") Integer userId);

    /**
     * 查询本月待付本金
     * 
     * @param userId
     * @return
     */
    BigDecimal queryCurMonthWaitPayCapital(Integer userId);

    /**
     * 查询本月待付利息
     * 
     * @param userId
     * @return
     */
    BigDecimal queryCurMonthWaitPayInterest(Integer userId);
   
    /**
     * 查询本标本期未还款的本金
     * @param loanId
     * @param period
     * @return
     */
    BigDecimal queryRemainPrincipalByLoanidAndPeriod(@Param("loanId") int loanId,@Param("period") int period);
    
    
    /**
     * 查询本标本期未还款的利息  add by 夏同同 20160412
     * @param loanId
     * @param period
     * @return
     */
    BigDecimal queryRemainInterestByLoanidAndPeriod(@Param("loanId") int loanId,@Param("period") int period);
    
    /**
     * 查询某用户待还本金之和
     * 
     * @param userId
     * @return
     */
    BigDecimal queryWaitPayCapital(Integer userId);
    
    /**
     * 查询用户按天汇总的还款计划
     * @param userId
     * @return
     */
    List<PaymentPlanDO> queryPaymentPlanByDay(@Param("userId")Integer userId, @Param("pageCondition")PageCondition pageCondition);
    /**
     * 查询用户按天汇总的还款计划的总个数
     * @param userId
     * @return
     */
    int countQueryPaymentPlanByDay(Integer userId);
    
    /**
     * add by 夏同同  20160518 计算当前日期之前的数据数目
     */
    int countListBeforeToday(Integer userId);
    /**
     * 删除项目的还款计划
     * @param loanId
     */
	void batchDeletePlanByLoanId(@Param("loanId")Integer loanId);
	/**
	 * 查询还款计划最后一期
	 * @param loanId
	 * @return
	 */
	int findMaxPeriodByLoanId(int loanId);

	PaymentPlanDO findAheadPaymentPlan(@Param("loanId")Integer loanId, @Param("paymentState")Integer paymentState, @Param("ppPayType")Integer ppPayType);
}
