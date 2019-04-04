package com.autoserve.abc.web.util;

import org.apache.velocity.tools.generic.NumberTool;

/**
 * @author yuqing.zheng Created on 2014-12-15,17:04
 */
public class NumberUtil extends NumberTool {
    @Override
    public String currency(Object obj) {
        String res = new NumberTool().currency(obj);
        if (res == null) {
            return "-";
        }

        return res;
    }

    @Override
    public String percent(Object obj) {
        String res = new NumberTool().percent(obj);
        if (res == null) {
            return "-";
        }

        return res;
    }

    @Override
    public Number toNumber(Object obj) {
        Number res = new NumberTool().toNumber(obj);
        if (res == null) {
            return -0;
        }

        return res;
    }

    public static void main(String[] args) {
        NumberTool numberTool = new NumberTool();
        System.out.println(numberTool.number(2232323.23));
        System.out.println(numberTool.currency(23232323));
        System.out.println(numberTool.currency(null));
        System.out.println(numberTool.percent(12));
        System.out.println(numberTool.percent(100.00 / 1201.00));
    }
}
