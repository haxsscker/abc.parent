package com.autoserve.abc.web.module.control.common;

import com.alibaba.citrus.turbine.Context;

/**
 * 转让标跟踪列表
 *
 * @author segen189 2015年2月8日 上午22:20:28
 */
public class TransferLoanTraceList {

    public void execute(Context context) {

        context.put("bidId", Integer.valueOf(context.get("bidId").toString()));
    }

}
