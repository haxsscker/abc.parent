package com.autoserve.abc.web.module.screen.moneyManage.json;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.FullTransferRecordJDO;
import com.autoserve.abc.dao.dataobject.search.FullTransferRecordSearchDO;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.intf.loan.fulltransfer.FullTransferService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.FullTransferRecordVOConverter;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.moneyManage.FullTransferRecordVO;

/**
 * 类实现描述 转让满标划转
 *
 * @author liuwei 2014年12月26日 下午5:50:22
 */
public class MoneyTransferListView {

    @Resource
    private FullTransferService fullTransferService;

    public JsonPageVO<FullTransferRecordVO> execute(ParameterParser params,
            @Params FullTransferRecordSearchDO fullTransferRecordSearchDO,
            @Param("page") int page, @Param("rows") int rows) {

        // 默认、未划转：满标审核通过
        if (fullTransferRecordSearchDO.getProLoanState() == null
                || fullTransferRecordSearchDO.getProLoanState().equals("0")) {
            fullTransferRecordSearchDO.setLoanStates(Arrays.asList(LoanState.FULL_REVIEW_PASS.state));
        }
        // 全部：满标审核通过，划转中，还款中
        else if (fullTransferRecordSearchDO.getProLoanState().equals("-1")) {
            fullTransferRecordSearchDO.setLoanStates(Arrays.asList(LoanState.FULL_REVIEW_PASS.state,
                    LoanState.MONEY_TRANSFERING.state, LoanState.REPAYING.state,LoanState.REPAY_COMPLETED.state));
        }
        // 已划转：还款中
        else if (fullTransferRecordSearchDO.getProLoanState().equals("1")) {
            fullTransferRecordSearchDO.setLoanStates(Arrays.asList(LoanState.REPAYING.state));
        }
        // 待响应即划转中
        else if (fullTransferRecordSearchDO.getProLoanState().equals("3")) {
            fullTransferRecordSearchDO.setLoanStates(Arrays.asList(LoanState.MONEY_TRANSFERING.state));
        }
        // 划转失败:满标审核通过
        else if (fullTransferRecordSearchDO.getProLoanState().equals("2")) {
            fullTransferRecordSearchDO.setLoanStates(Arrays.asList(LoanState.FULL_REVIEW_PASS.state));
        }

        PageCondition pageCondition = new PageCondition(page, rows);

        PageResult<FullTransferRecordJDO> pageResult = fullTransferService.queryMoneyTransferList(
                fullTransferRecordSearchDO, pageCondition);

        List<FullTransferRecordVO> convertedRows = FullTransferRecordVOConverter.toFullTransferRecordVOList(pageResult
                .getData());
        return ResultMapper.toPageVO(pageResult, convertedRows);
    }
}
