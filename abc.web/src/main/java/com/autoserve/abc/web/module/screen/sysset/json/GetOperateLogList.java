package com.autoserve.abc.web.module.screen.sysset.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.dao.dataobject.OperateLogJDO;
import com.autoserve.abc.dao.dataobject.search.OperateLogSearchDO;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.OperateLogVOConverter;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.sysset.OperateLogVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2015/1/10 21:45.
 */
public class GetOperateLogList {
    @Autowired
    private OperateLogService operateLogService;

    private static Logger logger = LoggerFactory.getLogger(GetOperateLogList.class);

    public JsonPageVO<OperateLogVO> execute(ParameterParser params) {
        JsonPageVO<OperateLogVO> vo = new JsonPageVO<OperateLogVO>();
        PageCondition pageCondition = new PageCondition(params.getInt("page"), params.getInt("rows"));
        PageResult<OperateLogJDO> pageResult;


        String searchForm = params.getString("SearchForm");
        if (StringUtils.isNotBlank(searchForm)) {
            OperateLogSearchDO searchDO = new OperateLogSearchDO();
            searchDO.setOlState(EntityState.STATE_ENABLE.getState());
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));
                    String method = String.valueOf(itemJson.get("Method"));

                    if ("emp_Id".equals(field)) {
                        searchDO.setEmpId(Integer.valueOf(value));
                    }
                    if ("olo_OperateType".equals(field)) {
                        searchDO.setOlOperateType(value);
                    }
                    if ("olo_OperateTime".equals(field)) {
                        if ("GreaterThanOrEqual".equals(method)) {
                            searchDO.setOlOperateStartTime(DateUtil.parseDate(value, DateUtil.DATE_FORMAT));
                        } else if ("LessThanOrEqual".equals(method)) {
                            searchDO.setOlOperateEndTime(DateUtil.parseDate(value, DateUtil.DATE_FORMAT));
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("数据操作日志－搜索查询 查询参数解析出错", e);
            }

            pageResult = operateLogService.searchList(searchDO, pageCondition);
        } else {
            OperateLogDO operateLogDO = new OperateLogDO();
            operateLogDO.setOlState(EntityState.STATE_ENABLE.getState());
            pageResult = operateLogService.queryList(operateLogDO, pageCondition);
        }

        List<OperateLogJDO> operateLogJDOs = pageResult.getData();
        if (!CollectionUtils.isEmpty(operateLogJDOs)) {
            vo.setRows(OperateLogVOConverter.convertToVOList(operateLogJDOs));
            vo.setTotal(pageResult.getTotalCount());
        } else {
            vo.setRows(new ArrayList<OperateLogVO>());
            vo.setTotal(0);
        }

        return vo;
    }
}
