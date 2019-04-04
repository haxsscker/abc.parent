package com.autoserve.abc.service.biz.intf.authority;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

public interface RoleService {


    /**
     * 新建角色
     * 
     * @param role
     * @return
     */
    public BaseResult createRole(RoleDO role);

    /**
     * 删除角色   同时EmployeeRole关联也应该删除
     * 
     * @param roleId
     * @return
     */
    public BaseResult removeRole(Integer roleId);

    /**
     * 修改角色信息
     * 
     * @param role
     * @return
     */
    public BaseResult modifyRole(RoleDO role);

    /**
     * 分页查询角色
     * 
     * @param condition
     * @return
     */
    public PageResult<RoleDO> queryPageRole(PageCondition condition);

}
