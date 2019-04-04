package com.autoserve.abc.web.module.screen.redrewardmanage.json;

import java.util.Arrays;
import java.util.List;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.module.screen.projectmanage.json.AbstractLoanProjectListView;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.projectmanage.LoanVO;

/**
 * 项目红包添加页面项目列表
 *
 * @author lipeng 2015年1月5日 下午2:45:40
 */
public class GetProApplyInfoList extends AbstractLoanProjectListView {

    public JsonPageVO<LoanVO> execute(Context context, ParameterParser params) {
        LoanSearchDO searchDO = assembleLoanSearchParamIfNecessary(params);
        if (searchDO == null) {
            // 查询参数为null，查询全部待还款中的项目
            searchDO = new LoanSearchDO();
            searchDO.setLoanState(Arrays.asList(LoanState.REPAYING.getState()));
        } else if (searchDO.getLoanState() == null) {
            // 查询参数不为null，但是查询参数中的项目状态字段为null，查询全部还款中的项目
            searchDO.setLoanState(Arrays.asList(LoanState.REPAYING.getState()));
        }

        PageCondition pageCondition = new PageCondition(params.getInt("page"), params.getInt("rows"));

        PageResult<Loan> pageResult = loanQueryService.queryLoanListBySearchParam(searchDO, pageCondition);

        List<LoanVO> convertedRows = convertLoanVOFields(pageResult);
        return ResultMapper.toPageVO(pageResult, convertedRows);
    }

}
