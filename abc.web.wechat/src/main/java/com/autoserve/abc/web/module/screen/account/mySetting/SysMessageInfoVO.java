package com.autoserve.abc.web.module.screen.account.mySetting;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SysMessageInfoVO
{
	 /**
     * 
     * abc_message_info.sys_message_id
     */
    private Integer sysMessageId;

    /**
     * 留言标题
     * abc_message_info.sys_message_title
     */
    private String sysMessageTitle;

    /**
     * 留言人
     * abc_message_info.sys_user_id
     */
    private Integer sysUserId;

    /**
     * 留言人类型（1：网友用户 4：平台用户	）
     * abc_message_info.sys_user_type
     */
    private String sysUserType;

    /**
     * 发送人
     * abc_message_info.sys_to_user
     */
    private Integer sysToUser;
    /**
     * 
     */
    private String sysToUserName;

    /**
     * 发送人类型（1：网友用户 4：平台用户	）
     * abc_message_info.sys_to_user_type
     */
    private String sysToUserType;

    /**
     * 信息状态（0：未回复 1：已回复）
     * abc_message_info.sys_message_state
     */
    private String sysMessageState;

    /**
     * 是否删除
     * abc_message_info.sys_del_sign
     */
    private String sysDelSign;

    /**
     * 留言内容
     * abc_message_info.sys_message_content
     */
    private String sysMessageContent;
    /**
     * 留言日期
     * abc_message_info.sys_message_content
     */
    private String sysMessageDate;
    /**
     * 已读标记
     * abc_message_info.sys_read
     */
    private String sysRead;
    public String getSysRead() {
		return sysRead;
	}

	public void setSysRead(String sysRead) {
		this.sysRead = sysRead;
	}

	/**
     * @return abc_message_info.sys_message_id
     */
    public Integer getSysMessageId() {
        return sysMessageId;
    }

    /**
     * @param Integer sysMessageId (abc_message_info.sys_message_id )
     */
    public void setSysMessageId(Integer sysMessageId) {
        this.sysMessageId = sysMessageId;
    }

    /**
     * @return abc_message_info.sys_message_title
     */
    public String getSysMessageTitle() {
        return sysMessageTitle;
    }

    /**
     * @param String sysMessageTitle (abc_message_info.sys_message_title )
     */
    public void setSysMessageTitle(String sysMessageTitle) {
        this.sysMessageTitle = sysMessageTitle == null ? null : sysMessageTitle.trim();
    }

    /**
     * @return abc_message_info.sys_user_id
     */
    public Integer getSysUserId() {
        return sysUserId;
    }

    /**
     * @param Integer sysUserId (abc_message_info.sys_user_id )
     */
    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    /**
     * @return abc_message_info.sys_user_type
     */
    public String getSysUserType() {
        return sysUserType;
    }

    /**
     * @param String sysUserType (abc_message_info.sys_user_type )
     */
    public void setSysUserType(String sysUserType) {
        this.sysUserType = sysUserType == null ? null : sysUserType.trim();
    }

    /**
     * @return abc_message_info.sys_to_user
     */
    public Integer getSysToUser() {
        return sysToUser;
    }

    /**
     * @param Integer sysToUser (abc_message_info.sys_to_user )
     */
    public void setSysToUser(Integer sysToUser) {
        this.sysToUser = sysToUser;
    }

    /**
     * @return abc_message_info.sys_to_user_type
     */
    public String getSysToUserType() {
        return sysToUserType;
    }

    /**
     * @param String sysToUserType (abc_message_info.sys_to_user_type )
     */
    public void setSysToUserType(String sysToUserType) {
        this.sysToUserType = sysToUserType == null ? null : sysToUserType.trim();
    }

    /**
     * @return abc_message_info.sys_message_state
     */
    public String getSysMessageState() {
        return sysMessageState;
    }

    /**
     * @param String sysMessageState (abc_message_info.sys_message_state )
     */
    public void setSysMessageState(String sysMessageState) {
        this.sysMessageState = sysMessageState == null ? null : sysMessageState.trim();
    }

    /**
     * @return abc_message_info.sys_del_sign
     */
    public String getSysDelSign() {
        return sysDelSign;
    }

    /**
     * @param String sysDelSign (abc_message_info.sys_del_sign )
     */
    public void setSysDelSign(String sysDelSign) {
        this.sysDelSign = sysDelSign == null ? null : sysDelSign.trim();
    }

    /**
     * @return abc_message_info.sys_message_content
     */
    public String getSysMessageContent() {
        return sysMessageContent;
    }

    /**
     * @param String sysMessageContent (abc_message_info.sys_message_content )
     */
    public void setSysMessageContent(String sysMessageContent) {
        this.sysMessageContent = sysMessageContent == null ? null : sysMessageContent.trim();
    }

	public String getSysMessageDate() {
		
		return sysMessageDate;
	}

	public void setSysMessageDate(Date sysMessageDate) {
		this.sysMessageDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sysMessageDate);
	}

	public String getSysToUserName()
	{
		return sysToUserName;
	}

	public void setSysToUserName(String sysToUserName)
	{
		this.sysToUserName = sysToUserName;
	}
	
}
