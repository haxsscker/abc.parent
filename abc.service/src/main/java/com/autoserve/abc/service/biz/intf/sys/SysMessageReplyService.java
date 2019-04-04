package com.autoserve.abc.service.biz.intf.sys;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysMessageReplyDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

public interface SysMessageReplyService {
	
	/**
	 * 根据留言ID查询回复
	 * @param userId
	 * @return
	 */
	public  PageResult<SysMessageReplyDO> queryByMessageId(int messageId,PageCondition pageCondition);
	
	/**
	 * 根据用户Id进行
	 * @param userId
	 * @return
	 */
	public PageResult<SysMessageReplyDO> queryByUserId(int userId,PageCondition pageCondition);
	
	/**
	 * 创建站内信息回复
	 * @param sysMessageReplyDO
	 * @return
	 */
	public BaseResult createMessage(SysMessageReplyDO sysMessageReplyDO);
	
    /**
     * 根据留言ID查询该条留言的所有回复
     * 
     * @param messageid
     * @param page
     * @return
     */
    public PageResult<SysMessageReplyDO> queryAllReplyByMesageid(Integer messageid, PageCondition page);
}
