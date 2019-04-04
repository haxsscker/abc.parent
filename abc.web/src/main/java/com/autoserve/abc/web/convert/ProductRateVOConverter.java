package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.ProductRateInfoDO;
import com.autoserve.abc.service.biz.entity.ProductRateInfo;
import com.autoserve.abc.web.vo.product.ProductRateInfoVO;

/**
 * 
 * 类ProductRateVOConverter.java的实现描述：ProductRateVOConverter
 * @author Administrator 2014年12月18日 下午6:20:18
 */
public class ProductRateVOConverter {

    public static ProductRateInfo toProductRateInfo(ProductRateInfoVO productRateInfoVO) {
        ProductRateInfo productRateInfo = new ProductRateInfo();
        productRateInfo.setProductId(productRateInfoVO.getPdo_product_id());
        productRateInfo.setProductName(productRateInfoVO.getPdo_product_name());
        productRateInfo.setProductMark(productRateInfoVO.getPdo_product_mark());
        productRateInfo.setProductMaxPeriod(productRateInfoVO.getPdo_max_period());
        productRateInfo.setProductMinPeriod(productRateInfoVO.getPdo_min_period());
        productRateInfo.setProductRate(productRateInfoVO.getPdo_product_rate());
        return productRateInfo;
    }

    public static ProductRateInfoVO toProductRateInfoVO(ProductRateInfoDO productRateInfoDO) {
        ProductRateInfoVO productRateInfoVO = new ProductRateInfoVO();
        productRateInfoVO.setPdo_product_id(productRateInfoDO.getProductId());
        productRateInfoVO.setPdo_product_name(productRateInfoDO.getProductName());
        productRateInfoVO.setPdo_product_mark(productRateInfoDO.getProductMark());
        productRateInfoVO.setPdo_max_period(productRateInfoDO.getProductMaxPeriod());
        productRateInfoVO.setPdo_min_period(productRateInfoDO.getProductMinPeriod());
        productRateInfoVO.setPdo_product_rate_Id(productRateInfoDO.getProductRateId());
        productRateInfoVO.setPdo_product_rate(productRateInfoDO.getProductRate());
        return productRateInfoVO;
    }
}
