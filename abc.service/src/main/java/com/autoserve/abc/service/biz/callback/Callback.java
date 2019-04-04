/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.callback;

import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * 回调接口
 *
 * @author segen189 2014年11月19日 下午4:42:00
 */
public interface Callback<T> {

    /**
     * 方法执行结束后，调用回调函数
     *
     * @param data 主动通知方发送的数据
     * @return BaseResult 处理完成/处理未完成
     */
    BaseResult doCallback(T data);
}
