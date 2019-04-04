package com.autoserve.abc.web.module.screen.product.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.entity.ProductRateInfo;
import com.autoserve.abc.service.biz.intf.product.ProductRateService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class ProductCheckYn {

    @Resource
    private ProductRateService productRateService;

    public JsonBaseVO execute(ParameterParser params) {

    	Integer prProductId = params.getInt("pdo_product_id");
        ListResult<ProductRateInfo> result = this.productRateService.queryByProductId(prProductId);

        JsonBaseVO vo = new JsonBaseVO();
        if (result.getCount() > 0) {
            vo.setSuccess(true);
        } else {
            vo.setSuccess(false);
        }
        return vo;
    }

}
