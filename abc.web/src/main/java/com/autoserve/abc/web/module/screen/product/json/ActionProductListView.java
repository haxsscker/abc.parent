package com.autoserve.abc.web.module.screen.product.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.ProductRateInfo;
import com.autoserve.abc.service.biz.intf.product.ProductRateService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.ProductRateInfoVOConverter;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.product.ProductRateInfoVO;

/**
 * 产品页面列表
 * 
 * @author weihuimin 2014年12月18日 下午6:21:44
 */
public class ActionProductListView {

    @Resource
    private ProductRateService productRateService;

    public JsonPageVO<ProductRateInfoVO> execute(ParameterParser params) {
        String pdo_product_name = "";
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");

        String searchForm = params.getString("searchForm");
        JSONObject searchFormJson = JSON.parseObject(searchForm);
        if (null != searchFormJson) {
            JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));
            for (Object item : itemsArray) {
                JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                String field = String.valueOf(itemJson.get("Field"));
                String value = String.valueOf(itemJson.get("Value"));

                if ("pdo_product_name".equals(field)) {
                    pdo_product_name = value;
                }
            }
        }

        PageCondition pageCondition = new PageCondition(page, rows);
        JsonPageVO<ProductRateInfoVO> voPage = new JsonPageVO<ProductRateInfoVO>();
        List<ProductRateInfo> list = new ArrayList<ProductRateInfo>();
        ProductRateInfo productRateInfo = new ProductRateInfo();

        int resultCount = 0;
        PageResult<ProductRateInfo> result = null;

        if ("".equals(pdo_product_name) || null == pdo_product_name) {
            result = productRateService.findProductRateInfoListByParam(null, pageCondition);
            resultCount = productRateService.findAllCount();
        } else {
            productRateInfo.setProductName(pdo_product_name);
            result = productRateService.findProductRateInfoListByParam(productRateInfo, pageCondition);
            resultCount = productRateService.findCountByParam(productRateInfo);
        }
        if (result.getData() != null) {
            list = result.getData();
            List<ProductRateInfoVO> listVo = ProductRateInfoVOConverter.toVOList(list);
            voPage.setRows(listVo);
            voPage.setSuccess(true);
            voPage.setTotal(resultCount);
        }
        return voPage;
    }

}
