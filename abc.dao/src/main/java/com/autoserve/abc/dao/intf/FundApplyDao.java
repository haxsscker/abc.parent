/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Tue Dec 02 13:24:46 CST 2014
 * Description:
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.FundApplyDO;
import com.autoserve.abc.dao.dataobject.search.FundApplySearchDO;

public interface FundApplyDao extends BaseDao<FundApplyDO, Integer> {

	/**
	 * 按条件查询分页结果
	 * 
	 * @param FundOrderDO
	 *            查询条件，可选
	 * @param pageCondition
	 *            分页和排序条件，可选
	 * @return List<FundOrderDO>
	 */
	List<FundApplyDO> findListByParam(@Param("fa") FundApplyDO fundApplyDO,
			@Param("pageCondition") PageCondition pageCondition);
	
	/**
	 * 计算查询总条数
	 * @param fundOrderDO
	 * @param pageCondition
	 * @return
	 */
	public int countListByParam(@Param("fa") FundApplyDO fundApplyDO);
	
	/**
	 * 按检索框条件检索，结果分页
	 * @param fundApplySearchDO
	 * @param pageCondition
	 * @return
	 */
	List<FundApplyDO> findPageListBySearchParam(@Param("fas") FundApplySearchDO fundApplySearchDO,
			@Param("pageCondition") PageCondition pageCondition);
	
	/**
	 * 按检索框条件检索结果总条数
	 * @param fundApplySearchDO
	 * @param pageCondition
	 * @return
	 */
	public int countListBySearchParam(@Param("fas") FundApplySearchDO fundApplySearchDO);
}