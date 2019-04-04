package com.autoserve.abc.service.job;

import java.util.Date;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-27,17:10
 */
public class PrintDateJob {
    public void run() {
        System.out.println("date:" + new Date().toString());
    }
}
