/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.callback;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.dataobject.LoanIntentApplyDO;
import com.autoserve.abc.dao.intf.LoanIntentAppplyDao;
import com.autoserve.abc.service.biz.callback.center.ReviewCallbackCenter;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.BuyLoanTraceRecord;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.TransferLoanTraceRecord;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.BuyLoanState;
import com.autoserve.abc.service.biz.enums.BuyLoanTraceOperation;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanTraceOperation;
import com.autoserve.abc.service.biz.enums.ReviewOpType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.TransferLoanTraceOperation;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 项目审核回调
 *
 * @author segen189 2014年12月28日 下午11:12:37
 */
@Component
public class LoanReviewedCallback implements Callback<ReviewOp>, InitializingBean {

    @Resource
    private LoanIntentAppplyDao    loanIntentAppplyDao;

    @Resource
    private LoanIntentApplyService loanIntentAppplyService;

    @Resource
    private LoanService            loanService;

    @Resource
    private TransferLoanService    transferLoanService;

    @Resource
    private BuyLoanService         buyLoanService;
    @Resource
    private GovernmentService     governmentService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult doCallback(ReviewOp reviewOp) {
        switch (reviewOp.getReview().getType()) {
        /**
         * 意向审核
         */
            case INTENTION_REVIEW: {
                if (ReviewOpType.PASS.equals(reviewOp.getOpType())) {
                    LoanIntentApply pojo = new LoanIntentApply();
                    pojo.setId(reviewOp.getReview().getApplyId());
                    pojo.setIntentState(LoanState.INTENT_REVIEW_PASS);

                    BaseResult intentModResult = loanIntentAppplyService.modifyApplyLoanIntent(pojo);
                    if (!intentModResult.isSuccess()) {
                        return intentModResult;
                    }

                    // 项目跟踪状态记录
                    LoanIntentApplyDO intentApplyDO = loanIntentAppplyDao.findById(reviewOp.getReview().getApplyId()); // CAN NOT BE NULL
                    if (intentApplyDO.getLiaLoanId() != null) {
                        LoanTraceRecord traceRecord = new LoanTraceRecord();
                        traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                        traceRecord.setLoanId(intentApplyDO.getLiaLoanId());
                        traceRecord.setLoanTraceOperation(LoanTraceOperation.INTENTION_REVIEW);
                        traceRecord.setOldLoanState(LoanState.WAIT_INTENT_REVIEW);
                        traceRecord.setNewLoanState(LoanState.INTENT_REVIEW_PASS);
                        traceRecord.setNote("意向审核通过");

                        return loanService.changeLoanState(traceRecord);
                    }

                    return intentModResult;
                } else if (ReviewOpType.REJECT.equals(reviewOp.getOpType())) {
                    LoanIntentApply pojo = new LoanIntentApply();
                    pojo.setId(reviewOp.getReview().getApplyId());
                    pojo.setIntentState(LoanState.INTENT_REVIEW_FAIL);

                    BaseResult intentModResult = loanIntentAppplyService.modifyApplyLoanIntent(pojo);
                    if (!intentModResult.isSuccess()) {
                        return intentModResult;
                    }

                    // 项目跟踪状态记录
                    LoanIntentApplyDO intentApplyDO = loanIntentAppplyDao.findById(reviewOp.getReview().getApplyId()); // CAN NOT BE NULL
                    if (intentApplyDO.getLiaLoanId() != null) {
                        LoanTraceRecord traceRecord = new LoanTraceRecord();
                        traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                        traceRecord.setLoanId(intentApplyDO.getLiaLoanId());
                        traceRecord.setLoanTraceOperation(LoanTraceOperation.INTENTION_REVIEW);
                        traceRecord.setOldLoanState(LoanState.WAIT_INTENT_REVIEW);
                        traceRecord.setNewLoanState(LoanState.INTENT_REVIEW_FAIL);
                        traceRecord.setNote("意向审核否决");

                        return loanService.changeLoanState(traceRecord);
                    }

                    return intentModResult;
                } else if (ReviewOpType.SEND.equals(reviewOp.getOpType())) {
                    if (reviewOp.getNextRole() == BaseRoleType.PLATFORM_SERVICE) {
                        LoanIntentApply pojo = new LoanIntentApply();
                        pojo.setId(reviewOp.getReview().getApplyId());
                        pojo.setIntentState(LoanState.WAIT_PROJECT_REVIEW);

                        BaseResult intentModResult = loanIntentAppplyService.modifyApplyLoanIntent(pojo);
                        if (!intentModResult.isSuccess()) {
                            return intentModResult;
                        }

                        // 项目跟踪状态记录
                        LoanIntentApplyDO intentApplyDO = loanIntentAppplyDao.findById(reviewOp.getReview()
                                .getApplyId()); // CAN NOT BE NULL
                        if (intentApplyDO.getLiaLoanId() != null) {
                            LoanTraceRecord traceRecord = new LoanTraceRecord();
                            traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                            traceRecord.setLoanId(intentApplyDO.getLiaLoanId());
                            traceRecord.setLoanTraceOperation(LoanTraceOperation.INTENTION_REVIEW);
                            traceRecord.setOldLoanState(LoanState.INTENT_REVIEW_PASS);
                            traceRecord.setNewLoanState(LoanState.WAIT_PROJECT_REVIEW);
                            traceRecord.setNote("意向审核完成发送平台项目初审");

                            return loanService.changeLoanState(traceRecord);
                        }

                        return intentModResult;
                    }else if (reviewOp.getNextRole() == BaseRoleType.INSURANCE_GOVERNMENT){
                        LoanIntentApplyDO intentApplyDO = loanIntentAppplyDao.findById(reviewOp.getReview()
                                .getApplyId());
                    	Loan loan = new Loan();
                        loan.setLoanId(intentApplyDO.getLiaLoanId());
                        loan.setLoanState(LoanState.WAIT_GUAR_REVIEW);

                        // 项目跟踪状态记录
                        LoanTraceRecord traceRecord = new LoanTraceRecord();
                        traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                        traceRecord.setLoanId(intentApplyDO.getLiaLoanId());
                        traceRecord.setLoanTraceOperation(LoanTraceOperation.INTENTION_REVIEW);
                        traceRecord.setOldLoanState(LoanState.INTENT_REVIEW_PASS);
                        traceRecord.setNewLoanState(LoanState.WAIT_GUAR_REVIEW);
                        traceRecord.setNote("意向审核完成发送担保审核");
                        return loanService.modifyLoanInfo(loan, traceRecord);
                    }
                } else if (ReviewOpType.REVOKE.equals(reviewOp.getOpType())) {
                    // 意向审核 撤回 将项目状态撤到意向待审核
                    LoanIntentApply pojo = new LoanIntentApply();
                    pojo.setId(reviewOp.getReview().getApplyId());
                    pojo.setIntentState(LoanState.WAIT_INTENT_REVIEW);

                    BaseResult intentModResult = loanIntentAppplyService.modifyApplyLoanIntent(pojo);
                    if (!intentModResult.isSuccess()) {
                        return intentModResult;
                    }

                    // 项目跟踪状态记录
                    LoanIntentApplyDO intentApplyDO = loanIntentAppplyDao.findById(reviewOp.getReview().getApplyId()); // CAN NOT BE NULL
                    if (intentApplyDO.getLiaLoanId() != null) {
                        LoanTraceRecord traceRecord = new LoanTraceRecord();
                        traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                        traceRecord.setLoanId(intentApplyDO.getLiaLoanId());
                        traceRecord.setLoanTraceOperation(LoanTraceOperation.INTENTION_REVIEW);
                        // traceRecord.setOldLoanState(LoanState.INTENT_REVIEW_PASS);
                        traceRecord.setNewLoanState(LoanState.WAIT_INTENT_REVIEW);
                        traceRecord.setNote("意向审核撤回");

                        return loanService.changeLoanState(traceRecord);
                    }

                    return intentModResult;
                } else if (ReviewOpType.ROLL_BACK.equals(reviewOp.getOpType())) {
                    // 意向审核 退回 将项目状态从意向审核通过退到意向待审核
                    LoanIntentApply pojo = new LoanIntentApply();
                    pojo.setId(reviewOp.getReview().getApplyId());
                    pojo.setIntentState(LoanState.WAIT_INTENT_REVIEW);

                    BaseResult intentModResult = loanIntentAppplyService.modifyApplyLoanIntent(pojo);
                    if (!intentModResult.isSuccess()) {
                        return intentModResult;
                    }

                    // 项目跟踪状态记录
                    LoanIntentApplyDO intentApplyDO = loanIntentAppplyDao.findById(reviewOp.getReview().getApplyId()); // CAN NOT BE NULL
                    if (intentApplyDO.getLiaLoanId() != null) {
                        LoanTraceRecord traceRecord = new LoanTraceRecord();
                        traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                        traceRecord.setLoanId(intentApplyDO.getLiaLoanId());
                        traceRecord.setLoanTraceOperation(LoanTraceOperation.INTENTION_REVIEW);
                        // traceRecord.setOldLoanState(LoanState.INTENT_REVIEW_PASS);
                        traceRecord.setNewLoanState(LoanState.WAIT_INTENT_REVIEW);
                        traceRecord.setNote("意向审核退回");

                        return loanService.changeLoanState(traceRecord);
                    }

                    return intentModResult;
                }

                break;
            }

            /**
             * 融资审核
             */
            case FINANCING_REVIEW: {
                if (ReviewOpType.PASS.equals(reviewOp.getOpType())) {
                    Loan loan = new Loan();
                    loan.setLoanId(reviewOp.getReview().getApplyId());
                    loan.setLoanState(LoanState.MAINTAIN_REVIEW_PASS);

                    // 项目跟踪状态记录
                    LoanTraceRecord traceRecord = new LoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(loan.getLoanId());
                    traceRecord.setLoanTraceOperation(LoanTraceOperation.FINANCING_REVIEW);
                    traceRecord.setOldLoanState(LoanState.WAIT_MAINTAIN_REVIEW);
                    traceRecord.setNewLoanState(LoanState.MAINTAIN_REVIEW_PASS);
                    traceRecord.setNote("融资审核通过");

                    return loanService.modifyLoanInfo(loan, traceRecord);
                } else if (ReviewOpType.SEND.equals(reviewOp.getOpType())) {
                    if (reviewOp.getNextRole() == BaseRoleType.PLATFORM_SERVICE) {
                        // 项目跟踪状态记录
                        LoanTraceRecord traceRecord = new LoanTraceRecord();
                        traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                        traceRecord.setLoanId(reviewOp.getReview().getApplyId());
                        traceRecord.setLoanTraceOperation(LoanTraceOperation.FINANCING_REVIEW);
                        traceRecord.setOldLoanState(LoanState.MAINTAIN_REVIEW_PASS);
                        traceRecord.setNewLoanState(LoanState.WAIT_PROJECT_REVIEW);
                        traceRecord.setNote("融资审核完成发送平台项目初审");

                        return loanService.changeLoanState(traceRecord);
                    }else{
                    	Loan loan = new Loan();
                        loan.setLoanId(reviewOp.getReview().getApplyId());
                        loan.setLoanState(LoanState.WAIT_GUAR_REVIEW);

                        // 项目跟踪状态记录
                        LoanTraceRecord traceRecord = new LoanTraceRecord();
                        traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                        traceRecord.setLoanId(loan.getLoanId());
                        traceRecord.setLoanTraceOperation(LoanTraceOperation.FINANCING_REVIEW);
                        traceRecord.setOldLoanState(LoanState.WAIT_MAINTAIN_REVIEW);
                        traceRecord.setNewLoanState(LoanState.WAIT_GUAR_REVIEW);
                        traceRecord.setNote("发送担保审核");
                        return loanService.modifyLoanInfo(loan, traceRecord);
                    }
                } else if (ReviewOpType.REVOKE.equals(reviewOp.getOpType())) {
                    // 融资审核 撤回 将项目状态撤到融资维护待审核
                    // 项目跟踪状态记录
                    LoanTraceRecord traceRecord = new LoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(reviewOp.getReview().getApplyId());
                    traceRecord.setLoanTraceOperation(LoanTraceOperation.FINANCING_REVIEW);
                    // traceRecord.setOldLoanState(LoanState.MAINTAIN_REVIEW_PASS);
                    traceRecord.setNewLoanState(LoanState.WAIT_MAINTAIN_REVIEW);
                    traceRecord.setNote("融资审核撤回");

                    return loanService.changeLoanState(traceRecord);
                } else if (ReviewOpType.ROLL_BACK.equals(reviewOp.getOpType())) {
                    // 融资审核 退回 将项目状态退到融资维护待审核
                    // 项目跟踪状态记录
                    LoanTraceRecord traceRecord = new LoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(reviewOp.getReview().getApplyId());
                    traceRecord.setLoanTraceOperation(LoanTraceOperation.FINANCING_REVIEW);
                    // traceRecord.setOldLoanState(LoanState.MAINTAIN_REVIEW_PASS);
                    traceRecord.setNewLoanState(LoanState.WAIT_MAINTAIN_REVIEW);
                    traceRecord.setNote("融资审核退回");

                    return loanService.changeLoanState(traceRecord);
                }else if(ReviewOpType.REJECT.equals(reviewOp.getOpType())){  //否决
                	//融资审核  否决  将项目的状态更改为融资审核未通过
                	LoanTraceRecord traceRecord = new LoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(reviewOp.getReview().getApplyId());
                    traceRecord.setLoanTraceOperation(LoanTraceOperation.FINANCING_REVIEW);
                    traceRecord.setNewLoanState(LoanState.MAINTAIN_REVIEW_FAIL);
                    traceRecord.setNote("融资审核否决");
                    return loanService.changeLoanState(traceRecord);                	
//                    Integer loanId=reviewOp.getReview().getApplyId();
//                	PlainResult<Loan> result=loanService.findLoanById(loanId);
//                	Loan loan=result.getData();
//                	if(loan!=null && loan.getLoanGuarGov()!=null){
//                		BaseResult govModResult =governmentService.computeSettGuarAmount(loan.getLoanGuarGov(),loan.getLoanMoney());
//           			 	if (!govModResult.isSuccess()) {
//           			 		throw new BusinessException("担保公司可用额度减少失败");
//           			 		}
//                	}
                	
                }

                break;
            }

            /**
             * 担保审核
             */
            case GUAR_REVIEW: {
                if (ReviewOpType.PASS.equals(reviewOp.getOpType())) {
                    Loan loan = new Loan();
                    loan.setLoanId(reviewOp.getReview().getApplyId());
                    loan.setLoanState(LoanState.GUAR_REVIEW_PASS);

                    // 项目跟踪状态记录
                    LoanTraceRecord traceRecord = new LoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(loan.getLoanId());
                    traceRecord.setLoanTraceOperation(LoanTraceOperation.GUAR_REVIEW);
                    traceRecord.setOldLoanState(LoanState.WAIT_GUAR_REVIEW);
                    traceRecord.setNewLoanState(LoanState.GUAR_REVIEW_PASS);
                    traceRecord.setNote("担保审核通过");

                    return loanService.modifyLoanInfo(loan, traceRecord);
                } else if (ReviewOpType.SEND.equals(reviewOp.getOpType())) {
                    if (ReviewState.PASS_REVIEW.equals(reviewOp.getReview().getState())) {
                        // 项目跟踪状态记录
                        LoanTraceRecord traceRecord = new LoanTraceRecord();
                        traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                        traceRecord.setLoanId(reviewOp.getReview().getApplyId());
                        traceRecord.setLoanTraceOperation(LoanTraceOperation.GUAR_REVIEW);
                        traceRecord.setOldLoanState(LoanState.GUAR_REVIEW_PASS);
                        traceRecord.setNewLoanState(LoanState.WAIT_PROJECT_REVIEW);
                        traceRecord.setNote("担保审核完成发送平台项目初审");

                        return loanService.changeLoanState(traceRecord);
                    }
                } else if (ReviewOpType.REVOKE.equals(reviewOp.getOpType())) {
                    // 融资审核 撤回 将项目状态撤到融资维护待审核
                    // 项目跟踪状态记录
                    LoanTraceRecord traceRecord = new LoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(reviewOp.getReview().getApplyId());
                    traceRecord.setLoanTraceOperation(LoanTraceOperation.LOAN_FIRST_REVIEW);
                    // traceRecord.setOldLoanState(LoanState.MAINTAIN_REVIEW_PASS);
                    traceRecord.setNewLoanState(LoanState.WAIT_GUAR_REVIEW);
                    traceRecord.setNote("担保审核撤回");

                    return loanService.changeLoanState(traceRecord);
                } else if (ReviewOpType.ROLL_BACK.equals(reviewOp.getOpType())) {
                    // 融资审核 退回 将项目状态退到融资维护待审核
                    // 项目跟踪状态记录
                    LoanTraceRecord traceRecord = new LoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(reviewOp.getReview().getApplyId());
                    traceRecord.setLoanTraceOperation(LoanTraceOperation.GUAR_REVIEW);
                    // traceRecord.setOldLoanState(LoanState.MAINTAIN_REVIEW_PASS);
                    traceRecord.setNewLoanState(LoanState.WAIT_MAINTAIN_REVIEW);
                    traceRecord.setNote("担保审核退回");

                    return loanService.changeLoanState(traceRecord);
                }else if(ReviewOpType.REJECT.equals(reviewOp.getOpType())){  //否决
                    Integer loanId=reviewOp.getReview().getApplyId();
                	PlainResult<Loan> result=loanService.findLoanById(loanId);
                	Loan loan=result.getData();
                	if(loan!=null && loan.getLoanGuarGov()!=null){
                		BaseResult govModResult =governmentService.computeSettGuarAmount(loan.getLoanGuarGov(),loan.getLoanMoney());
           			 	if (!govModResult.isSuccess()) {
           			 		throw new BusinessException("担保公司可用额度更新失败");
           			 		}
                	}
                	Loan newloan = new Loan();
                	newloan.setLoanId(reviewOp.getReview().getApplyId());
                	newloan.setLoanState(LoanState.GUAR_REVIEW_FAIL);

                	//担保审核  否决  将项目的状态更改为担保审核未通过
                	LoanTraceRecord traceRecord = new LoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(reviewOp.getReview().getApplyId());
                    traceRecord.setLoanTraceOperation(LoanTraceOperation.GUAR_REVIEW);
                    traceRecord.setNewLoanState(LoanState.GUAR_REVIEW_FAIL);
                    traceRecord.setNote("担保审核否决");
                    return loanService.modifyLoanInfo(newloan, traceRecord);
                }

                break;
            }
            
            /**
             * 项目初审
             */
            case LOAN_FIRST_REVIEW: {
                if (ReviewOpType.PASS.equals(reviewOp.getOpType())) {
                    Loan loan = new Loan();
                    loan.setLoanId(reviewOp.getReview().getApplyId());
                    // loan.setLoanState(LoanState.PROJECT_REVIEW_PASS);
                    loan.setLoanState(LoanState.WAIT_RELEASE);

                    // 项目跟踪状态记录
                    LoanTraceRecord traceRecord = new LoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(loan.getLoanId());
                    traceRecord.setLoanTraceOperation(LoanTraceOperation.LOAN_FIRST_REVIEW);
                    traceRecord.setOldLoanState(LoanState.WAIT_PROJECT_REVIEW);
                    // traceRecord.setNewLoanState(LoanState.PROJECT_REVIEW_PASS);
                    traceRecord.setNewLoanState(LoanState.WAIT_RELEASE);
                    traceRecord.setNote("项目初审通过");

                    return loanService.modifyLoanInfo(loan, traceRecord);
                } else if (ReviewOpType.REJECT.equals(reviewOp.getOpType())) {
                    Loan loan = new Loan();
                    loan.setLoanId(reviewOp.getReview().getApplyId());
                    loan.setLoanState(LoanState.PROJECT_REVIEW_FAIL);

                    // 项目跟踪状态记录
                    LoanTraceRecord traceRecord = new LoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(loan.getLoanId());
                    traceRecord.setLoanTraceOperation(LoanTraceOperation.LOAN_FIRST_REVIEW);
                    traceRecord.setOldLoanState(LoanState.WAIT_PROJECT_REVIEW);
                    traceRecord.setNewLoanState(LoanState.PROJECT_REVIEW_FAIL);
                    traceRecord.setNote("项目初审否决");

                    return loanService.changeLoanState(traceRecord);
                }

                break;
            }

            /**
             * 项目转让审核
             */
            case LOAN_TRANSFER_REVIEW: {
                if (ReviewOpType.PASS.equals(reviewOp.getOpType())) {
                    TransferLoan transferLoan = new TransferLoan();
                    transferLoan.setId(reviewOp.getReview().getApplyId());
                    transferLoan.setTransferLoanState(TransferLoanState.TRANSFERING);
                    transferLoan.setInvestStarttime(new Date());
                    transferLoan.setReleaseDate(new Date());

                    // 项目跟踪状态记录
                    TransferLoanTraceRecord traceRecord = new TransferLoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(null);
                    traceRecord.setTransferLoanId(transferLoan.getId());
                    traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.transfering);
                    traceRecord.setOldTransferLoanState(TransferLoanState.WAIT_REVIEW);
                    traceRecord.setNewTransferLoanState(TransferLoanState.TRANSFERING);
                    traceRecord.setNote("项目转让审核通过");

                    return transferLoanService.modifyTransferLoanInfo(transferLoan, traceRecord);
                } else if (ReviewOpType.REJECT.equals(reviewOp.getOpType())) {
                    TransferLoan transferLoan = new TransferLoan();
                    transferLoan.setId(reviewOp.getReview().getApplyId());
                    transferLoan.setTransferLoanState(TransferLoanState.FIRST_REVIEW_FAIL);

                    // 项目跟踪状态记录
                    TransferLoanTraceRecord traceRecord = new TransferLoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(null);
                    traceRecord.setTransferLoanId(transferLoan.getId());
                    traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.failFirstReview);
                    traceRecord.setOldTransferLoanState(TransferLoanState.WAIT_REVIEW);
                    traceRecord.setNewTransferLoanState(TransferLoanState.FIRST_REVIEW_FAIL);
                    traceRecord.setNote("项目转让审核否决");

                    return transferLoanService.modifyTransferLoanInfo(transferLoan, traceRecord);
                }

                break;
            }

            /**
             * 收购审核
             */
            case LOAN_PURCHASE_REVIEW: {
                if (ReviewOpType.PASS.equals(reviewOp.getOpType())) {
                    BuyLoan buyLoan = new BuyLoan();
                    buyLoan.setId(reviewOp.getReview().getApplyId());
                    buyLoan.setBuyLoanState(BuyLoanState.BUYING);
                    buyLoan.setStartTime(new Date());

                    // 项目跟踪状态记录
                    BuyLoanTraceRecord traceRecord = new BuyLoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(null);
                    traceRecord.setBuyLoanId(buyLoan.getId());
                    traceRecord.setBuyLoanTraceOperation(BuyLoanTraceOperation.buying);
                    traceRecord.setOldBuyLoanState(BuyLoanState.WAIT_REVIEW);
                    traceRecord.setNewBuyLoanState(BuyLoanState.BUYING);
                    traceRecord.setNote("项目收购审核通过");

                    return buyLoanService.modifyBuyLoanInfo(buyLoan, traceRecord);
                } else if (ReviewOpType.REJECT.equals(reviewOp.getOpType())) {
                    BuyLoan buyLoan = new BuyLoan();
                    buyLoan.setId(reviewOp.getReview().getApplyId());
                    buyLoan.setBuyLoanState(BuyLoanState.FIRST_REVIEW_FAIL);

                    // 项目跟踪状态记录
                    BuyLoanTraceRecord traceRecord = new BuyLoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(null);
                    traceRecord.setBuyLoanId(buyLoan.getId());
                    traceRecord.setBuyLoanTraceOperation(BuyLoanTraceOperation.failFirstReview);
                    traceRecord.setOldBuyLoanState(BuyLoanState.WAIT_REVIEW);
                    traceRecord.setNewBuyLoanState(BuyLoanState.BUYING);
                    traceRecord.setNote("项目收购审核否决");

                    return buyLoanService.modifyBuyLoanInfo(buyLoan, traceRecord);
                }

                break;
            }

            /**
             * 满标审核
             */
            case LOAN_FULL_BID_REVIEW: {
                if (ReviewOpType.PASS.equals(reviewOp.getOpType())) {
                    Loan loan = new Loan();
                    loan.setLoanId(reviewOp.getReview().getApplyId());
                    loan.setLoanState(LoanState.FULL_REVIEW_PASS);

                    // 项目跟踪状态记录
                    LoanTraceRecord traceRecord = new LoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(loan.getLoanId());
                    traceRecord.setLoanTraceOperation(LoanTraceOperation.LOAN_FULL_BID_REVIEW);
                    traceRecord.setOldLoanState(LoanState.FULL_WAIT_REVIEW);
                    traceRecord.setNewLoanState(LoanState.FULL_REVIEW_PASS);
                    traceRecord.setNote("普通标满标审核通过");

                    return loanService.modifyLoanInfo(loan, traceRecord);
                } else if (ReviewOpType.REJECT.equals(reviewOp.getOpType())) {
                    Loan loan = new Loan();
                    loan.setLoanId(reviewOp.getReview().getApplyId());
                    loan.setLoanState(LoanState.FULL_REVIEW_FAIL);

                    // 项目跟踪状态记录
                    LoanTraceRecord traceRecord = new LoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(loan.getLoanId());
                    traceRecord.setLoanTraceOperation(LoanTraceOperation.LOAN_FULL_BID_REVIEW);
                    traceRecord.setOldLoanState(LoanState.FULL_WAIT_REVIEW);
                    traceRecord.setNewLoanState(LoanState.FULL_REVIEW_FAIL);
                    traceRecord.setNote("普通标满标审核否决");

                    return loanService.modifyLoanInfo(loan, traceRecord);
                } else if (ReviewOpType.ROLL_BACK.equals(reviewOp.getOpType())) {
                    Loan loan = new Loan();
                    loan.setLoanId(reviewOp.getReview().getApplyId());
                    loan.setLoanState(LoanState.FULL_WAIT_REVIEW);

                    // 项目跟踪状态记录
                    LoanTraceRecord traceRecord = new LoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(loan.getLoanId());
                    traceRecord.setLoanTraceOperation(LoanTraceOperation.LOAN_FULL_BID_REVIEW);
                    traceRecord.setOldLoanState(LoanState.FULL_REVIEW_PASS);
                    traceRecord.setNewLoanState(LoanState.FULL_WAIT_REVIEW);
                    traceRecord.setNote("普通标满标审核退回");

                    return loanService.modifyLoanInfo(loan, traceRecord);
                }

                break;
            }

            /**
             * 转让满标审核
             */
            case TRANSFER_FULL_BID_REVIEW: {
                if (ReviewOpType.PASS.equals(reviewOp.getOpType())) {
                    TransferLoan transferLoan = new TransferLoan();
                    transferLoan.setId(reviewOp.getReview().getApplyId());
                    transferLoan.setTransferLoanState(TransferLoanState.FULL_REVIEW_PASS);

                    // 项目跟踪状态记录
                    TransferLoanTraceRecord traceRecord = new TransferLoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(null);
                    traceRecord.setTransferLoanId(transferLoan.getId());
                    traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.passFullReview);
                    traceRecord.setOldTransferLoanState(TransferLoanState.FULL_WAIT_REVIEW);
                    traceRecord.setNewTransferLoanState(TransferLoanState.FULL_REVIEW_PASS);
                    traceRecord.setNote("转让标满标审核通过");

                    return transferLoanService.modifyTransferLoanInfo(transferLoan, traceRecord);
                } else if (ReviewOpType.REJECT.equals(reviewOp.getOpType())) {
                    TransferLoan transferLoan = new TransferLoan();
                    transferLoan.setId(reviewOp.getReview().getApplyId());
                    transferLoan.setTransferLoanState(TransferLoanState.FULL_REVIEW_FAIL);

                    // 项目跟踪状态记录
                    TransferLoanTraceRecord traceRecord = new TransferLoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(null);
                    traceRecord.setTransferLoanId(transferLoan.getId());
                    traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.failFullReview);
                    traceRecord.setOldTransferLoanState(TransferLoanState.FULL_WAIT_REVIEW);
                    traceRecord.setNewTransferLoanState(TransferLoanState.FULL_REVIEW_FAIL);
                    traceRecord.setNote("转让标满标审核否决");

                    return transferLoanService.modifyTransferLoanInfo(transferLoan, traceRecord);
                }

                break;
            }

            /**
             * 收购满标审核
             */
            case PURCHASE_FULL_BID_REVIEW: {
                if (ReviewOpType.PASS.equals(reviewOp.getOpType())) {
                    BuyLoan buyLoan = new BuyLoan();
                    buyLoan.setId(reviewOp.getReview().getApplyId());
                    buyLoan.setBuyLoanState(BuyLoanState.FULL_REVIEW_PASS);

                    // 项目跟踪状态记录
                    BuyLoanTraceRecord traceRecord = new BuyLoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(null);
                    traceRecord.setBuyLoanId(buyLoan.getId());
                    traceRecord.setBuyLoanTraceOperation(BuyLoanTraceOperation.passFullReview);
                    traceRecord.setOldBuyLoanState(BuyLoanState.FULL_WAIT_REVIEW);
                    traceRecord.setNewBuyLoanState(BuyLoanState.FULL_REVIEW_PASS);
                    traceRecord.setNote("收购标满标审核通过");

                    return buyLoanService.modifyBuyLoanInfo(buyLoan, traceRecord);
                } else if (ReviewOpType.REJECT.equals(reviewOp.getOpType())) {
                    BuyLoan buyLoan = new BuyLoan();
                    buyLoan.setId(reviewOp.getReview().getApplyId());
                    buyLoan.setBuyLoanState(BuyLoanState.FULL_REVIEW_FAIL);

                    // 项目跟踪状态记录
                    BuyLoanTraceRecord traceRecord = new BuyLoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(null);
                    traceRecord.setBuyLoanId(buyLoan.getId());
                    traceRecord.setBuyLoanTraceOperation(BuyLoanTraceOperation.failFullReview);
                    traceRecord.setOldBuyLoanState(BuyLoanState.FULL_WAIT_REVIEW);
                    traceRecord.setNewBuyLoanState(BuyLoanState.FULL_REVIEW_FAIL);
                    traceRecord.setNote("收购标满标审核否决");

                    return buyLoanService.modifyBuyLoanInfo(buyLoan, traceRecord);
                }

                break;
            }

            /**
             * 撤销融资项目审核
             */
            case LOAN_CANCLE_REVIEW: {
                if (ReviewOpType.PASS.equals(reviewOp.getOpType())) {
                    Loan loan = new Loan();
                    loan.setLoanId(reviewOp.getReview().getApplyId());
                    loan.setLoanState(LoanState.PROJECT_REVIEW_BACK);// TODO check add new LoanState

                    // 项目跟踪状态记录
                    LoanTraceRecord traceRecord = new LoanTraceRecord();
                    traceRecord.setCreator(reviewOp.getEmployee().getEmpId());
                    traceRecord.setLoanId(loan.getLoanId());
                    traceRecord.setLoanTraceOperation(LoanTraceOperation.LOAN_CANCLE_REVIEW);
                    traceRecord.setOldLoanState(LoanState.WAIT_MAINTAIN_REVIEW);
                    traceRecord.setNewLoanState(LoanState.MAINTAIN_REVIEW_PASS);
                    traceRecord.setNote("撤销融资审核通过");

                    return loanService.modifyLoanInfo(loan, traceRecord);
                }

                break;
            }

            default:
                break;
        }

        return BaseResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ReviewCallbackCenter.registerCallback(ReviewType.INTENTION_REVIEW, this);
        ReviewCallbackCenter.registerCallback(ReviewType.FINANCING_REVIEW, this);
        ReviewCallbackCenter.registerCallback(ReviewType.GUAR_REVIEW, this);
        ReviewCallbackCenter.registerCallback(ReviewType.LOAN_FIRST_REVIEW, this);
        ReviewCallbackCenter.registerCallback(ReviewType.LOAN_TRANSFER_REVIEW, this);
        ReviewCallbackCenter.registerCallback(ReviewType.LOAN_PURCHASE_REVIEW, this);
        ReviewCallbackCenter.registerCallback(ReviewType.LOAN_FULL_BID_REVIEW, this);
        ReviewCallbackCenter.registerCallback(ReviewType.TRANSFER_FULL_BID_REVIEW, this);
        ReviewCallbackCenter.registerCallback(ReviewType.PURCHASE_FULL_BID_REVIEW, this);
        ReviewCallbackCenter.registerCallback(ReviewType.LOAN_CANCLE_REVIEW, this);
    }
}
