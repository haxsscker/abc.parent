package com.autoserve.abc.web.module.screen.extr.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.TocashRecordJDO;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.TocashRecordVOConverter;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.cash.TocashRecordVO;

/**
 * 机构提现查询
 * 
 * @author liuwei 2014年12月24日 下午3:19:04
 */
public class ActionGuarGovExtrListView {
    @Resource
    private ToCashService ToCashService;

    private static Logger logger = LoggerFactory.getLogger(ActionGuarGovExtrListView.class);

    public JsonPageVO<TocashRecordVO> execute(ParameterParser params, @Param("page") int page, @Param("rows") int rows) {

        String searchForm = params.getString("SearchForm");

        String startDate = null;
        String endDate = null;
        //搜索功能取值
        TocashRecordJDO tocashRecordJDO = new TocashRecordJDO();
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));

                    if ("name".equals(field)) {
                        tocashRecordJDO.setCustName(value);
                    }
                    if ("type".equals(field)) {
                        tocashRecordJDO.setExtcashState(Integer.valueOf(value));
                    }
                    if ("start_add_date".equals(field)) {
                        startDate = value;
                    }
                    if ("end_add_date".equals(field)) {
                        endDate = value;
                    }
                }
            } catch (Exception e) {
                logger.error("统计查询条件解析出错！", e);
            }
        }

        PageCondition pageCondition = new PageCondition(page, rows);

        PageResult<TocashRecordJDO> result = this.ToCashService.queryEmpInvestorExtr(tocashRecordJDO, pageCondition,
                startDate, endDate);

        List<TocashRecordVO> listVO = new ArrayList<TocashRecordVO>();

        for (TocashRecordJDO jdo : result.getData()) {
            TocashRecordVO tocashRecordVO = TocashRecordVOConverter.toTocashRecordVO(jdo);
            listVO.add(tocashRecordVO);
        }
        JsonPageVO<TocashRecordVO> vo = new JsonPageVO<TocashRecordVO>();
        vo.setRows(listVO);
        vo.setTotal(result.getTotalCount());
        return vo;
    }
}
