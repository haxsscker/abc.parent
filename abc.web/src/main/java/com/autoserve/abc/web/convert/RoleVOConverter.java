package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.web.vo.role.RoleVO;

public class RoleVOConverter {

    public static RoleDO convertToDo(RoleVO vo){
        RoleDO rdo=new RoleDO();
        rdo.setRoleId(vo.getRoleId());
        rdo.setRoleSort(vo.getRoleSort());
        rdo.setRoleName(vo.getRoleName());
        rdo.setRoleDefault(vo.getRoleDefault());
        rdo.setRoleDes(vo.getRoleNode());
        return rdo;
    }

    public static RoleVO convert(RoleDO rdo) {
        RoleVO rvo = new RoleVO();
        rvo.setRoleId(rdo.getRoleId());
        rvo.setRoleDefault(rdo.getRoleDefault());
        rvo.setRoleName(rdo.getRoleName());
        rvo.setRoleNode(rdo.getRoleDes());
        rvo.setRoleSort(rdo.getRoleSort());
        return rvo;
    }

    public static List<RoleVO> convertList(List<RoleDO> list) {
        List<RoleVO> result = new ArrayList<RoleVO>();
        for (RoleDO rdo : list) {
            result.add(convert(rdo));
        }
        return result;
    }
}
