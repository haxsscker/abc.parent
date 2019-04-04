/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.callback;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.intf.BuyLoanDao;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.callback.center.CashCallBackCenter;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.TransferLoanTraceRecord;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanTraceOperation;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.TransferLoanTraceOperation;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 投资撤投执行后的回调
 *
 * @author segen189 2014年12月16日 上午12:47:02
 */
@Component
public class InvestWithdrawedCallback implements Callback<DealNotify>, InitializingBean {
    private static final Log    log = LogFactory.getLog(InvestWithdrawedCallback.class);

    @Resource
    private InvestDao           investDao;

    @Resource
    private LoanDao             loanDao;

    @Resource
    private TransferLoanDao     transferLoanDao;

    @Resource
    private BuyLoanDao          buyLoanDao;

    @Resource
    private LoanService         loanService;

    @Resource
    private TransferLoanService transferLoanService;

    @Resource
    private BuyLoanService      buyLoanService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult doCallback(DealNotify data) {
        switch (data.getState()) {
            case SUCCESS:
                return doWithdrawSuccess(data);
            case FAILURE:
                return doWithdrawFailure(data);
            default:
                return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "交易状态的值不符合预期");
        }
    }

    /**
     * 撤资交易成功时回调：<br>
     * 1. 撤资成功<br>
     * 1.1 更新投资的状态<br>
     * 1.2 判断更新标的有效投资金额、如果以前标状态为满标则修改为招标中<br>
     */
    private BaseResult doWithdrawSuccess(DealNotify data) {
        InvestDO param = new InvestDO();
        param.setInWithdrawSeqNo(data.getInnerSeqNo());
        InvestDO investDO = investDao.findByParam(param);
        if (investDO == null) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "投资记录不存在");
        }

        // 1.1 更新投资的状态
        int count = investDao.updateValidInvestMoneyByWithdrawSeqNo(data.getTotalFee().negate(), data.getInnerSeqNo(),
                null, InvestState.WITHDRAWED.getState());
        if (count <= 0) {
            throw new BusinessException("投资活动的有效投资金额、状态修改失败");
        }

        // 1.2 判断更新标的有效投资金额、如果以前标状态为满标则修改为招标中
        if (investDO.getInBidType().equals(BidType.COMMON_LOAN.getType())) {
            final LoanDO loanDO = loanDao.findByLoanIdWithLock(investDO.getInBidId());
            if (loanDO == null) {
                throw new BusinessException("普通标撤投查询失败");
            }

            Loan toModify = new Loan();
            LoanTraceRecord traceRecord = null;

            toModify.setLoanId(loanDO.getLoanId());
            if (loanDO.getLoanCurrentValidInvest().equals(loanDO.getLoanMoney())) {
                toModify.setLoanState(LoanState.BID_INVITING);

                // 项目跟踪状态记录
                traceRecord = new LoanTraceRecord();
                traceRecord.setCreator(0);
                traceRecord.setLoanId(loanDO.getLoanId());
                traceRecord.setLoanTraceOperation(LoanTraceOperation.loanFullBack);
                traceRecord.setOldLoanState(LoanState.FULL_WAIT_REVIEW);
                traceRecord.setNewLoanState(LoanState.BID_INVITING);
                traceRecord.setNote("普通标项目满标回到招标中");
            }
            toModify.setLoanCurrentValidInvest(loanDO.getLoanCurrentValidInvest().subtract(data.getTotalFee()));

            BaseResult loanModifyResult = loanService.modifyLoanInfo(toModify, traceRecord);
            if (!loanModifyResult.isSuccess()) {
                log.warn(loanModifyResult.getMessage());
                throw new BusinessException("转让标撤投修改失败");
            }
        } else if (investDO.getInBidType().equals(BidType.TRANSFER_LOAN.getType())) {
            final TransferLoanDO transferLoanDO = transferLoanDao.findByTransferLoanIdWithLock(investDO.getInBidId());
            if (transferLoanDO == null) {
                throw new BusinessException("转让标撤投查询失败");
            }

            TransferLoan toModify = new TransferLoan();
            TransferLoanTraceRecord traceRecord = null;

            toModify.setId(transferLoanDO.getTlId());
            if (transferLoanDO.getTlCurrentValidInvest().equals(transferLoanDO.getTlTransferMoney())) {
                toModify.setTransferLoanState(TransferLoanState.TRANSFERING);

                // 项目跟踪状态记录
                traceRecord = new TransferLoanTraceRecord();
                traceRecord.setCreator(0);
                traceRecord.setLoanId(transferLoanDO.getTlOriginId());
                traceRecord.setTransferLoanId(transferLoanDO.getTlId());
                traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.transfering);
                traceRecord.setOldTransferLoanState(TransferLoanState.FULL_WAIT_REVIEW);
                traceRecord.setNewTransferLoanState(TransferLoanState.TRANSFERING);
                traceRecord.setNote("转让标项目满标回到转让中，transferLoanId=" + transferLoanDO.getTlId());
            }
            toModify.setCurrentValidInvest(transferLoanDO.getTlCurrentValidInvest().subtract(data.getTotalFee()));

            BaseResult transferLoanModifyResult = transferLoanService.modifyTransferLoanInfo(toModify, traceRecord);
            if (!transferLoanModifyResult.isSuccess()) {
                log.warn(transferLoanModifyResult.getMessage());
                throw new BusinessException("转让标撤投修改失败");
            }
        }

        return BaseResult.SUCCESS;
    }

    /**
     * 撤资交易失败时回调：<br>
     * 2. 撤资失败<br>
     * 2.1 回滚标的投资金额<br>
     */
    private BaseResult doWithdrawFailure(DealNotify data) {
        InvestDO param = new InvestDO();
        param.setInWithdrawSeqNo(data.getInnerSeqNo());
        InvestDO investDO = investDao.findByParam(param);
        if (investDO == null) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "投资记录不存在");
        }

        int count = investDao.updateInvestMoney(data.getTotalFee(), data.getInnerSeqNo(), null, null);
        if (count <= 0) {
            throw new BusinessException("投资活动的有效投资金额、状态修改失败");
        }

        // 还原投资的撤投流水号
        int resetCount = investDao.resetWithdrawSeqNo(investDO.getInId());
        if (resetCount <= 0) {
            throw new BusinessException("还原投资的撤投流水号失败");
        }

        return BaseResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CashCallBackCenter.registCallBack(DealType.WITHDRAWAL_INVESTER, this);
    }

}
