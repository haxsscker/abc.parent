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
import com.autoserve.abc.service.biz.enums.BuyLoanState;
import com.autoserve.abc.service.biz.intf.loan.fulltransfer.FullTransferService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.FullTransferRecordVOConverter;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.moneyManage.FullTransferRecordVO;

/**
 * 收购满标划转
 *
 * @author liuwei 2014年12月27日 下午1:19:35
 */
public class BuyFullTransferListView {
    @Resource
    private FullTransferService fullTransferService;

    public JsonPageVO<FullTransferRecordVO> execute(ParameterParser params,
            @Params FullTransferRecordSearchDO fullTransferRecordSearchDO,
            @Param("page") int page, @Param("rows") int rows) {
        // 默认、未划转：满标审核通过
        if (fullTransferRecordSearchDO.getProLoanState() == null
                || fullTransferRecordSearchDO.getProLoanState().equals("0")) {
            fullTransferRecordSearchDO.setLoanStates(Arrays.asList(BuyLoanState.FULL_REVIEW_PASS.state));
        }
        // 全部：满标待审通过，划转中，已划转
        else if (fullTransferRecordSearchDO.getProLoanState().equals("-1")) {
            fullTransferRecordSearchDO.setLoanStates(Arrays.asList(BuyLoanState.FULL_REVIEW_PASS.state,
                    BuyLoanState.MONEY_TRANSFERING.state, BuyLoanState.MONEY_TRANSFERED.state));
        }
        // 已划转：划转中，已划转
        else if (fullTransferRecordSearchDO.getProLoanState().equals("1")) {
            fullTransferRecordSearchDO.setLoanStates(Arrays.asList(BuyLoanState.MONEY_TRANSFERING.state,
                    BuyLoanState.MONEY_TRANSFERED.state));
        }

        PageCondition pageCondition = new PageCondition(page, rows);

        PageResult<FullTransferRecordJDO> pageResult = fullTransferService.queryBuyFullTransferListView(
                fullTransferRecordSearchDO, pageCondition);
        List<FullTransferRecordVO> convertedRows = FullTransferRecordVOConverter.toFullTransferRecordVOList(pageResult
                .getData());
        return ResultMapper.toPageVO(pageResult, convertedRows);
    }

}
