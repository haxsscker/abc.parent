package com.autoserve.abc.dao.intf;

import java.util.List;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.WxTokenDO;

public interface WxTokenDao extends BaseDao<WxTokenDO, Integer> {
	
	/**
     * 查询平台绑定token信息
     * @param id 用户id
     * @return UserSpouseDO
     */
    public WxTokenDO findById(int id);
    
  
    /**
     * 更新信息
     * @param id 用户id
     * @return UserSpouseDO
     */
    @Override
	public int update(WxTokenDO wxTokenDO);
    /**
     * 查询平台绑定token信息
     * @param id 用户id
     * @return UserSpouseDO
     */
    public List<WxTokenDO> find();
}
