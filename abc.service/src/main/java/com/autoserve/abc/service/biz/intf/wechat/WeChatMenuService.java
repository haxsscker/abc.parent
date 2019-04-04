package com.autoserve.abc.service.biz.intf.wechat;

import com.autoserve.abc.dao.dataobject.WeChatMenuDO;
import com.autoserve.abc.service.biz.entity.WeChatMenuItem;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public interface WeChatMenuService {

    /**
     * 查询所有的微信菜单
     * 
     * @return
     */
    PlainResult<WeChatMenuItem> queryAllMenu();

    /**
     * 查询所有以启用的微信菜单
     * 
     * @return
     */
    PlainResult<WeChatMenuItem> queryAllUseMenu();

    /**
     * 添加菜单
     * 
     * @param menu
     * @return 返回成功与否
     */
    public BaseResult createMenu(WeChatMenuDO menu);

    /**
     * 修改菜单
     * 
     * @param menu
     * @return 返回成功与否
     */
    public BaseResult modifyMenu(WeChatMenuDO menu);

    /**
     * 删除菜单 如果有子菜单 不能删除
     * 
     * @param id
     * @return 返回成功与否
     */
    public BaseResult removeMenu(Integer id);

    /**
     * 根据菜单关键字查询菜单信息
     * 
     * @param menuKey
     * @return
     */
    PlainResult<WeChatMenuDO> queryWeChatMenuByKey(String menuKey);
}
