package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.ButtonDO;

import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface ButtonDao extends BaseDao<ButtonDO, Integer> {

    public List<ButtonDO> findAll();

    public ButtonDO findByTag(String tag);

    public List<ButtonDO> findByList(List<Integer> list);

	public List<ButtonDO> findBtnByEmpAndMenu(@Param("empId")Integer empId, @Param("menuId")Integer menuId);
}
