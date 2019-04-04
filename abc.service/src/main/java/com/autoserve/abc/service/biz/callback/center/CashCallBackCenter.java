/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.callback.center;

import java.util.HashMap;
import java.util.Map;

import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.enums.DealType;

/**
 * 类CashCallBackCenter.java的实现描述：资金模块callback中心，所有类型的callback均存于此处
 * 
 * @author J.YL 2014年11月24日 上午10:22:25
 */
public class CashCallBackCenter {
    private static Map<DealType, Callback<DealNotify>> callBackMapper = new HashMap<DealType, Callback<DealNotify>>();

    public static Callback<DealNotify> getCallBackByType(DealType key) {
        return callBackMapper.get(key);
    }

    public static void registCallBack(DealType type, Callback<DealNotify> callBack) {
        if (!callBackMapper.containsKey(type)) {
            callBackMapper.put(type, callBack);
        }
    }
}
