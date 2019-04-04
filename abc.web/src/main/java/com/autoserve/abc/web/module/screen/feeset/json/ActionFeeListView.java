package com.autoserve.abc.web.module.screen.feeset.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.search.FeeSettingSearchDO;
import com.autoserve.abc.service.biz.entity.FeeSetting;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.intf.sys.FeeSettingService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.convert.FeeSetVOConverter;
import com.autoserve.abc.web.vo.feeset.FeeSettingItemVO;
import com.autoserve.abc.web.vo.feeset.FeeSettingVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ActionFeeListView {
    @Resource
    private FeeSettingService feeSettingService;

    private static Logger logger = LoggerFactory.getLogger(ActionFeeListView.class);

    public FeeSettingItemVO execute(ParameterParser params) {

        String formJson = params.getString("searchForm");

        FeeSettingSearchDO feeSettingSearchDO = this.jsonTo(formJson);

        Integer type = params.getInt("feeType");
        ListResult<FeeSetting> result = feeSettingService.queryByFeeType(FeeType.valueOf(type), feeSettingSearchDO);
        List<FeeSettingVO> list = new ArrayList<FeeSettingVO>();
        for (FeeSetting feeSetting : result.getData()) {
            list.add(FeeSetVOConverter.toFeeSetingVO(feeSetting));
        }
        FeeSettingItemVO itemVO = new FeeSettingItemVO();
        itemVO.setRows(list);
        itemVO.setTotal(list.size());
        return itemVO;
    }

    private FeeSettingSearchDO jsonTo(String searchForm) {

        FeeSettingSearchDO feeSettingSearchDO = new FeeSettingSearchDO();
        if (StringUtils.isNotBlank(searchForm)) {

            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));

                    if ("sys_min_money".equals(field)) {
                        if (!StringUtil.isEmpty(value))
                            feeSettingSearchDO.setMinAmount(new BigDecimal(value));
                    }
                    if ("sys_max_money".equals(field)) {
                        if (!StringUtil.isEmpty(value))
                            feeSettingSearchDO.setMaxAmount(new BigDecimal(value));
                    }
                    if ("sys_collect_type".equals(field)) {
                        if (!StringUtil.isEmpty(value))
                            feeSettingSearchDO.setSelFeeType(Integer.valueOf(value));
                    }
                    if ("pro_product_id".equals(field)) {
                        if (!StringUtil.isEmpty(value))
                            feeSettingSearchDO.setProductType(Integer.valueOf(value));
                    }

                }

            } catch (Exception e) {
                logger.error("统计查询条件解析出错！", e);
                return null;
            }
        }
        return feeSettingSearchDO;

    }
}
