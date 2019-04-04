package com.autoserve.abc.service.biz.intf.authority;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.ButtonDO;
import com.autoserve.abc.service.biz.entity.MenuNode;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public interface AuthorityService {

    /**
     * 查询 empId 和 menuId 下分配的按钮
     * 
     * @param empId
     * @param menuId
     * @return
     */
    public PlainResult<List<ButtonDO>> queryAllocatedButton(Integer empId, Integer menuId);

    /**
     * 根据用户ID 寻找用户具有的角色列表 再展示左侧的菜单栏
     * 
     * @param empId
     * @return
     */
    public PlainResult<MenuNode> queryMenuByEmpId(Integer empId);

    /**
     * 查询前端角色矩阵
     * 
     * @param roleId
     * @return
     */
    public PlainResult<JSONObject> queryRoleMatrix(Integer empId);

    /**
     * 修改角色矩阵 实质是先删除 后插入
     * 
     * @param matrixMap
     * @return
     */
    public BaseResult modifyRoleMatrix(Map<Integer, List<Integer>> matrixMap, Integer roleId);
}
