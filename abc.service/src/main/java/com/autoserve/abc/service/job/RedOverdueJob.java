package com.autoserve.abc.service.job;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;

/**
 * 红包过期处理job
 * @author ding747
 *
 */
public class RedOverdueJob implements BaseJob {
    private static final Logger logger = LoggerFactory.getLogger(RedOverdueJob.class);

    @Resource
    private RedsendService   redsendService;
    

    @Override
    public void run() {
    	
    	try {
    		System.out.println("=============红包job============"+new Date());
    		logger.info("红包过期处理job开始 "+new Date());
    		redsendService.redOverdue(new Date());
    		logger.info("红包过期处理job结束"+new Date());
		} catch (Exception e) {
			logger.info("红包过期处理执行异常 "+new Date());
		}
    	
    }


}
