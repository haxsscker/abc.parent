package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;
import com.autoserve.abc.dao.dataobject.ProductRateDO;
import com.autoserve.abc.service.biz.entity.ProductRate;

/**
 * ProductRate与ProductRateDO互相转换
 *
 * @author weihuimin 2014年12月11日 
 */
public class ProductRateConverter {

    public static ProductRateDO toProductRateDO(ProductRate productRate) {
        if (productRate == null) {
            return null;
        }

        ProductRateDO productRateDo = new ProductRateDO();
        
		productRateDo.setProductRateId(productRate.getProductRateId());
		productRateDo.setProductId(productRate.getProductId());
		productRateDo.setProductRate(productRate.getProductRate());
		productRateDo.setProductMinPeriod(productRate.getProductMinPeriod());
		productRateDo.setProductMaxPeriod(productRate.getProductMaxPeriod());

        return productRateDo;
    }

    public static ProductRate toProductRate(ProductRateDO productRateDo) {
        if (productRateDo == null) {
            return null;
        }

        ProductRate productRate = new ProductRate();
        
        productRate.setProductRateId(productRateDo.getProductRateId());
        productRate.setProductId(productRateDo.getProductId());
        productRate.setProductRate(productRateDo.getProductRate());
        productRate.setProductMinPeriod(productRateDo.getProductMinPeriod());
        productRate.setProductMaxPeriod(productRateDo.getProductMaxPeriod());
        return productRate;
    }

    public static List<ProductRate> toProductRateList(List<ProductRateDO> productRateDoList) {
        if (productRateDoList == null) {
            return null;
        }

        List<ProductRate> productRateList = new ArrayList<ProductRate>(productRateDoList.size());
        for (ProductRateDO productRateDo : productRateDoList) {
            productRateList.add(toProductRate(productRateDo));
        }

        return productRateList;
    }

}