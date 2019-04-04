/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Mon Jan 19 18:16:33 CST 2015
 * Description:
 */
package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 *  SysMessageReplyDO
 *  abc_message_reply
 */
public class SysMessageReplyDO {
    /**
     * 
     * abc_message_reply.sys_reply_id
     */
    private Integer sysReplyId;

    /**
     * 外键表：SYS_messag_info 留言ID
     * abc_message_reply.sys_message_id
     */
    private Integer sysMessageId;

    /**
     * 外键表：CST_user_info 留言人
     * abc_message_reply.sys_user_id
     */
    private Integer sysUserId;

    /**
     * 留言人类型
     * abc_message_reply.sys_user_type
     */
    private String sysUserType;

    /**
     * 回复日期
     * abc_message_reply.sys_reply_date
     */
    private Date sysReplyDate;

    /**
     * 回复内容
     * abc_message_reply.sys_reply_content
     */
    private String sysReplyContent;
    /**
     * 留言人姓名
     */
    private String  sysUserName;
    
    /**
     * @return abc_message_reply.sys_reply_id
     */
    public Integer getSysReplyId() {
        return sysReplyId;
    }

    /**
     * @param Integer sysReplyId (abc_message_reply.sys_reply_id )
     */
    public void setSysReplyId(Integer sysReplyId) {
        this.sysReplyId = sysReplyId;
    }

    /**
     * @return abc_message_reply.sys_message_id
     */
    public Integer getSysMessageId() {
        return sysMessageId;
    }

    /**
     * @param Integer sysMessageId (abc_message_reply.sys_message_id )
     */
    public void setSysMessageId(Integer sysMessageId) {
        this.sysMessageId = sysMessageId;
    }

    /**
     * @return abc_message_reply.sys_user_id
     */
    public Integer getSysUserId() {
        return sysUserId;
    }

    /**
     * @param Integer sysUserId (abc_message_reply.sys_user_id )
     */
    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    /**
     * @return abc_message_reply.sys_user_type
     */
    public String getSysUserType() {
        return sysUserType;
    }

    /**
     * @param String sysUserType (abc_message_reply.sys_user_type )
     */
    public void setSysUserType(String sysUserType) {
        this.sysUserType = sysUserType == null ? null : sysUserType.trim();
    }

    /**
     * @return abc_message_reply.sys_reply_date
     */
    public Date getSysReplyDate() {
        return sysReplyDate;
    }

    /**
     * @param Date sysReplyDate (abc_message_reply.sys_reply_date )
     */
    public void setSysReplyDate(Date sysReplyDate) {
        this.sysReplyDate = sysReplyDate;
    }

    /**
     * @return abc_message_reply.sys_reply_content
     */
    public String getSysReplyContent() {
        return sysReplyContent;
    }

    /**
     * @param String sysReplyContent (abc_message_reply.sys_reply_content )
     */
    public void setSysReplyContent(String sysReplyContent) {
        this.sysReplyContent = sysReplyContent == null ? null : sysReplyContent.trim();
    }

	/**
	 * @return the sysUserName
	 */
	public String getSysUserName() {
		return sysUserName;
	}

	/**
	 * @param sysUserName the sysUserName to set
	 */
	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}
    
}