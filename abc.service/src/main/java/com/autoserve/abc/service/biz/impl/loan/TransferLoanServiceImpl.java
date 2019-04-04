/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.BuyLoanSubscribeDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.SysConfigDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.dao.dataobject.search.TransferLoanSearchDO;
import com.autoserve.abc.dao.intf.BuyLoanSubscribeDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.SysConfigDao;
import com.autoserve.abc.dao.intf.TraceRecordDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.convert.TransferLoanConverter;
import com.autoserve.abc.service.biz.convert.TransferLoanTraceRecordConverter;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.TransferLoanTraceRecord;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.BuyLoanSubscribeState;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.TransferLoanTraceOperation;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.NumberRuleService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.service.util.DateUtil;

/**
 * 转让标服务的实现
 * 
 * @author segen189 2014年11月23日 下午3:42:46
 */
@Service
public class TransferLoanServiceImpl implements TransferLoanService,InitializingBean {
	private static final Logger logger = LoggerFactory
			.getLogger(TransferLoanServiceImpl.class);

	@Resource
	private TransferLoanDao transferLoanDao;

	@Resource
	private LoanDao loanDao;

	@Resource
	private TraceRecordDao traceRecordDao;

	@Resource
	private BuyLoanSubscribeDao buyLoanSubscribeDao;

	@Resource
	private PaymentPlanService paymentPlanService;

	@Resource
	private IncomePlanService incomePlanService;

	@Resource
	private InvestQueryService investQueryService;

	@Resource
	private ReviewService reviewService;

	@Resource
	private NumberRuleService numberRuleService;
	@Resource
	private SysConfigDao sysConfigDao;
	/**转让距离付息日天数限制*/
	private static int  BEFORE_PAY_DAY = 3;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		SysConfigDO sysConfigDO = sysConfigDao.findByConfigKey("BEFORE_PAY_DAY");
		if(sysConfigDO!=null){
			try{
				BEFORE_PAY_DAY = Integer.parseInt(sysConfigDO.getConfValue());
			}catch(Exception e){
				//从数据库中取值异常，使用默认值
			}
		}
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PlainResult<Integer> createTransferLoan(TransferLoan pojo) {
        PlainResult<Integer> result = new PlainResult<Integer>();

        if (pojo == null) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM, "transferLoan");
        } else if (pojo.getTransferMoney() == null || pojo.getTransferMoney().compareTo(BigDecimal.ZERO) <= 0) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM, "transferMoney");
        } else if (pojo.getTransferPeriod() == null || pojo.getTransferPeriod() <= 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "转让期数小于或等于0");
        }

        // 检查 LoanState
        LoanDO loanDO = loanDao.findById(pojo.getOriginId());
        if (loanDO == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "所属借款标不存在");
        } else if (!loanDO.getLoanState().equals(LoanState.REPAYING.getState())) {
            return result.setError(CommonResultCode.BIZ_ERROR, "所属借款标的当前状态不允许发起转让");
        }
        
        //检查转让金额
        if(pojo.getTransferMoney().compareTo(new BigDecimal("1000"))<0){
        	return result.setError(CommonResultCode.BIZ_ERROR, "少于1000元不允许转让");
        }
        
        //检查是否逾期
        IncomePlan incomePlan = new IncomePlan();
        incomePlan.setInvestId(pojo.getInvestId());
        ListResult<IncomePlan> incomePlanResult =incomePlanService.queryIncomePlanList(incomePlan);
        if(!incomePlanResult.isSuccess()){
        	return result.setError(CommonResultCode.BIZ_ERROR, "回款计划查询失败");
        }
        List<IncomePlan> incomePlanList = incomePlanResult.getData();
        Date now = new Date();
        for(IncomePlan in:incomePlanList){
        	//逾期未还 in.getPaytime().after(dateTime.toDate()
        	if(IncomePlanState.GOING.equals(in.getIncomePlanState()) && DateUtil.substractDay(in.getPaytime(), now)<0){
        		return result.setError(CommonResultCode.BIZ_ERROR, "逾期还款的项目不允许转让");
        	}
        	//有逾期记录  edit by 夏同同   客户要求不限制有逾期记录的标转让
        	//if(IncomePlanState.CLEARED.equals(in.getIncomePlanState()) && DateUtil.substractDay(in.getCollecttime(), in.getPaytime())>0){
        		//return result.setError(CommonResultCode.BIZ_ERROR, " 此项目有逾期记录，不允许转让");
        	//}
        	//检查是否提前还款
        	if(IncomePlanState.CLEARED.equals(in.getIncomePlanState()) && DateUtil.compareDay(in.getPaytime(), now)>0){
            	return result.setError(CommonResultCode.BIZ_ERROR, " 此项目已提前还款，不允许转让");
        	}
        	//当期距付息日x天不允许转让
        	if(IncomePlanState.GOING.equals(in.getIncomePlanState()) && DateUtil.substractDay(in.getPaytime(), now)<=BEFORE_PAY_DAY){
        		return result.setError(CommonResultCode.BIZ_ERROR, "付息日"+BEFORE_PAY_DAY+"天前不允许转让，可等到付息日之后转让");
        	}
        }
        
        // 检查 InvestState
        //        Invest param = new Invest();
        //        param.setUserId(pojo.getUserId());
        //        param.setOriginId(pojo.getOriginId());
        //        param.setInvestState(InvestState.EARNING); // 支付成功待收益的债权才可用来转让
        //        PlainResult<Invest> investResult = investQueryService.queryInvest(param);
        //        if (!investResult.isSuccess()) {
        //            return result.setError(CommonResultCode.BIZ_ERROR, "要用来转让的债权投资记录查询失败");
        //        } else if (investResult.getData() == null) {
        //            return result.setError(CommonResultCode.BIZ_ERROR, "要用来转让的债权投资记录不存在");
        //        } else if (!investResult.getData().getInvestState().equals(InvestState.EARNING)) {
        //            return result.setError(CommonResultCode.BIZ_ERROR, "要用来转让的债权投资记录的状态不合法");
        //        }

        //根据投资ID进行转让
        PlainResult<Invest> investResult = this.investQueryService.queryById(pojo.getInvestId());
        if (!investResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "要用来转让的债权投资记录查询失败");
        } else if (investResult.getData() == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "要用来转让的债权投资记录不存在");
        } else if (!investResult.getData().getInvestState().equals(InvestState.EARNING)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "要用来转让的债权投资记录的状态不合法");
        }
        //控制一期之内不允许二转
        if(investResult.getData().getBidType()==BidType.TRANSFER_LOAN){
        	//第一次收益没拿到，说明一期之内发生了二转
        	IncomePlan firstIncome = incomePlanList.get(0);
        	if(firstIncome.getIncomePlanState()!=IncomePlanState.CLEARED){
        		return result.setError(CommonResultCode.BIZ_ERROR, "一期内不允许二次转让，可在付息日后再转让");
        	}
        }

        // 设置investId
        pojo.setInvestId(investResult.getData().getId());

        PlainResult<PaymentPlan> nextPaymentPlan = paymentPlanService.queryNextPaymentPlan(pojo.getOriginId());
        if (!nextPaymentPlan.isSuccess() || nextPaymentPlan.getData() == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "借款标要进行的下一期还款计划查询失败");
        }

        // 无锁检查有没有重复发起转让(根据LoanId)
        //        TransferLoanDO transferParam = new TransferLoanDO();
        //        transferParam.setTlUserId(pojo.getUserId());
        //        transferParam.setTlOriginId(pojo.getOriginId());
        //        TransferLoanDO transferLoanDO = transferLoanDao.findByParam(transferParam);
        //        if (transferLoanDO != null && !transferLoanDO.getTlState().equals(TransferLoanState.DELETED.getState())) {
        //            return result.setError(CommonResultCode.BIZ_ERROR, "转让人已经发起过转让，不可重复转让");
        //        }

        // 无锁检查有没有重复发起转让(根据investId)
        TransferLoanDO transferParam = new TransferLoanDO();
        transferParam.setTlUserId(pojo.getUserId());
        transferParam.setTlInvestId(pojo.getInvestId());
        TransferLoanDO transferLoanDO = transferLoanDao.findByParam(transferParam);
        // edit by 夏同同   20160527 解决转让初审不过后无法再申请的问题
        // 若查询到的记录为初审未通过，则删除该笔记录
        if (transferLoanDO != null) {
            if(transferLoanDO.getTlState() == TransferLoanState.FIRST_REVIEW_FAIL.getState() 
            		|| transferLoanDO.getTlState() == TransferLoanState.BID_CANCELED.getState())
            {
                //删除该笔数据
                int delResult = transferLoanDao.delete(transferLoanDO.getTlId());
                if(delResult != 1){
                    return result.setError(CommonResultCode.BIZ_ERROR, "已经发起过转让，不可再次转让");
                }
            }else{
                return result.setError(CommonResultCode.BIZ_ERROR, "已经发起过转让，不可再次转让");
            }
        }

        // 全额转让，转让期数不做控制
        //        PlainResult<BigDecimal> transferTotalResult = incomePlanService.countCapitalByInvestId(pojo.getInvestId(),
        //                pojo.getTransferPeriod(), IncomePlanState.GOING);
        PlainResult<BigDecimal> transferTotalResult = incomePlanService.sumCapitalByInvestId(investResult.getData()
                .getId(), null, IncomePlanState.GOING);
        if (!transferTotalResult.isSuccess() || transferTotalResult.getData() == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "转让人当前未结清的债权查询失败");
        }

        // 检查转让的债权没有被用来作认购
        BuyLoanSubscribeDO subscribeDO = buyLoanSubscribeDao.findOneWithLock(null, pojo.getOriginId(),
                pojo.getUserId(), BuyLoanSubscribeState.SUBSCRIBING.getState());
        if (subscribeDO != null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "不可转让已经用来认购的债权");
        }

        TransferLoanDO toInsert = TransferLoanConverter.toTransferLoanDO(pojo);
        //下一次还款计划id
        toInsert.setTlNextPaymentId(nextPaymentPlan.getData().getId());
        toInsert.setTlTransferTotal(transferTotalResult.getData());
        toInsert.setTlState(TransferLoanState.WAIT_REVIEW.getState());
        toInsert.setTlCurrentInvest(BigDecimal.ZERO);
        toInsert.setTlCurrentValidInvest(BigDecimal.ZERO);

        // 转让折让费
        pojo.setTransferDiscountFee(transferTotalResult.getData().subtract(pojo.getTransferMoney()));

        // 转让折让率 ＝ 1 - 转让金额/转让债权总额
        toInsert.setTlTransferDiscountRate(new BigDecimal((1 - Arith.div(pojo.getTransferMoney().doubleValue(),
                transferTotalResult.getData().doubleValue())) * 100));

        // 转让后利率 ＝ 转让总债券*转让前利率/转让金额
//        toInsert.setTlTransferRate(new BigDecimal(Arith.div(transferTotalResult.getData()
//                .multiply(loanDO.getLoanRate()).doubleValue(), pojo.getTransferMoney().doubleValue())));
        toInsert.setTlTransferRate(new BigDecimal(loanDO.getLoanRate().doubleValue()));
        
        transferLoanDao.insert(toInsert);
        PlainResult<String> resutlt = numberRuleService.createNumberByYear();
        toInsert.setTlLoanNo(resutlt.getData());
        transferLoanDao.update(toInsert);
        // 添加转让项目跟踪记录
        TransferLoanTraceRecord traceRecord = new TransferLoanTraceRecord();

        traceRecord.setCreator(pojo.getUserId());
        traceRecord.setLoanId(pojo.getOriginId());
        traceRecord.setTransferLoanId(toInsert.getTlId());
        traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.launch);
        traceRecord.setOldTransferLoanState(null);
        traceRecord.setNewTransferLoanState(TransferLoanState.WAIT_REVIEW);
        traceRecord.setNote("发起转让项目");

        traceRecordDao.insert(TransferLoanTraceRecordConverter.toTraceRecordDO(traceRecord));

        // 创建转让标成功后发起转让初审
        Review review = new Review();
        review.setType(ReviewType.LOAN_TRANSFER_REVIEW);
        review.setApplyId(toInsert.getTlId());
        review.setCurrRole(BaseRoleType.PLATFORM_SERVICE);
        BaseResult reviewRes = reviewService.initiateReview(review);
        if (!reviewRes.isSuccess()) {
            logger.error("发起转让审核失败！TransferLoanId={}", toInsert.getTlId());
            throw new BusinessException("发起转让审核失败");
        }

        result.setData(toInsert.getTlId());
        return result;
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public BaseResult modifyTransferLoanInfo(TransferLoan pojo,
			TransferLoanTraceRecord traceRecord) {
		if (pojo == null) {
			return new BaseResult().setError(CommonResultCode.ILEEGAL_ARGUMENT);
		} else if (pojo.getTransferLoanState() != null && traceRecord == null) {
			return new BaseResult().setError(CommonResultCode.ILEEGAL_ARGUMENT);
		}

		// 添加项目跟踪状态记录
		if (traceRecord != null) {
			int inCount = traceRecordDao
					.insert(TransferLoanTraceRecordConverter
							.toTraceRecordDO(traceRecord));
			if (inCount <= 0) {
				return new BaseResult().setError(CommonResultCode.BIZ_ERROR,
						"项目跟踪状态记录创建失败");
			}
		}
		// 编辑项目信息
		if (pojo != null) {
			int upCount = transferLoanDao.update(TransferLoanConverter
					.toTransferLoanDO(pojo));
			if (upCount <= 0) {
				throw new BusinessException("转让标项目信息修改失败");
			}
		}
		return BaseResult.SUCCESS;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public BaseResult changeTransferLoanState(
			TransferLoanTraceRecord traceRecord) {
		BaseResult result = new BaseResult();

		// 添加项目跟踪状态记录
		int count = traceRecordDao.insert(TransferLoanTraceRecordConverter
				.toTraceRecordDO(traceRecord));
		if (count <= 0) {
			return result.setError(CommonResultCode.BIZ_ERROR, "项目跟踪状态记录创建失败");
		}

		// 更改项目状态
		count = transferLoanDao.updateState(traceRecord.getTransferLoanId(), 
				traceRecord.getOldTransferLoanState().getState(), 
				traceRecord.getNewTransferLoanState().getState());
		if (count <= 0) {
			return result.setError(CommonResultCode.BIZ_ERROR, "项目状态改变失败");
		}

		return result;
	}

	@Override
	public PlainResult<TransferLoan> queryById(int id) {
		PlainResult<TransferLoan> result = new PlainResult<TransferLoan>();

		TransferLoan transferLoan = TransferLoanConverter
				.toTransferLoan(transferLoanDao.findById(id));
		if (transferLoan == null) {
			return result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS,
					"TransferLoan");
		}

		result.setData(transferLoan);
		return result;
	}

	@Override
	public ListResult<TransferLoan> queryByIds(List<Integer> ids) {
		ListResult<TransferLoan> result = new ListResult<TransferLoan>();

		if (CollectionUtils.isEmpty(ids)) {
			return result.setErrorMessage(CommonResultCode.ILEEGAL_ARGUMENT,
					"loanIds");
		}

		List<TransferLoanDO> transferLoanDOList = transferLoanDao
				.findByList(ids);

		if (CollectionUtils.isEmpty(transferLoanDOList)) {
			return result.setErrorMessage(
					CommonResultCode.ERROR_DATA_NOT_EXISTS, "loanIds");
		}

		result.setData(TransferLoanConverter
				.toTransferLoanList(transferLoanDOList));
		return result;
	}

	@Override
	public PlainResult<TransferLoan> queryByParam(TransferLoan pojo) {
		PlainResult<TransferLoan> result = new PlainResult<TransferLoan>();
		result.setData(TransferLoanConverter.toTransferLoan(transferLoanDao
				.findByParam(TransferLoanConverter.toTransferLoanDO(pojo))));
		return result;
	}	

	@Override
	public PageResult<TransferLoan> queryListByParam(TransferLoan pojo,
			PageCondition pageCondition) {
		PageResult<TransferLoan> result = new PageResult<TransferLoan>(
				pageCondition.getPage(), pageCondition.getPageSize());

		int totalCount = transferLoanDao.countListByParam(TransferLoanConverter
				.toTransferLoanDO(pojo));
		result.setTotalCount(totalCount);

		if (totalCount > 0) {
			result.setData(TransferLoanConverter
					.toTransferLoanList(transferLoanDao.findListByParam(
							TransferLoanConverter.toTransferLoanDO(pojo),
							pageCondition)));
		}

		return result;
	}
	
	@Override
	public ListResult<TransferLoan> queryListByParam(TransferLoan pojo) {
		
		ListResult<TransferLoan> result = new ListResult<TransferLoan>();
		List<TransferLoanDO> data = transferLoanDao.findListByParam(
				TransferLoanConverter.toTransferLoanDO(pojo),null);
		if(data!=null && data.size()>0){
			result.setData(TransferLoanConverter.toTransferLoanList(data));
		}
		return result;
	}

	@Override
	public PageResult<TransferLoanJDO> queryListByParam(
			TransferLoanSearchDO pojo, PageCondition pageCondition) {
		PageResult<TransferLoanJDO> result = new PageResult<TransferLoanJDO>(
				pageCondition);

		int totalCount = transferLoanDao.countListBySearchParam(pojo);
		if (totalCount > 0) {
			result.setData(transferLoanDao.findListBySearchParam(pojo,
					pageCondition));
			result.setTotalCount(totalCount);
		}

		return result;
	}

	@Override
	public PageResult<TransferLoanTraceRecord> queryTraceRecordList(
			int transferLoanId, PageCondition pageCondition) {
		PageResult<TransferLoanTraceRecord> result = new PageResult<TransferLoanTraceRecord>(
				pageCondition);

		int totalCount = traceRecordDao.countList(transferLoanId,
				BidType.TRANSFER_LOAN.getType());
		if (totalCount > 0) {
			result.setData(TransferLoanTraceRecordConverter
					.toTraceRecordList(traceRecordDao.findList(transferLoanId,
							BidType.TRANSFER_LOAN.getType(), pageCondition)));
			result.setTotalCount(totalCount);
		}

		return result;
	}

	@Override
	public ListResult<TransferLoanJDO> queryListByParam(
			TransferLoanSearchDO pojo) {
		ListResult<TransferLoanJDO> result = new ListResult<TransferLoanJDO>();
		List<TransferLoanJDO> data = transferLoanDao.findListBySearchParam(
				pojo, null);
		result.setData(data);
		return result;
	}
	
	//add by xiatt 20160630 查询优选债券
	@Override
	public ListResult<TransferLoanJDO> queryTrOptimization(Integer topN){
		ListResult<TransferLoanJDO> result = new ListResult<TransferLoanJDO>();
		List<TransferLoanJDO> data = transferLoanDao.findTrOptimization(topN);
		result.setData(data);
		return result;
	}
	
	
}
