package com.autoserve.abc.web.module.screen.product;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.ProductRateInfo;
import com.autoserve.abc.service.biz.intf.product.ProductRateService;
import com.autoserve.abc.service.biz.result.ListResult;

/**
 * 产品页面查看按钮
 * 
 * @author weihuimin 2014/12/15
 */
public class ProductLookUpView {

    @Resource
    private ProductRateService productRateService;

    public void execute(Context context, ParameterParser params) {
        Integer prProductId = params.getInt("pdo_product_id");

        ListResult<ProductRateInfo> result = this.productRateService.queryByProductId(prProductId);
        context.put("list", result.getData());
        context.put("productName", result.getData().get(0).getProductName());
        context.put("productMark", result.getData().get(0).getProductMark());
    }
}
