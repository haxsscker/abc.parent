package com.autoserve.abc.dao.intf;

import java.util.List;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.MenuBtnDO;

public interface MenuBtnDao extends BaseDao<MenuBtnDO, Integer> {

    /**
     * @func 按照menuId删除
     * @param menuId
     * @return
     */
    public int deleteByMenuId(Integer menuId);

    /**
     * @func 按照menuid 和btnid查询
     * @param menuId
     * @param btnId
     * @return
     */
    public MenuBtnDO findByMenuAndBtn(Integer menuId, Integer btnId);

    public List<MenuBtnDO> findByMenuId(Integer menuId);
}
