package com.autoserve.abc.web.module.screen.income.json;

import java.text.ParseException;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.IncomeJDO;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * 类LoanCollectList.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月19日 下午2:04:49
 */
public class LoanCollectList {
    @Resource
    private IncomePlanService incomePlanService;

    public JsonPageVO<IncomeJDO> execute(@Param("rows") int rows, @Param("page") int page, Context context,
                                         ParameterParser params, @Param("planId") int planId) throws ParseException {
    	PageCondition pageCondition = new PageCondition(page, rows);
        ListResult<IncomeJDO> result = this.incomePlanService.queryIncomeList(planId, pageCondition);
        for (IncomeJDO incomeJDO : result.getData()) {
            incomeJDO.setPro_invest_date(DateUtil.formatDate(
                    DateUtil.parseDate(incomeJDO.getPro_invest_date(), DateUtil.DEFAULT_DAY_STYLE),
                    DateUtil.DEFAULT_DAY_STYLE));
        }
        JsonPageVO<IncomeJDO> vo = new JsonPageVO<IncomeJDO>();
        vo.setRows(result.getData());
        vo.setTotal(result.getCount());
        return vo;

    }
}
