package com.autoserve.abc.web.module.screen.government.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.vo.JsonPageVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 获取所有机构列表
 *
 * @author RJQ 2014/12/6 11:16.
 */
public class GetGovList {
    private static Logger logger = LoggerFactory.getLogger(GetGovList.class);

    @Resource
    private GovernmentService govService;

    public JsonPageVO<GovPlainJDO> execute(ParameterParser params) {
        JsonPageVO<GovPlainJDO> jsonPageVO = new JsonPageVO<GovPlainJDO>();

        //建立分页查询条件
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);

        //建立搜索条件，可为空
        GovPlainJDO govPlainJDO = new GovPlainJDO();
        BigDecimal moneyStart = null;
        BigDecimal moneyEnd = null;
        String superAreaCode = null;
        String customerManagerName = null;

        /*
         * {"Items": [ {"Field":"gov_name","Value":"ew"},
         * {"Field":"emp_Name","Value":"ew"},
         * {"Field":"gov_max_lend_money","Value":"1"},
         * {"Field":"gov_max_lend_money","Value":"2"},
         * {"Field":"SuperArea","Value":"110"},
         * {"Field":"gov_area_id","Value":"1000"},
         * {"Field":"gov_is_use","Value":"0"} ] }
         */
        String searchForm = params.getString("SearchForm");
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));

                    // 机构名称
                    if ("gov_name".equals(field)) {
                        govPlainJDO.setGovName(value);
                    }
                    // 客户经理
                    else if ("emp_Name".equals(field)) {
                        customerManagerName = value;
                    }
                    // 最大借款额度低值
                    else if ("money_start".equals(field)) {
                        moneyStart = BigDecimal.valueOf(Double.valueOf(value));
                    }
                    // 最大借款额度高值
                    else if ("money_end".equals(field)) {
                        moneyEnd = BigDecimal.valueOf(Double.valueOf(value));
                    }
                    // 所属地区省
                    else if ("SuperArea".equals(field)) {
                        superAreaCode = value;
                    }
                    // 所属地区市
                    else if ("gov_area_id".equals(field)) {
                        govPlainJDO.setGovArea(value);
                    }
                    // 启用状态
                    else if ("gov_is_use".equals(field)) {
                        govPlainJDO.setGovIsEnable(Integer.valueOf(value));
                    }
                }
            } catch (Exception e) {
                logger.error("机构维护－信息维护－搜索查询 查询参数解析出错", e);
            }
        }

        //根据条件进行查询
        PageResult<GovPlainJDO> pageResult = govService.queryListWithPlainInfo(govPlainJDO, moneyStart, moneyEnd, customerManagerName,
                superAreaCode, pageCondition);
        if (pageResult.getData() != null) {
            jsonPageVO.setRows(pageResult.getData());
            jsonPageVO.setTotal(pageResult.getTotalCount());
        }
        return jsonPageVO;
    }
}
