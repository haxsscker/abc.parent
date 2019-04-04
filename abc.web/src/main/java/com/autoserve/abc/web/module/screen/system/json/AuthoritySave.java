package com.autoserve.abc.web.module.screen.system.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * Created by pp on 2014/12/4.
 */
public class AuthoritySave {

    @Resource
    private AuthorityService authorityService;

    public JsonBaseVO execute(@Param("data") String str, @Param("empId") Integer roleId) {
        JSONObject jobj = JSON.parseObject(str);
        JSONArray array = jobj.getJSONArray("Btns");
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject tmp = array.getJSONObject(i);
            Integer menuId = tmp.getInteger("MenuId");
            List<Integer> list = new ArrayList<Integer>();
            JSONArray butList = tmp.getJSONArray("buttons");
            for (int j = 0; j < butList.size(); j++) {
                String id = butList.getString(j);
                list.add(Integer.parseInt(id));
            }
            map.put(menuId, list);
        }
        return ResultMapper.toBaseVO(authorityService.modifyRoleMatrix(map, roleId));
    }
}
