package com.autoserve.abc.dao.intf;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.OnlineDO;

public interface OnlineDao extends BaseDao<OnlineDO, Integer> {

	public List<OnlineDO> findByUserIdAndDayDate(@Param("userId") Integer userId, @Param("dayDate") Date dayDate);
	
	public Integer updateByUserIdAndDayDate(OnlineDO onlineDO);
}
