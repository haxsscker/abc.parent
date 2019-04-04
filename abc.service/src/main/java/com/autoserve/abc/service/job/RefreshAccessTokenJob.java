package com.autoserve.abc.service.job;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.dao.dataobject.WxTokenDO;
import com.autoserve.abc.service.biz.intf.wxproxy.WxProxyService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;

/**
 * 类RefreshAccessTokenJob.java的实现描述：微信AccessToken定时更新任务
 * 
 * @author WangMing 2015年6月27日 上午11:10:34
 */
public class RefreshAccessTokenJob implements BaseJob {
    private static final Logger logger = LoggerFactory.getLogger(RefreshAccessTokenJob.class);

    @Resource
    private WxProxyService      wxProxyService;

    @Override
    public void run() {

        logger.info("定时更新AccessToken任务开始");
        String AppID = SystemGetPropeties.getStrString("AppID");
        String AppSecret = SystemGetPropeties.getStrString("AppSecret");
        logger.info("AppID:" + AppID);
        logger.info("AppSecret:" + AppSecret);
        PlainResult<WxTokenDO> result = wxProxyService.CreatToken(AppID, AppSecret);
        if (result.isSuccess()) {
            logger.info("更新AccessToken成功...");
        } else {
            logger.error("更新AccessToken失败...");
        }
        logger.info("定时更新AccessToken任务结束");

    }
}
