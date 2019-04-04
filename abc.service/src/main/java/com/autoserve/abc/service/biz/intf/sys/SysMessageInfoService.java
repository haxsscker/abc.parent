package com.autoserve.abc.service.biz.intf.sys;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 站内信留言查询
 * @author liuwei
 *
 */
public interface SysMessageInfoService {

	/**
	 * 根据用户Id进行站内信留言查询
	 * @param userId
	 * @return
	 */
	public  PageResult<SysMessageInfoDO> queryByUserId(int userId,PageCondition pageCondition);
	/**
	 * 根据id进行删除
	 * @param messageId
	 * @return
	 */
	public BaseResult removeById(int messageId);
	
	/**
	 * 创建站内信息
	 * @param SysMessageInfoDO
	 * @return BaseResult
	 */
	public BaseResult createMessage(SysMessageInfoDO sysMessageInfoDO);
	
	/**
	 * 根据用户Id进行
	 * @param userId
	 * @return
	 */
	public PageResult<SysMessageInfoDO> queryBytoUserId(int userId,PageCondition pageCondition);
	
    /**
     * 对站内信进行修改
     * 
     * @param sysMessageInfoDo
     */
    public int updateMessage(SysMessageInfoDO sysMessageInfoDo);

    /**
     * 查询未读站内信数量
     * 
     * @param userId
     * @return
     */
    public int queryNotReadMessageCount(int userId);

    /**
     * 查询站内信息
     * 
     * @param message
     * @param pageCondition
     * @return
     */
    public PageResult<SysMessageInfoDO> queryMessage(SysMessageInfoDO message, PageCondition pageCondition);

    /**
     * 彻底删除站内信
     * 
     * @param messageId
     * @return
     */
    int deleteById(int messageId);

    SysMessageInfoDO queryMesageById(Integer messageid);
    
	BaseResult createMessageForRibao(SysMessageInfoDO sysMessageInfoDO);
}
