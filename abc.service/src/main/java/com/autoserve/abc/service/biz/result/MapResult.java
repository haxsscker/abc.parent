package com.autoserve.abc.service.biz.result;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-19,20:24
 */
public class MapResult<T, K> extends BaseResult {
    private static final long serialVersionUID = -1044729443054115388L;

    private Map<T, K> data = Maps.newHashMap();

    public Map<T, K> getData() {
        return data;
    }

    public void setData(Map<T, K> data) {
        this.data = data;
    }


}
