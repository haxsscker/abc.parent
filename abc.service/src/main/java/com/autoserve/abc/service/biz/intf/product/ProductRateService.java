package com.autoserve.abc.service.biz.intf.product;

import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.ProductRate;
import com.autoserve.abc.service.biz.entity.ProductRateInfo;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 
 * 类ProductRateService.java的实现描述：产品Service 
 * @author weihuimin 2014年12月18日 下午6:19:30
 */
public interface ProductRateService {

    public PlainResult<ProductRate> queryById(int productId);

    public int findAllCount();
    public int findCountByParam(ProductRateInfo productRateInfo);
    public PageResult<ProductRateInfo> findProductAllInfo(PageCondition pageCondition);
    public ListResult<ProductRateInfo> queryByProductId(int productId) ;

    public PageResult<ProductRate> findListByParam(ProductRate productRate, PageCondition pageCondition);
    public int findRateCountByParam(ProductRate product);
    public PageResult<ProductRateInfo> findProductRateInfoListByParam(ProductRateInfo productRateInfo, PageCondition pageCondition);

    public BaseResult insertProduct(ProductRateInfo mainPojo, List<ProductRateInfo> pojo);
    public BaseResult insertRateUpdateInfo(ProductRateInfo mainPojo, List<ProductRateInfo> pojo);

    public BaseResult delete(Integer productRateId);

    public BaseResult deleteByProductId(Integer productId);

}
