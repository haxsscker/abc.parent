package com.autoserve.abc.service.util;

import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-17,12:03
 */
public class RoleUtil {
    /**
     * 检查一个BaseRoleType是否在给定的roleList中
     */
    public static boolean checkRole(List<RoleDO> roleList, BaseRoleType roleType) {
        for (RoleDO roleDO : roleList) {
            if (roleType.roleName.equals(roleDO.getRoleName())) {
                return true;
            }
        }

        return false;
    }

    public static boolean checkRoles(List<RoleDO> roleList, List<BaseRoleType> roleTypes) {
        for (RoleDO roleDO : roleList) {
            for (BaseRoleType roleType : roleTypes) {
                if (roleType.roleName.equals(roleDO.getRoleName())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static List<Integer> toRoleIndexList(List<BaseRoleType> roleList) {
        List<Integer> roles = Lists.newArrayList();
        for (BaseRoleType role : roleList) {
            roles.add(role.index);
        }

        return roles;
    }

    public static List<BaseRoleType> toRoleTypeList(List<RoleDO> roleList) {
        List<BaseRoleType> roleTypes = Lists.newArrayList();
        for (RoleDO roleDO : roleList) {
            roleTypes.add(BaseRoleType.valueOfRoleName(roleDO.getRoleName()));
        }

        return roleTypes;
    }

    public static List<Integer> toRoleIdxList(List<RoleDO> roleList) {
        List<BaseRoleType> roleTypeList = toRoleTypeList(roleList);
        List<Integer> roleIdxs = Lists.transform(roleTypeList, new Function<BaseRoleType, Integer>() {
            @Override
            public Integer apply(BaseRoleType roleType) {
                return roleType.index;
            }
        });

        return  roleIdxs;
    }

    public static String printRoleList(List<RoleDO> roleList) {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for (RoleDO roleDO : roleList) {
            sb.append(roleDO.getRoleName() + ",");
        }
        sb.append("]");

        return sb.toString();
    }
}
