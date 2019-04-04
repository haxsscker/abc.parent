/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon Jan 19 18:16:33 CST 2015
 * Description:
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysMessageReplyDO;

public interface SysMessageReplyDao  extends BaseDao<SysMessageReplyDO, Integer>{
    /**
     * deleteByPrimaryKey
     * @param Integer sysReplyId
     * @return int 
     */
    int deleteByPrimaryKey(Integer sysReplyId);

    /**
     * insert
     * @param SysMessageReplyDO record
     * @return int 
     */
    @Override
	Integer insert(SysMessageReplyDO record);

    /**
     * insertSelective
     * @param SysMessageReplyDO record
     * @return int 
     */
    int insertSelective(SysMessageReplyDO record);

    /**
     * selectByPrimaryKey
     * @param Integer sysReplyId
     * @return SysMessageReplyDO 
     */
    SysMessageReplyDO selectByPrimaryKey(Integer sysReplyId);

    /**
     * updateByPrimaryKeySelective
     * @param SysMessageReplyDO record
     * @return int 
     */
    int updateByPrimaryKeySelective(SysMessageReplyDO record);

    /**
     * updateByPrimaryKeyWithBLOBs
     * @param SysMessageReplyDO record
     * @return int 
     */
    int updateByPrimaryKeyWithBLOBs(SysMessageReplyDO record);

    /**
     * updateByPrimaryKey
     * @param SysMessageReplyDO record
     * @return int 
     */
    int updateByPrimaryKey(SysMessageReplyDO record);
    

	/**
	 * selectByMessage
	 * @param messageId
	 * @param page
	 * @return
	 */
    List<SysMessageReplyDO> selectByMessageId(@Param("messageId")Integer messageId,@Param("pageCondition")PageCondition page);

	/**
	 * countByMessageId
	 * @param messageId
	 * @param page
	 * @return
	 */
    int countByMessageId(@Param("messageId")Integer messageId,@Param("pageCondition")PageCondition page);
    

	/**
	 * selectByUserId
	 * @param messageId
	 * @param page
	 * @return
	 */
    List<SysMessageReplyDO> selectByUserId(@Param("userId")Integer userId,@Param("pageCondition")PageCondition page);
    
	/**
	 * selectByMessage
	 * @param messageId
	 * @param page
	 * @return
	 */
    int countByUserId(@Param("userId")Integer userId,@Param("pageCondition")PageCondition page);
    
    /**
     * 查询用户是否被标记为删除
     * @param user
     * @param messageId
     * @return
     */
    int queryUserState(@Param("messageId")Integer messageId);
}