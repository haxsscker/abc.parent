package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AheadRepay;

/**
 * 提前还款申请
 * @author zhangkang
 *
 */
public interface AheadRepayDao extends BaseDao<AheadRepay, Integer>{
	/**
	 * 根据条件查找
	 * @param aheadRepay
	 * @return
	 */
	AheadRepay findOne(AheadRepay aheadRepay);

	List<AheadRepay> findList(@Param("ahead")AheadRepay aheadRepay, @Param("pageCondition") PageCondition pageCondition);
	
	int countList(@Param("ahead")AheadRepay aheadRepay);
}
