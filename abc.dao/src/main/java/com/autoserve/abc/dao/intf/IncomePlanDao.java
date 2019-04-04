package com.autoserve.abc.dao.intf;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.IncomeJDO;
import com.autoserve.abc.dao.dataobject.IncomePlanDO;
import com.autoserve.abc.dao.dataobject.pdfBean.InvestInformationDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.stage.statistics.StatisticsPaymentPlan;

public interface IncomePlanDao extends BaseDao<IncomePlanDO, Integer> {

    /**
     * 批量更改收益计划的状态
     * 
     * @param fullTransRecordId 满标资金划转记录id, 必选
     * @param oldState 原来的状态, 必选
     * @param newState 要更改为的新状态, 必选
     * @return int 更改的条数
     */
    int batchUpdateStateByFullTransRecordId(@Param("fullTransRecordId") int fullTransRecordId,
                                            @Param("oldState") int oldState, @Param("newState") int newState);

    /**
     * 批量更改收益计划的状态
     * 
     * @param innerSeqNo 内部交易流水号，必选
     * @param oldState 旧的状态，必选
     * @param newState 新的状态，必选
     * @return int
     */
    int batchUpdateStateByInnerSeqNo(@Param("innerSeqNo") String innerSeqNo, @Param("oldState") int oldState,
                                     @Param("newState") int newState);

    /**
     * 批量更改收益计划的状态
     * 
     * @param userId 收益人id，必选
     * @param fullTransRecordId 满标资金划转记录id, 必选
     * @param oldState 原来的状态, 必选
     * @param newState 要更改为的新状态, 必选
     * @return int 更改的条数
     */
    int batchUpdateStateByUserIdAndFullTransRecordId(@Param("userId") int userId,
                                                     @Param("fullTransRecordId") int fullTransRecordId,
                                                     @Param("oldState") int oldState, @Param("newState") int newState);

    /**
     * 批量更改收益计划的状态
     * 
     * @param loanId 借款标id，必选
     * @param beneficiaryIdList 收益人id列表，可选
     * @param oldState 原来的状态, 必选
     * @param newState 要更改为的新状态, 必选
     * @return int 更改的条数
     */
    int batchUpdateStateByLoanId(@Param("loanId") int loanId,
                                 @Param("beneficiaryIdList") List<Integer> beneficiaryIdList,
                                 @Param("oldState") int oldState, @Param("newState") int newState);

    /**
     * 批量更改投资记录和收益计划的状态
     * 
     * @param fullTransRecordId 满标资金划转记录id, 必选
     * @param oldIncomeState 原来的收益状态, 必选
     * @param newIncomeState 要更改为的新收益状态, 必选
     * @param oldInvestState 原来的投资状态, 必选
     * @param newInvestState 要更改为的新投资状态, 必选
     * @return int 更改的条数
     */
    int batchUpdateIncomePlanAndInvestByFtrId(@Param("fullTransRecordId") int fullTransRecordId,
                                              @Param("oldIncomeState") int oldIncomeState,
                                              @Param("newIncomeState") int newIncomeState,
                                              @Param("oldInvestState") int oldInvestState,
                                              @Param("newInvestState") int newInvestState);

    /**
     * 批量更改投资记录和收益计划的状态
     * 
     * @param ftrId 满标划转记录id, 选填
     * @param loanId 原始普通标id, 必选
     * @param beneficiaryId 收益人id, 可选
     * @param oldIncomeState 原来的收益状态, 必选
     * @param newIncomeState 要更改为的新收益状态, 必选
     * @param oldInvestState 原来的投资状态, 必选
     * @param newInvestState 要更改为的新投资状态, 必选
     * @return int 更改的条数
     */
    int batchUpdateIncomePlanAndInvestByLoanId(@Param("fullTransRecordId") Integer ftrId, @Param("loanId") int loanId,
                                               @Param("beneficiaryId") Integer beneficiaryId,
                                               @Param("oldIncomeState") int oldIncomeState,
                                               @Param("newIncomeState") int newIncomeState,
                                               @Param("oldInvestState") int oldInvestState,
                                               @Param("newInvestState") int newInvestState);

    /**
     * 分配罚息更新收益计划表中投资人应还罚息、应还总额、实还的罚息、实还总额
     * 
     * @param payPlanId 还款计划id，必选
     * @param punishMoney 总罚息，必选
     * @param incomeState 收益计划表的状态，可选
     * @return int
     */
    int updateIncomePlanByAllocPunishMoney(@Param("payPlanId") int payPlanId, @Param("punishMoney") double punishMoney,
                                           @Param("state") Integer state);

    /**
     * 更改收益计划表的状态和内部交易流水号，实还日期
     * 
     * @param paymentPlanId 还款计划id，必选
     * @param oldState 原来的状态, 必选
     * @param newState 要更改为的新状态, 必选
     * @param innerSeqNo 要更改为的内部交易流水号, 必选
     * @return int
     */
    int updateStateAndInneSeq(@Param("paymentPlanId") int paymentPlanId, @Param("oldState") int oldState,
                              @Param("newState") int newState, @Param("innerSeqNo") String innerSeqNo);

    /**
     * 按条件查询分页结果
     * 
     * @param plan 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<IncomePlanDO>
     */
    List<IncomePlanDO> findListByParam(@Param("plan") IncomePlanDO plan,
                                       @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询指定状态且投资id在列表中的收益计划集合
     * 
     * @param incomePlanState 收益计划的状态，必选
     * @param loanId 借款标id，必选
     * @param beneficiaryIdList 收益人id列表，可选
     * @return List<IncomePlanDO>
     */
    List<IncomePlanDO> findListByStateAndInvestIdList(@Param("incomePlanState") int incomePlanState,
                                                      @Param("investIdList") List<Integer> investIdList);

    List<IncomePlanDO> findListByStateAndLoanId(@Param("incomePlanState") int incomePlanState,
                                                @Param("loanId") int loanId,
                                                @Param("beneficiaryIdList") List<Integer> beneficiaryIdList);

    /**
     * 查询转让出去的本金
     * 
     * @param loanId
     * @param beneficiaryIdList
     * @return
     */
    InvestInformationDO findTheMoney(@Param("piLoanId") int loanId, @Param("piBeneficiary") int piBeneficiary, @Param("investId") int investId,
                                     @Param("piState") int piState);

    /**
     * 根据list查询投资的总结和应得利息
     * 
     * @param loanId
     * @param beneficiaryIdList
     * @return
     */
    List<InvestInformationDO> findByList(@Param("piLoanId") int loanId,
                                         @Param("userIdList") List<Integer> beneficiaryIdList);
    
    /**
     * 根据list查询投资的总结和应得利息
     * 
     * @param loanId
     * @param beneficiaryIdList
     * @return
     */
    List<InvestInformationDO> findByInvestIdList(@Param("piLoanId") int loanId,
                                         @Param("userIdList") List<Integer> beneficiaryIdList, @Param("investIdList") List<Integer> investIdList);

    /**
     * 每个项目的所有投资人收益汇总
     * 
     * @param loanId
     * @return
     */
    List<InvestInformationDO> findSumPerUser(Integer loanId);

    /**
     * 统计按条件查询的分页结果总数
     * 
     * @param plan 查询条件，可选
     * @return int 符合条件的总数
     */
    int countListByParam(@Param("plan") IncomePlanDO plan);

    /**
     * 查询投资记录为 investId 对应的 按先后次序的 count 期的收益计划状态为 planState 的总债权
     * 
     * @param investId 投资记录id, 必选
     * @param count 期数, 可选
     * @param planState 收益计划状态，可选
     * @return BigDecimal 满足条件的债权总额<br>
     *         注意：当count大于实际总的未结清期数时，返回实际未结清期数对应的债权总额。
     */
    BigDecimal sumCapitalByInvestId(@Param("investId") int investId, @Param("count") Integer count,
                                    @Param("planState") Integer planState);

    /**
     * 查询投资记录为 investId 对应的 按先后次序的 count 期的收益计划状态为 planState 的总债权
     * 
     * @param userId 投资记录id, 必选
     * @param loanId 普通标id, 必选
     * @param planState 收益计划状态，可选
     * @return BigDecimal 满足条件的债权总额
     */
    BigDecimal sumCapitalByUserIdAndLoanId(@Param("userId") int userId, @Param("loanId") int loanId,
                                           @Param("planState") Integer planState);

    /**
     * 本期受益人列表
     * 
     * @param paymentPlanId
     * @param pageCondition
     * @return List<IncomeJDO>
     */
    List<IncomeJDO> findIncomeList(@Param("paymentPlanId") int paymentPlanId,
                                   @Param("pageCondition") PageCondition pageCondition);
    /**
     * 总数本期受益人
     * 
     * @param paymentPlanId
     * @return int
     */
    int countIncomeList(@Param("paymentPlanId") int paymentPlanId);
    
    /**
     * 查询最后一期收益
     * 
     * @param incomePlanDO
     * @return
     */
    IncomePlanDO findlastIncomePlanBySearch(@Param("plan") IncomePlanDO incomePlanDO,
                                            @Param("searchJDO") InvestSearchJDO investSearchJDO);

    /**
     * 统计按条件查询的分页结果总数(增加搜索条件)
     * 
     * @param plan 查询条件，可选
     * @return int 符合条件的总数
     */
    int countListByParamBySrearh(@Param("plan") IncomePlanDO plan, @Param("searchJDO") InvestSearchJDO investSearchJDO);

    /**
     * 按条件查询分页结果(增加搜索条件)
     * 
     * @param plan 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<IncomePlanDO>
     */
    List<IncomePlanDO> findListByParamBySrearh(@Param("plan") IncomePlanDO plan,
                                               @Param("searchJDO") InvestSearchJDO investSearchJDO,
                                               @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询借款标loanId下的，当前收益状态为incomePlanState的收益人集合
     * 
     * @param loanId 借款标id，必选
     * @param planState 收益计划状态，可选
     * @return List<Integer>
     */
    List<Integer> findBeneficiaryList(@Param("loanId") int loanId, @Param("planState") Integer planState);

    /**
     * 查询回款计划概况
     * 
     * @param userId 用户id，必选
     * @return List<PaymentPlan>
     */
    List<StatisticsPaymentPlan> findMyPaymentPlan(@Param("userId") Integer userId);

    /**
     * 查询债权总价值
     * 
     * @param pojo
     * @return
     */
    IncomePlanDO queryBondMoney(@Param("plan") IncomePlanDO plan);

    /**
     * 批量回寫回款計劃已還金額
     * 
     * @param incomePlanDOList
     */
    int batchIncomeMoneryUpdate(Integer planId);

    /**
     * 取得投资人收益总额
     */
    BigDecimal totalRevenue();
    
    /**
     * 当年投资人收益总额
     */
    BigDecimal totalRevenueNowYear();
    
    /**
     * 上个月投资人赚取的收益
     */
    BigDecimal totalRevenueLastMonth();
    
    /**
     * 取得投资待收本金总额
     */
    BigDecimal totalRevenueToPay();

    /**
     * 查询某个用户的所有回款金额
     * 
     * @param userId
     * @return
     */
    BigDecimal queryTotalIncomeMoneyByUserId(@Param("userId") Integer userId);
    
    /**
     * 查询某个用户的所有本金金额
     * @param userId
     * @return
     */
    BigDecimal queryTotalIncomeCapitalMoneyByUserId(@Param("userId") Integer userId);

    /**
     * 更改收益计划表的状态和内部交易流水号，实还日期
     * 
     * @param Id 还款计划id，必选
     * @param oldState 原来的状态, 必选
     * @param newState 要更改为的新状态, 必选
     * @param innerSeqNo 要更改为的内部交易流水号, 必选
     * @return int
     */
    int updateStateAndInneSeqById(@Param("incomePlanId") int incomePlanId, @Param("oldState") int oldState,
                                  @Param("newState") int newState, @Param("innerSeqNo") String innerSeqNo);

    /**
     * 查询某一期的还款人员信息
     * 
     * @param paymentPlanId
     * @return
     */
    List<Map<String, Object>> findUserMapByPaymentPlanId(@Param("paymentPlanId") Integer paymentPlanId);

    /**
     * 查询本月待收本金
     * 
     * @param userId
     * @return
     */
    BigDecimal findCurMonthWaitIncomeCapital(Integer userId);

    /**
     * 查询本月待收利息
     * 
     * @param userId
     * @return
     */
    BigDecimal findCurMonthWaitIncomeInterest(Integer userId);
    /**
     * 更新收益计划中的内容(主要是应还罚金、应还总额)
     * @param incomePlan
     * @return
     */
    int updateIncomePlan(@Param("incomePlan") IncomePlanDO incomePlan);
    
    /**
     * 按条件查询分页结果(增加搜索条件),按天对收益计划进行汇总
     * 
     * @param plan 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<IncomePlanDO>
     */
    List<IncomePlanDO> findListToDayByParamBySrearh(@Param("plan") IncomePlanDO plan,
                                               @Param("searchJDO") InvestSearchJDO investSearchJDO,
                                               @Param("pageCondition") PageCondition pageCondition);
    /**
     * 统计按条件查询的分页结果总数(增加搜索条件),按天对收益计划进行汇总
     * 
     * @param plan 查询条件，可选
     * @return int 符合条件的总数
     */
    int countListToDayByParamBySrearh(@Param("plan") IncomePlanDO plan, @Param("searchJDO") InvestSearchJDO investSearchJDO);
    
    
    /**
     * 统计按条件查询的分页结果总数(增加搜索条件),按天对收益计划进行汇总
     * add by 夏同同  20160518 计算当前日期之前的数据数目
     * @param plan 查询条件，可选
     * @return int 符合条件的总数
     */
    int countListBeforeToday(@Param("plan") IncomePlanDO plan, @Param("searchJDO") InvestSearchJDO investSearchJDO);
    
    /**
     * 根据条件查询汇总信息
     */
    IncomePlanDO queryTotalIncomeMoneyByIncome(@Param("plan") IncomePlanDO incomePlanDO);
    
    /**
     * 查询用户某一天的收益
     * 
     * @param userid
     * @return
     */
    BigDecimal queryIncomeTotalInTodayByUserId(Integer userid);

	int batchUpdateStateByLoanId(int investId, int oldState, int newState);
	
	
	int batchUpdateStateByInvestId(@Param("investId") int investId, @Param("oldState")int oldState, @Param("newState")int newState);

	void batchDeletePlanByLoanId(@Param("loanId")Integer loanId);
	
}
