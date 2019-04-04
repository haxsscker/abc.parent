/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon Jan 19 15:36:36 CST 2015
 * Description:
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;

public interface MessageInfoDao extends BaseDao<SysMessageInfoDO, Integer>{
	/**
	 * deleteByPrimaryKey
	 * 
	 * @param Integer
	 *            sysMessageId
	 * @return int
	 */
	int deleteByPrimaryKey(Integer sysMessageId);

	/**
	 * insert
	 * 
	 * @param SysMessageInfoDO
	 *            record
	 * @return int
	 */
	@Override
	Integer insert(SysMessageInfoDO record);

	/**
	 * insertSelective
	 * 
	 * @param SysMessageInfoDO
	 *            record
	 * @return int
	 */
	int insertSelective(SysMessageInfoDO record);

	/**
	 * selectByPrimaryKey
	 * 
	 * @param Integer
	 *            sysMessageId
	 * @return SysMessageInfoDO
	 */
	SysMessageInfoDO selectByPrimaryKey(Integer sysMessageId);

	/**
	 * updateByPrimaryKeySelective
	 * 
	 * @param SysMessageInfoDO
	 *            record
	 * @return int
	 */
	int updateByPrimaryKeySelective(SysMessageInfoDO record);

	/**
	 * updateByPrimaryKeyWithBLOBs
	 * 
	 * @param SysMessageInfoDO
	 *            record
	 * @return int
	 */
	int updateByPrimaryKeyWithBLOBs(SysMessageInfoDO record);

	/**
	 * updateByPrimaryKey
	 * 
	 * @param SysMessageInfoDO
	 *            record
	 * @return int
	 */
	int updateByPrimaryKey(SysMessageInfoDO record);

	/**
	 * selectByUserId
	 * 
	 * @param Integer
	 *            userId
	 * @return SysMessageInfoDO
	 */
	List<SysMessageInfoDO> selectByUserId(@Param("userId") Integer userId,
			@Param("pageCondition") PageCondition pageCondition);

	/**
	 * selectByUserId
	 * 
	 * @param Integer
	 *            userId
	 * @return SysMessageInfoDO
	 */
	int countByUserId(@Param("userId") Integer userId);
	
    /**
     * @param userId
     * @return int
     */
    int countNotReadByUserId(@Param("userId") Integer userId);
    
	/**
	 * selectByUserId
	 * 
	 * @param Integer
	 *            messageId
	 * @return SysMessageInfoDO
	 */
	int deleteById(Integer messageId);

	/**
	 * selectByToUserId
	 * 
	 * @param Integer
	 *            userId
	 * @return SysMessageInfoDO
	 */
	List<SysMessageInfoDO> selectByToUserId(@Param("userId") Integer userId,
			@Param("pageCondition") PageCondition pageCondition);

	/**
	 * selectByToUserId
	 * 
	 * @param Integer
	 *            userId
	 * @return SysMessageInfoDO
	 */
	int countByToUserId(@Param("userId") Integer userId);
	
    /**
     * 根据SysMessageInfoDO对象查询站内信息
     * 
     * @param record
     * @param pageCondition
     * @return
     */
    List<SysMessageInfoDO> queryMessageByDo(@Param("message") SysMessageInfoDO message,
                                            @Param("pageCondition") PageCondition pageCondition);

    /**
     * 根据SysMessageInfoDO对象查询满足条件的站内信息总数
     * 
     * @param record
     * @param pageCondition
     * @return
     */
    int countMessageByDo(@Param("message") SysMessageInfoDO message, @Param("pageCondition") PageCondition pageCondition);
}