/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.callback;

import java.text.MessageFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.FullTransferRecordDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.SmsNotifyCfg;
import com.autoserve.abc.dao.dataobject.SmsNotifyDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.BuyLoanSubscribeDao;
import com.autoserve.abc.dao.intf.CompanyCustomerDao;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.dao.intf.FullTransferRecordDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.SmsNotifyDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.callback.center.CashCallBackCenter;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.BuyLoanTraceRecord;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.TransferLoanTraceRecord;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.BuyLoanState;
import com.autoserve.abc.service.biz.enums.BuyLoanSubscribeState;
import com.autoserve.abc.service.biz.enums.BuyLoanTraceOperation;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanTraceOperation;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.TransferLoanTraceOperation;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.message.mail.SendMailService;
import com.autoserve.abc.service.util.Jpush.JpushUtils;

/**
 * 满标资金划转交易执行后的回调函数
 *
 * @author segen189 2014年12月3日 上午11:19:10
 */
@Component
public class MoneyTransferedCallback implements Callback<DealNotify>, InitializingBean {
    private static final Logger   log = LoggerFactory.getLogger(MoneyTransferedCallback.class);
    @Resource
    private DealRecordDao         dealRecordDao;
    @Resource
    private FullTransferRecordDao fullTransferRecordDao;

    @Resource
    private LoanService           loanService;
    @Resource
    private AccountInfoService    accountInfoService;

    @Resource
    private UserService           userService;

    @Resource
    private TransferLoanService   transferLoanService;

    @Resource
    private BuyLoanService        buyLoanService;

    @Resource
    private BuyLoanSubscribeDao   buyLoanSubscribeDao;

    @Resource
    private InvestService         investService;

    @Resource
    private InvestQueryService    investQueryService;

    @Resource
    private PaymentPlanService    paymentPlanService;

    @Resource
    private IncomePlanService     incomePlanService;
    @Resource
    private LoanDao               loanDao;
    @Resource
    private SendMailService       sendMailService;
    @Resource
    private TransferLoanDao       transferLoanDao;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private SmsNotifyDao smsNotifyDao;
    @Resource
    private CompanyCustomerDao companyCustomerDao;
    @Autowired
    private GovernmentService   govService;
    @Autowired
    private EmployeeService employeeService;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult doCallback(DealNotify data) {
        switch (data.getState()) {
            case SUCCESS:
            	return doTransferedSuccess(data);
            case FAILURE:
                return doTransferedFailure(data);
            default:
                return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "交易状态的值不符合预期");
        }

    }

    /**
     * 满标资金划转成功：<br>
     * 1. 更新标状态<br>
     * 2. 更新投资状态，保证幂等，增加用户积分<br>
     * 3. 更新还款计划表状态<br>
     * 4. 更新收益计划表状态<br>
     * 5. 更新满标资金划转记录状态<br>
     * 6. 短信通知
     */
    private BaseResult doTransferedSuccess(DealNotify data) {
        // 1. 更新标状态
        // 2. 更新投资状态
        // check 幂等性
        FullTransferRecordDO fullTransferRecordDO = fullTransferRecordDao.findByInnerSeqNo(data.getInnerSeqNo());
        if (!fullTransferRecordDO.getFtrDealState().equals(DealState.NOCALLBACK.getState())) {
            return BaseResult.SUCCESS;
        }

        if (fullTransferRecordDO.getFtrBidType().equals(BidType.COMMON_LOAN.getType())) {
            Loan toModify = new Loan();
            toModify.setLoanId(fullTransferRecordDO.getFtrBidId());
            toModify.setLoanState(LoanState.REPAYING);
            toModify.setLoanFullTransferedtime(new Date());

            // 项目跟踪状态记录
            LoanTraceRecord traceRecord = new LoanTraceRecord();
            traceRecord.setCreator(fullTransferRecordDO.getFtrOperator());
            traceRecord.setLoanId(toModify.getLoanId());
            traceRecord.setLoanTraceOperation(LoanTraceOperation.loanMoneyTransfer);
            traceRecord.setOldLoanState(LoanState.MONEY_TRANSFERING);
            traceRecord.setNewLoanState(LoanState.REPAYING);
            traceRecord.setNote("普通标项目满标资金划转成功");

            BaseResult modResult = loanService.modifyLoanInfo(toModify, traceRecord);
            if (!modResult.isSuccess()) {
                throw new BusinessException("普通标状态修改失败");
            }

            // 3. 更新还款计划表状态
            PlainResult<Integer> modifyPayPlanStateResult = paymentPlanService.batchModifyStateByFullTransRecordId(
                    fullTransferRecordDO.getFtrId(), PayState.INACTIVED, PayState.UNCLEAR);
            if (!modifyPayPlanStateResult.isSuccess() || modifyPayPlanStateResult.getData() <= 0) {
                throw new BusinessException("批量修改还款计划表状态为未支付失败");
            }

            // 4. 更新收益计划表状态，投资状态
            PlainResult<Integer> newModResult = incomePlanService.batchModifyIncomePlanAndInvest(
                    fullTransferRecordDO.getFtrId(), IncomePlanState.INACTIVED, IncomePlanState.GOING,
                    InvestState.PAID, InvestState.EARNING);
            if (!newModResult.isSuccess() || newModResult.getData() <= 0) {
                log.warn(newModResult.getMessage());
                throw new BusinessException("批量修改投资人的收益计划状态和投资状态失败");
            }

            //xiatt 20161209 二次开发的记录在DmFullTransferServiceImpl.java:413行处加了
//            String allocation = loanDo.getLoanSecondaryAllocation();
//            if (!("2".equals(allocation))) {
//                BigDecimal moneyAmount = data.getTotalFee();
//                DealRecordDO addDeal = new DealRecordDO();
//                addDeal.setDrMoneyAmount(moneyAmount);
//                AccountInfoDO accountInfo = accountInfoService.queryByAccountMark(loanDo.getLoanUserId(),
//                        loanDo.getLoanEmpType());
//                addDeal.setDrPayAccount(accountInfo.getAccountNo());
//                
//                //
//                AccountInfoDO accountInfoDO = null;
//                if("0".equals(allocation)){
//                //	PlainResult<User> userResult = userService.findEntityById(loanDo.getLoanSecondaryUser());
//                	accountInfoDO = accountInfoService.queryByAccountMark(loanDo.getLoanSecondaryUser(),
//                            UserType.PERSONAL.type);
//                }
//                else if("1".equals(allocation)){
//            		accountInfoDO = accountInfoService.queryByAccountMark(loanDo.getLoanSecondaryUser(),
//                            UserType.PARTNER.type);
//                }
//                
//                addDeal.setDrReceiveAccount(accountInfoDO.getAccountNo());
//                addDeal.setDrBusinessId(fullTransferRecordDO.getFtrBidId());
//                addDeal.setDrType(DealType.TRANSFER.getType());
//                addDeal.setDrDetailType(DealDetailType.SECONDARY.getType());
//                addDeal.setDrInnerSeqNo(data.getInnerSeqNo());
//                addDeal.setDrOperator(loanDo.getLoanUserId());
//                List<DealRecordDO> addDealRecords = new ArrayList<DealRecordDO>();
//                addDealRecords.add(addDeal);
//                int flag = dealRecordDao.batchInsert(addDealRecords);
//                if (flag <= 0) {//插入不成功处理
//                    throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),
//                            "[DealRecordServiceImpl][createBusinessRecord] 批量插入交易记录出错");
//                }
//            }
            try
            {
            	LoanDO loanDo = loanDao.findByLoanId(fullTransferRecordDO.getFtrBidId());
            	sendMailService.sendMailToInvestUser(fullTransferRecordDO.getFtrBidId(), loanDo.getLoanNo());
            }
            catch (Exception e)
            {
            	e.printStackTrace();
            }

        } else if (fullTransferRecordDO.getFtrBidType().equals(BidType.TRANSFER_LOAN.getType())) {
            // IncomePlanState.GOING -> IncomePlanState.TRANSFERED
            // InvestState.EARNING -> InvestState.TRANSFERED
            // 更新转让人的收益计划表状态
            PlainResult<Integer> oldModResult = incomePlanService.batchModifyIncomePlanAndInvest(fullTransferRecordDO.getFtrId(),
                    fullTransferRecordDO.getFtrOriginId(), fullTransferRecordDO.getFtrUserId(), IncomePlanState.GOING,
                    IncomePlanState.TRANSFERED, InvestState.EARNING, InvestState.TRANSFERED);
            if (!oldModResult.isSuccess() || oldModResult.getData() <= 0) {
                log.warn(oldModResult.getMessage());
                throw new BusinessException("批量修改转让人的收益计划状态和投资状态失败");
            }
        	
            TransferLoan toModify = new TransferLoan();
            toModify.setId(fullTransferRecordDO.getFtrBidId());
            toModify.setTransferLoanState(TransferLoanState.MONEY_TRANSFERED);
            toModify.setFullTransferedtime(new Date());

            // 项目跟踪状态记录
            TransferLoanTraceRecord traceRecord = new TransferLoanTraceRecord();
            traceRecord.setCreator(fullTransferRecordDO.getFtrOperator());
            traceRecord.setLoanId(toModify.getOriginId());
            traceRecord.setTransferLoanId(fullTransferRecordDO.getFtrBidId());
            traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.moneyTransferSucceed);
            traceRecord.setOldTransferLoanState(TransferLoanState.FULL_REVIEW_PASS);
            traceRecord.setNewTransferLoanState(TransferLoanState.MONEY_TRANSFERED);
            traceRecord.setNote("转让标项目满标资金划转成功");

            BaseResult modResult = transferLoanService.modifyTransferLoanInfo(toModify, traceRecord);
            if (!modResult.isSuccess()) {
                throw new BusinessException("转让标状态修改失败");
            }

            // 4. 更新受让人的收益计划表状态，受让人的投资状态
            PlainResult<Integer> newModResult = incomePlanService.batchModifyIncomePlanAndInvest(
                    fullTransferRecordDO.getFtrId(), IncomePlanState.INACTIVED, IncomePlanState.GOING,
                    InvestState.PAID, InvestState.EARNING);
            log.debug("更新受让人收益计划表，投资状态，影响行数：{}", newModResult.getData());
            if (!newModResult.isSuccess() || newModResult.getData() <= 0) {
                log.warn(newModResult.getMessage());
                throw new BusinessException("批量修改转让人的收益计划状态和投资状态失败");
            }
            
            //更新转让人的收益计划表
            PlainResult<Integer> newModResult2 = incomePlanService.batchModifyIncomePlanAndInvest(
                    fullTransferRecordDO.getFtrId(), IncomePlanState.INACTIVED, IncomePlanState.GOING,
                    InvestState.TRANSFERED, InvestState.TRANSFERED);
            log.debug("更新转让人收益计划表，影响行数：{}", newModResult2.getData());
            
            try
            {
            	TransferLoanDO loanDo = transferLoanDao.findById(fullTransferRecordDO.getFtrBidId());
                sendMailService.sendMailToCreditoUser(fullTransferRecordDO.getFtrBidId(), loanDo.getTlLoanNo());
            }
            catch (Exception e)
            {
            	e.printStackTrace();
            }
            // 增加用户积分
            //            BaseResult scoreAddResult = userService.modifyUserScore(fullTransferRecordDO.getFtrUserId(),
            //                    ScoreType.TRANSFER_LOAN, "用户转让项目，系统赠送用户积分");
            //            if (!scoreAddResult.isSuccess()) {
            //                log.warn(scoreAddResult.getMessage());
            //                //throw new BusinessException("赠送用户积分失败"); THROW OR NOT
            //            } 

        } else if (fullTransferRecordDO.getFtrBidType().equals(BidType.BUY_LOAN.getType())) {
            // LOCK NEXT PAYMENT
            // IncomePlanState.GOING, IncomePlanState.BUYED
            // InvestState.EARNING -> InvestState.BUYED
            // 更新认购人的收益计划表状态，认购人的旧投资的投资状态
            // 全额收购，否决则收购失败check
            PlainResult<Integer> oldModResult = incomePlanService.batchModifyIncomePlanAndInvest(null,
                    fullTransferRecordDO.getFtrOriginId(), null, IncomePlanState.GOING, IncomePlanState.BUYED,
                    InvestState.EARNING, InvestState.BUYED);
            if (!oldModResult.isSuccess() || oldModResult.getData() <= 0) {
                log.warn(oldModResult.getMessage());
                throw new BusinessException("批量修改认购人的收益计划状态和投资状态失败");
            }

            BuyLoan toModify = new BuyLoan();
            toModify.setId(fullTransferRecordDO.getFtrBidId());
            toModify.setBuyLoanState(BuyLoanState.MONEY_TRANSFERED);
            toModify.setFullTransferedtime(new Date());

            // 项目跟踪状态记录
            BuyLoanTraceRecord traceRecord = new BuyLoanTraceRecord();
            traceRecord.setCreator(fullTransferRecordDO.getFtrOperator());
            traceRecord.setLoanId(toModify.getOriginId());
            traceRecord.setBuyLoanId(fullTransferRecordDO.getFtrBidId());
            traceRecord.setBuyLoanTraceOperation(BuyLoanTraceOperation.moneyTransferSucceed);
            traceRecord.setOldBuyLoanState(BuyLoanState.MONEY_TRANSFERING);
            traceRecord.setNewBuyLoanState(BuyLoanState.MONEY_TRANSFERED);
            traceRecord.setNote("收购标项目满标资金划转成功，buyLoanId=" + toModify.getId());

            BaseResult modResult = buyLoanService.modifyBuyLoanInfo(toModify, traceRecord);
            if (!modResult.isSuccess()) {
                throw new BusinessException("收购标状态修改失败");
            }

            // 认购人投资状态修改
            int count = buyLoanSubscribeDao.updateState(fullTransferRecordDO.getFtrBidId(), null,
                    BuyLoanSubscribeState.SUBSCRIBING.getState(), BuyLoanSubscribeState.SUBSCRIBE_PASS.getState());
            if (count <= 0) {
                throw new BusinessException("批量更新收购认购表状态失败");
            }

            // 4. 更新收购人的收益计划表状态，认购人的新投资的投资状态
            PlainResult<Integer> newModResult = incomePlanService.batchModifyIncomePlanAndInvest(
                    fullTransferRecordDO.getFtrId(), IncomePlanState.INACTIVED, IncomePlanState.GOING,
                    InvestState.PAID, InvestState.EARN_COMPLETED);
            if (!newModResult.isSuccess() || newModResult.getData() <= 0) {
                log.warn(newModResult.getMessage());
                throw new BusinessException("批量修改收购人的收益计划状态和投资状态失败");
            }

            // 增加用户积分
            //            BaseResult scoreAddResult = userService.modifyUserScore(fullTransferRecordDO.getFtrUserId(),
            //                    ScoreType.PURCHASE_LOAN, "用户收购项目，系统赠送用户积分");
            //            if (!scoreAddResult.isSuccess()) {
            //                log.warn(scoreAddResult.getMessage());
            //                //throw new BusinessException("赠送用户积分失败");THROW OR NOT
            //            }
        }

        // 5. 更新满标资金划转记录状态
        FullTransferRecordDO toModify = new FullTransferRecordDO();
        toModify.setFtrId(fullTransferRecordDO.getFtrId());
        toModify.setFtrDealState(DealState.SUCCESS.getState());
        fullTransferRecordDao.update(toModify);

        // 6. 短信通知
        try {
        	if (fullTransferRecordDO.getFtrBidType().equals(BidType.COMMON_LOAN.getType())) { // 普通标划转
        		SmsNotifyCfg smsNotifyCfg = JSON.parseObject(sysConfigService.querySysConfig(SysConfigEntry.SMS_NOTIFY_COMMON_TRANSFER_CFG).getData().getConfValue(), SmsNotifyCfg.class);
        		if(smsNotifyCfg.getSwitchState() == 1) {
        			SmsNotifyDO smsNotifyDO = new SmsNotifyDO();
        			String pattern = smsNotifyCfg.getContentTemplate();
        			LoanDO loanDo = loanDao.findByLoanId(fullTransferRecordDO.getFtrBidId());
            		UserDO userDo = userService.findById(loanDo.getLoanUserId()).getData();
            		String phone = null;
            		if(userDo.getUserType() == 1) {
            			phone = userDo.getUserPhone();
            		} else {
            			phone = companyCustomerDao.findByUserId(userDo.getUserId()).getCcContactPhone();
            		}
            		String content = MessageFormat.format(pattern, loanDo.getLoanNo(), loanDo.getLoanMoney());
            		smsNotifyDO.setReceivePhone(phone);
                	smsNotifyDO.setContent(content);
                	smsNotifyDO.setCreateTime(new Date());
                	smsNotifyDO.setSendStatus(0);
                	smsNotifyDO.setSendCount(0);
                	smsNotifyDao.insert(smsNotifyDO);
                	/**消息推送给app**/
                	try{
                		JpushUtils.sendPush_alias(userDo.getUserName(),content);
                	}catch(Exception e){
                		log.error(e.getMessage());
                	}
        		}
        		
        	} else if (fullTransferRecordDO.getFtrBidType().equals(BidType.TRANSFER_LOAN.getType())) { // 转让标划转
        		SmsNotifyCfg smsNotifyCfg = JSON.parseObject(sysConfigService.querySysConfig(SysConfigEntry.SMS_NOTIFY_SPECIAL_TRANSFER_CFG).getData().getConfValue(), SmsNotifyCfg.class);
        		if(smsNotifyCfg.getSwitchState() == 1) {
        			SmsNotifyDO smsNotifyDO = new SmsNotifyDO();
        			String pattern = smsNotifyCfg.getContentTemplate();
        			TransferLoanDO loanDo = transferLoanDao.findById(fullTransferRecordDO.getFtrBidId());
            		UserDO userDo = userService.findById(loanDo.getTlUserId()).getData();
            		String phone = null;
            		if(userDo.getUserType() == 1) {
            			phone = userDo.getUserPhone();
            		} else {
            			phone = companyCustomerDao.findByUserId(userDo.getUserId()).getCcContactPhone();
            		}
            		String content = MessageFormat.format(pattern, loanDo.getTlLoanNo(), loanDo.getTlTransferMoney());
            		smsNotifyDO.setReceivePhone(phone);
                	smsNotifyDO.setContent(content);
                	smsNotifyDO.setCreateTime(new Date());
                	smsNotifyDO.setSendStatus(0);
                	smsNotifyDO.setSendCount(0);
                	smsNotifyDao.insert(smsNotifyDO);
                	/**消息推送给app**/
                	try{
                		JpushUtils.sendPush_alias(userDo.getUserName(),content);
					}catch(Exception e){
						log.error(e.getMessage());       		
					}
        		}
        		
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return BaseResult.SUCCESS;
    }

    /**
     * 满标资金划转失败：<br>
     * 1. 更新标状态<br>
     * 2. 更新满标资金划转记录状态<br>
     */
    private BaseResult doTransferedFailure(DealNotify data) {
        // 1. 更新标状态
        FullTransferRecordDO fullTransferRecordDO = fullTransferRecordDao.findByInnerSeqNo(data.getInnerSeqNo());

        if (fullTransferRecordDO.getFtrBidType().equals(BidType.COMMON_LOAN.getType())) {
            Loan toModify = new Loan();
            toModify.setLoanId(fullTransferRecordDO.getFtrBidId());
            toModify.setLoanState(LoanState.FULL_REVIEW_PASS);
            toModify.setLoanFullTransferedtime(new Date());

            // 项目跟踪状态记录
            LoanTraceRecord traceRecord = new LoanTraceRecord();
            traceRecord.setCreator(fullTransferRecordDO.getFtrOperator());
            traceRecord.setLoanId(fullTransferRecordDO.getFtrBidId());
            traceRecord.setLoanTraceOperation(LoanTraceOperation.loanMoneyTransfer);
            traceRecord.setOldLoanState(LoanState.MONEY_TRANSFERING);
            traceRecord.setNewLoanState(LoanState.FULL_REVIEW_PASS);
            traceRecord.setNote("普通标项目满标资金划转失败");

            BaseResult modResult = loanService.modifyLoanInfo(toModify, traceRecord);
            if (!modResult.isSuccess()) {
                log.warn(modResult.getMessage());
                throw new BusinessException("普通标状态修改失败");
            }
        } else if (fullTransferRecordDO.getFtrBidType().equals(BidType.TRANSFER_LOAN.getType())) {
            TransferLoan toModify = new TransferLoan();
            toModify.setId(fullTransferRecordDO.getFtrBidId());
            toModify.setTransferLoanState(TransferLoanState.FULL_REVIEW_PASS);
            toModify.setFullTransferedtime(new Date());

            // 项目跟踪状态记录
            TransferLoanTraceRecord traceRecord = new TransferLoanTraceRecord();
            traceRecord.setCreator(fullTransferRecordDO.getFtrOperator());
            traceRecord.setLoanId(toModify.getOriginId());
            traceRecord.setTransferLoanId(fullTransferRecordDO.getFtrBidId());
            traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.moneyTransferFail);
            traceRecord.setOldTransferLoanState(TransferLoanState.MONEY_TRANSFERING);
            traceRecord.setNewTransferLoanState(TransferLoanState.FULL_REVIEW_PASS);

            traceRecord.setNote("转让标项目满标资金划转失败");

            BaseResult modResult = transferLoanService.modifyTransferLoanInfo(toModify, traceRecord);
            if (!modResult.isSuccess()) {
                throw new BusinessException("转让标状态修改失败");
            }
        } else if (fullTransferRecordDO.getFtrBidType().equals(BidType.BUY_LOAN.getType())) {
            BuyLoan toModify = new BuyLoan();
            toModify.setId(fullTransferRecordDO.getFtrBidId());
            toModify.setBuyLoanState(BuyLoanState.FULL_REVIEW_PASS);
            toModify.setFullTransferedtime(new Date());

            // 项目跟踪状态记录
            BuyLoanTraceRecord traceRecord = new BuyLoanTraceRecord();
            traceRecord.setCreator(fullTransferRecordDO.getFtrOperator());
            traceRecord.setLoanId(toModify.getOriginId());
            traceRecord.setBuyLoanId(fullTransferRecordDO.getFtrBidId());
            traceRecord.setBuyLoanTraceOperation(BuyLoanTraceOperation.moneyTransferFail);
            traceRecord.setOldBuyLoanState(BuyLoanState.MONEY_TRANSFERING);
            traceRecord.setNewBuyLoanState(BuyLoanState.FULL_REVIEW_PASS);
            traceRecord.setNote("收购标项目满标资金划转失败，buyLoanId=" + fullTransferRecordDO.getFtrBidId());

            BaseResult modResult = buyLoanService.modifyBuyLoanInfo(toModify, traceRecord);
            if (!modResult.isSuccess()) {
                throw new BusinessException("收购标状态修改失败");
            }

            int count = buyLoanSubscribeDao.updateState(fullTransferRecordDO.getFtrBidId(), null,
                    BuyLoanSubscribeState.SUBSCRIBING.getState(), BuyLoanSubscribeState.WAITING.getState());
            if (count <= 0) {
                throw new BusinessException("批量更新收购认购表状态失败");
            }
        }

        // 2. 更新满标资金划转记录状态
        FullTransferRecordDO toModify = new FullTransferRecordDO();
        toModify.setFtrSeqNo(data.getInnerSeqNo());
        toModify.setFtrDealState(DealState.FAILURE.getState());
        fullTransferRecordDao.updateByInnerSeqNo(toModify);

        return BaseResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CashCallBackCenter.registCallBack(DealType.TRANSFER, this);
    }
}
