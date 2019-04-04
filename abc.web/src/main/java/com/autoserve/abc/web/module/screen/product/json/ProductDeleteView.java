package com.autoserve.abc.web.module.screen.product.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.entity.ProductInfo;
import com.autoserve.abc.service.biz.entity.ProductRate;
import com.autoserve.abc.service.biz.intf.product.ProductInfoService;
import com.autoserve.abc.service.biz.intf.product.ProductRateService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 产品删除
 * 
 * @author wei.huimin 2014年12月3日 下午1:21:02
 */
public class ProductDeleteView {

    @Resource
    private ProductRateService productRateService;

    @Resource
    private ProductInfoService productInfoService;

    public JsonBaseVO execute(ParameterParser params) {
        int pdoProductId = params.getInt("pdo_product_id");
        int pdoRateId = params.getInt("pdo_product_rate_Id");
        ProductRate productRate = new ProductRate();
        productRate.setProductId(pdoProductId);
        int countRate = productRateService.findRateCountByParam(productRate);
        BaseResult result = this.productRateService.delete(pdoRateId);
        BaseResult result2 = new BaseResult();
        result2.setSuccess(true);
        if (result.isSuccess() && (countRate == 1)) {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setProductId(pdoProductId);
            result2 = this.productInfoService.delete(productInfo);
        }
        JsonBaseVO vo = new JsonBaseVO();
        if (result.isSuccess() && result2.isSuccess()) {
            vo.setSuccess(result.isSuccess());
            vo.setMessage(result.getMessage());
        }
        return vo;
    }
}
