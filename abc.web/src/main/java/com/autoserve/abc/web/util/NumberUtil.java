package com.autoserve.abc.web.util;

import org.apache.velocity.tools.generic.NumberTool;

/**
 * @author yuqing.zheng Created on 2014-12-15,17:04
 */
public class NumberUtil {

    public String currency(Object obj) {
        NumberTool tool = new NumberTool();
        String res = tool.currency(obj);
        if (res == null) {
            return "-";
        }

        return res;
    }

    public String format(Object obj) {
        NumberTool tool = new NumberTool();
        String res = tool.format("#0.00" + "(元)", obj);
        if (res == null) {
            return "-";
        }

        return res;
    }
    
    public static String moneyFormat(Object obj) {
        NumberTool tool = new NumberTool();
        String res = tool.format("#0.00", obj);
        if (res == null) {
            return "-";
        }

        return res;
    }

    public String percent(Object obj) {
        NumberTool tool = new NumberTool();
        String res = tool.percent(obj);
        if (res == null) {
            return "-";
        }

        return res;
    }
}
