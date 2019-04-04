package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 免费提现额度申请表
 * 
 * @author RJQ 2014/11/13 18:15.
 */
public class CashSqDO {
	
//	CREATE TABLE `abc_cash_sq` (
//			  `user_id` int(11) NOT NULL COMMENT '用户id',
//			  `sqtime` datetime DEFAULT NULL COMMENT '申请时间',
//			  `user_cash_quota_sqadd` decimal(10,0) DEFAULT NULL COMMENT '增加免费提现额度',
//			  `shyj` varchar(255) DEFAULT NULL COMMENT '审核意见',
//			  `user_cash_quota_shadd` decimal(10,0) DEFAULT NULL COMMENT '审核通过的免费提现额度',
//			  `state` int(1) DEFAULT NULL COMMENT '状态1 待审核  2 通过 3不通过',
//			  PRIMARY KEY (`user_id`)
//			) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	
	
	
	
    /**
     * 主键 abc_cash_sq.user_id
     */
	
    private Integer    Id;
	
	
	
    private Integer    userId;

    
	
    /**
     * 申请时间   
     */
    private Date    sqtime;
    /**
     * 申请时间   str
     */
    private String    sqtimestr;
    
	
    /**
     * 申请额度
     */
    private BigDecimal    userCashQuotaSqadd;
	
    /**
     * 审核意见
     */
    private String    shyj;
	
    /**
     * 通过额度
     */
    private BigDecimal    userCashQuotaShadd;

    /**
     * 状态
     */
    private Integer    state;

    
    
    
	public String getSqtimestr() {
		return sqtimestr;
	}

	public void setSqtimestr(String sqtimestr) {
		this.sqtimestr = sqtimestr;
	}



 
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getSqtime() {
		return sqtime;
	}

	public void setSqtime(Date sqtime) {
		this.sqtime = sqtime;
	}

	public BigDecimal getUserCashQuotaSqadd() {
		return userCashQuotaSqadd;
	}

	public void setUserCashQuotaSqadd(BigDecimal userCashQuotaSqadd) {
		this.userCashQuotaSqadd = userCashQuotaSqadd;
	}

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public BigDecimal getUserCashQuotaShadd() {
		return userCashQuotaShadd;
	}

	public void setUserCashQuotaShadd(BigDecimal userCashQuotaShadd) {
		this.userCashQuotaShadd = userCashQuotaShadd;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
    
    
    
    
}
