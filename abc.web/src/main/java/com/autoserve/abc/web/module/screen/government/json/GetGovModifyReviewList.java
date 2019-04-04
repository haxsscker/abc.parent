package com.autoserve.abc.web.module.screen.government.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.dao.dataobject.search.GovReviewSearchDO;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/15 13:53.
 */
public class GetGovModifyReviewList {
    @Resource
    private GovernmentService govService;

    private static Logger logger = LoggerFactory.getLogger(GetGovModifyReviewList.class);

    public JsonPageVO<GovPlainJDO> execute(ParameterParser params) {
        //建立分页查询条件
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);

//        {
//            "Items":[{
//            "Field":"gov_name", "Value":"fda", "Method":"Contains"
//        },{
//            "Field":"emp_Name", "Value":"asdf", "Method":"Contains"
//        },{
//            "Field":"SuperArea", "Value":"110", "Method":"Equal"
//        },{
//            "Field":"gov_area_id", "Value":"1000", "Method":"Equal"
//        },{
//            "Field":"gov_edit_date", "Value":"2014-12-09", "Method":"GreaterThanOrEqual"
//        },{
//            "Field":"gov_edit_date", "Value":"2014-12-17", "Method":"LessThanOrEqual"
//        },{
//            "Field":"gov_check_state", "Value":"0", "Method":"Equal"
//        }]}

        GovReviewSearchDO searchDO = new GovReviewSearchDO();
        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {//搜索

            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));
                    String method = String.valueOf(itemJson.get("Method"));

                    if ("gov_name".equals(field)) {
                        searchDO.setGovName(value);
                    } else if ("emp_Name".equals(field)) {
                        searchDO.setGovCustomerManagerName(value);
                    } else if ("SuperArea".equals(field)) {
                        searchDO.setSuperArea(value);
                    } else if ("gov_area_id".equals(field)) {
                        searchDO.setGovArea(value);
                    } else if ("gov_edit_date".equals(field)) {
                        if ("GreaterThanOrEqual".equals(method)) {
                            searchDO.setUpdateStartDate(DateUtil.parseDate(value, DateUtil.DATE_FORMAT));
                        } else if ("LessThanOrEqual".equals(method)) {
                            searchDO.setUpdateEndDate(DateUtil.parseDate(value, DateUtil.DATE_FORMAT));
                        }
                    } else if ("gov_check_state".equals(field)) {
                        searchDO.setGovState(Integer.valueOf(value));
                    }
                }
            } catch (Exception e) {
                logger.error("机构更新审核－搜索查询 查询参数解析出错", e);
            }
        }
        searchDO.setUpdateSearchFlag(true);
        PageResult<GovPlainJDO> pageResult = govService.queryListWithReviewInfo(searchDO, pageCondition);

        JsonPageVO<GovPlainJDO> pageVO = new JsonPageVO<GovPlainJDO>();
        pageVO.setRows(pageResult.getData());
        pageVO.setTotal(pageResult.getTotalCount());
        return pageVO;
    }
}
