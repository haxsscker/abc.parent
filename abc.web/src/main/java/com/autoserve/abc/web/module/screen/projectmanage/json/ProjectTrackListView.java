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
 * 项目管理－项目跟踪
 *
 * @author segen189 2014年12月22日 下午1:03:17
 */
public class ProjectTrackListView extends AbstractLoanProjectListView {

    public JsonPageVO<LoanVO> execute(Context context, ParameterParser params) {
        LoanSearchDO searchDO = assembleLoanSearchParamIfNecessary(params);
        if (searchDO != null) {
            if (searchDO.getLoanState() != null && searchDO.getLoanState().get(0) == 100) {
                searchDO.setLoanState(Arrays.asList(LoanState.DELETED.getState()));
            }
        }
        if (searchDO == null) {
            searchDO = new LoanSearchDO();
            searchDO.setExcludeLoanState(Arrays.asList(LoanState.DELETED.getState()));// 默认不显示已经被删除的项目
        }

        PageCondition pageCondition = new PageCondition(params.getInt("page"), params.getInt("rows"));

        // 兼容融资维护页面的项目列表
        if (params.containsKey("isFromIntent")) {
            searchDO.setIsFromIntent(params.getBoolean("isFromIntent"));
        }
        searchDO.setFlag(1);   //使用后台的排序条件
        PageResult<Loan> pageResult = loanQueryService.queryLoanListBySearchParam(searchDO, pageCondition);

        List<LoanVO> convertedRows = convertLoanVOFields(pageResult);
        return ResultMapper.toPageVO(pageResult, convertedRows);
    }

}
