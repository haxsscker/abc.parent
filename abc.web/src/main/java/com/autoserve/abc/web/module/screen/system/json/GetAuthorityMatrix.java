package com.autoserve.abc.web.module.screen.system.json;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSONArray;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;

/**
 * Created by pp on 2014/12/4.
 */
public class GetAuthorityMatrix {

    @Resource
    private AuthorityService authorityService;

    public JSONArray execute(@Param("empId") Integer empId) {

        return authorityService.queryRoleMatrix(empId).getData().getJSONArray("children");
    }
}
