package com.autoserve.abc.dao.intf;

import java.util.List;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.MenuDO;

public interface MenuDao extends BaseDao<MenuDO, Integer> {

    List<MenuDO> findAll();

    List<MenuDO> findByParent(int parent);

    List<MenuDO> findByList(List<Integer> list);

    List<MenuDO> findByEmpId(Integer empId);
}
