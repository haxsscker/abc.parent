package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * 类UserInvestInfoReport.java的实现描述：注册用户投资、借款明细表
 * 
 * @author WangMing 2015年6月5日 下午1:57:03
 */
public class UserInvestInfoReportDO {

    /**
     * 注册用户名
     */
    private String     user_name;
    /**
     * 姓名
     */
    private String     user_real_name;
    /**
     * 身份证号
     */
    private String     user_doc_no;
    /**
     * 手机号
     */
    private String     user_phone;
    /**
     * 邮箱
     */
    private String     user_email;
    /**
     * 注册时间
     */
    private String       user_register_date;
    /**
     * 推荐人用户名
     */
    private String tj_user_name;
    /**
     * 推荐人姓名
     */
    private String     tj_user_real_name;
    /**
     * 推荐人手机
     */
    private String     tj_user_phone;
    /**
     * 投资合计
     */
    private BigDecimal total_invest_money;
    /**
     * 已收本金
     */
    private BigDecimal income_capital;
    /**
     * 已收利息
     */
    private BigDecimal income_interest;
    /**
     * 侍收本金
     */
    private BigDecimal income_ds_capital;
    /**
     * 侍收利息
     */
    private BigDecimal income_ds_interest;
    /**
     * 借款合计
     */
    private BigDecimal total_valid_invest;
    /**
     * 已还本金
     */
    private BigDecimal pay_capital;
    /**
     * 已还利息
     */
    private BigDecimal pay_interest;
    /**
     * 侍还本金
     */
    private BigDecimal pay_dh_capital;
    /**
     * 侍还利息
     */
    private BigDecimal pay_dh_interest;
    /**
     * 联系地址
     */
    private String     address;
    /**
     * 邮编
     */
    private String post;
    /**
     * 家庭电话
     */
    private String homePhone;
    /**
     * 城市
     */
    private String city;

    public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getTj_user_name() {
		return tj_user_name;
	}

	public void setTj_user_name(String tj_user_name) {
		this.tj_user_name = tj_user_name;
	}

	public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_real_name() {
        return user_real_name;
    }

    public void setUser_real_name(String user_real_name) {
        this.user_real_name = user_real_name;
    }

    public String getUser_doc_no() {
        return user_doc_no;
    }

    public void setUser_doc_no(String user_doc_no) {
        this.user_doc_no = user_doc_no;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }


    public String getUser_register_date() {
		return user_register_date;
	}

	public void setUser_register_date(String user_register_date) {
		this.user_register_date = user_register_date;
	}

	public String getTj_user_real_name() {
        return tj_user_real_name;
    }

    public void setTj_user_real_name(String tj_user_real_name) {
        this.tj_user_real_name = tj_user_real_name;
    }

    public String getTj_user_phone() {
        return tj_user_phone;
    }

    public void setTj_user_phone(String tj_user_phone) {
        this.tj_user_phone = tj_user_phone;
    }

    public BigDecimal getTotal_invest_money() {
        return total_invest_money;
    }

    public void setTotal_invest_money(BigDecimal total_invest_money) {
        this.total_invest_money = total_invest_money;
    }

    public BigDecimal getIncome_capital() {
        return income_capital;
    }

    public void setIncome_capital(BigDecimal income_capital) {
        this.income_capital = income_capital;
    }

    public BigDecimal getIncome_interest() {
        return income_interest;
    }

    public void setIncome_interest(BigDecimal income_interest) {
        this.income_interest = income_interest;
    }

    public BigDecimal getIncome_ds_capital() {
        return income_ds_capital;
    }

    public void setIncome_ds_capital(BigDecimal income_ds_capital) {
        this.income_ds_capital = income_ds_capital;
    }

    public BigDecimal getIncome_ds_interest() {
        return income_ds_interest;
    }

    public void setIncome_ds_interest(BigDecimal income_ds_interest) {
        this.income_ds_interest = income_ds_interest;
    }

    public BigDecimal getTotal_valid_invest() {
        return total_valid_invest;
    }

    public void setTotal_valid_invest(BigDecimal total_valid_invest) {
        this.total_valid_invest = total_valid_invest;
    }

    public BigDecimal getPay_capital() {
        return pay_capital;
    }

    public void setPay_capital(BigDecimal pay_capital) {
        this.pay_capital = pay_capital;
    }

    public BigDecimal getPay_interest() {
        return pay_interest;
    }

    public void setPay_interest(BigDecimal pay_interest) {
        this.pay_interest = pay_interest;
    }

    public BigDecimal getPay_dh_capital() {
        return pay_dh_capital;
    }

    public void setPay_dh_capital(BigDecimal pay_dh_capital) {
        this.pay_dh_capital = pay_dh_capital;
    }

    public BigDecimal getPay_dh_interest() {
        return pay_dh_interest;
    }

    public void setPay_dh_interest(BigDecimal pay_dh_interest) {
        this.pay_dh_interest = pay_dh_interest;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
