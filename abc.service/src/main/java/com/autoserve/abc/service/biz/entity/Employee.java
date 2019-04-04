package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.UserAuthorizeFlag;

public class Employee {
    /**
     * 主键
     */
    private Integer empId;

    /**
     * 用户名
     */
    private String empName;

    /**
     * 真实姓名
     */
    private String empRealName;

    /**
     * 角色
     */
    private String empRole;

    /**
     * 手机号码
     */
    private String empMobile;

    /**
     * 状态
     */
    private Integer empState;

    /**
     * 员工工号
     */
    private String empNo;

    /**
     * 电子邮箱
     */
    private String empEmail;

    /**
     * 照片
     */
    private String empPhoto;

    /**
     * 登录次数
     */
    private Integer empLoginCount;

    /**
     * 最近一次登录时间
     */
    private Date empLastlogintime;

    /**
     * 头像
     */
    private String empHeadImg;
    
    /**
     * 是否开启授权 0 未开启 1 已开启
     */
    private UserAuthorizeFlag userAuthorizeFlag;
    /**
     * 授权流水号
     */
    private String authorizeSeqNo;
    /**
     * 缴费授权类型 11、投标 59、缴费 60、还款
     */
    private String authorizeFeeType;
    /**
     * 缴费授权有效开始日
     */
    private Date authorizeFeeStartDate;
    /**
     * 缴费授权有效结束日
     */
    private Date authorizeFeeEndDate;
    /**
     * 缴费授权金额
     */
    private BigDecimal authorizeFeeAmount;
    /**
     * 还款授权类型 11、投标 59、缴费 60、还款
     */
    private String authorizeRepayType;
    /**
     * 还款授权有效开始日
     */
    private Date authorizeRepayStartDate;
    /**
     * 还款授权有效结束日
     */
    private Date authorizeRepayEndDate;
    /**
     * 还款授权金额
     */
    private BigDecimal authorizeRepayAmount;

    public Employee() {
    }

    public Employee(Integer empId) {
        this.empId = empId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }

    public Integer getEmpLoginCount() {
        return empLoginCount;
    }

    public void setEmpLoginCount(Integer empLoginCount) {
        this.empLoginCount = empLoginCount;
    }

    public Date getEmpLastlogintime() {
        return empLastlogintime;
    }

    public void setEmpLastlogintime(Date empLastlogintime) {
        this.empLastlogintime = empLastlogintime;
    }

    public String getEmpHeadImg() {
        return empHeadImg;
    }

    public void setEmpHeadImg(String empHeadImg) {
        this.empHeadImg = empHeadImg;
    }

    public String getEmpRealName() {
        return empRealName;
    }

    public void setEmpRealName(String empRealName) {
        this.empRealName = empRealName;
    }

    public String getEmpRole() {
        return empRole;
    }

    public void setEmpRole(String empRole) {
        this.empRole = empRole;
    }

    public String getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(String empMobile) {
        this.empMobile = empMobile;
    }

    public Integer getEmpState() {
        return empState;
    }

    public void setEmpState(Integer empState) {
        this.empState = empState;
    }

	public UserAuthorizeFlag getUserAuthorizeFlag() {
		return userAuthorizeFlag;
	}

	public void setUserAuthorizeFlag(UserAuthorizeFlag userAuthorizeFlag) {
		this.userAuthorizeFlag = userAuthorizeFlag;
	}

	public String getAuthorizeSeqNo() {
		return authorizeSeqNo;
	}

	public void setAuthorizeSeqNo(String authorizeSeqNo) {
		this.authorizeSeqNo = authorizeSeqNo;
	}

	public String getAuthorizeFeeType() {
		return authorizeFeeType;
	}

	public void setAuthorizeFeeType(String authorizeFeeType) {
		this.authorizeFeeType = authorizeFeeType;
	}

	public Date getAuthorizeFeeStartDate() {
		return authorizeFeeStartDate;
	}

	public void setAuthorizeFeeStartDate(Date authorizeFeeStartDate) {
		this.authorizeFeeStartDate = authorizeFeeStartDate;
	}

	public Date getAuthorizeFeeEndDate() {
		return authorizeFeeEndDate;
	}

	public void setAuthorizeFeeEndDate(Date authorizeFeeEndDate) {
		this.authorizeFeeEndDate = authorizeFeeEndDate;
	}

	public BigDecimal getAuthorizeFeeAmount() {
		return authorizeFeeAmount;
	}

	public void setAuthorizeFeeAmount(BigDecimal authorizeFeeAmount) {
		this.authorizeFeeAmount = authorizeFeeAmount;
	}

	public String getAuthorizeRepayType() {
		return authorizeRepayType;
	}

	public void setAuthorizeRepayType(String authorizeRepayType) {
		this.authorizeRepayType = authorizeRepayType;
	}

	public Date getAuthorizeRepayStartDate() {
		return authorizeRepayStartDate;
	}

	public void setAuthorizeRepayStartDate(Date authorizeRepayStartDate) {
		this.authorizeRepayStartDate = authorizeRepayStartDate;
	}

	public Date getAuthorizeRepayEndDate() {
		return authorizeRepayEndDate;
	}

	public void setAuthorizeRepayEndDate(Date authorizeRepayEndDate) {
		this.authorizeRepayEndDate = authorizeRepayEndDate;
	}

	public BigDecimal getAuthorizeRepayAmount() {
		return authorizeRepayAmount;
	}

	public void setAuthorizeRepayAmount(BigDecimal authorizeRepayAmount) {
		this.authorizeRepayAmount = authorizeRepayAmount;
	}
}
