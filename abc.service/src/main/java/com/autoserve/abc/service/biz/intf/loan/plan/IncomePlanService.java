/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.loan.plan;

import java.math.BigDecimal;
import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.IncomeJDO;
import com.autoserve.abc.dao.dataobject.IncomePlanDO;
import com.autoserve.abc.dao.dataobject.pdfBean.InvestInformationDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.stage.statistics.StatisticsPaymentPlan;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 收益计划服务
 * 
 * @author segen189 2014年11月29日 上午11:51:35
 */
public interface IncomePlanService {
    /**
     * 批量生成投资人的收益计划
     * 
     * @param incomePlanList 待添加的收益计划
     * @return PlainResult<Integer> 新增的条数
     */
    PlainResult<Integer> batchCreateIncomePlanList(List<IncomePlan> incomePlanList);

    /**
     * ［内部］批量更改满标资金划转记录id对应收益计划的状态
     * 
     * @param fullTransRecordId 满标资金划转记录id, 必选
     * @param oldState 原来的状态, 必选
     * @param newState 要更改为的新状态, 必选
     * @return PlainResult<Integer> 更改的条数
     */
    PlainResult<Integer> batchModifyStateByFullTransRecordId(int fullTransRecordId, IncomePlanState oldState,
                                                             IncomePlanState newState);

    /**
     * 批量更改指定内部交易流水号对应的收益计划的状态
     * 
     * @param innerSeqNo 还款时创建的内部交易流水号, 必选
     * @param oldState 原来的状态, 必选
     * @param newState 要更改为的新状态, 必选
     * @param paymentPlan
     * @return PlainResult<Integer> 更改的条数
     */
    PlainResult<Integer> batchModifyStateByInnerSeqNo(String innerSeqNo, IncomePlanState oldState,
                                                      IncomePlanState newState);

    /**
     * 批量更改收益人id对应收益计划的状态
     * 
     * @param userId 收益人id, 必选
     * @param fullTransRecordId 满标资金划转记录id, 必选
     * @param oldState 原来的状态, 必选
     * @param newState 要更改为的新状态, 必选
     * @return PlainResult<Integer> 更改的条数
     */
    PlainResult<Integer> batchModifyStateByUserIdAndFullTransRecordId(int userId, int fullTransRecordId,
                                                                      IncomePlanState oldState, IncomePlanState newState);

    /**
     * ［内部］批量更改投资id对应的收益计划的状态
     * 
     * @param loanId 借款标id，必选
     * @param beneficiaryIdList 收益人id列表，可选
     * @param oldState 原来的状态, 必选
     * @param newState 要更改为的新状态, 必选
     * @return PlainResult<Integer> 更改的条数
     */
    PlainResult<Integer> batchUpdateStateByLoanId(int loanId, List<Integer> beneficiaryIdList,
                                                  IncomePlanState oldState, IncomePlanState newState);

    /**
     * ［内部］批量更改投资记录和收益计划的状态
     * 
     * @param fullTransRecordId 满标资金划转记录id, 必选
     * @param oldIncomeState 原来的收益状态, 必选
     * @param newIncomeState 要更改为的新收益状态, 必选
     * @param oldInvestState 原来的投资状态, 必选
     * @param newInvestState 要更改为的新投资状态, 必选
     * @return PlainResult<Integer> 更改的条数
     */
    PlainResult<Integer> batchModifyIncomePlanAndInvest(int fullTransRecordId, IncomePlanState oldIncomeState,
                                                        IncomePlanState newIncomeState, InvestState oldInvestState,
                                                        InvestState newInvestState);

    /**
     * ［内部］批量更改投资记录和收益计划的状态
     * 
     * @param ftrId 满标划转记录id, 选填
     * @param loanId 原始普通标idid, 必选
     * @param beneficiaryId 收益人id, 可选
     * @param oldIncomeState 原来的收益状态, 必选
     * @param newIncomeState 要更改为的新收益状态, 必选
     * @param oldInvestState 原来的投资状态, 必选
     * @param newInvestState 要更改为的新投资状态, 必选
     * @return PlainResult<Integer> 更改的条数
     */
    PlainResult<Integer> batchModifyIncomePlanAndInvest(Integer ftrId, int loanId, Integer beneficiaryId,
                                                        IncomePlanState oldIncomeState, IncomePlanState newIncomeState,
                                                        InvestState oldInvestState, InvestState newInvestState);

    /**
     * 修改收益计划表
     * 
     * @param paymentPlanId 对应的还款计划id，必选
     * @param oldState 原来的状态，必选
     * @param newState 要更改为的新状态，必选
     * @param innerSeqNo 要更改为的内部交易流水号，必选
     * @return BaseResult 如果没有收益计划被修改，则返回错误结果
     */
    BaseResult modifyIncomePlan(int paymentPlanId, PayState oldState, PayState newState, String innerSeqNo);

    /**
     * 查询投资记录为 investId 对应的 按先后次序的 count 期的收益计划状态为 incomePlanState 的总债权
     * 
     * @param investId 投资记录id，必选
     * @param count 期数，可选
     * @param incomePlanState 收益计划状态，必选
     * @return PlainResult<BigDecimal> 满足条件的债权总额。<br>
     *         注意：当count大于实际总的未结清期数时，返回实际未结清期数对应的债权总额。
     */
    PlainResult<BigDecimal> sumCapitalByInvestId(int investId, Integer count, IncomePlanState incomePlanState);

    /**
     * 查询收益人userId在普通标loanId下的收益状态为incomePlanState的债权总额
     * 
     * @param userId 收益人id，必选
     * @param loanId 普通标id，可选
     * @param incomePlanState 收益计划状态，必选
     * @return PlainResult<BigDecimal> 满足条件的债权总额。<br>
     */
    PlainResult<BigDecimal> sumCapitalByUserIdAndLoanId(int userId, int loanId, IncomePlanState incomePlanState);

    /**
     * 查询分页的收益计划列表
     * 
     * @param pojo 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<IncomePlan>
     */
    PageResult<IncomePlan> queryIncomePlanList(IncomePlan pojo, PageCondition pageCondition);

    /**
     * 查询收益计划列表
     * 
     * @param pojo 查询条件，必选
     * @return ListResult<IncomePlan>
     */
    ListResult<IncomePlan> queryIncomePlanList(IncomePlan pojo);

    /**
     * 查询指定状态且投资id在列表中的收益计划集合
     * 
     * @param incomePlanState 收益计划的状态，必选
     * @param loanId 借款标id，必选
     * @param beneficiaryIdList 收益人id列表，可选
     * @return ListResult<IncomePlan>
     */
    ListResult<IncomePlan> queryIncomePlanList(IncomePlanState incomePlanState, int loanId,
                                               List<Integer> beneficiaryIdList);

    /**
     * 本期收益人列表
     * 
     * @param paymentPlanId 还款计划id，必选
     * @param pageCondition 分页条件，可选
     * @return ListResult<IncomeJDO>
     */
    ListResult<IncomeJDO> queryIncomeList(int paymentPlanId, PageCondition pageCondition);

    /**
     * 查询借款标loanId下的，当前收益状态为incomePlanState的收益人集合
     * 
     * @param loanId 借款标id，必须
     * @param incomePlanState 收益计划状态，可选
     * @return ListResult<Integer>
     */
    ListResult<Integer> queryBeneficiaryList(int loanId, IncomePlanState incomePlanState);

    /**
     * 分配罚息更新收益计划表中投资人应还罚息、应还总额、实还的罚息、实还总额
     * 
     * @param payPlanId 还款计划id，必选
     * @param punishMoney 总罚息，必选
     * @param incomeState 收益计划表的状态，可选
     * @return BaseResult
     */
    BaseResult modifyIncomePlanByAllocPunishMoney(int payPlanId, double punishMoney, IncomePlanState incomeState);

    /**
     * 查询最后收益计划列表(增加查询)
     * 
     * @param pojo 查询条件，必选
     * @return PlainResult<IncomePlan>
     */
    PlainResult<IncomePlan> querylastIncomePlan(IncomePlan pojo, InvestSearchJDO investSearchJDO);

    /**
     * 查询分页的收益计划列表
     * 
     * @param pojo 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<IncomePlan>
     */
    PageResult<IncomePlan> queryIncomePlanList(IncomePlan pojo, InvestSearchJDO investSearchJDO,
                                               PageCondition pageCondition);

    /**
     * 合同中甲方投资人列表
     * 
     * @param loanId 标号
     * @param incomeList 用户IDList
     * @return ListResult<IncomePlan>
     */
    ListResult<InvestInformationDO> findIncomePlanList(int loanId, List<Integer> incomeList, List<Integer> investIdList);

    /**
     * 查询回款计划概况
     * 
     * @param userId 用户id，必选
     * @return List<PaymentPlan>
     */
    ListResult<StatisticsPaymentPlan> findMyPaymentPlan(Integer userId);

    /**
     * 查询债权总价值
     * 
     * @return
     */
    PlainResult<BigDecimal> queryBondMoney(IncomePlan pojo);

    /**
     * 查询装让的本金
     * 
     * @param loanId 标号
     * @param userId 用户名
     * @param type 状态
     */
    PlainResult<BigDecimal> queryContractMoney(int loanId, int userId, int investId, int type);

    /**
     * 根据内部交易查询收益计划
     * 
     * @return
     */
    PlainResult<IncomePlan> queryIncomePlanByInnerSeqNo(String innerSeqNo);

    /**
     * 为投资人赚取的收益
     * 
     * @return
     */
    PlainResult<BigDecimal> findIncome();
    
    /**
     * 为投资人赚取的收益当年
     * 
     * @return
     */
    PlainResult<BigDecimal> findIncomeNowYear();
    
    /**
     * 上个月投资人赚取的收益
     * 
     * @return
     */
    PlainResult<BigDecimal> findIncomeLastMonth();
    
    /**
     * 为投资待收的收益
     * 
     * @return
     */
    PlainResult<BigDecimal> findIncomeToPay();

    /**
     * 修改收益计划表
     * 
     * @param Id 对应的还款计划id，必选
     * @param oldState 原来的状态，必选
     * @param newState 要更改为的新状态，必选
     * @param innerSeqNo 要更改为的内部交易流水号，必选
     * @return BaseResult 如果没有收益计划被修改，则返回错误结果
     */
    BaseResult modifyIncomePlanById(int incomePlanId, PayState oldState, PayState newState, String innerSeqNo);

    /**
     * 查询本月待收本金
     * 
     * @param userId
     * @return
     */
    PlainResult<BigDecimal> queryCurMonthWaitIncomeCapital(Integer userId);

    /**
     * 查询本月待收利息
     * 
     * @param userId
     * @return
     */
    PlainResult<BigDecimal> queryCurMonthWaitIncomeInterest(Integer userId);
    
    /**
     *批量更新应还罚金、应还总额（逾期罚息分配模块）
     * @param incomePlan
     * @return
     */
    BaseResult updateIncomePlanByIncome(List<IncomePlan> incomePlans);
    /**
     * 查询分页的收益计划列表,按天进行汇总
     * 
     * @param pojo 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<IncomePlan>
     */
    PageResult<IncomePlan> queryIncomePlanToDayList(IncomePlan pojo, InvestSearchJDO investSearchJDO,
                                               PageCondition pageCondition);
    
    /**
     * 查询当前日期之前的回款计划数目
     * add by 夏同同 20160518
     */
    int queryCountListBeforeToday(IncomePlan pojo, InvestSearchJDO investSearchJDO);
    
    /**
     * 根据时间受益人状态 或者汇总信息
     * 
     * @param incomePlanDO
     * @return
     */
    PlainResult<IncomePlanDO> findSumPlanIncome(IncomePlanDO incomePlanDO);

    /**
     * 查询用户今日待收金额
     * 
     * @param userid
     * @return
     */
    BigDecimal queryIncomeInToday(Integer userid);

    /**
     * ［内部］根据投资id批量更改收益计划的状态
     * @param investId
     * @param oldState
     * @param newState
     * @return
     */
	PlainResult<Integer> batchUpdateStateByInvestId(int investId,
			IncomePlanState oldState, IncomePlanState newState);

}
