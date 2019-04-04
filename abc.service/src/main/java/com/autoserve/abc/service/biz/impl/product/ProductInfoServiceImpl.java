package com.autoserve.abc.service.biz.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ProductInfoDO;
import com.autoserve.abc.dao.intf.ProductInfoDao;
import com.autoserve.abc.service.biz.convert.ProductInfoConverter;
import com.autoserve.abc.service.biz.entity.ProductInfo;
import com.autoserve.abc.service.biz.intf.product.ProductInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 
 * 类ProductInfoServiceImpl.java的实现描述：产品信息
 * @author weihuimin 2014年12月11日 下午3:01:08
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Resource
	private ProductInfoDao productInfoDao;

	@Override
	public PlainResult<ProductInfo> queryById(int productId) {
		PlainResult<ProductInfo> result = new PlainResult<ProductInfo>();
		ProductInfo productInfo = ProductInfoConverter
				.toProductInfo(productInfoDao.findById(productId));
		if (productInfo == null) {
			return result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS,
					"ProductInfo");
		}

		result.setData(productInfo);
		return result;
	}

	@Override
	public BaseResult insert(ProductInfo pojo) {
		int returnVal = productInfoDao.insert(ProductInfoConverter.toProductInfoDO(pojo));
		BaseResult result = new BaseResult();
        if(returnVal <= 0){
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "增加产品信息失败");
        }
        return result;
	}

	@Override
	public BaseResult findAllCount(ProductInfoDO productInfoDO,
			PageCondition pageCondition) {
		int returnVal = productInfoDao.findAllCount();
		BaseResult result = new BaseResult();
        if(returnVal <= 0){
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "增加产品信息失败");
        }
		return result;
	}

    @Override
	public PageResult<ProductInfoDO> findList(PageCondition pageCondition) {
        PageResult<ProductInfoDO> pageResult = new PageResult<ProductInfoDO>(pageCondition);
        List<ProductInfoDO> listDO = this.productInfoDao.findList(pageCondition);
//        List<ProductInfoDO> list = new ArrayList<ProductInfo>();
//        for (ProductInfoDO productInfoDO : listDO) {
//            list.add(ProductInfoConverter.toProductInfo(productInfoDO));
//        }
        pageResult.setData(listDO);
        pageResult.setTotalCount(listDO.size());
        return pageResult;
    }

    @Override
	public PageResult<ProductInfo> findListByParam(ProductInfoDO productInfoDO,PageCondition pageCondition) {
        PageResult<ProductInfo> pageResult = new PageResult<ProductInfo>(pageCondition);
        List<ProductInfoDO> listDO = this.productInfoDao.findListByParam(productInfoDO, pageCondition);
        List<ProductInfo> list = new ArrayList<ProductInfo>();
        for (ProductInfoDO productInfoDOT : listDO) {
            list.add(ProductInfoConverter.toProductInfo(productInfoDOT));
        }
        pageResult.setData(list);
        return pageResult;
    }

	@Override
	public BaseResult delete(ProductInfo pojo) {
		Integer productId = 0;
		int returnVal = 0;
		if(null != pojo){
			productId = pojo.getProductId();
			returnVal = productInfoDao.delete(productId);
		}
		BaseResult result = new BaseResult();
        if((null == pojo) || (returnVal <= 0)){
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "增加产品信息失败");
        }
		return result;
	}

}
