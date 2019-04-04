/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.service.biz.convert.PaymentPlanConverter;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;

/**
 * 根据项目状态发送站内信定时任务
 * 
 * @author wuqiang.du 2015年3月2日11:08:50
 */
public class SendSysMessageJob implements BaseJob {
    private static final Logger   logger = LoggerFactory.getLogger(SendSysMessageJob.class);

    @Resource
    private SysMessageInfoService sysMessageInfoService;

    @Autowired
    private PaymentPlanDao        paymentPlanDao;

    @Override
    public void run() {
        logger.info("项目站内信任务开始");
        List<PaymentPlan> paymentPlanList = new ArrayList<PaymentPlan>();
        paymentPlanList = PaymentPlanConverter.toPaymentPlanList(paymentPlanDao.findListByParam(null, null));
        for (PaymentPlan paymentPlan : paymentPlanList) {
            //currentDate is later paytime
            int days = Days.daysBetween(new LocalDate(new Date()), new LocalDate(paymentPlan.getPaytime())).getDays();
            if (days <= 2) {
                //发送站内信
                SysMessageInfoDO sysMessageInfoDO = new SysMessageInfoDO();
                sysMessageInfoDO.setSysMessageTitle("还款到期通知");
                sysMessageInfoDO.setSysUserType("1");
                sysMessageInfoDO.setSysMessageContent("您的还款即将到期，请您关注");
                sysMessageInfoDO.setSysToUser(1);
                sysMessageInfoDO.setSysToUserType("4");
                sysMessageInfoDO.setSysMessageDate(new Date());
                sysMessageInfoDO.setSysMessageState("0");
                sysMessageInfoDO.setSysUserId(paymentPlan.getLoanee());
                sysMessageInfoDO.setSysToUser(3);
                sysMessageInfoDO.setSysDelSign("0");
                sysMessageInfoService.createMessage(sysMessageInfoDO);
            }
        }
        logger.info("项目站内信任务结束");
    }

}
