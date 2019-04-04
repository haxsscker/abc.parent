/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.projectmanage.json;

import java.util.Arrays;
import java.util.List;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.projectmanage.LoanVO;

/**
 * 项目管理-项目发布
 *
 * @author segen189 2014年12月22日 下午1:12:27
 */
public class ProjectReleaseListView extends AbstractLoanProjectListView {

    public JsonPageVO<LoanVO> execute(Context context, ParameterParser params) {
        LoanSearchDO searchDO = assembleLoanSearchParamIfNecessary(params);
        if (searchDO == null) {
            // 查询参数为null，查询全部待发布、招标中的项目
            searchDO = new LoanSearchDO();
            searchDO.setLoanState(Arrays.asList(LoanState.WAIT_RELEASE.getState(), LoanState.BID_INVITING.getState()));
        } else if (searchDO.getLoanState() == null) {
            // 查询参数不为null，但是查询参数中的项目状态字段为null，查询全部待发布、招标中的项目
            searchDO.setLoanState(Arrays.asList(LoanState.WAIT_RELEASE.getState(), LoanState.BID_INVITING.getState()));
        }

        PageCondition pageCondition = new PageCondition(params.getInt("page"), params.getInt("rows"));
        searchDO.setFlag(1);
        PageResult<Loan> pageResult = loanQueryService.queryLoanListBySearchParam(searchDO, pageCondition);

        List<LoanVO> convertedRows = convertLoanVOFields(pageResult);
        return ResultMapper.toPageVO(pageResult, convertedRows);
    }

}
