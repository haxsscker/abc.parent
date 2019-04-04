package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ProductInfoDO;
/**
 * 
 * 类ProductInfoDao.java的实现描述：产品信息表
 * @author wei.huimin 2014年12月26日 上午9:47:20
 */
public interface ProductInfoDao extends BaseDao<ProductInfoDO, Integer> {
	public List<ProductInfoDO> findListByParam(@Param("productInfoDO") ProductInfoDO productInfoDO,
			@Param("pageCondition") PageCondition pageCondition);

	public List<ProductInfoDO> findList(PageCondition pageCondition);

	public int findAllCount();
}