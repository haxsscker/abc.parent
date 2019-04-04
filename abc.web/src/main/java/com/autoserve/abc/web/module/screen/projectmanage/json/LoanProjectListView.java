/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.projectmanage.json;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.projectmanage.LoanVO;

/**
 * 项目列表
 *
 * @author segen189 2015年1月6日 下午7:48:48
 */
public class LoanProjectListView extends AbstractLoanProjectListView {
    private static final Log log = LogFactory.getLog(LoanProjectListView.class);

    @Resource
    private LoanQueryService loanQueryService;

    public JsonPageVO<LoanVO> execute(Context context, ParameterParser params) {
        PageCondition pageCondition = new PageCondition(params.getInt("page"), params.getInt("rows"));

        // 传入借款人id作为查询条件
        LoanSearchDO searchDO = new LoanSearchDO();
        searchDO.setLoaneeId(params.getInt("loaneeId"));

        PageResult<Loan> pageResult = loanQueryService.queryLoanListBySearchParam(searchDO, pageCondition);

        List<LoanVO> convertedRows = convertLoanVOFields(pageResult);
        return ResultMapper.toPageVO(pageResult, convertedRows);

    }

}
