/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.convert.tool;

import java.lang.reflect.Method;

import com.autoserve.abc.web.vo.sysset.LoginLogVO;

/**
 * 帮助打印Entity对象与VO对象转换的代码的工具
 *
 * @author segen189 2014年11月18日 上午10:56:28
 */
public class PrintVOConverterTool {

    public static void printVOSetMethods(Class<?> setterClz, Class<?> getterClaz) {
        Method[] methods = setterClz.getMethods();

        for (Method m : methods) {
            if (m.getName().startsWith("set")) {
                StringBuilder sb = new StringBuilder();
                sb.append(setterClz.getSimpleName().substring(0, 1).toLowerCase());
                sb.append(setterClz.getSimpleName().substring(1));
                sb.append(".");
                sb.append(m.getName());
                sb.append("(");
                sb.append(getterClaz.getSimpleName().substring(0, 1).toLowerCase());
                sb.append(getterClaz.getSimpleName().substring(1));
                sb.append(".g");
                sb.append(m.getName().substring(1));
                sb.append("());");

                System.out.println(sb);
            }
        }
    }

    public static void printSetParamsMethods(Class<?> setterClz) {
        Method[] methods = setterClz.getMethods();

        for (Method m : methods) {
            if (m.getName().startsWith("set")) {
                StringBuilder sb = new StringBuilder();
                sb.append(setterClz.getSimpleName().substring(0, 1).toLowerCase());
                sb.append(setterClz.getSimpleName().substring(1));
                sb.append(".");
                sb.append(m.getName());
                sb.append("(params.getString(\"xxx\"));");

                System.out.println(sb);
            }
        }
    }

    public static void main(String[] args) {
        //        printVOSetMethods(LoanCar.class, LoanCar.class);
        printSetParamsMethods(LoginLogVO.class);
    }

}
