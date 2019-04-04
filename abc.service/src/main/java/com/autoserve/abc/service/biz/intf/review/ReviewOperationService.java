package com.autoserve.abc.service.biz.intf.review;

import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-21,11:45
 */
public interface ReviewOperationService {
    /**
     * 对一个审核进行操作
     * 需要指定ReviewType、applyId，与ReviewOp
     * 调用此接口时，ReviewOp对象中操作类型reviewOpType与操作人employee必须显式地设置
     * 而当前审核人role与当前操作相关的审核review两个属性不用设置，将由系统自动设置
     *
     * @param reviewType 审核类型
     * @param applyId    审核的相关申请id
     * @param reviewOp   具体的审核操作
     * @return
     */
    public BaseResult doReview(ReviewType reviewType, Integer applyId, ReviewOp reviewOp);

    /**
     * 将融资审核发送至项目初审
     * @param applyId    融资审核对应的loanId
     * @param employeeId 当前登录操作人的员工ID
     */
    public BaseResult sendFinancingReviewToPlatform(Integer applyId, Integer employeeId);
}
