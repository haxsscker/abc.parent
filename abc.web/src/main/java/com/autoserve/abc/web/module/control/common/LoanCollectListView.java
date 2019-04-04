package com.autoserve.abc.web.module.control.common;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;

/**
 * 类LoanCollectListView.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月19日 下午3:44:04
 */
public class LoanCollectListView {

    public void execute(Context context, @Param("planId") int planId) {

        context.put("planId", planId);
    }

}
