package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ProductRateDO;
import com.autoserve.abc.dao.dataobject.ProductRateInfoDO;

/**
 * 类ProductRateDao.java的实现描述：产品利率表和产品联合查询DAO
 * 
 * @author wei.huimin 2014年12月18日 下午6:17:31
 */
public interface ProductRateDao extends BaseDao<ProductRateDO, Integer> {
    public List<ProductRateDO> findListByParam(@Param("productRate") ProductRateDO productRateDO,
                                               @Param("pageCondition") PageCondition pageCondition);

    public List<ProductRateDO> findList(@Param("pageCondition") PageCondition pageCondition);

    public int findAllCount();

    public int findCountByParam(@Param("productRateInfo") ProductRateInfoDO productRateInfo);

    public int findRateCountByParam(@Param("productRate") ProductRateDO productRateDO);

    public int deleteByProductId(Integer productId);

    public List<ProductRateInfoDO> findProductAllInfoByProductId(Integer productId);

    public List<ProductRateInfoDO> findProductAllInfo(@Param("pageCondition") PageCondition pageCondition);

    public Integer insertProduct(ProductRateInfoDO productRateInfoDO);

    public List<ProductRateInfoDO> findProductRateInfoListByParam(@Param("productRateInfo") ProductRateInfoDO productRateInfo,
                                                                  @Param("pageCondition") PageCondition pageCondition);

}
