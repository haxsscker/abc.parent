/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

/**
 * 类型转换器<br>
 * F: 转换前的类型 T: 转换后的类型
 *
 * @author segen189 2014年12月23日 下午2:36:17
 */
public abstract class ResultConverter<F, T> {
    public abstract T convert(F context);

    public List<T> convert(List<F> context) {
        if (context == null) {
            return null;
        }

        List<T> result = new ArrayList<T>(context.size());
        for (F f : context) {
            result.add(convert(f));
        }
        return result;
    }
}
