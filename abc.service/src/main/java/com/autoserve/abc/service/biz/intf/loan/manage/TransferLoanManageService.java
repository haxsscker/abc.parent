/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.loan.manage;

import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * 转让标项目管理服务
 *
 * @author segen189 2015年1月9日 下午5:12:55
 */
public interface TransferLoanManageService {

    /**
     * 转让标强制满标
     *
     * @param transferLoanId 转让标id，必选
     * @param operatorId 操作人，必选
     * @param note 备注，可选
     * @return BaseResult
     */
    BaseResult forceTransferLoanToFull(int transferLoanId, int operatorId, String note);

    /**
     * 转让标流标
     *
     * @param transferLoanId 转让标id，必选
     * @param operatorId 操作人，必选
     * @param note 备注，可选
     * @return BaseResult
     */
    BaseResult cancelTransferLoan(int transferLoanId, int operatorId, String note);
}
