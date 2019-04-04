package com.autoserve.abc.web.module.screen.loan.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.TransferSchedule;
import com.autoserve.abc.service.biz.intf.loan.TransferScheduleService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * 转让进度
 *
 * @author liuwei 2015年1月6日 下午4:54:55
 */
public class LoanStepListView {

    @Resource
    private TransferScheduleService transferScheduleService;

    public JsonPageVO<TransferSchedule> execute(ParameterParser params, @Param("loanId") int loanId,
            @Param("rows") int rows, @Param("page") int page) {

        PageCondition pageCondition = new PageCondition(page, rows);

        PageResult<TransferSchedule> result = this.transferScheduleService.queryByLoanId(loanId, pageCondition);

        return ResultMapper.toPageVO(result);
    }

}
