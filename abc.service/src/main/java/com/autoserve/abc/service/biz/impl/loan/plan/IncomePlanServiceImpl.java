package com.autoserve.abc.service.biz.impl.loan.plan;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.citrus.util.StringUtil;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.IncomeJDO;
import com.autoserve.abc.dao.dataobject.IncomePlanDO;
import com.autoserve.abc.dao.dataobject.pdfBean.InvestInformationDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.stage.statistics.StatisticsPaymentPlan;
import com.autoserve.abc.dao.intf.IncomePlanDao;
import com.autoserve.abc.service.biz.convert.IncomePlanConverter;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 收益计划服务实现
 * 
 * @author segen189 2014年11月29日 上午11:52:13
 */
@Service
public class IncomePlanServiceImpl implements IncomePlanService {
    @Resource
    private IncomePlanDao incomePlanDao;

    @Override
    public PlainResult<Integer> batchCreateIncomePlanList(List<IncomePlan> incomePlanList) {
        PlainResult<Integer> result = new PlainResult<Integer>();

        if (CollectionUtils.isEmpty(incomePlanList)) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM_BLANK, "incomePlanList");
        }

        int count = incomePlanDao.batchInsert(IncomePlanConverter.toIncomePlanDOList(incomePlanList));
        if (count <= 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "收益计划创建失败");
        }

        result.setData(count);
        return result;
    }

    @Override
    public PlainResult<Integer> batchModifyStateByFullTransRecordId(int fullTransRecordId, IncomePlanState oldState,
                                                                    IncomePlanState newState) {
        PlainResult<Integer> result = new PlainResult<Integer>();

        if (oldState == null || newState == null) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM, "IncomePlanState");
        }

        int count = incomePlanDao.batchUpdateStateByFullTransRecordId(fullTransRecordId, oldState.getState(),
                newState.getState());
        if (count <= 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "更改收益计划状态失败");
        }

        result.setData(count);
        return result;
    }

    @Override
    public PlainResult<Integer> batchModifyStateByInnerSeqNo(String innerSeqNo, IncomePlanState oldState,
                                                             IncomePlanState newState) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        if (StringUtil.isBlank(innerSeqNo) || oldState == null || newState == null) {
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        result.setData(incomePlanDao.batchUpdateStateByInnerSeqNo(innerSeqNo, oldState.getState(), newState.getState()));
        return result;
    }

    @Override
    public PlainResult<Integer> batchModifyStateByUserIdAndFullTransRecordId(int userId, int fullTransRecordId,
                                                                             IncomePlanState oldState,
                                                                             IncomePlanState newState) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        if (userId <= 0 || oldState == null || newState == null) {
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        result.setData(incomePlanDao.batchUpdateStateByUserIdAndFullTransRecordId(userId, fullTransRecordId,
                oldState.getState(), newState.getState()));
        return result;
    }

    @Override
    public PlainResult<Integer> batchUpdateStateByLoanId(int loanId, List<Integer> beneficiaryIdList,
                                                         IncomePlanState oldState, IncomePlanState newState) {
        if (oldState == null || newState == null) {
            return new PlainResult<Integer>().setError(CommonResultCode.ILLEGAL_PARAM, "IncomePlanState");
        }

        int count = incomePlanDao.batchUpdateStateByLoanId(loanId, beneficiaryIdList, oldState.getState(),
                newState.getState());
        return new PlainResult<Integer>(count);
    }
    
    @Override
    public PlainResult<Integer> batchUpdateStateByInvestId(int investId,IncomePlanState oldState, IncomePlanState newState) {
        if (oldState == null || newState == null) {
            return new PlainResult<Integer>().setError(CommonResultCode.ILLEGAL_PARAM, "IncomePlanState");
        }

        int count = incomePlanDao.batchUpdateStateByInvestId(investId, oldState.getState(),newState.getState());
        return new PlainResult<Integer>(count);
    }
    

    @Override
    public PlainResult<Integer> batchModifyIncomePlanAndInvest(int fullTransRecordId, IncomePlanState oldIncomeState,
                                                               IncomePlanState newIncomeState,
                                                               InvestState oldInvestState, InvestState newInvestState) {
        if (oldIncomeState == null || newIncomeState == null || oldInvestState == null || newInvestState == null) {
            return new PlainResult<Integer>().setError(CommonResultCode.ILLEGAL_PARAM);
        }

        int count = incomePlanDao.batchUpdateIncomePlanAndInvestByFtrId(fullTransRecordId, oldIncomeState.getState(),
                newIncomeState.getState(), oldInvestState.getState(), newInvestState.getState());
        return new PlainResult<Integer>(count);
    }

    @Override
    public PlainResult<Integer> batchModifyIncomePlanAndInvest(Integer ftrId, int loanId, Integer beneficiaryId,
                                                               IncomePlanState oldIncomeState,
                                                               IncomePlanState newIncomeState,
                                                               InvestState oldInvestState, InvestState newInvestState) {
        if (oldIncomeState == null || newIncomeState == null || oldInvestState == null || newInvestState == null) {
            return new PlainResult<Integer>().setError(CommonResultCode.ILLEGAL_PARAM);
        }

        int count = incomePlanDao.batchUpdateIncomePlanAndInvestByLoanId(ftrId, loanId, beneficiaryId,
                oldIncomeState.getState(), newIncomeState.getState(), oldInvestState.getState(),
                newInvestState.getState());
        return new PlainResult<Integer>(count);
    }

    @Override
    public BaseResult modifyIncomePlan(int paymentPlanId, PayState oldState, PayState newState, String innerSeqNo) {
        int count = incomePlanDao.updateStateAndInneSeq(paymentPlanId, oldState.getState(), newState.getState(),
                innerSeqNo);

        if (count <= 0) {
            BaseResult result = new BaseResult();
            return result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改收益计划表的内部交易流水号失败");
        }

        return BaseResult.SUCCESS;
    }

    @Override
    public PlainResult<BigDecimal> sumCapitalByInvestId(int investId, Integer count, IncomePlanState incomePlanState) {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
        result.setData(incomePlanDao.sumCapitalByInvestId(investId, count, incomePlanState.getState()));
        return result;
    }

    @Override
    public PlainResult<BigDecimal> sumCapitalByUserIdAndLoanId(int userId, int loanId, IncomePlanState incomePlanState) {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
        result.setData(incomePlanDao.sumCapitalByUserIdAndLoanId(userId, loanId, incomePlanState.getState()));
        return result;
    }

    @Override
    public PageResult<IncomePlan> queryIncomePlanList(IncomePlan pojo, PageCondition pageCondition) {
        PageResult<IncomePlan> result = new PageResult<IncomePlan>(pageCondition.getPage(), pageCondition.getPageSize());

        int totalCount = incomePlanDao.countListByParam(IncomePlanConverter.toIncomePlanDO(pojo));
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(IncomePlanConverter.toIncomePlanList(incomePlanDao.findListByParam(
                    IncomePlanConverter.toIncomePlanDO(pojo), pageCondition)));
        }

        return result;
    }

    @Override
    public ListResult<IncomePlan> queryIncomePlanList(IncomePlan pojo) {
        ListResult<IncomePlan> result = new ListResult<IncomePlan>();
        result.setData(IncomePlanConverter.toIncomePlanList(incomePlanDao.findListByParam(
                IncomePlanConverter.toIncomePlanDO(pojo), null)));
        return result;
    }

    @Override
    public ListResult<IncomePlan> queryIncomePlanList(IncomePlanState incomePlanState, int loanId,
                                                      List<Integer> beneficiaryIdList) {
        ListResult<IncomePlan> result = new ListResult<IncomePlan>();

        if (incomePlanState == null) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM, "incomePlanState");
        }

        result.setData(IncomePlanConverter.toIncomePlanList(incomePlanDao.findListByStateAndLoanId(
                incomePlanState.getState(), loanId, beneficiaryIdList)));
        return result;
    }

    @Override
    public ListResult<IncomeJDO> queryIncomeList(int paymentPlanId, PageCondition pageCondition) {
    	 ListResult<IncomeJDO> result = new ListResult<IncomeJDO>();
         int total = incomePlanDao.countIncomeList(paymentPlanId);
         List<IncomeJDO> list = incomePlanDao.findIncomeList(paymentPlanId, pageCondition);
         result.setData(list);
         result.setCount(total);
         return result;
    }

    @Override
    public ListResult<Integer> queryBeneficiaryList(int loanId, IncomePlanState incomePlanState) {
        ListResult<Integer> result = new ListResult<Integer>();
        List<Integer> data = incomePlanDao.findBeneficiaryList(loanId,
                incomePlanState == null ? null : incomePlanState.getState());
        result.setData(data);
        return result;
    }

    @Override
    public BaseResult modifyIncomePlanByAllocPunishMoney(int payPlanId, double punishMoney, IncomePlanState incomeState) {
        int count = incomePlanDao.updateIncomePlanByAllocPunishMoney(payPlanId, punishMoney, incomeState == null ? null
                : incomeState.getState());
        if (count > 0) {
            return BaseResult.SUCCESS;
        } else {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "收益计划表分配罚息失败");
        }
    }

    @Override
    public PlainResult<IncomePlan> querylastIncomePlan(IncomePlan pojo, InvestSearchJDO investSearchJDO) {
        PlainResult<IncomePlan> result = new PlainResult<IncomePlan>();
        result.setData(IncomePlanConverter.toIncomePlan(incomePlanDao.findlastIncomePlanBySearch(
                IncomePlanConverter.toIncomePlanDO(pojo), investSearchJDO)));
        return result;
    }

    @Override
    public PageResult<IncomePlan> queryIncomePlanList(IncomePlan pojo, InvestSearchJDO investSearchJDO,
                                                      PageCondition pageCondition) {
        PageResult<IncomePlan> result = new PageResult<IncomePlan>(pageCondition.getPage(), pageCondition.getPageSize());

        int totalCount = incomePlanDao.countListByParamBySrearh(IncomePlanConverter.toIncomePlanDO(pojo),
                investSearchJDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(IncomePlanConverter.toIncomePlanList(incomePlanDao.findListByParamBySrearh(
                    IncomePlanConverter.toIncomePlanDO(pojo), investSearchJDO, pageCondition)));
        }

        return result;
    }

    @Override
    public ListResult<StatisticsPaymentPlan> findMyPaymentPlan(Integer userId) {
        ListResult<StatisticsPaymentPlan> list = new ListResult<StatisticsPaymentPlan>();
        List<StatisticsPaymentPlan> paymentPlanDOList = incomePlanDao.findMyPaymentPlan(userId);
        list.setData(paymentPlanDOList);
        return list;
    }

    @Override
    public PlainResult<BigDecimal> queryBondMoney(IncomePlan pojo) {
		PlainResult<BigDecimal> plain = new PlainResult<BigDecimal>();
		IncomePlanDO incomePlanDO = incomePlanDao.queryBondMoney(IncomePlanConverter.toIncomePlanDO(pojo));
		if (incomePlanDO == null) {
			plain.setData(new BigDecimal(0));
		} else {
//			BigDecimal loanRate = incomePlanDO.getPiCollectInterest();// 年利率
			BigDecimal lastAmount = incomePlanDO.getPiPayCapital();// 剩余本金
//			BigDecimal investAmount = incomePlanDO.getPiCollectTotal();// 本金
//			Date collectTime = incomePlanDO.getPiCollecttime();// 最后还款时间（实还日期）
//			Date paytime = incomePlanDO.getPiPaytime(); // 应还日期
//			Date fullTime = incomePlanDO.getPiCreateTime();// 放款时间
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//			Long days = 0L;
//			try {
//				// 当前日期
//				String now = df.format(new Date());
//				Date nowx = df.parse(now);
//				if (collectTime != null && collectTime.getTime() > fullTime.getTime()) { // 有还款行为
//					// 应还日期
//					Date paytimex = df.parse(df.format(paytime));
//					if (collectTime.getTime() <= paytime.getTime()) {   //提前还款
//						days =(new Date().getTime()-paytimex.getTime())/ (1000 * 60 * 60 * 24 ); // 时间
//					} else { // 有逾期还款
//						plain.setErrorMessage(CommonResultCode.BIZ_ERROR,"此项目有逾期还款行为，不可转让");
//						return plain;
//					}
//				} else {
//					// 放款日期
//					Date fullTimex = df.parse(df.format(fullTime));
//					days = (nowx.getTime() - fullTimex.getTime()) / (1000 * 60 * 60 * 24); // 时间
//				}
//				
//				/**
//				 * 债权总价值的计算公式：
//				 * 时间=now（）-应还日期
//				 *债权总价值= 本金*日利率*时间+剩余本金
//				 */
////				BigDecimal rate = loanRate.divide(new BigDecimal(36000), 10,BigDecimal.ROUND_HALF_UP);// 计算日利率				
////				BigDecimal result=investAmount.multiply(rate).multiply(new BigDecimal(days)).add(lastAmount).setScale(2,BigDecimal.ROUND_HALF_UP);
//				BigDecimal result=investAmount.multiply(new BigDecimal(days+"")).multiply(loanRate);
//				result = result.divide(new BigDecimal("36000"), 2, RoundingMode.HALF_EVEN);
//				result = result.add(lastAmount);
//				plain.setData(result);
				//只转剩余本金，不要代付利息 2015-12-17
				plain.setData(lastAmount);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
		}

		return plain;
    }
    
    public static void main(String[] args) {
//    	BigDecimal loanRate = new BigDecimal("11");
//    	BigDecimal investAmount = new BigDecimal("1100");
//    	BigDecimal rate = loanRate.divide(new BigDecimal(36000), 10,BigDecimal.ROUND_HALF_UP);// 计算日利率				
//		BigDecimal result=investAmount.multiply(rate).multiply(new BigDecimal("-9")).add(investAmount).setScale(10,BigDecimal.ROUND_HALF_UP);
//		System.out.println(result);
    	
    	BigDecimal loanRate = new BigDecimal("11");
    	BigDecimal investAmount = new BigDecimal("1125");
    	
		BigDecimal result=investAmount.multiply(new BigDecimal("-9")).multiply(loanRate);
		result = result.divide(new BigDecimal("36000"), 2, RoundingMode.HALF_EVEN);
		result = result.add(investAmount);
    	
//    	BigDecimal a = new BigDecimal("105.13");
//    	BigDecimal b = a.divide(new BigDecimal("2"), 2, RoundingMode.HALF_EVEN);
//		
	}
    
    

    @Override
    public PlainResult<IncomePlan> queryIncomePlanByInnerSeqNo(String innerSeqNo) {
        PlainResult<IncomePlan> plainResult = new PlainResult<IncomePlan>();
        IncomePlanDO incomePlanDOSearch = new IncomePlanDO();
        incomePlanDOSearch.setPiInnerSeqNo(innerSeqNo);
        IncomePlanDO incomePlanDO = this.incomePlanDao.findlastIncomePlanBySearch(incomePlanDOSearch, null);
        plainResult.setData(IncomePlanConverter.toIncomePlan(incomePlanDO));
        return plainResult;
    }

    @Override
    public ListResult<InvestInformationDO> findIncomePlanList(int loanId, List<Integer> incomeList, List<Integer> investIdList) {
        ListResult<InvestInformationDO> result = new ListResult<InvestInformationDO>();
        if (null == investIdList || investIdList.isEmpty())
        {
        	List<InvestInformationDO> resultList = incomePlanDao.findByList(loanId, incomeList);
            result.setData(resultList);
        }
        else
        {
        	List<InvestInformationDO> resultList = incomePlanDao.findByInvestIdList(loanId, incomeList, investIdList);
            result.setData(resultList);
        }
        return result;
    }

    @Override
    public PlainResult<BigDecimal> queryContractMoney(int loanId, int userId, int investId, int type) {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
        InvestInformationDO list = incomePlanDao.findTheMoney(loanId, userId, investId,type);
        BigDecimal money = list.getPayTotalMoney();
        result.setData(money);
        return result;
    }

    @Override
    public PlainResult<BigDecimal> findIncome() {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
        BigDecimal money = incomePlanDao.totalRevenue();
        result.setData(money);
        return result;
    }
    
    @Override
    public PlainResult<BigDecimal> findIncomeNowYear() {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
        BigDecimal money = incomePlanDao.totalRevenueNowYear();
        result.setData(money);
        return result;
    }
    
    @Override
    public PlainResult<BigDecimal> findIncomeLastMonth() {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
        BigDecimal money = incomePlanDao.totalRevenueLastMonth();
        result.setData(money);
        return result;
    }
    
    @Override
    public PlainResult<BigDecimal> findIncomeToPay() {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
        BigDecimal money = incomePlanDao.totalRevenueToPay();
        result.setData(money);
        return result;
    }

    @Override
    public BaseResult modifyIncomePlanById(int incomePlanId, PayState oldState, PayState newState, String innerSeqNo) {
        int count = incomePlanDao.updateStateAndInneSeqById(incomePlanId, oldState.getState(), newState.getState(),
                innerSeqNo);

        if (count <= 0) {
            BaseResult result = new BaseResult();
            return result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改收益计划表的内部交易流水号失败");
        }

        return BaseResult.SUCCESS;
    }

    @Override
    public PlainResult<BigDecimal> queryCurMonthWaitIncomeCapital(Integer userId) {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
        BigDecimal money = incomePlanDao.findCurMonthWaitIncomeCapital(userId);
        result.setData(money);
        return result;
    }

    @Override
    public PlainResult<BigDecimal> queryCurMonthWaitIncomeInterest(Integer userId) {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
        BigDecimal money = incomePlanDao.findCurMonthWaitIncomeInterest(userId);
        result.setData(money);
        return result;
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public BaseResult updateIncomePlanByIncome(List<IncomePlan> incomePlans) {
		for(IncomePlan incomePlan:incomePlans){
			int count=incomePlanDao.updateIncomePlan(IncomePlanConverter.toIncomePlanDO(incomePlan));
			  if (count <= 0) {
		            BaseResult result = new BaseResult();
		            return result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改收益计划表的应还罚金、应还总额失败");
		        }
		}		
		return BaseResult.SUCCESS;
	}

	@Override
	public PageResult<IncomePlan> queryIncomePlanToDayList(IncomePlan pojo,
			InvestSearchJDO investSearchJDO, PageCondition pageCondition) {
		  PageResult<IncomePlan> result = new PageResult<IncomePlan>(pageCondition.getPage(), pageCondition.getPageSize());

	        int totalCount = incomePlanDao.countListToDayByParamBySrearh(IncomePlanConverter.toIncomePlanDO(pojo),
	                investSearchJDO);
	        result.setTotalCount(totalCount);

	        if (totalCount > 0) {
	            result.setData(IncomePlanConverter.toIncomePlanList(incomePlanDao.findListToDayByParamBySrearh(
	                    IncomePlanConverter.toIncomePlanDO(pojo), investSearchJDO, pageCondition)));
	        }

	        return result;
	}
	
    /**
     * 查询当前日期之前的回款计划数目
     * add by 夏同同 20160518
     */
	@Override
	public int queryCountListBeforeToday(IncomePlan pojo,InvestSearchJDO investSearchJDO){
		int beforeTodayCount = incomePlanDao.countListBeforeToday(IncomePlanConverter.toIncomePlanDO(pojo),
				investSearchJDO);
		return beforeTodayCount;
	}
	
	@Override
    public PlainResult<IncomePlanDO> findSumPlanIncome(IncomePlanDO incomePlanDO) {
        PlainResult<IncomePlanDO> result = new PlainResult<IncomePlanDO>();
        result.setData(incomePlanDao.queryTotalIncomeMoneyByIncome(incomePlanDO));
        return result;
    }

    @Override
    public BigDecimal queryIncomeInToday(Integer userid) {

        BigDecimal result = incomePlanDao.queryIncomeTotalInTodayByUserId(userid);

        return result;
    }
}
