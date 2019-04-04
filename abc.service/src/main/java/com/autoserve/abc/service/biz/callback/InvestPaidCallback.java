/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.callback;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.SmsNotifyCfg;
import com.autoserve.abc.dao.dataobject.SmsNotifyDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.BuyLoanDao;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.SmsNotifyDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.callback.center.CashCallBackCenter;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.entity.Red;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.TransferLoanTraceRecord;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanTraceOperation;
import com.autoserve.abc.service.biz.enums.OrderState;
import com.autoserve.abc.service.biz.enums.RedState;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.enums.ScoreType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.TransferLoanTraceOperation;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.ActivityRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanSubscribeService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.intf.score.ScoreHistoryService;
import com.autoserve.abc.service.biz.intf.score.ScoreService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.message.sms.SendMsgService;
import com.autoserve.abc.service.util.Jpush.JpushUtils;

/**
 * 投资支付成功时的回调
 * 
 * @author segen189 2014年12月3日 上午10:43:43
 */
@Component
public class InvestPaidCallback implements Callback<DealNotify>, InitializingBean {
    private static final Logger     log = LoggerFactory.getLogger(InvestPaidCallback.class);

    @Resource
    private InvestDao               investDao;

    @Resource
    private LoanDao                 loanDao;

    @Resource
    private TransferLoanDao         transferLoanDao;

    @Resource
    private BuyLoanDao              buyLoanDao;

    @Resource
    private InvestOrderService      investOrderService;

    @Resource
    private ActivityRecordService   activityRecordService;

    @Resource
    private DealRecordService       dealRecordService;

    @Resource
    private LoanService             loanService;

    @Resource
    private TransferLoanService     transferLoanService;

    @Resource
    private BuyLoanService          buyLoanService;

    @Resource
    private BuyLoanSubscribeService buyLoanSubscribeService;

    @Resource
    private ReviewService           reviewService;

    @Resource
    private UserService             userService;

    @Resource
    private RedService              redService;

    @Resource
    private UserDao                 userDao;

    @Resource
    private ScoreService            scoreService;

    @Resource
    private ScoreHistoryService     scoreHistoryService;

    @Resource
    private SysConfigService        sysConfigService;
    
    @Resource
	private SendMsgService 			sendMsgService;

    @Resource
    private SmsNotifyDao smsNotifyDao;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult doCallback(DealNotify data) {
        switch (data.getState()) {
            case SUCCESS:
                return doPaidSuccess(data);
            case FAILURE:
                return doPaidFailure(data);
            default:
                return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "交易状态的值不符合预期");
        }
    }

    /**
     * 当监听到资金交易完成后， 须触发检查：<br>
     * 1. 支付成功时：<br>
     * 1.1 更新所投标的有效投资金额 、如果满标则进入满标待审状态<br>
     * 1.2 更新订单的状态<br>
     * 1.3 更新投资活动的有效投资金额 、状态<br>
     * 1.4 增加用户积分
     * 1.5 短信通知
     */
    private BaseResult doPaidSuccess(DealNotify data) {
        // 1.3 更新所投标的有效投资金额、如果满标则进入满标待审状态
    	log.info("DealNotify流水号========"+data.getInnerSeqNo());
        InvestDO param = new InvestDO();
        param.setInInnerSeqNo(data.getInnerSeqNo());
        InvestDO investDO = investDao.findByParam(param);
        
        UserDO userDO = userDao.findById(investDO.getInUserId());
        if (userDO == null) {
            throw new BusinessException("未找到指定用户");
        }

        if (investDO.getInBidType().equals(BidType.COMMON_LOAN.getType())) {
            final LoanDO loanDO = loanDao.findByLoanIdWithLock(investDO.getInBidId());
            if (loanDO == null) {
                throw new BusinessException("普通标查询失败");
            }

            Loan toModify = new Loan();
            LoanTraceRecord traceRecord = null;

            toModify.setLoanId(loanDO.getLoanId());
            toModify.setLoanCurrentValidInvest(loanDO.getLoanCurrentValidInvest().add(data.getTotalFee()));
            if (toModify.getLoanCurrentValidInvest().equals(loanDO.getLoanMoney())) {
                toModify.setLoanState(LoanState.FULL_WAIT_REVIEW);
                toModify.setLoanInvestFulltime(new Date());

                // 发起审核流程
                Review review = new Review();
                review.setApplyId(loanDO.getLoanId());
                review.setType(ReviewType.LOAN_FULL_BID_REVIEW);
                review.setCurrRole(BaseRoleType.PLATFORM_SERVICE);
                BaseResult reviewRes = reviewService.initiateReview(review);
                if (!reviewRes.isSuccess()) {
                    log.error("发起项目满标审核失败！LoanId={}", loanDO.getLoanId());
                    throw new BusinessException("发起项目满标审核失败");
                }

                // 项目跟踪状态记录
                traceRecord = new LoanTraceRecord();
                traceRecord.setCreator(0);
                traceRecord.setLoanId(loanDO.getLoanId());
                traceRecord.setLoanTraceOperation(LoanTraceOperation.loanToFull);
                traceRecord.setOldLoanState(LoanState.BID_INVITING);
                traceRecord.setNewLoanState(LoanState.FULL_WAIT_REVIEW);
                traceRecord.setNote("普通标项目满标");
            }

            BaseResult loanModifyResult = loanService.modifyLoanInfo(toModify, traceRecord);
            if (!loanModifyResult.isSuccess()) {
                log.warn(loanModifyResult.getMessage());
                throw new BusinessException("普通标修改失败");
            }
        } else if (investDO.getInBidType().equals(BidType.TRANSFER_LOAN.getType())) {
            final TransferLoanDO transferLoanDO = transferLoanDao.findByTransferLoanIdWithLock(investDO.getInBidId());
            if (transferLoanDO == null) {
                throw new BusinessException("转让标查询失败");
            }

            TransferLoan toModify = new TransferLoan();
            TransferLoanTraceRecord traceRecord = null;

            toModify.setId(transferLoanDO.getTlId());
            toModify.setCurrentValidInvest(transferLoanDO.getTlCurrentValidInvest().add(data.getTotalFee()));
            if (toModify.getCurrentValidInvest().equals(transferLoanDO.getTlTransferMoney())) {//满标即划转成功，不需满标审核
                toModify.setTransferLoanState(TransferLoanState.MONEY_TRANSFERED);
                toModify.setFulltime(new Date());

                // 发起审核流程
                /*Review review = new Review();
                review.setApplyId(transferLoanDO.getTlId());
                review.setType(ReviewType.TRANSFER_FULL_BID_REVIEW);
                review.setCurrRole(BaseRoleType.PLATFORM_SERVICE);
                BaseResult reviewRes = reviewService.initiateReview(review);
                if (!reviewRes.isSuccess()) {
                    log.error("发起转让项目满标审核失败！TransferLoanId={}", transferLoanDO.getTlId());
                    throw new BusinessException("发起转让项目满标审核失败");
                }*/

                // 项目跟踪状态记录
                traceRecord = new TransferLoanTraceRecord();
                traceRecord.setCreator(0);
                traceRecord.setLoanId(transferLoanDO.getTlOriginId());
                traceRecord.setTransferLoanId(transferLoanDO.getTlId());
                traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.passFullReview);
                traceRecord.setOldTransferLoanState(TransferLoanState.TRANSFERING);
                traceRecord.setNewTransferLoanState(TransferLoanState.MONEY_TRANSFERED);
                traceRecord.setNote("转让标项目满标");
            }

            BaseResult transferLoanModifyResult = transferLoanService.modifyTransferLoanInfo(toModify, traceRecord);
            if (!transferLoanModifyResult.isSuccess()) {
                log.warn(transferLoanModifyResult.getMessage());
                throw new BusinessException("转让标修改失败");
            }
        }
        
        try {
        	
        	// 1.2 修改订单状态为支付成功 注意投资订单记录的流水号在保存时末尾加0了
            BaseResult orderModResult = investOrderService.modifyInvestOrderState(data.getInnerSeqNo()+"0", OrderState.PAID);
            if (!orderModResult.isSuccess()) {
                log.warn(orderModResult.getMessage());
                throw new BusinessException("订单支付状态修改失败");
            }

            // 1.3 修改投资活动的有效投资金额、状态
            int count = 0;
            if (investDO.getInBidType().equals(BidType.COMMON_LOAN.getType())) {
            	count = investDao.updateValidInvestMoneyByInnerSeqNo(data.getTotalFee(), data.getInnerSeqNo(),
            			InvestState.UNPAID.getState(), InvestState.PAID.getState());
            }else{
            	count = investDao.updateValidInvestMoneyByInnerSeqNo(data.getTotalFee(), data.getInnerSeqNo(),
            			InvestState.EARNING.getState(), InvestState.EARNING.getState());
            }
            if (count <= 0) {
            	throw new BusinessException("投资活动的有效投资金额、状态修改失败");
            }

            // 添加投资返送红包
            if (investDO.getInBidType().equals(BidType.COMMON_LOAN.getType())) {
            	LoanDO loanDO = loanDao.findByLoanIdWithLock(investDO.getInBidId());
                if (loanDO == null) {
                    throw new BusinessException("普通标查询失败");
                }
                BaseResult sendInvestRedResult = sendInvestRed(loanDO, investDO.getInUserId(), data.getTotalFee());
                if (!sendInvestRedResult.isSuccess()) {
                    log.warn(sendInvestRedResult.getMessage());
                    throw new BusinessException("发放投资返送红包失败");
                }
            }
            
    		// 1.4 增加用户积分
    		BaseResult scoreAddResult = userService.modifyUserScoreByInvest(investDO.getInUserId(), ScoreType.INVEST_SCORE,
    				"用户投资项目，系统赠送用户积分", investDO.getInInvestMoney());
    		if (!scoreAddResult.isSuccess()) {
    			log.warn(scoreAddResult.getMessage());
    			throw new BusinessException("赠送用户积分失败");
    		}
    		
    		// 1.5 首次投资奖励
    		sendInvitInvestRed(userDO);
        	
    		// 1.6 短信通知及
        	SmsNotifyCfg smsNotifyCfg = JSON.parseObject(sysConfigService.querySysConfig(SysConfigEntry.SMS_NOTIFY_INVEST_CFG).getData().getConfValue(), SmsNotifyCfg.class);
            if(smsNotifyCfg.getSwitchState() == 1) {
            	SmsNotifyDO smsNotifyDO = new SmsNotifyDO();
            	smsNotifyDO.setReceivePhone(userDO.getUserPhone());
            	String pattern = smsNotifyCfg.getContentTemplate();
            	String content = null;
            	if (investDO.getInBidType().equals(BidType.COMMON_LOAN.getType())) { // 普通标
            		LoanDO loanDO = loanDao.findById(investDO.getInBidId());
            		content = MessageFormat.format(pattern, loanDO.getLoanNo(), investDO.getInInvestMoney());
            	} else if(investDO.getInBidType().equals(BidType.TRANSFER_LOAN.getType())) { // 转让标
            		TransferLoanDO  transferLoanDO = transferLoanDao.findByTransferLoanId(investDO.getInBidId());
            		content = MessageFormat.format(pattern, transferLoanDO.getTlLoanNo(), investDO.getInInvestMoney());
            	}
            	smsNotifyDO.setContent(content);
            	smsNotifyDO.setCreateTime(new Date());
            	smsNotifyDO.setSendStatus(0);
            	smsNotifyDO.setSendCount(0);
            	smsNotifyDao.insert(smsNotifyDO);
            	/**消息推送给app**/
            	/*try{
            		JpushUtils.sendPush_alias(userDO.getUserName(),content);
            	}catch(Exception e){
            		log.error(e.getMessage());
            	}*/
				
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return BaseResult.SUCCESS;
    }
    
    /**
     * 推荐注册后第一比投资发送红包
     */
    private BaseResult sendInvitInvestRed(UserDO userDO) {
    	BaseResult baseResult = new BaseResult();

		Red redParam = new Red();
		redParam.setRedType(RedenvelopeType.INVIT_INVEST_RED);
		// 查询邀请投资红包
		ListResult<Red> redResult = redService.queryList(redParam, null);
		if (!redResult.isSuccess()) {
			baseResult.setError(CommonResultCode.BIZ_ERROR,
					redResult.getMessage());
			return baseResult;
		}

		// 不存在投资红包直接返回
		List<Red> redList = redResult.getData();
		if (CollectionUtils.isEmpty(redList)) {
			return baseResult;
		}

		// 对用户发放投资红包
		List<Redsend> sendList = new ArrayList<Redsend>();
		
		DateTime fullDaytime = new DateTime();
		double redAmount = 0.00;
		for (Red red : redList) {
			if (RedState.EFFECTIVE.equals(red.getRedState()) && investDao.totalInvest(userDO.getUserId()) <= 1) {
				
				if (null != userDO.getUserRecommendUserid() && userDO.getUserRecommendUserid() > 0)
				{
					Redsend redsendr = new Redsend();
					redsendr.setRsTheme(red.getRedTheme());
					redsendr.setRsUserid(userDO.getUserRecommendUserid());
					redsendr.setRsRedId(red.getRedId());
					redsendr.setRsUseScope(red.getRedUseScope());
					redsendr.setRsState(RsState.WITHOUT_USE);
					redsendr.setRsClosetime(fullDaytime.plusDays(red.getRedClosetime()).toDate());
					redsendr.setRsStarttime(new Date());
					redsendr.setRsSender(red.getRedCreator());
					redsendr.setRsType(RedenvelopeType.INVIT_INVEST_RED);
					redsendr.setRsAmt(red.getRedAmt());
					redsendr.setRsValidAmount(red.getRedAmount());
					sendList.add(redsendr);
				}
				
				Redsend redsend = new Redsend();

				redsend.setRsTheme(red.getRedTheme());
				redsend.setRsUserid(userDO.getUserId());
				redsend.setRsRedId(red.getRedId());
				redsend.setRsUseScope(red.getRedUseScope());
				redsend.setRsState(RsState.WITHOUT_USE);
				redsend.setRsClosetime(fullDaytime.plusDays(red.getRedClosetime()).toDate());
				redsend.setRsStarttime(new Date());
				redsend.setRsSender(red.getRedCreator());
				redsend.setRsType(RedenvelopeType.INVIT_INVEST_RED);
				redsend.setRsAmt(red.getRedAmt());
				redsend.setRsValidAmount(red.getRedAmount());

				redAmount += red.getRedAmount();
				sendList.add(redsend);
			}
		}

		if (CollectionUtils.isEmpty(sendList)) {
			return baseResult;
		}

		baseResult = redService.batchSendRed(sendList);
		
		//发送邀请注册短信
		if (0.00 != redAmount)
		{
			StringBuffer sb = new StringBuffer();
			sb.append("尊敬的用户：").append(userDO.getUserName())
				.append("您的首笔投资已成功，恭喜您获得").append(redAmount).append("元投资红包奖励，可登录账户查询并在投资时使用，感谢支持和信赖。回T退订");
        	/**向app推送消息**/
			JpushUtils.sendPush_alias(userDO.getUserName(),sb.toString());

			sendMsgService.sendMsg(userDO.getUserPhone(), sb.toString(), "","1");
		}
		
		return baseResult; 
    }

    /**
     * 投资成功后发送红包
     */
    private BaseResult sendInvestRed(LoanDO loanDO, Integer userid, BigDecimal inValidInvestMoney) {
        BaseResult baseResult = new BaseResult();

        // 比例为零即不派发红包
        if (loanDO.getInvestRedsendRatio() == null || loanDO.getInvestRedsendRatio() <= 0) {
            return baseResult;
        }
        //返送红包(规则按照1000整数倍派发。比例为零即不派发红包)
        Integer num = inValidInvestMoney.divide(new BigDecimal(1000),2).intValue();
        Double sendRedAmount = loanDO.getInvestRedsendRatio() * num;

//        Red redParam = new Red();
//        redParam.setRedType(RedenvelopeType.INVESTOR_RED);

        // 查询投资返送红包
        //ListResult<Red> redResult = redService.queryList(redParam, null);
        //if (!redResult.isSuccess()) {
        //    baseResult.setError(CommonResultCode.BIZ_ERROR, redResult.getMessage());
        //    return baseResult;
        //}

        // 不存在投资返送红包直接返回
        //List<Red> redList = redResult.getData();
        //if (CollectionUtils.isEmpty(redList)) {
        //    return baseResult;
        //}
        /**
         * 发送红包规则，把总金额分成n份，每份的金额为5元，不足5元的舍去
         */
        int copies=new BigDecimal(sendRedAmount).divide(new BigDecimal("5"),2,BigDecimal.ROUND_HALF_UP).intValue();
        List<Redsend> sendList = new ArrayList<Redsend>();
        for(int i=0;i<copies;i++){
        	DateTime fullDaytime = DateTime.now();
            Redsend redsend = new Redsend();
            redsend.setRsTheme(RedenvelopeType.INVESTOR_RED.des);
            redsend.setRsUserid(userid);
            redsend.setRsUseScope(redSendScopeConverse(loanDO.getLoanRedUseScopes()));
            redsend.setRsState(RsState.WITHOUT_USE);
            redsend.setRsClosetime(fullDaytime.plusDays(
                    Integer.valueOf(sysConfigService.querySysConfig(SysConfigEntry.INVEST_RED_VAKIDITY).getData()
                            .getConfValue())).toDate());
            redsend.setRsStarttime(new Date());
            redsend.setRsType(RedenvelopeType.INVESTOR_RED);
            redsend.setRsAmt(sendRedAmount);
            redsend.setRsValidAmount(new Double(5));  //发送红包的金额为5元
            redsend.setRsBizid(loanDO.getLoanId());    //投资送红包对应的项目id
            sendList.add(redsend);
        }       
        if (CollectionUtils.isEmpty(sendList)) {
            return baseResult;
        }
        baseResult = redService.batchSendRed(sendList);
        return baseResult;
    }

    /**
     * 当监听到资金交易完成后， 须触发检查：<br>
     * 2. 支付失败时：<br>
     * 2.1 减少标的投资总额、 如果以前满标则修改为招标中<br>
     * 2.2 修改订单状态<br>
     * 2.3 修改投资状态<br>
     */
    private BaseResult doPaidFailure(DealNotify data) {
        // 2.1 减少标的投资总额、如果以前满标则修改为招标中
        InvestDO param = new InvestDO();
        param.setInInnerSeqNo(data.getInnerSeqNo());
        InvestDO investDO = investDao.findByParam(param);
        Integer inId = investDO.getInId();
        if (investDO.getInBidType().equals(BidType.COMMON_LOAN.getType())) {
            final LoanDO loanDO = loanDao.findByLoanIdWithLock(investDO.getInBidId());
            if (loanDO == null) {
                throw new BusinessException("普通标查询失败");
            }

            Loan toModify = new Loan();

            toModify.setLoanId(loanDO.getLoanId());
            toModify.setLoanCurrentInvest(loanDO.getLoanCurrentInvest().subtract(data.getTotalFee()));

            //                        LoanTraceRecord traceRecord = null;
            //                        if (loanDO.getLoanState().equals(LoanState.FULL_WAIT_REVIEW.getState())) {
            //                            toModify.setLoanState(LoanState.BID_INVITING); // 普通标满标之前的一个状态
            //
            //                            // 项目跟踪状态记录
            //                            traceRecord = new LoanTraceRecord();
            //                            traceRecord.setCreator(0);
            //                            traceRecord.setLoanId(loanDO.getLoanId());
            //                            traceRecord.setLoanTraceOperation(LoanTraceOperation.loanFullBack);
            //                            traceRecord.setOldLoanState(LoanState.FULL_WAIT_REVIEW);
            //                            traceRecord.setNewLoanState(LoanState.BID_INVITING);
            //                            traceRecord.setNote("普通标项目满标投资失败，loanId=" + loanDO.getLoanId());
            //                        }

            BaseResult loanModifyResult = loanService.modifyLoanInfo(toModify, null);
            if (!loanModifyResult.isSuccess()) {
                log.warn(loanModifyResult.getMessage());
                throw new BusinessException("普通标修改失败");
            }
        } else if (investDO.getInBidType().equals(BidType.TRANSFER_LOAN.getType())) {
            final TransferLoanDO transferLoanDO = transferLoanDao.findByTransferLoanIdWithLock(investDO.getInBidId());
            if (transferLoanDO == null) {
                throw new BusinessException("转让标查询失败");
            }

            TransferLoan toModify = new TransferLoan();

            toModify.setId(transferLoanDO.getTlId());
            toModify.setCurrentInvest(transferLoanDO.getTlCurrentInvest().subtract(data.getTotalFee()));

            //                        TransferLoanTraceRecord traceRecord = null;
            //                        if (transferLoanDO.getTlState().equals(TransferLoanState.FULL_WAIT_REVIEW.getState())) {
            //                            toModify.setTransferLoanState(TransferLoanState.TRANSFERING); // 转让满标之前的一个状态
            //
            //                            // 项目跟踪状态记录
            //                            traceRecord = new TransferLoanTraceRecord();
            //                            traceRecord.setCreator(0);
            //                            traceRecord.setLoanId(transferLoanDO.getTlOriginId());
            //                            traceRecord.setTransferLoanId(transferLoanDO.getTlId());
            //                            traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.transferLoanFullBack);
            //                            traceRecord.setOldTransferLoanState(TransferLoanState.FULL_WAIT_REVIEW);
            //                            traceRecord.setNewTransferLoanState(TransferLoanState.TRANSFERING);
            //                            traceRecord.setNote("转让标项目满标投资失败，transferLoanId=" + transferLoanDO.getTlId());
            //                        }

            BaseResult transferLoanModifyResult = transferLoanService.modifyTransferLoanInfo(toModify, null);
            if (!transferLoanModifyResult.isSuccess()) {
                log.warn(transferLoanModifyResult.getMessage());
                throw new BusinessException("转让标修改失败");
            }
        }

        // 2.2 修改订单状态为支付失败
        BaseResult orderModResult = investOrderService.modifyInvestOrderState(data.getInnerSeqNo(),
                OrderState.FAIL_PAID);
        if (!orderModResult.isSuccess()) {
            log.error(orderModResult.getMessage());
            throw new BusinessException("订单支付状态修改失败");
        }

        // 2.3 修改投资状态为支付失败
        investDO = new InvestDO();
        investDO.setInInnerSeqNo(data.getInnerSeqNo());
        investDO.setInInvestState(InvestState.FAIL_PAID.getState());
        investDO.setInId(inId);

        investDao.update(investDO);

        return BaseResult.SUCCESS;
    }

    //红包范围转换
    private String redSendScopeConverse(String redScope) {
        StringBuffer redScopes = new StringBuffer();
        if (redScope != null && !"".equals(redScope)) {
            String[] str = redScope.split("\\|");
            for (int i = 0; i < str.length; i++) {
                if (i == str.length - 1) {
                    redScopes.append(LoanCategory.valueOf(Integer.parseInt(str[i])).getPrompt());
                } else {
                    redScopes.append(LoanCategory.valueOf(Integer.parseInt(str[i])).getPrompt() + ",");
                }
            }
        }
        return redScopes.toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CashCallBackCenter.registCallBack(DealType.INVESTER, this);
    }

}
