/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.loan.fulltransfer;

import java.math.BigDecimal;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.FullTransferRecordJDO;
import com.autoserve.abc.dao.dataobject.search.FullTransferRecordSearchDO;
import com.autoserve.abc.service.biz.enums.FullTransferType;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 满标资金划转服务
 *
 * @author segen189 2014年11月29日 下午2:27:50
 */
public interface FullTransferService {
    /**
     * 普通标满标资金划转
     *
     * @param loanId 普通标id，必选
     * @param actrualOperateFee 实收手续费
     * @param actualGuarFee 实收担保费
     * @param periods 还款计划表总生成期数
     * @param transferType 资金划转类型，必选
     * @param operatorId 操作人id，必选
     * @return BaseResult
     */
    BaseResult commonBidMoneyTransfer(int loanId, double actrualOperateFee, BigDecimal actualGuarFee, int periods, FullTransferType transferType,
                                      int operatorId);

    /**
     * 转让标满标资金划转
     *
     * @param transLoanId 转让标id，必选
     * @param actrualOperateFee 实收手续费
     * @param transferType 资金划转类型，必选
     * @param operatorId 操作人id，必选
     * @return BaseResult
     */
    BaseResult transferBidMoneyTransfer(int transLoanId, double actrualOperateFee, FullTransferType transferType,
                                        int operatorId);

    /**
     * 收购标满标资金划转
     *
     * @param buyLoanId 收购标id，必选
     * @param actrualOperateFee 实收手续费
     * @param transferType 资金划转类型，必选
     * @param operatorId 操作人id，必选
     * @return BaseResult
     */
    BaseResult buyBidMoneyTransfer(int buyLoanId, double actrualOperateFee, FullTransferType transferType,
                                   int operatorId);

    /**
     * 满标划转
     *
     * @param fullTransferRecordSearchDO
     * @param pageCondition
     * @return
     */
    PageResult<FullTransferRecordJDO> queryMoneyTransferList(FullTransferRecordSearchDO fullTransferRecordSearchDO,
            PageCondition pageCondition);

    /**
     * 转让满标划转
     *
     * @param fullTransferRecordSearchDO
     * @param pageCondition
     * @return
     */
    PageResult<FullTransferRecordJDO> queryAttFulScaTsfListView(FullTransferRecordSearchDO fullTransferRecordSearchDO,
            PageCondition pageCondition);

    /**
     * 收购满标划转
     *
     * @param fullTransferRecordSearchDO
     * @param pageCondition
     * @return
     */
    PageResult<FullTransferRecordJDO> queryBuyFullTransferListView(FullTransferRecordSearchDO fullTransferRecordSearchDO,
            PageCondition pageCondition);

}
