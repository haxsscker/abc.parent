/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.projectmanage.json;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.dao.dataobject.search.TransferLoanSearchDO;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.projectmanage.TransferLoanVO;

/**
 * 项目管理-转让跟踪
 * 
 * @author segen189 2014年12月22日 下午12:05:58
 */
public class ProjectScheduleListView extends AbstractTransferLoanProjectListView {
    private static final Log    log = LogFactory.getLog(ProjectScheduleListView.class);

    @Resource
    private TransferLoanService transferLoanService;

    public JsonPageVO<TransferLoanVO> execute(Context context, ParameterParser params) {
        TransferLoanSearchDO pojo = assembleTransferLoanSearchParam(params);
        PageCondition pageCondition = new PageCondition(params.getInt("page"), params.getInt("rows"));

        PageResult<TransferLoanJDO> pageResult = transferLoanService.queryListByParam(pojo, pageCondition);

        List<TransferLoanVO> convertedRows = convertTransferLoanVOFields(pageResult);
        return ResultMapper.toPageVO(pageResult, convertedRows);
    }

    protected TransferLoanSearchDO assembleTransferLoanSearchParam(ParameterParser params) {
        String searchForm = params.getString("SearchForm");
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                TransferLoanSearchDO searchDO = new TransferLoanSearchDO();

                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));

                    // 项目名称
                    if ("loanNo".equals(field)) {
                        searchDO.setLoanNo(value);
                    }
                    // 转让人
                    if ("transferUserName".equals(field)) {
                        searchDO.setTransferUserName(value);
                    }
                    // 项目类型 －2表示全部
                    else if ("loanCategory".equals(field) && !"-2".equals(value)) {
                        searchDO.setLoanCategory(Integer.valueOf(value));
                    }
                    // 申请日期低值
                    else if ("applyDateStart".equals(field)) {
                        searchDO.setApplyDateStart(DateUtil.parseDate(value, DateUtil.DATE_FORMAT));
                    }
                    // 申请日期高值
                    else if ("applyDateEnd".equals(field)) {
                        searchDO.setApplyDateEnd(DateUtil.parseDate(value, DateUtil.DATE_FORMAT));
                    }

                    // 转让金额低值
                    else if ("transferMoneyStart".equals(field)) {
                        searchDO.setTransferMoneyStart(Double.valueOf(value));
                    }
                    // 转让金额高值
                    else if ("transferMoneyEnd".equals(field)) {
                        searchDO.setTransferMoneyEnd(Double.valueOf(value));
                    }
                    // 转让状态 -2表示全部
                    else if ("transferLoanState".equals(field) && !"-2".equals(value)) {
                        searchDO.setTransferLoanState(Integer.valueOf(value));
                    }
                }
                return searchDO;
            } catch (Exception e) {
                log.error("项目管理－搜索查询 查询参数解析出错", e);
            }
        }
        return null;
    }
}
