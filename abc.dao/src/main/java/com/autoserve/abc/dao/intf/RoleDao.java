package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao extends BaseDao<RoleDO, Integer> {

    public int findAllCount();

    /**
     * 分页查询角色列表 以role_Sort降序排序
     * 
     * @param from
     * @param length
     * @return
     */
    public List<RoleDO> findByPage(@Param("pageCondition") PageCondition pageCondition);

    public RoleDO findByRoleName(String roleName);

    public List<RoleDO> findAll();
}
