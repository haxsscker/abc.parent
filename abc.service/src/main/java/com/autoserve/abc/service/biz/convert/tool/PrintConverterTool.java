/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert.tool;

import java.lang.reflect.Field;

import com.autoserve.abc.dao.dataobject.TraceRecordDO;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;

/**
 * 帮助打印DO对象与Entity对象转换的代码的工具
 *
 * @author segen189 2014年11月18日 上午10:56:28
 */
public class PrintConverterTool {

    /**
     * 简单模式
     *
     * @param pojoClass 要进行转换的类
     * @param doPrefix DO对象的字段的前缀
     */
    public static void simpleMode_print(Class<?> pojoClass, String doPrefix) {
        String leftName = pojoClass.getSimpleName().substring(0, 1).toLowerCase()
                + pojoClass.getSimpleName().substring(1);
        String rightName;
        boolean leftIsDO;
        if (leftName.endsWith("DO")) {
            rightName = leftName.substring(0, leftName.length() - 2);
            leftIsDO = true;
        } else {
            rightName = leftName + "DO";
            leftIsDO = false;
        }

        Field[] fields = pojoClass.getDeclaredFields();
        for (Field f : fields) {
            StringBuilder sb = new StringBuilder();

            sb.append(leftName);
            sb.append(".set");
            sb.append(f.getName().substring(0, 1).toUpperCase());
            sb.append(f.getName().substring(1));

            sb.append("(");
            sb.append(rightName);
            sb.append(".get");

            if (leftIsDO) {
                sb.append(f.getName().substring(doPrefix.length()));
            } else {
                sb.append(doPrefix.substring(0, 1).toUpperCase());
                sb.append(doPrefix.substring(1).toLowerCase());
                sb.append(f.getName().substring(0, 1).toUpperCase());
                sb.append(f.getName().substring(1));
            }

            sb.append("());");
            System.out.println(sb);
        }

        System.out.println("=========================================");
    }

    public static void main(String[] args) {
        simpleMode_print(LoanTraceRecord.class, "ltr");
        simpleMode_print(TraceRecordDO.class, "ltr");
    }

}
