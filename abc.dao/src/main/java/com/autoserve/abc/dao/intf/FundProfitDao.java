/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Fri Dec 19 13:23:55 CST 2014
 * Description:
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.FundProfitDO;


public interface FundProfitDao extends BaseDao<FundProfitDO, Integer> {
	/**
	 * 按条件查询结果
	 * @param fundProfitDO
	 * @return
	 */
	List<FundProfitDO> queryListByParam(@Param("fp") FundProfitDO fundProfitDO);
}