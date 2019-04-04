package com.autoserve.abc.service.biz.intf.product;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ProductInfoDO;
import com.autoserve.abc.service.biz.entity.ProductInfo;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 
 * 类ProductInfoService.java的实现描述：产品Service
 * @author Administrator 2014年12月18日 下午6:19:12
 */
public interface ProductInfoService {

	public PlainResult<ProductInfo> queryById(int productId);

	public BaseResult findAllCount(ProductInfoDO productInfoDO, PageCondition pageCondition);

	public PageResult<ProductInfoDO> findList(PageCondition pageCondition);

	public PageResult<ProductInfo> findListByParam(ProductInfoDO productInfoDO,
			PageCondition pageCondition);

	public BaseResult insert(ProductInfo pojo);
	public BaseResult delete(ProductInfo pojo);
}
