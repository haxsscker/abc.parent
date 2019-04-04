/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.autoserve.abc.service.biz.entity.BatchPayQuery;

/**
 * 提现job查询的list
 * 
 * @author J.YL 2014年12月1日 下午2:18:00
 */
public class ToCashQueryList {
    private static Queue<BatchPayQuery> toCashQueue = new ConcurrentLinkedQueue<BatchPayQuery>();

    public synchronized static void add(BatchPayQuery data) {
        toCashQueue.add(data);
    }

    public synchronized static List<BatchPayQuery> pollAll() {
        List<BatchPayQuery> ret = new LinkedList<BatchPayQuery>();
        if (!toCashQueue.isEmpty()) {
            ret.addAll(toCashQueue);
            toCashQueue.clear();
        }
        return ret;
    }
}
