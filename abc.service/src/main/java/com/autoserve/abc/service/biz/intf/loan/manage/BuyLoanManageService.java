/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.loan.manage;

import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * 收购标项目管理服务
 *
 * @author segen189 2015年1月9日 下午5:12:55
 */
public interface BuyLoanManageService {

    /**
     * 收购标强制满标
     *
     * @param buyLoanId 收购标id，必选
     * @param operatorId 操作人，必选
     * @param note 备注，可选
     * @return BaseResult
     */
    BaseResult forceBuyLoanToFull(int buyLoanId, int operatorId, String note);

    /**
     * 收购标流标
     *
     * @param buyLoanId 收购标id，必选
     * @param operatorIdId 操作人，必选
     * @param note 备注，可选
     * @return BaseResult
     */
    BaseResult cancelBuyLoan(int buyLoanId, int operatorIdId, String note);

}
