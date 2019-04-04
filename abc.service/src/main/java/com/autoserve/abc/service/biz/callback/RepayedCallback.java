/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.callback;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.FullTransferRecordDO;
import com.autoserve.abc.dao.dataobject.IncomePlanDO;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.SmsNotifyCfg;
import com.autoserve.abc.dao.dataobject.SmsNotifyDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.dao.dataobject.search.TransferLoanSearchDO;
import com.autoserve.abc.dao.intf.FullTransferRecordDao;
import com.autoserve.abc.dao.intf.IncomePlanDao;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.dao.intf.SmsNotifyDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.callback.center.CashCallBackCenter;
import com.autoserve.abc.service.biz.convert.PaymentPlanConverter;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanTraceOperation;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.manage.TransferLoanManageService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.Jpush.JpushUtils;

/**
 * 还款交易执行后的回调函数
 * 
 * @author segen189 2014年12月19日 下午9:06:29
 */
@Service
public class RepayedCallback implements Callback<DealNotify>, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(RepayedCallback.class);

    @Resource
    private LoanDao             loanDao;

    @Resource
    private PaymentPlanService  paymentPlanService;

    @Resource
    private IncomePlanService   incomePlanService;

    @Resource
    private UserService         userService;

    @Resource
    private LoanService         loanService;

    @Resource
    private PaymentPlanDao      paymentPlanDao;

    @Resource
    private IncomePlanDao       incomePlanDao;
    
    @Resource
    private SysConfigService 	sysConfigService;
    
    @Resource
    private FullTransferRecordDao fullTransferRecordDao;
    
    @Resource
    private TransferLoanDao transferLoanDao;

    @Resource
    private SmsNotifyDao smsNotifyDao;
    @Autowired
    private InvestDao investDao;
    @Resource
	private TransferLoanManageService transferLoanManageService;
    
    @Autowired
    private ReviewService  reviewService;//xiatt 20160714 转让表因借款人还款后流标，需可以重新申请
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult doCallback(DealNotify data) {
        switch (data.getState()) {
            case SUCCESS:
                return doRepayedSuccess(data);
            case FAILURE:
                return doRepayedFailure(data);
            default:
                return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "交易状态的值不符合预期");
        }
    }

    // TODO  还款的时候去改机构信息（可用担保额度）
    // 添加还款记录
    private BaseResult doRepayedSuccess(DealNotify data) {
        BaseResult result = new BaseResult();

        PlainResult<PaymentPlan> paymentResult = paymentPlanService.queryPaymentPlanByInnerSeqNo(data.getInnerSeqNo());
        if (!paymentResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "还款计划查询失败");
        }

        final PaymentPlan paymentPlan = paymentResult.getData();
        if (paymentPlan == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "根据内部交易流水号未查到还款计划");
        }
        if(PayState.CLEAR==paymentPlan.getPayState()){
        	result.setMessage("已经还款成功！");
        	return result;
        }
        PlainResult<PaymentPlan> nextPaymentResult = paymentPlanService.queryNextPaymentPlan(paymentPlan.getLoanId());
        if (!nextPaymentResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "下一期还款计划查询失败");
        }

        // 更新还款计划状态
        PayState newPayState;
        if (PayType.COMMON_CLEAR == paymentPlan.getPayType() || PayType.FORCE_CLEAR == paymentPlan.getPayType()) {
            newPayState = PayState.CLEAR;
        } else {
            //newPayState = PayState.UNCLEAR;//平台代还请
        	newPayState = PayState.CLEAR;//担保机构代还请
        }

        //更新已还信息
        if (paymentPlan != null) {
            paymentPlan.setPayCollectCapital(paymentPlan.getPayCapital());
            paymentPlan.setPayCollectFine(paymentPlan.getPayFine());
            
            //add by 夏同同  增加违约罚金 20160411
            paymentPlan.setPayCollectBreachFine(paymentPlan.getPayBreachFine());
            
            paymentPlan.setPayCollectInterest(paymentPlan.getPayInterest());
            paymentPlan.setCollectServiceFee(paymentPlan.getPayServiceFee());
            paymentPlan.setCollectGuarFee(paymentPlan.getCollectGuarFee());
            paymentPlan.setCollectTotal(paymentPlan.getPayTotalMoney());
            this.paymentPlanDao.update(PaymentPlanConverter.toPaymentPlanDO(paymentPlan));
        }

        //更新收益已还信息
        this.incomePlanDao.batchIncomeMoneryUpdate(paymentPlan.getId());
        result = paymentPlanService.modifyPaymentPlan(data.getInnerSeqNo(), PayState.PAYING, newPayState,
                PayType.GUAR_CLEAR.equals(paymentPlan.getPayType()), paymentPlan);

        if (!result.isSuccess()) {
            log.warn("批量修改还款计划状态失败！{}", result.getMessage());
            throw new BusinessException("批量修改还款计划状态失败！");
        }

        // 全部还款都还清时
        if (PayState.CLEAR.equals(newPayState) && nextPaymentResult.getData() == null) {
            // 更新loan状态
            // 项目跟踪状态记录
            LoanTraceRecord traceRecord = new LoanTraceRecord();
            traceRecord.setCreator(0);
            traceRecord.setLoanId(paymentPlan.getLoanId());
            traceRecord.setLoanTraceOperation(LoanTraceOperation.repayedCompleted);
            traceRecord.setOldLoanState(LoanState.REPAYING);
            traceRecord.setNewLoanState(LoanState.REPAY_COMPLETED);
            traceRecord.setNote("借款全部还款已结束");

            BaseResult modResult = loanService.changeLoanState(traceRecord);
            if (!modResult.isSuccess()) {
                log.warn(modResult.getMessage());
                throw new BusinessException("借款全部还款已结束，普通标状态修改失败");
            }
           
            if (!paymentPlan.getReplaceState()) {
                // 更新收益计划状态，投资状态
                PlainResult<Integer> oldModResult = incomePlanService.batchModifyIncomePlanAndInvest(null,
                        paymentPlan.getLoanId(), null, IncomePlanState.PAYING, IncomePlanState.CLEARED,
                        InvestState.EARNING, InvestState.EARN_COMPLETED);
                if (!oldModResult.isSuccess()) {
                    log.warn("借款全部还款已结束，投资人投资记录状态更新失败！{}", oldModResult.getMessage());
                    throw new BusinessException("借款全部还款已结束，投资人投资记录状态更新失败");
                }
            }

        } 
        //全部还款未还清
        else {
            if (!paymentPlan.getReplaceState()) {
                // 更新收益计划状态
                result = incomePlanService.batchModifyStateByInnerSeqNo(data.getInnerSeqNo(), IncomePlanState.PAYING,
                        IncomePlanState.CLEARED);
                if (!result.isSuccess()) {
                    log.warn("批量修改收益计划状态失败！{}", result.getMessage());
                    throw new BusinessException("批量修改收益计划状态失败！");
                }
                //平台代换最后一期
                if(nextPaymentResult.getData()==null){
                	//仅修改投资状态
                	incomePlanService.batchModifyIncomePlanAndInvest(null,
                          paymentPlan.getLoanId(), null, IncomePlanState.CLEARED, IncomePlanState.CLEARED,
                          InvestState.EARNING, InvestState.EARN_COMPLETED);
                }
                
            }
        }
        //如果存在转让人的最后一期，将所有转让人的投资状态改为已转出
        IncomePlanDO income = new IncomePlanDO();
        income.setPiInnerSeqNo(data.getInnerSeqNo());
        List<IncomePlanDO>  incomeList = incomePlanDao.findListByParam(income, null);
        for(IncomePlanDO in:incomeList){
        	if(Boolean.TRUE.equals(in.getPiTransferLast())){
        		InvestDO invest = new InvestDO();
            	invest.setInId(in.getPiInvestId());
            	invest.setInInvestState(InvestState.TRANSFERED.state);
            	investDao.update(invest);
        	}
        }
       
        
        // 短信通知
        try {
        	SmsNotifyCfg smsNotifyCfg = JSON.parseObject(sysConfigService.querySysConfig(SysConfigEntry.SMS_NOTIFY_REPAYMENT_CFG).getData().getConfValue(), SmsNotifyCfg.class);
            if(smsNotifyCfg.getSwitchState() == 1) {
            	String pattern = smsNotifyCfg.getContentTemplate();
            	String loanNo = null;
            	FullTransferRecordDO fullTransferRecordDO = fullTransferRecordDao.findById(paymentPlan.getFullTransRecordId());
            	if(fullTransferRecordDO.getFtrBidType() == 0) { // 正常标
            		LoanDO loanDo = loanDao.findByLoanId(fullTransferRecordDO.getFtrBidId());
            		loanNo = loanDo.getLoanNo();
            		
            	} else if(fullTransferRecordDO.getFtrBidType() == 1) { // 转让标
            		TransferLoanDO loanDo = transferLoanDao.findById(fullTransferRecordDO.getFtrBidId());
            		loanNo = loanDo.getTlLoanNo();
            		
            	}
            	List<Map<String, Object>> list = incomePlanDao.findUserMapByPaymentPlanId(paymentPlan.getId());
            	for (Map<String, Object> map : list) {
            		SmsNotifyDO smsNotifyDO = new SmsNotifyDO();
            		String content = MessageFormat.format(pattern, loanNo, map.get("money"));
            		smsNotifyDO.setReceivePhone((String)map.get("phone"));
                	smsNotifyDO.setContent(content);
                	smsNotifyDO.setCreateTime(new Date());
                	smsNotifyDO.setSendStatus(0);
                	smsNotifyDO.setSendCount(0);
                	smsNotifyDao.insert(smsNotifyDO);
                	/**消息推送给app**/
                	try{
                		JpushUtils.sendPush_alias(map.get("userName").toString(),content);
                	}catch(Exception e){
                		log.error(e.getMessage());
                	}
                	
				}
            }
            //流标处理-还款流掉项目下所有未资金划转的债权
			TransferLoanSearchDO pojo = new TransferLoanSearchDO();
			pojo.setLoanId(paymentPlan.getLoanId());
			pojo.setTransferLoanStates(Arrays.asList(
					TransferLoanState.WAIT_REVIEW.state,
					TransferLoanState.TRANSFERING.state,
					TransferLoanState.FULL_WAIT_REVIEW.state,
					TransferLoanState.FULL_REVIEW_PASS.state,
					TransferLoanState.FULL_REVIEW_FAIL.state));
			List<TransferLoanJDO> tlList = transferLoanDao
					.findListBySearchParam(pojo, null);
			for (TransferLoanJDO tl : tlList) {
				//edit by xiatt  20160714   转让表因借款人还款后流标，需可以重新申请
				//transferLoanManageService.cancelTransferLoan(tl.getTlId(), 0,"还款自动流标");
				BaseResult cancelResult = transferLoanManageService.cancelTransferLoan(tl.getTlId(), 0,"还款自动流标");
				if (cancelResult.isSuccess()) {
					//还款自动流标后，软删除审核信息   解决还款自动流标后无法继续申请的问题(逻辑删)
					Integer reviewType = null;
					if(tl.getTlState() == TransferLoanState.FULL_WAIT_REVIEW.state || 
							tl.getTlState() == TransferLoanState.FULL_REVIEW_PASS.state ||
							tl.getTlState() == TransferLoanState.FULL_REVIEW_FAIL.state){
						reviewType = ReviewType.TRANSFER_FULL_BID_REVIEW.type;
					}else{
						reviewType = ReviewType.LOAN_TRANSFER_REVIEW.type;
					}
					reviewService.deleteReviewByApplyId(tl.getTlId(),reviewType);
				}
			}
        } catch (Exception e) {
			e.printStackTrace();
		}
        return result;
    }

    private BaseResult doRepayedFailure(DealNotify data) {
        PlainResult<PaymentPlan> paymentResult = paymentPlanService.queryPaymentPlanByInnerSeqNo(data.getInnerSeqNo());
        if (!paymentResult.isSuccess()) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "还款计划查询失败");
        }

        final PaymentPlan paymentPlan = paymentResult.getData();
        if (paymentPlan == null) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "根据内部交易流水号未查到还款计划");
        }

        BaseResult result = paymentPlanService.modifyPaymentPlan(data.getInnerSeqNo(), PayState.PAYING,
                PayState.UNCLEAR, null, paymentPlan);
        if (!result.isSuccess()) {
            return result;
        }

        result = incomePlanService.batchModifyStateByInnerSeqNo(data.getInnerSeqNo(), IncomePlanState.PAYING,
                IncomePlanState.GOING);
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CashCallBackCenter.registCallBack(DealType.PAYBACK, this);
    }

}
