package com.autoserve.abc.web.module.screen.sysset;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.vo.sysset.SysVO;

public class SystemParameterAddView {

    @Resource
    private SysConfigService SysConfigService;

    public void execute(Context context) {

        List<String> list = new ArrayList<String>();
        list.add("SHUTDOWN_SITE");
        list.add("SHUTDOWN_INFO");
        list.add("MIN_CREDIT_LIMIT");
        list.add("PUNISH_INTEREST_RATE");
        /**
         *  修改目的：新增违约罚息率
    	 *  修改内容：新增以下1行代码
    	 *  修改人/时间：夏同同/2016-04-08 15:27:00
         */
        list.add("PUNISH_BREACH_INTEREST_RATE");
        list.add("CONTINUE_DESIGNATED_PAY");
        list.add("LOAN_TRANSFER_LIMIT");
        list.add("LOAN_TRANSFER_DISCONT_MIN");
        list.add("LOAN_TRANSFER_DISCONT_MAX");
        list.add("LOAN_TRANSFER_FEE_RATE");
        list.add("LOAN_TRANSFER_PERIOD");
        list.add("LOAN_TRANSFER_PERIOD_TYPE");
        list.add("LOAN_LIMIT_PER_DAY");
        list.add("INVEST_RED_VAKIDITY");
        list.add("EXPIRE_REMIND");
        list.add("MONTHFREE_TOCASH_TIMES");
        list.add("WAIT_PAY_CAPITAL");
        list.add("LOGIN_COUNT_LIMIT");
        list.add("LOGIN_TIME_LIMIT");
        list.add("BEFORE_PAY_DAY");
        list.add("QUERYBALANCE_SYNCH_ACCOUNT");//余额查询同步账户开关
        //list.add("AHEADREPAY_HAND_FEE");//提前还款是否收取全部平台服务费
        list.add("AHEADREPAY_HAND_FEE_REBATE");//提前还款全部平台服务费折扣
        //新增用户提现手续费费率
        list.add("WITHDRAW_RATE");
        
        ListResult<SysConfig> result = this.SysConfigService.queryListByParam(list);

        SysVO vo = new SysVO();

        for (SysConfig SysConfig : result.getData()) {
            if (SysConfig.getConf() == SysConfigEntry.SHUTDOWN_SITE) {
                vo.setSys_is_stop(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.SHUTDOWN_INFO) {
                vo.setSys_stop_tip(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.MIN_CREDIT_LIMIT) {
                vo.setSys_min_credit(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.PUNISH_INTEREST_RATE) {
                vo.setSys_over_rate(SysConfig.getConfValue());
            }
            /**
             *  修改目的：新增违约罚息率
        	 *  修改内容：新增以下3行代码
        	 *  修改人/时间：夏同同/2016-04-08 15:27:00
             */
            if (SysConfig.getConf() == SysConfigEntry.PUNISH_BREACH_INTEREST_RATE) {
                vo.setSys_breach_rate(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.CONTINUE_DESIGNATED_PAY) {
                vo.setSys_repay_limit(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.LOAN_TRANSFER_LIMIT) {
                vo.setSys_transfer_count(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.LOAN_TRANSFER_DISCONT_MIN) {
                vo.setSys_min_transfer_rate(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.LOAN_TRANSFER_DISCONT_MAX) {
                vo.setSys_max_transfer_rate(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.LOAN_TRANSFER_FEE_RATE) {
                vo.setSys_transfer_rate(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.LOAN_TRANSFER_PERIOD) {
                vo.setSys_transfer_cycle(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.LOAN_TRANSFER_PERIOD_TYPE) {
                vo.setSys_transfer_type(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.LOAN_LIMIT_PER_DAY) {
                vo.setSys_borrow_count(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.INVEST_RED_VAKIDITY) {
                vo.setInvest_red_vakidity(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.EXPIRE_REMIND) {
                vo.setExpire_remind(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.MONTHFREE_TOCASH_TIMES) {
            	vo.setMonthfree_tocash_times(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.WAIT_PAY_CAPITAL) {
            	vo.setWait_pay_capital(SysConfig.getConfValue());
            }
            if(SysConfig.getConf()== SysConfigEntry.LOGIN_COUNT_LIMIT){
            	vo.setLogin_count_limit(SysConfig.getConfValue());
            }
            if(SysConfig.getConf()== SysConfigEntry.LOGIN_TIME_LIMIT){
            	vo.setLogin_time_limit(SysConfig.getConfValue());
            }
            if(SysConfig.getConf()== SysConfigEntry.BEFORE_PAY_DAY){
            	vo.setBefore_pay_day(SysConfig.getConfValue());
            }
            if(SysConfig.getConf()== SysConfigEntry.WITHDRAW_RATE){
            	vo.setWithdraw_rate(SysConfig.getConfValue());
            }
            if(SysConfig.getConf()== SysConfigEntry.QUERYBALANCE_SYNCH_ACCOUNT){
            	vo.setQuerybalance_synch_account(SysConfig.getConfValue());
            }
            
//            if(SysConfig.getConf()== SysConfigEntry.AHEADREPAY_HAND_FEE){
//            	vo.setAhead_hand_fee(SysConfig.getConfValue());
//            }
            
            if(SysConfig.getConf()== SysConfigEntry.AHEADREPAY_HAND_FEE_REBATE){
            	vo.setAhead_hand_fee_rebate(SysConfig.getConfValue());
            }
        }
        context.put("vo", vo);
    }

}
