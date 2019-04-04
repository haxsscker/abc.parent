package com.autoserve.abc.web.module.screen.sysset.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * @author liuwei
 */
public class SystemParametersData {
    @Resource
    private SysConfigService SysConfigService;

    public JsonBaseVO execute(ParameterParser params) {

        BaseResult baseResult = null;

        List<SysConfig> list = new ArrayList<SysConfig>();
        String sys_is_stop = params.getString("sys_is_stop");
        SysConfig is_stop = new SysConfig();
        is_stop.setConf(SysConfigEntry.SHUTDOWN_SITE);
        is_stop.setConfValue(sys_is_stop);
        list.add(is_stop);

        String sys_stop_tip = params.getString("sys_stop_tip");
        SysConfig stop_tip = new SysConfig();
        stop_tip.setConf(SysConfigEntry.SHUTDOWN_INFO);
        stop_tip.setConfValue(sys_stop_tip);
        list.add(stop_tip);

        String sys_min_credit = params.getString("sys_min_credit");
        SysConfig min_credit = new SysConfig();
        min_credit.setConf(SysConfigEntry.MIN_CREDIT_LIMIT);
        min_credit.setConfValue(sys_min_credit);
        list.add(min_credit);

        String sys_over_rate = params.getString("sys_over_rate");
        SysConfig over_rate = new SysConfig();
        over_rate.setConf(SysConfigEntry.PUNISH_INTEREST_RATE);
        over_rate.setConfValue(sys_over_rate);
        list.add(over_rate);
        
        /**
         *  修改目的：新增违约罚息率
    	 *  修改内容：新增以下5行代码,新增违约罚息率
    	 *  修改人/时间：夏同同/2016-04-08 15:03:00
         */
        String sys_breach_rate = params.getString("sys_breach_rate");
        SysConfig breach_rate = new SysConfig();
        breach_rate.setConf(SysConfigEntry.PUNISH_BREACH_INTEREST_RATE);
        breach_rate.setConfValue(sys_breach_rate);
        list.add(breach_rate);

        String sys_repay_limit = params.getString("sys_repay_limit");
        SysConfig repay_limit = new SysConfig();
        repay_limit.setConf(SysConfigEntry.CONTINUE_DESIGNATED_PAY);
        repay_limit.setConfValue(sys_repay_limit);
        list.add(repay_limit);

        String sys_transfer_count = params.getString("sys_transfer_count");
        if(sys_transfer_count==null || "".equals(sys_transfer_count)){
        	sys_transfer_count="-1";
        }
        SysConfig transfer_count = new SysConfig();
        transfer_count.setConf(SysConfigEntry.LOAN_TRANSFER_LIMIT);
        transfer_count.setConfValue(sys_transfer_count);
        list.add(transfer_count);

        String min_transfer_rate = params.getString("sys_min_transfer_rate");
        SysConfig mintransfer_rate = new SysConfig();
        mintransfer_rate.setConf(SysConfigEntry.LOAN_TRANSFER_DISCONT_MIN);
        mintransfer_rate.setConfValue(min_transfer_rate);
        list.add(mintransfer_rate);

        String sys_max_transfer_rate = params.getString("sys_max_transfer_rate");
        SysConfig max_transfer_rate = new SysConfig();
        max_transfer_rate.setConf(SysConfigEntry.LOAN_TRANSFER_DISCONT_MAX);
        max_transfer_rate.setConfValue(sys_max_transfer_rate);
        list.add(max_transfer_rate);

        String sys_transfer_rate = params.getString("sys_transfer_rate");
        SysConfig transfer_rate = new SysConfig();
        transfer_rate.setConf(SysConfigEntry.LOAN_TRANSFER_FEE_RATE);
        transfer_rate.setConfValue(sys_transfer_rate);
        list.add(transfer_rate);

        String sys_transfer_cycle = params.getString("sys_transfer_cycle");
        SysConfig transfer_cycle = new SysConfig();
        transfer_cycle.setConf(SysConfigEntry.LOAN_TRANSFER_PERIOD);
        transfer_cycle.setConfValue(sys_transfer_cycle);
        list.add(transfer_cycle);

        String sys_transfer_type = params.getString("sys_transfer_type");
        SysConfig transfer_type = new SysConfig();
        transfer_type.setConf(SysConfigEntry.LOAN_TRANSFER_PERIOD_TYPE);
        transfer_type.setConfValue(sys_transfer_type);
        list.add(transfer_type);

        String sys_borrow_count = params.getString("sys_borrow_count");
        SysConfig borrow_count = new SysConfig();
        borrow_count.setConf(SysConfigEntry.LOAN_LIMIT_PER_DAY);
        borrow_count.setConfValue(sys_borrow_count);
        list.add(borrow_count);

        String invest_red_vakidity = params.getString("invest_red_vakidity");
        SysConfig red_vakidity = new SysConfig();
        red_vakidity.setConf(SysConfigEntry.INVEST_RED_VAKIDITY);
        red_vakidity.setConfValue(invest_red_vakidity);
        list.add(red_vakidity);

        String expire_remind = params.getString("expire_remind");
        SysConfig expire = new SysConfig();
        expire.setConf(SysConfigEntry.EXPIRE_REMIND);
        expire.setConfValue(expire_remind);
        list.add(expire);
        
        String monthfree_tocash_times = params.getString("monthfree_tocash_times");
        SysConfig monthfree = new SysConfig();
        monthfree.setConf(SysConfigEntry.MONTHFREE_TOCASH_TIMES);
        monthfree.setConfValue(monthfree_tocash_times);
        list.add(monthfree);
        
        String wait_pay_capital = params.getString("wait_pay_capital");
        SysConfig wait = new SysConfig();
        wait.setConf(SysConfigEntry.WAIT_PAY_CAPITAL);
        wait.setConfValue(wait_pay_capital);
        list.add(wait);
        
        String withdraw_rate = params.getString("withdraw_rate");
        SysConfig withdrawRate = new SysConfig();
        withdrawRate.setConf(SysConfigEntry.WITHDRAW_RATE);
        withdrawRate.setConfValue(withdraw_rate);
        list.add(withdrawRate);
        
        String login_count_limit = params.getString("login_count_limit");
        SysConfig countLimitConfig = new SysConfig();
        countLimitConfig.setConf(SysConfigEntry.LOGIN_COUNT_LIMIT);
        countLimitConfig.setConfValue(login_count_limit);
        list.add(countLimitConfig);
        
        String login_time_limit = params.getString("login_time_limit");
        SysConfig timeLimitConfig = new SysConfig();
        timeLimitConfig.setConf(SysConfigEntry.LOGIN_TIME_LIMIT);
        timeLimitConfig.setConfValue(login_time_limit);
        list.add(timeLimitConfig);
        
        String before_pay_day = params.getString("before_pay_day");
        SysConfig before_pay_dayConfig = new SysConfig();
        before_pay_dayConfig.setConf(SysConfigEntry.BEFORE_PAY_DAY);
        before_pay_dayConfig.setConfValue(before_pay_day);
        list.add(before_pay_dayConfig);
        
        String querybalance_synch_account = params.getString("querybalance_synch_account");
        SysConfig querybalance_synch_accountConfig = new SysConfig();
        querybalance_synch_accountConfig.setConf(SysConfigEntry.QUERYBALANCE_SYNCH_ACCOUNT);
        querybalance_synch_accountConfig.setConfValue(querybalance_synch_account);
        list.add(querybalance_synch_accountConfig);
        
        
//        String ahead_hand_fee = params.getString("ahead_hand_fee");
//        SysConfig ahead_hand_feeConfig = new SysConfig();
//        ahead_hand_feeConfig.setConf(SysConfigEntry.AHEADREPAY_HAND_FEE);
//        ahead_hand_feeConfig.setConfValue(ahead_hand_fee);
//        list.add(ahead_hand_feeConfig);
        
        
        String ahead_hand_fee_rebate = params.getString("ahead_hand_fee_rebate");
        SysConfig ahead_hand_fee_rebateConfig = new SysConfig();
        ahead_hand_fee_rebateConfig.setConf(SysConfigEntry.AHEADREPAY_HAND_FEE_REBATE);
        ahead_hand_fee_rebateConfig.setConfValue(ahead_hand_fee_rebate);
        list.add(ahead_hand_fee_rebateConfig);

        for (SysConfig sysConfig : list) {
            baseResult = this.SysConfigService.modifySysConfig(sysConfig.getConf(), sysConfig.getConfValue());
            if (!baseResult.isSuccess()) {
                baseResult.setMessage(sysConfig.getConf().descrip + "修改错误！请正确填写");
                baseResult.setSuccess(false);
            }
        }

        return ResultMapper.toBaseVO(baseResult);
    }

}
