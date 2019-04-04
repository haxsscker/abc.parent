package com.autoserve.abc.service.biz.intf.loan;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.TransferSchedule;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 转让进度
 *
 * @author liuwei 2015年1月6日 下午2:47:22
 */
public interface TransferScheduleService {

    public PageResult<TransferSchedule> queryByLoanId(int loanId, PageCondition pageCondition);

}
