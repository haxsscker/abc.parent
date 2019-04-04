package com.autoserve.abc.service.biz.intf.authority;

import java.util.List;

import com.autoserve.abc.dao.dataobject.ButtonDO;
import com.autoserve.abc.service.biz.result.PlainResult;

public interface ButtonService {

    /**
     * 获取所有的按钮
     * 
     * @return
     */
    public PlainResult<List<ButtonDO>> queryAllButton();

    public PlainResult<ButtonDO> queryByTag(String tag);
}
