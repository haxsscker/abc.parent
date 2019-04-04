/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.loan.fulltransfer;

import java.util.Date;
import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.FullTransferFundsSearchDO;
import com.autoserve.abc.dao.dataobject.summary.FullTransferSummaryDO;
import com.autoserve.abc.service.biz.entity.TransferFundsDetailInfo;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 类FullTransferQueryService.java的实现描述：资金划转统计
 * 
 * @author J.YL 2014年12月31日 下午5:49:49
 */
public interface FullTransferQueryService {

    /**
     * 查询资金划转统计信息
     * 
     * @param year
     * @param pageCondition
     * @return
     */
    public ListResult<FullTransferSummaryDO> queryTransferSummaryInfo(List<Integer> year);

    /**
     * 查询资金划转年份
     * 
     * @param pageCondition
     * @return
     */
    public PageResult<Integer> queryYears(PageCondition pageCondition);

    /**
     * 查询资金划转详情
     * 
     * @param pageCondition
     * @return
     */
    public PageResult<TransferFundsDetailInfo> queryDetail(FullTransferFundsSearchDO searchDO, Date date,
                                                           PageCondition pageCondition);

}
