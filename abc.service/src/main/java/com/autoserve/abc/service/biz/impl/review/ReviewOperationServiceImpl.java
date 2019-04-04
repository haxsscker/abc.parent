package com.autoserve.abc.service.biz.impl.review;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.callback.center.ReviewCallbackCenter;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.ReviewOpType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.impl.review.core.ReviewRevokeHelper;
import com.autoserve.abc.service.biz.impl.review.core.ReviewRollbackHelper;
import com.autoserve.abc.service.biz.impl.review.core.ReviewSendHelper;
import com.autoserve.abc.service.biz.intf.employee.EmployeeRoleService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.manage.LoanManageService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.review.ReviewOpLogService;
import com.autoserve.abc.service.biz.intf.review.ReviewOperationService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.RoleUtil;

/**
 * @author yuqing.zheng Created on 2014-11-21,14:41
 */
@Service
public class ReviewOperationServiceImpl implements ReviewOperationService {
    private static Logger        logger = LoggerFactory.getLogger(ReviewOperationServiceImpl.class);

    @Resource
    private LoanManageService loanManageService;
    
    @Autowired
    private ReviewOpLogService   reviewOpLogService;

    @Autowired
    private ReviewService        reviewService;

    @Autowired
    private ReviewSendHelper     sendHelper;

    @Autowired
    private ReviewRevokeHelper   revokeHelper;

    @Autowired
    private ReviewRollbackHelper rollbackHelper;

    @Autowired
    private EmployeeRoleService  employeeRoleService;
    
    @Autowired
    private TransferLoanService transferLoanService;
    
    @Autowired
    private PaymentPlanService paymentPlanService;
    @Resource
	private TransferLoanDao transferLoanDao;
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult doReview(ReviewType reviewType, Integer applyId, ReviewOp reviewOp) {
        try {
            paramCheck(reviewType, applyId, reviewOp);
        } catch (IllegalArgumentException e) {
            return new BaseResult(CommonResultCode.ILEEGAL_ARGUMENT);
        }
        
        //转让初审&&通过 -- 检测原始标有无还款行为
        if(ReviewType.LOAN_TRANSFER_REVIEW.equals(reviewType) && ReviewOpType.PASS.equals(reviewOp.getOpType())){  
        	TransferLoan transferLoan = transferLoanService.queryById(applyId).getData();
        	PaymentPlan paymentPlan = paymentPlanService.queryById(transferLoan.getNextPaymentId()).getData();
        	if(PayState.CLEAR.equals(paymentPlan.getPayState())){
        		//throw new BusinessException("原始标有还款行为，不能审核通过");
        		BaseResult result = new BaseResult();
        		result.setSuccess(false);
        		result.setCode(403);
        		result.setMessage("原始标有还款行为，不能审核通过");
        		return result;
        	}
        }
        

        // 根据reviewType 与 applyId 查询审核对象
        // 注意 这里对Review记录加了数据库行级锁
        PlainResult<Review> reviewRes = reviewService.queryActiveByTypeApplyIdWithLock(reviewType, applyId);
        if (!reviewRes.isSuccess()) {
            logger.error("找不到相关审核信息，reviewType={}, applyId={}", reviewType.type, applyId);
            throw new BusinessException("操作失败！");
        }
        reviewOp.setReview(reviewRes.getData());

        BaseResult result = doReviewOperation(reviewOp,applyId);
        if (!result.isSuccess()) {
            logger.error("审核操作执行失败，reviewId={}", reviewRes.getData().getReviewId());
            throw new BusinessException(result.getMessage());
        }

        return BaseResult.SUCCESS;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public BaseResult sendFinancingReviewToPlatform(Integer applyId, Integer employeeId) {
        checkArgument(applyId != null && applyId > 0, "参数校验失败，applyId必须不为空并且大于0，当前值为%s", applyId);
        checkArgument(employeeId != null && employeeId > 0, "参数校验失败，employeeId必须不为空并且大于0，当前值为%s", employeeId);

        // 根据reviewType 与 applyId 查询审核对象
        // 注意 这里对Review记录加了数据库行级锁
        ReviewType reviewType = ReviewType.FINANCING_REVIEW;
        PlainResult<Review> reviewRes = reviewService.queryActiveByTypeApplyIdWithLock(reviewType, applyId);
        if (!reviewRes.isSuccess()) {
            logger.error("找不到相关审核信息，reviewType={}, applyId={}", reviewType.type, applyId);
            throw new BusinessException("发送失败！");
        }

        Review review = reviewRes.getData();
        switch (review.getState()) {
            case FAILED_PASS_REVIEW: {
                // 如果审核是“已否决”状态，则不可发送
                logger.warn("融资审核已否决，不可发送, reviewId={}", review.getReviewId());
                BaseResult result = new BaseResult();
                return result.setError(CommonResultCode.BIZ_ERROR, "该项目不可发送！");
            }
            case PENDING_REVIEW: {
                // 如果审核是”待审核“状态，则将其通过
                BaseResult passRes = passFinancingReview(review, employeeId);
                if (!passRes.isSuccess()) {
                    logger.error("将融资审核状态置为成功时失败，reviewId={}", review.getReviewId());
                    throw new BusinessException("发送失败！");
                }

                // 审核通过后，将其发送
                BaseResult sendRes = sendFinancingReview(review, employeeId);
                if (!sendRes.isSuccess()) {
                    logger.error("将融资审核发送至项目初审时失败，reviewId={}", review.getReviewId());
                    throw new BusinessException("发送失败！");
                }

                return BaseResult.SUCCESS;
            }
            case PASS_REVIEW: {
                BaseResult sendRes = sendFinancingReview(review, employeeId);
                if (!sendRes.isSuccess()) {
                    logger.error("将融资审核发送至项目初审时失败，reviewId={}", review.getReviewId());
                    throw new BusinessException("发送失败！");
                }

                return BaseResult.SUCCESS;
            }
        }

        return null;
    }

    /**
     * 通过一个融资审核
     */
    private BaseResult passFinancingReview(Review review, Integer employeeId) {
        ReviewOp passOp = new ReviewOp();
        passOp.setReview(review);
        passOp.setOpType(ReviewOpType.PASS);
        passOp.setEmployee(new Employee(employeeId));

        return doReviewOperation(passOp,null);
    }

    /**
     * 将融资审核发送至项目初审
     */
    private BaseResult sendFinancingReview(Review review, Integer employeeId) {
        ReviewOp sendOp = new ReviewOp();
        sendOp.setReview(review);
        sendOp.setEmployee(new Employee(employeeId));
        sendOp.setOpType(ReviewOpType.SEND);
        sendOp.setNextRole(BaseRoleType.PLATFORM_SERVICE);

        return doReviewOperation(sendOp,null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private BaseResult doReviewOperation(ReviewOp reviewOp,Integer applyId) {
        BaseResult result;

        // 判断审核的当前待审角色是否是自己
        // 撤回操作时，不需要判断当前角色，因为当前审核角色肯定不是自己
        Review review = reviewOp.getReview();
        if (reviewOp.getOpType() != ReviewOpType.REVOKE) {
            if (!checkRoleAndEmp(reviewOp)) {
                logger.error("当前操作人没有审核权限");
                throw new BusinessException("当前操作人没有审核权限");
            }

            // 如果操作不是撤回, 将Review的待审角色设为ReviewOp的操作人角色
            // 如果操作时撤回，操作人需要查询发送表，找出上次最后的发送角色
            if (reviewOp.getOpType() != ReviewOpType.REVOKE) {
                reviewOp.setRole(review.getCurrRole());
            }
        }
        boolean isDoCallBackFlag=true;
        switch (reviewOp.getOpType()) {
            case PASS: {
                result = pass(reviewOp);
                break;
            }
            case REJECT: {
                result = reject(reviewOp,applyId);
                //isDoCallBackFlag=false;
                break;
            }
            case SUSPEND: {
                result = suspend(reviewOp);
                break;
            }
            case ROLL_BACK: {
                result = rollback(reviewOp);
                break;
            }
            case SEND: {
                result = send(reviewOp);
                break;
            }
            case REVOKE: {
                result = revoke(reviewOp);
                break;
            }
            default: {
                result = new BaseResult(CommonResultCode.BIZ_ERROR);
            }
        }

		if(isDoCallBackFlag){
			if (!result.isSuccess()) {
				logger.error("执行审核操作失败");
				throw new BusinessException(result.getMessage());
			}
			return doReviewCallback(review, reviewOp);
		}else{
			if (!result.isSuccess()) {
				return new BaseResult(CommonResultCode.BIZ_ERROR, result.getMessage());
			}
		}
		return BaseResult.SUCCESS;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult pass(ReviewOp reviewOp) {
        Review review = reviewOp.getReview();
        if (review.isEnd()) {
            logger.warn("审核已结束，不能执行通过操作！reviewId={}", review.getReviewId());
            return new BaseResult(CommonResultCode.BIZ_ERROR, "审核已结束，不能执行通过操作！");
        }

        BaseResult res = reviewOpLogService.creatReviewOpLog(reviewOp);
        if (!res.isSuccess()) {
            logger.error("创建审核操作记录出错");
            return new BaseResult(CommonResultCode.BIZ_ERROR, "创建审核操作记录出错");
        }

        review.setLastOp(reviewOp);
        review.setState(ReviewState.PASS_REVIEW);
        review.setEnd(true);
        BaseResult reviewRes = reviewService.updateReview(review);
        if (!reviewRes.isSuccess()) {
            logger.error("更新审核状态出错");
            throw new BusinessException("更新审核状态出错");
        }

        return BaseResult.SUCCESS;
    }
    /**
     * 退回满标待审
     * @param reviewOp
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult returnFullAwait(ReviewOp reviewOp) {
        Review review = reviewOp.getReview();

        BaseResult res = reviewOpLogService.creatReviewOpLog(reviewOp);
        if (!res.isSuccess()) {
            logger.error("创建审核操作记录出错");
            return new BaseResult(CommonResultCode.BIZ_ERROR, "创建审核操作记录出错");
        }
        review.setLastOp(reviewOp);
        review.setState(ReviewState.PENDING_REVIEW);
        review.setCurrRole(BaseRoleType.PLATFORM_SERVICE);
        review.setEnd(false);
        BaseResult reviewRes = reviewService.updateReview(review);
        if (!reviewRes.isSuccess()) {
            logger.error("更新审核状态出错");
            throw new BusinessException("更新审核状态出错");
        }

        return BaseResult.SUCCESS;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult reject(ReviewOp reviewOp,Integer applyId) {
        Review review = reviewOp.getReview();
        if (review.isEnd()) {
            logger.warn("审核已结束，不能执行否决操作！reviewId={}", review.getReviewId());
            return new BaseResult(CommonResultCode.BIZ_ERROR, "审核已结束，不能执行否决操作！");
        }

        BaseResult res = reviewOpLogService.creatReviewOpLog(reviewOp);
        if (!res.isSuccess()) {
            logger.error("创建审核操作记录出错");
            return new BaseResult(CommonResultCode.BIZ_ERROR, "创建审核操作记录出错");
        }

        ReviewState reviewState=ReviewState.FAILED_PASS_REVIEW;
        //add by xiatt 20160626  满标审核不通过时，流标
        if(review.getType().type == ReviewType.LOAN_FULL_BID_REVIEW.type){
        	Employee employee = reviewOp.getEmployee();
        	BaseResult cancelResult = loanManageService.cancelLoan(review.getApplyId(), employee.getEmpId(), "满标审核不通过后流标");
        	if (!cancelResult.isSuccess()) {
                return new BaseResult(CommonResultCode.BIZ_ERROR, cancelResult.getMessage());
            }
        	if(cancelResult.getCode()==CommonResultCode.CANCELSUCCESS.code){
        		reviewState=ReviewState.FAILED_PASS_REVIEW;//流标成功
        	}else{
        		reviewState=ReviewState.CANCEL_WAIT_REVIEW;//流标处理中
        	}
        }
        review.setLastOp(reviewOp);
        review.setState(reviewState);
        review.setEnd(true);
        BaseResult reviewRes = reviewService.updateReview(review);
        if (!reviewRes.isSuccess()) {
            logger.error("更新审核状态出错");
            throw new BusinessException("更新审核状态出错");
        }
        //add by 夏同同  20160527 审核不通过后，软删除   解决债权转让初审不通过后无法继续申请的问题(逻辑删)
        if(review.getType().type == ReviewType.LOAN_TRANSFER_REVIEW.type){
        	reviewRes = reviewService.deleteReview(review.getReviewId());
        	//转让初审不通过将转让状态置为审核不通过
            transferLoanDao.updateState(applyId, TransferLoanState.WAIT_REVIEW.getState(), TransferLoanState.FIRST_REVIEW_FAIL.getState());
        }
        return BaseResult.SUCCESS;
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult suspend(ReviewOp reviewOp) {
        Review review = reviewOp.getReview();
        if (review.isEnd()) {
            logger.warn("审核已结束，不能执行挂起操作！reviewId={}", review.getReviewId());
            return new BaseResult(CommonResultCode.BIZ_ERROR, "审核已结束，不能执行挂起操作！");
        }

        BaseResult res = reviewOpLogService.creatReviewOpLog(reviewOp);
        if (!res.isSuccess()) {
            logger.error("创建审核操作记录出错");
            return new BaseResult(CommonResultCode.BIZ_ERROR, "创建审核操作记录出错");
        }

        review.setLastOp(reviewOp);
        review.setSuspend(true);
        BaseResult reviewRes = reviewService.updateReview(review);
        if (!reviewRes.isSuccess()) {
            logger.error("更新审核状态出错");
            throw new BusinessException("更新审核状态出错");
        }

        return BaseResult.SUCCESS;
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult rollback(ReviewOp reviewOp) {
    	if(ReviewType.LOAN_FULL_BID_REVIEW.equals(reviewOp.getReview().getType())){
    		return returnFullAwait(reviewOp);
    	}else{
    		return rollbackHelper.doRollback(reviewOp);
    	}
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult send(ReviewOp reviewOp) {
        return sendHelper.doSend(reviewOp);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private BaseResult revoke(ReviewOp reviewOp) {
        return revokeHelper.doRevoke(reviewOp);
    }

    /**
     * 检查当前操作人的EmployeeID与角色列表，是否满足审核的待审角色与Employee
     */
    private boolean checkRoleAndEmp(ReviewOp reviewOp) {
        // 当前审核人
        Employee employee = reviewOp.getEmployee();
        ListResult<RoleDO> roleListRes = employeeRoleService.queryRoleBySingleEmp(employee.getEmpId());
        if (!roleListRes.isSuccess()) {
            logger.error("找不到当前审核人的角色列表，审核人EmployeeID={}", employee.getEmpId());
            throw new BusinessException("查不到审核人角色列表");
        }
        List<RoleDO> roleList = roleListRes.getData();

        Review review = reviewOp.getReview();
        BaseRoleType reviewRole = review.getCurrRole();
        logger.info("当前审核人角色={}，当前待审角色为{}", RoleUtil.printRoleList(roleList), reviewRole.roleName);

        boolean rolePass = RoleUtil.checkRole(roleList, reviewRole);
        // 待审角色是平台客服与平台财务，不需要进一步验证EmployeeID
        if (reviewRole == BaseRoleType.PLATFORM_FINANCIAL || reviewRole == BaseRoleType.PLATFORM_SERVICE) {
            return rolePass;
        } else if (rolePass) {
            Employee reviewEmp = review.getCurrEmp();
            logger.info("当前审核人员工ID={}， 当前待审人ID={}", employee.getEmpId(), reviewEmp.getEmpId());
            return reviewEmp.getEmpId().equals(employee.getEmpId());
        }

        return false;
    }

    /**
     * 审核操作成功执行后 执行相关的callback
     */
    private BaseResult doReviewCallback(Review review, ReviewOp reviewOp) {
        Callback<ReviewOp> reviewCallback = ReviewCallbackCenter.getCallback(review.getType());
        if (reviewCallback == null) {
            logger.info("没有审核类型为 {} 的callback，审核操作成功结束", review.getType().prompt);
            return BaseResult.SUCCESS;
        }

        BaseResult callbackRes = reviewCallback.doCallback(reviewOp);
        if (!callbackRes.isSuccess()) {
            logger.error("执行审核callback失败, reviewId = {}, reviewType = {}, reviewOpType = {}",
                    String.valueOf(review.getReviewId()), review.getType().prompt, reviewOp.getOpType());
            throw new BusinessException("执行审核操作失败");
        }

        return BaseResult.SUCCESS;
    }

    private void paramCheck(ReviewType reviewType, Integer applyId, ReviewOp reviewOp) {
        if (reviewType == null) {
            logger.error("reviewType不能为空");
            throw new IllegalArgumentException();
        }

        if (applyId == null || applyId <= 0) {
            logger.error("applyId不是合法的值，{}", applyId);
            throw new IllegalArgumentException();

        }

        if (reviewOp == null) {
            logger.error("ReviewOp不能为空");
            throw new IllegalArgumentException();
        }

        if (reviewOp.getEmployee() == null || reviewOp.getEmployee().getEmpId() <= 0) {
            logger.error("缺少审核人信息, ReviewOp.employee");
            throw new IllegalArgumentException();
        }

        if (reviewOp.getOpType() == null) {
            logger.error("操作类型不能为空, ReviewOp.opType");
            throw new IllegalArgumentException();
        }

        if (reviewOp.getOpType() == ReviewOpType.SEND) {
            if (reviewOp.getNextRole() == null) {
                logger.error("缺少要发送到的审核人角色信息, ReviewOp.nextRole");
                throw new IllegalArgumentException();
            }

            // 如果发送到的角色不是平台客服与财务，需要进一步检验其employeeId是否为空
            if (reviewOp.getNextRole() != BaseRoleType.PLATFORM_FINANCIAL
                    && reviewOp.getNextRole() != BaseRoleType.PLATFORM_SERVICE) {
                if (reviewOp.getNextEmp() == null || reviewOp.getNextEmp().getEmpId() <= 0) {
                    logger.error("缺少要发送到的审核人信息, ReviewOp.nextEmp");
                    throw new IllegalArgumentException();
                }
            }
        }
    }
}
