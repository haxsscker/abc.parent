package com.autoserve.abc.service.biz.callback.center;

import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.enums.ReviewType;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有审核的Callback注册中心
 * 审核的callback与审核类型ReviewType相关，一个ReviewType对应一个callback
 *
 * 注意：审核的callback只在审核状态发生改变时会被调 用（如审核结束，从一个审核退回/撤回到另一个审核时）
 *
 * 注意：所有与审核相关的Callback都必须确保在容器启动时注册到注册中心
 *
 * @author yuqing.zheng
 *         Created on 2014-12-01,14:55
 */
public class ReviewCallbackCenter {
    private static Map<ReviewType, Callback<ReviewOp>> callbackMap = new HashMap<ReviewType, Callback<ReviewOp>>();

    public static Callback<ReviewOp> getCallback(ReviewType key) {
        return callbackMap.get(key);
    }

    public static void registerCallback(ReviewType type, Callback<ReviewOp> callBack) {
        if (!callbackMap.containsKey(type)) {
            callbackMap.put(type, callBack);
        }
    }
}
