package com.autoserve.abc.service.biz.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ProductInfoDO;
import com.autoserve.abc.dao.dataobject.ProductRateDO;
import com.autoserve.abc.dao.dataobject.ProductRateInfoDO;
import com.autoserve.abc.dao.intf.ProductInfoDao;
import com.autoserve.abc.dao.intf.ProductRateDao;
import com.autoserve.abc.service.biz.convert.ProductRateConverter;
import com.autoserve.abc.service.biz.convert.ProductRateInfoConverter;
import com.autoserve.abc.service.biz.entity.ProductRate;
import com.autoserve.abc.service.biz.entity.ProductRateInfo;
import com.autoserve.abc.service.biz.intf.product.ProductRateService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author weihuimin 2014年12月11日
 */

/**
 * 类ProductRateServiceImpl.java的实现描述：产品期限利率
 *
 * @author weihuimin 2014年12月18日 下午3:02:26
 */
@Service
public class ProductRateServiceImpl implements ProductRateService {
    @Resource
    private ProductRateDao productRateDao;
    @Resource
    private ProductInfoDao productInfoDao;

    @Override
    public BaseResult insertProduct(ProductRateInfo mainPojo, List<ProductRateInfo> lstPojo) {
        BaseResult result = new BaseResult();
        ProductInfoDO productInfoDO = new ProductInfoDO();
        List<ProductRateDO> list = new ArrayList<ProductRateDO>();

        if (null != lstPojo && lstPojo.size() > 0) {
            //期限区间重复判断
            for (int a = 0; a < lstPojo.size(); a++) {
                ProductRateInfo proa = lstPojo.get(a);

                for (int b = a + 1; b < lstPojo.size(); b++) {

                    ProductRateInfo prob = lstPojo.get(b);

                    if (prob.getProductMaxPeriod() > proa.getProductMinPeriod()
                            && prob.getProductMaxPeriod() < proa.getProductMaxPeriod()) {
                        result.setErrorMessage(CommonResultCode.BIZ_ERROR, "最大区间有重复");
                        return result;
                    }
                    if (prob.getProductMinPeriod() > proa.getProductMinPeriod()
                            && prob.getProductMinPeriod() < proa.getProductMaxPeriod()) {
                        result.setErrorMessage(CommonResultCode.BIZ_ERROR, "最小区间有重复");
                        return result;
                    }

                }
            }

            productInfoDO.setProductId(mainPojo.getProductId());
            productInfoDO.setProductMark(mainPojo.getProductMark());
            productInfoDO.setProductName(mainPojo.getProductName());
            productInfoDao.insert(productInfoDO);

            for (int i = 0; i < lstPojo.size(); i++) {

                ProductRateDO productRateDO = new ProductRateDO();
                productRateDO.setProductId(productInfoDO.getProductId());
                productRateDO.setProductMaxPeriod(lstPojo.get(i).getProductMaxPeriod());
                productRateDO.setProductMinPeriod(lstPojo.get(i).getProductMinPeriod());
                productRateDO.setProductRate(lstPojo.get(i).getProductRate());
                productRateDO.setProductRateId(lstPojo.get(i).getProductRateId());
                list.add(productRateDO);

            }
            if (!CollectionUtils.isEmpty(list)) {
                productRateDao.batchInsert(list);
            }
        }
        return result;
    }

    @Override
    public BaseResult insertRateUpdateInfo(ProductRateInfo mainPojo, List<ProductRateInfo> lstPojo) {
        BaseResult result = new BaseResult();
        ProductInfoDO productInfoDO = new ProductInfoDO();
        List<ProductRateDO> list = new ArrayList<ProductRateDO>();

        if (null != lstPojo && lstPojo.size() > 0) {
            //期限区间重复判断
            for (int a = 0; a < lstPojo.size(); a++) {
                ProductRateInfo proa = lstPojo.get(a);

                for (int b = a + 1; b < lstPojo.size(); b++) {

                    ProductRateInfo prob = lstPojo.get(b);

                    if (prob.getProductMaxPeriod() > proa.getProductMinPeriod()
                            && prob.getProductMaxPeriod() < proa.getProductMaxPeriod()) {
                        result.setErrorMessage(CommonResultCode.BIZ_ERROR, "最大区间有重复");
                        return result;
                    }
                    if (prob.getProductMinPeriod() > proa.getProductMinPeriod()
                            && prob.getProductMinPeriod() < proa.getProductMaxPeriod()) {
                        result.setErrorMessage(CommonResultCode.BIZ_ERROR, "最小区间有重复");
                        return result;
                    }

                }
            }

            productInfoDO.setProductId(mainPojo.getProductId());
            productInfoDO.setProductMark(mainPojo.getProductMark());
            productInfoDO.setProductName(mainPojo.getProductName());
            productInfoDao.update(productInfoDO);

            for (int i = 0; i < lstPojo.size(); i++) {

                ProductRateDO productRateDO = new ProductRateDO();
                productRateDO.setProductId(productInfoDO.getProductId());
                productRateDO.setProductMaxPeriod(lstPojo.get(i).getProductMaxPeriod());
                productRateDO.setProductMinPeriod(lstPojo.get(i).getProductMinPeriod());
                productRateDO.setProductRate(lstPojo.get(i).getProductRate());
                productRateDO.setProductRateId(lstPojo.get(i).getProductRateId());
                list.add(productRateDO);
            }

            productRateDao.batchInsert(list);
        }
        return result;
    }

    @Override
    public int findAllCount() {
        int returnVal = productRateDao.findAllCount();
        BaseResult result = new BaseResult();
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "查询产品信息条数失败");
        }
        return returnVal;
    }

    @Override
    public int findCountByParam(ProductRateInfo productRateInfo) {
        ProductRateInfoDO productRateInfoDO = ProductRateInfoConverter.toProductRateInfoDO(productRateInfo);
        int returnVal = productRateDao.findCountByParam(productRateInfoDO);
        BaseResult result = new BaseResult();
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "查询产品信息条数失败");
        }
        return returnVal;
    }

    @Override
    public BaseResult delete(Integer productId) {
        int returnVal = productRateDao.delete(productId);
        BaseResult result = new BaseResult();
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "删除产品信息失败");
        }
        return result;
    }

    @Override
    public BaseResult deleteByProductId(Integer productId) {
        int returnVal = productRateDao.deleteByProductId(productId);
        BaseResult result = new BaseResult();
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "删除产品信息失败");
        }
        return result;
    }

    @Override
    public PlainResult<ProductRate> queryById(int producRatetId) {
        PlainResult<ProductRate> result = new PlainResult<ProductRate>();

        ProductRate productRate = ProductRateConverter.toProductRate(productRateDao.findById(producRatetId));
        if (productRate == null) {
            return result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "ProductRate");
        }

        result.setData(productRate);
        return result;
    }

    @Override
    public ListResult<ProductRateInfo> queryByProductId(int productId) {
        ListResult<ProductRateInfo> lstResult = new ListResult<ProductRateInfo>();

        List<ProductRateInfoDO> listDO = this.productRateDao.findProductAllInfoByProductId(productId);
        List<ProductRateInfo> list = ProductRateInfoConverter.toProductRateInfoList(listDO);
        if (list == null || list.size() == 0) {
            return lstResult.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "ProductRate");
        }
        lstResult.setData(list);
        lstResult.setCount(list.size());
        return lstResult;
    }

    @Override
    public PageResult<ProductRateInfo> findProductAllInfo(PageCondition pageCondition) {
        int totalCount = this.productRateDao.findAllCount();
        PageResult<ProductRateInfo> pageResult = new PageResult<ProductRateInfo>(pageCondition.getPage(),
                pageCondition.getPageSize());
        List<ProductRateInfoDO> listDO = this.productRateDao.findProductAllInfo(pageCondition);
        List<ProductRateInfo> list = ProductRateInfoConverter.toProductRateInfoList(listDO);
        pageResult.setTotalCount(totalCount);
        pageResult.setData(list);
        return pageResult;
    }

    @Override
    public PageResult<ProductRate> findListByParam(ProductRate productRate, PageCondition pageCondition) {
        PageResult<ProductRate> pageResult = new PageResult<ProductRate>(pageCondition);
        ProductRateDO productRateDO = ProductRateConverter.toProductRateDO(productRate);
        List<ProductRateDO> listDO = this.productRateDao.findListByParam(productRateDO, pageCondition);
        List<ProductRate> list = ProductRateConverter.toProductRateList(listDO);
        pageResult.setData(list);
        return pageResult;
    }

    @Override
    public PageResult<ProductRateInfo> findProductRateInfoListByParam(ProductRateInfo productRateInfo,
                                                                      PageCondition pageCondition) {
        PageResult<ProductRateInfo> pageResult = new PageResult<ProductRateInfo>(pageCondition);
        List<ProductRateInfoDO> listDO;
        if (null == productRateInfo) {
            listDO = this.productRateDao.findProductAllInfo(pageCondition);
        } else {
            ProductRateInfoDO productRateInfoDO = ProductRateInfoConverter.toProductRateInfoDO(productRateInfo);
            listDO = this.productRateDao.findProductRateInfoListByParam(productRateInfoDO, pageCondition);
        }
        List<ProductRateInfo> list = ProductRateInfoConverter.toProductRateInfoList(listDO);
        pageResult.setData(list);
        return pageResult;
    }

    @Override
    public int findRateCountByParam(ProductRate productRate) {
        ProductRateDO productRateDO = ProductRateConverter.toProductRateDO(productRate);
        return this.productRateDao.findRateCountByParam(productRateDO);
    }

}
