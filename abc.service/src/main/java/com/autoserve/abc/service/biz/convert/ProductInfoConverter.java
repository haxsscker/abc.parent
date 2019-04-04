package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;
import com.autoserve.abc.dao.dataobject.ProductInfoDO;
import com.autoserve.abc.service.biz.entity.ProductInfo;

/**
 * ProductInfo与ProductInfoDO互相转换
 *
 * @author weihuimin 2014年12月11日 
 */
public class ProductInfoConverter {

    public static ProductInfoDO toProductInfoDO(ProductInfo productInfo) {
        if (productInfo == null) {
            return null;
        }

        ProductInfoDO productInfoDo = new ProductInfoDO();

        
        productInfoDo.setProductId(productInfo.getProductId());
        productInfoDo.setProductName(productInfo.getProductName());
        productInfoDo.setProductMark(productInfo.getProductMark());

        return productInfoDo;
    }

    public static ProductInfo toProductInfo(ProductInfoDO productInfoDo) {
        if (productInfoDo == null) {
            return null;
        }

        ProductInfo productInfo = new ProductInfo();

        productInfo.setProductId(productInfoDo.getProductId());
        productInfo.setProductName(productInfoDo.getProductName());
        productInfo.setProductMark(productInfoDo.getProductMark());
        
        return productInfo;
    }

    public static List<ProductInfo> toProductInfoList(List<ProductInfoDO> productInfoDoList) {
        if (productInfoDoList == null) {
            return null;
        }

        List<ProductInfo> productInfoList = new ArrayList<ProductInfo>(productInfoDoList.size());
        for (ProductInfoDO productInfoDo : productInfoDoList) {
            productInfoList.add(toProductInfo(productInfoDo));
        }

        return productInfoList;
    }

}
