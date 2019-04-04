package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;
import com.autoserve.abc.dao.dataobject.ProductRateInfoDO;
import com.autoserve.abc.service.biz.entity.ProductRateInfo;

/**
 * ProductRate与ProductRateDO互相转换
 *
 * @author weihuimin 2014年12月11日 
 */
public class ProductRateInfoConverter {

    public static ProductRateInfoDO toProductRateInfoDO(ProductRateInfo productRateInfo) {
        if (productRateInfo == null) {
            return null;
        }

        ProductRateInfoDO productRateInfoDo = new ProductRateInfoDO();
        
		productRateInfoDo.setProductRateId(productRateInfo.getProductRateId());
		productRateInfoDo.setProductId(productRateInfo.getProductId());
		productRateInfoDo.setProductRate(productRateInfo.getProductRate());
		productRateInfoDo.setProductMinPeriod(productRateInfo.getProductMinPeriod());
		productRateInfoDo.setProductMaxPeriod(productRateInfo.getProductMaxPeriod());
		productRateInfoDo.setProductMark(productRateInfo.getProductMark());
		productRateInfoDo.setProductName(productRateInfo.getProductName());

        return productRateInfoDo;
    }

    public static ProductRateInfo toProductRateInfo(ProductRateInfoDO productRateInfoDo) {
        if (productRateInfoDo == null) {
            return null;
        }

        ProductRateInfo productRateInfo = new ProductRateInfo();
        
        productRateInfo.setProductRateId(productRateInfoDo.getProductRateId());
        productRateInfo.setProductId(productRateInfoDo.getProductId());
        productRateInfo.setProductRate(productRateInfoDo.getProductRate());
        productRateInfo.setProductMinPeriod(productRateInfoDo.getProductMinPeriod());
        productRateInfo.setProductMaxPeriod(productRateInfoDo.getProductMaxPeriod());
        productRateInfo.setProductMark(productRateInfoDo.getProductMark());
        productRateInfo.setProductName(productRateInfoDo.getProductName());
        return productRateInfo;
    }

    public static List<ProductRateInfo> toProductRateInfoList(List<ProductRateInfoDO> productRateInfoDoListDO) {
        if (productRateInfoDoListDO == null) {
            return null;
        }

        List<ProductRateInfo> productRateInfoList = new ArrayList<ProductRateInfo>(productRateInfoDoListDO.size());
        for (ProductRateInfoDO productRateInfoDO : productRateInfoDoListDO) {
            productRateInfoList.add(toProductRateInfo(productRateInfoDO));
        }

        return productRateInfoList;
    }

}