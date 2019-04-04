package com.autoserve.abc.web.vo.sysset;

public class SysVO {

    private String sys_is_stop;

    private String sys_stop_tip;

    private String sys_min_credit;

    private String sys_over_rate;
    
    /**
     *  修改目的：新增违约罚息率
	 *  修改内容：新增以下1行代码
	 *  修改人/时间：夏同同/2016-04-08 15:25:00
     */
    private String sys_breach_rate;
    
    private String sys_repay_limit;

    private String sys_transfer_count;

    private String sys_min_transfer_rate;

    private String sys_max_transfer_rate;

    private String sys_transfer_rate;

    private String sys_transfer_cycle;

    private String sys_transfer_type;

    private String sys_borrow_count;

    private String invest_red_vakidity;
    
    private String expire_remind;
    
    private String monthfree_tocash_times;
    
    private String wait_pay_capital;
    
    private String login_count_limit;
    
    private String login_time_limit;
    
    private String before_pay_day;
    
    private String withdraw_rate;
    
    private String querybalance_synch_account;

    private String ahead_hand_fee;
    
    private String ahead_hand_fee_rebate;
    
    public String getBefore_pay_day() {
		return before_pay_day;
	}

	public void setBefore_pay_day(String before_pay_day) {
		this.before_pay_day = before_pay_day;
	}

	public String getLogin_count_limit() {
		return login_count_limit;
	}

	public void setLogin_count_limit(String login_count_limit) {
		this.login_count_limit = login_count_limit;
	}

	public String getLogin_time_limit() {
		return login_time_limit;
	}

	public void setLogin_time_limit(String login_time_limit) {
		this.login_time_limit = login_time_limit;
	}

	public String getSys_is_stop() {
        return sys_is_stop;
    }

    public void setSys_is_stop(String sys_is_stop) {
        this.sys_is_stop = sys_is_stop;
    }

    public String getSys_stop_tip() {
        return sys_stop_tip;
    }

    public void setSys_stop_tip(String sys_stop_tip) {
        this.sys_stop_tip = sys_stop_tip;
    }

    public String getSys_min_credit() {
        return sys_min_credit;
    }

    public void setSys_min_credit(String sys_min_credit) {
        this.sys_min_credit = sys_min_credit;
    }

    public String getSys_over_rate() {
        return sys_over_rate;
    }

    public void setSys_over_rate(String sys_over_rate) {
        this.sys_over_rate = sys_over_rate;
    }

    public String getSys_breach_rate() {
		return sys_breach_rate;
	}

	public void setSys_breach_rate(String sys_breach_rate) {
		this.sys_breach_rate = sys_breach_rate;
	}

	public String getSys_repay_limit() {
        return sys_repay_limit;
    }

    public void setSys_repay_limit(String sys_repay_limit) {
        this.sys_repay_limit = sys_repay_limit;
    }

    public String getSys_transfer_count() {
        return sys_transfer_count;
    }

    public void setSys_transfer_count(String sys_transfer_count) {
        this.sys_transfer_count = sys_transfer_count;
    }

    public String getSys_min_transfer_rate() {
        return sys_min_transfer_rate;
    }

    public void setSys_min_transfer_rate(String sys_min_transfer_rate) {
        this.sys_min_transfer_rate = sys_min_transfer_rate;
    }

    public String getSys_transfer_rate() {
        return sys_transfer_rate;
    }

    public void setSys_transfer_rate(String sys_transfer_rate) {
        this.sys_transfer_rate = sys_transfer_rate;
    }

    public String getSys_transfer_cycle() {
        return sys_transfer_cycle;
    }

    public void setSys_transfer_cycle(String sys_transfer_cycle) {
        this.sys_transfer_cycle = sys_transfer_cycle;
    }

    public String getSys_transfer_type() {
        return sys_transfer_type;
    }

    public void setSys_transfer_type(String sys_transfer_type) {
        this.sys_transfer_type = sys_transfer_type;
    }

    public String getSys_borrow_count() {
        return sys_borrow_count;
    }

    public void setSys_borrow_count(String sys_borrow_count) {
        this.sys_borrow_count = sys_borrow_count;
    }

    public String getSys_max_transfer_rate() {
        return sys_max_transfer_rate;
    }

    public void setSys_max_transfer_rate(String sys_max_transfer_rate) {
        this.sys_max_transfer_rate = sys_max_transfer_rate;
    }

    public String getInvest_red_vakidity() {
        return invest_red_vakidity;
    }

    public void setInvest_red_vakidity(String invest_red_vakidity) {
        this.invest_red_vakidity = invest_red_vakidity;
    }

	public String getExpire_remind() {
		return expire_remind;
	}

	public void setExpire_remind(String expire_remind) {
		this.expire_remind = expire_remind;
	}

	public String getMonthfree_tocash_times() {
		return monthfree_tocash_times;
	}

	public void setMonthfree_tocash_times(String monthfree_tocash_times) {
		this.monthfree_tocash_times = monthfree_tocash_times;
	}

	public String getWait_pay_capital() {
		return wait_pay_capital;
	}

	public void setWait_pay_capital(String wait_pay_capital) {
		this.wait_pay_capital = wait_pay_capital;
	}

	public String getWithdraw_rate() {
		return withdraw_rate;
	}

	public void setWithdraw_rate(String withdraw_rate) {
		this.withdraw_rate = withdraw_rate;
	}

	public String getQuerybalance_synch_account() {
		return querybalance_synch_account;
	}

	public void setQuerybalance_synch_account(String querybalance_synch_account) {
		this.querybalance_synch_account = querybalance_synch_account;
	}

	public String getAhead_hand_fee() {
		return ahead_hand_fee;
	}

	public void setAhead_hand_fee(String ahead_hand_fee) {
		this.ahead_hand_fee = ahead_hand_fee;
	}

	public String getAhead_hand_fee_rebate() {
		return ahead_hand_fee_rebate;
	}

	public void setAhead_hand_fee_rebate(String ahead_hand_fee_rebate) {
		this.ahead_hand_fee_rebate = ahead_hand_fee_rebate;
	}
    

}
