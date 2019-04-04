package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.ReviewOpLogDO;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.ReviewOpType;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-24,11:01
 */
public class ReviewOpConverter {
    public static ReviewOpLogDO toReviewOpLogDO(ReviewOp reviewOp) {
        ReviewOpLogDO reviewOpLogDO = new ReviewOpLogDO();
        if (reviewOp == null) {
            return reviewOpLogDO;
        }

        reviewOpLogDO.setRolDate(reviewOp.getDate());
        reviewOpLogDO.setRolMsg(reviewOp.getMessage());
        reviewOpLogDO.setRolOp(reviewOp.getOpType().typeId);

        if (reviewOp.getReview() != null) {
            reviewOpLogDO.setRolReviewId(reviewOp.getReview().getReviewId());
        }

        if (reviewOp.getRole() != null) {
            reviewOpLogDO.setRolRoleIdx(reviewOp.getRole().index);
        }

        if (reviewOp.getEmployee() != null) {
            reviewOpLogDO.setRolEmpId(reviewOp.getEmployee().getEmpId());
        }

        if (reviewOp.getNextRole() != null) {
            reviewOpLogDO.setRolNextRoleIdx(reviewOp.getNextRole().index);
        }

        if (reviewOp.getNextEmp() != null) {
            reviewOpLogDO.setRolNextEmpId(reviewOp.getNextEmp().getEmpId());
        }

        return reviewOpLogDO;
    }

    public static ReviewOp toReviewOp(ReviewOpLogDO reviewOpLogDO) {
        ReviewOp reviewOp = new ReviewOp();
        if (reviewOpLogDO == null) {
            return reviewOp;
        }

        reviewOp.setOpLogId(reviewOpLogDO.getRolId());
        reviewOp.setDate(reviewOpLogDO.getRolDate());
        reviewOp.setDate(reviewOpLogDO.getRolDate());
        reviewOp.setMessage(reviewOpLogDO.getRolMsg());

        Integer reviewId = reviewOpLogDO.getRolReviewId();
        reviewOp.setReview(new Review(reviewId == null ? Integer.valueOf(0) : reviewId));

        Integer empId = reviewOpLogDO.getRolEmpId();
        reviewOp.setEmployee(new Employee(empId == null ? Integer.valueOf(0) : empId));

        Integer nextEmpId = reviewOpLogDO.getRolNextEmpId();
        reviewOp.setNextEmp(new Employee(nextEmpId == null ? Integer.valueOf(0) : nextEmpId));

        BaseRoleType role = BaseRoleType.valueOf(reviewOpLogDO.getRolRoleIdx());
        reviewOp.setRole(role);

        BaseRoleType nextRole = BaseRoleType.valueOf(reviewOpLogDO.getRolNextRoleIdx());
        reviewOp.setNextRole(nextRole);

        ReviewOpType opType = ReviewOpType.valueOf(reviewOpLogDO.getRolOp());
        reviewOp.setOpType(opType);

        return reviewOp;
    }

    public static List<ReviewOp> toReviewOpList(List<ReviewOpLogDO> opLogDOList) {
        if (CollectionUtils.isEmpty(opLogDOList)) {
            return Collections.emptyList();
        }

        List<ReviewOp> reviewOpList = Lists.newArrayListWithCapacity(opLogDOList.size());
        for (ReviewOpLogDO opLogDO : opLogDOList) {
            reviewOpList.add(toReviewOp(opLogDO));
        }

        return reviewOpList;
    }
}
