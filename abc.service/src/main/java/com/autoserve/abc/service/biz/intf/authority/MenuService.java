package com.autoserve.abc.service.biz.intf.authority;

import java.util.List;

import com.autoserve.abc.dao.dataobject.ButtonDO;
import com.autoserve.abc.dao.dataobject.MenuDO;
import com.autoserve.abc.service.biz.entity.MenuItem;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 类MenuService.java的实现描述：菜单操作的业务类
 * 
 * @author pp 2014年11月17日 下午4:12:46
 */
public interface MenuService {

    /**
     * 获取某个菜单下已经分配的button
     * 
     * @return
     */
    public PlainResult<List<ButtonDO>> queryAllocatedButton(Integer menuId);

    /**
     * 对menu分配按钮 对以前的数据 先删除
     * 
     * @param menuId
     * @param btns
     * @return
     */
    public BaseResult allocBtn(Integer menuId, List<Integer> btns);

    /**
     * 查询所有菜单 在菜单管理栏(页面中部做显示)做显示
     * 
     * @return
     */
    public PlainResult<MenuItem> queryAllMenu();

    /**
     * 建立菜单
     * 
     * @param menu
     * @return 返回成功与否
     */
    public BaseResult createMenu(MenuDO menu);

    /**
     * 修改菜单
     * 
     * @param menu
     * @return 返回成功与否
     */
    public BaseResult modifyMenu(MenuDO menu);

    /**
     * 删除菜单 如果有子菜单 不能删除
     * 
     * @param id
     * @return 返回成功与否
     */
    public BaseResult removeMenu(Integer id);

}
