/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.loan.manage;

import java.util.Map;

import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * 普通标项目管理服务
 *
 * @author segen189 2015年1月9日 下午5:12:55
 */
public interface LoanManageService {

    /**
     * 从待发布状态有条件地撤回到待项目初审
     *
     * @param loanId 普通标id，必选
     * @param operatorId 操作人，必选
     * @param note 备注，可选
     * @return BaseResult
     */
    BaseResult revokeToWaitProjectReview(int loanId, int operatorId, String note);

    /**
     * 把项目状态从待发布退回待项目初审
     *
     * @param loanId 普通标id，必选
     * @param operatorId 操作人，必选
     * @param note 备注，可选
     * @return BaseResult
     */
    BaseResult sendBackToWaitProjectReview(int loanId, int operatorId, String note);

    /**
     * 把项目状态从项目初审通过改为待发布
     *
     * @param loanId 普通标id，必选
     * @param operatorId 操作人，必选
     * @param note 备注，可选
     * @return BaseResult
     */
    BaseResult sendToWaitRelease(int loanId, int operatorId, String note);

    /**
     * 融资维护项目删除
     *
     * @param loanId 普通标id，必选
     * @param operatorId 操作人，必选
     * @param note 备注，可选
     * @return BaseResult
     */
    BaseResult removeProject(int loanId, int operatorId, String note);

    /**
     * 普通标流标
     *
     * @param loanId 普通标id，必选
     * @param operatorId 操作人，必选
     * @param note 备注，可选
     * @return BaseResult
     */
    BaseResult cancelLoan(int loanId, int operatorId, String note);

    /**
     * 普通标强制满标
     *
     * @param loanId 普通标id，必选
     * @param operatorId 操作人，必选
     * @param note 备注，可选
     * @return BaseResult
     */
    BaseResult forceLoanToFull(int loanId, int operatorId, String note);

    /**
     * 发布普通标
     *
     * @param loanId 普通标id，必选
     * @param operatorId 操作人，必选
     * @param note 备注，可选
     * @return BaseResult
     */
    BaseResult releaseLoan(int loanId, int operatorId, String note);

    /**
     * 取消发布普通标
     *
     * @param loanId 普通标id，必选
     * @param operatorId 操作人，必选
     * @param note 备注，可选
     * @return BaseResult
     */
    BaseResult cancelRelease(int loanId, int operatorId, String note);
    /**
     * 流标接口
     * @param map
     * @return
     */
	Map<String, String> cancelBid(Map<String, String> map);

}
