package com.autoserve.abc.web.module.screen.government.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovernmentUpdateHistoryDO;
import com.autoserve.abc.service.biz.entity.GovUpdateHistory;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.GovUpdateHistoryVOConverter;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.government.GovUpdateHistoryVO;

/**
 * @author RJQ 2014/12/20 11:33.
 */
public class GetModifyList {
    @Autowired
    private GovernmentService governmentService;

    private static Logger     logger = LoggerFactory.getLogger(GetModifyList.class);

    public JsonPageVO<GovUpdateHistoryVO> execute(ParameterParser params) {
        //分页查询条件
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);

        PageResult<GovUpdateHistory> histories;

        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {//搜索
            GovernmentUpdateHistoryDO historyDO = new GovernmentUpdateHistoryDO();
            Date updateStartDate = null;
            Date updateEndDate = null;
            String updateEmpName = null;

            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));
                    String method = String.valueOf(itemJson.get("Method"));

                    if ("gov_id".equals(field)) {
                        historyDO.setGuhGovid(Integer.valueOf(value));
                    } else if ("eEditName".equals(field)) {
                        updateEmpName = value;
                    } else if ("gov_edit_date".equals(field)) {
                        if ("GreaterThanOrEqual".equals(method)) {
                            updateStartDate = DateUtil.parseDate(value, DateUtil.DATE_FORMAT);
                        } else if ("LessThanOrEqual".equals(method)) {
                            updateEndDate = DateUtil.parseDate(value, DateUtil.DATE_FORMAT);
                        }
                    } else if ("gov_check_state".equals(field)) {
                        historyDO.setGuhAuditState(Integer.valueOf(value));
                    }
                }
            } catch (Exception e) {
                logger.error("机构维护记录－搜索查询 查询参数解析出错", e);
            }

            histories = governmentService.searchReviewOpLog(historyDO, updateStartDate, updateEndDate, updateEmpName,
                    pageCondition);

        } else {
            histories = governmentService.queryReviewOpLogByGovId(null, pageCondition);
        }

        List<GovUpdateHistoryVO> vos = new ArrayList<GovUpdateHistoryVO>();
        if (histories != null && histories.getData().size() != 0) {
            vos = GovUpdateHistoryVOConverter.convertToList(histories.getData());
        }
        JsonPageVO<GovUpdateHistoryVO> jsonPageVO = new JsonPageVO<GovUpdateHistoryVO>();
        jsonPageVO.setTotal(histories.getTotalCount());
        jsonPageVO.setRows(vos);
        jsonPageVO.setMessage(histories.getMessage());
        return jsonPageVO;

    }
}
