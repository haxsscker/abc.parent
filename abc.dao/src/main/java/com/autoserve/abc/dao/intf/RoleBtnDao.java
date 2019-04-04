package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.RoleBtnDO;

public interface RoleBtnDao extends BaseDao<RoleBtnDO, Integer> {

    public RoleBtnDO findByMenuIRoleBtn(Integer menuId, Integer roleId, Integer btnId);

    public List<RoleBtnDO> findByMenuAndRole(Integer empId, Integer menuId);

    public int deleteByRoleId(Integer roleId);

    public List<RoleBtnDO> findByRoleList(List<Integer> list);

    public int deleteByMenuAndRole(Integer menuId, Integer roleId);

    public int deleteByRole(Integer roleId);

    public int deleteMenuByNotInList(@Param("menuId") Integer menuId, @Param("list") List<Integer> list);

}
