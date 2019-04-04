package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.service.biz.entity.ProductRateInfo;
import com.autoserve.abc.web.vo.product.ProductRateInfoVO;

/**
 * 
 * 类ProductRateInfoVOConverter.java的实现描述：ProductRateInfoVO 
 * @author weihuimin 2014年12月18日 下午6:19:57
 */
public class ProductRateInfoVOConverter {

    public static ProductRateInfo toProductRateInfo(ProductRateInfoVO productRateInfoVO) {
        ProductRateInfo productRateInfo = new ProductRateInfo();
        productRateInfo.setProductId(productRateInfoVO.getPdo_product_id());
        productRateInfo.setProductMaxPeriod(productRateInfoVO.getPdo_max_period());
        productRateInfo.setProductMinPeriod(productRateInfoVO.getPdo_min_period());
        productRateInfo.setProductRate(productRateInfoVO.getPdo_product_rate());
        productRateInfo.setProductName(productRateInfoVO.getPdo_product_name());
        productRateInfo.setProductMark(productRateInfoVO.getPdo_product_mark());
        return productRateInfo;
    }

    public static ProductRateInfoVO toProductRateInfoVO(ProductRateInfo productRateInfo) {
        ProductRateInfoVO productRateVO = new ProductRateInfoVO();
        productRateVO.setPdo_product_id(productRateInfo.getProductId());
        productRateVO.setPdo_product_name(productRateInfo.getProductName());
        productRateVO.setPdo_max_period(productRateInfo.getProductMaxPeriod());
        productRateVO.setPdo_min_period(productRateInfo.getProductMinPeriod());
        productRateVO.setPdo_product_rate_Id(productRateInfo.getProductRateId());
        productRateVO.setPdo_product_rate(productRateInfo.getProductRate());
        productRateVO.setPdo_product_mark(productRateInfo.getProductMark());
        return productRateVO;
    }
    

    public static List<ProductRateInfoVO> toVOList(List<ProductRateInfo> productRateInfoList) {
        List<ProductRateInfoVO> result = new ArrayList<ProductRateInfoVO>();
        if (productRateInfoList == null || productRateInfoList.isEmpty()) {
            return result;
        }
        for (ProductRateInfo productRateInfo : productRateInfoList) {
            result.add(toProductRateInfoVO(productRateInfo));
        }
        return result;
    }

    public static List<ProductRateInfo> toEntityList(List<ProductRateInfoVO> productRateInfoVOList) {
        List<ProductRateInfo> result = new ArrayList<ProductRateInfo>();
        if (productRateInfoVOList == null || productRateInfoVOList.isEmpty()) {
            return result;
        }
        for (ProductRateInfoVO ProductRateInfoVO : productRateInfoVOList) {
            result.add(toProductRateInfo(ProductRateInfoVO));
        }
        return result;
    }
}
