package com.autoserve.abc.web.module.screen.system.json;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.service.biz.intf.authority.RoleService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.RoleVOConverter;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.role.RoleVO;

public class GetRoleList {

    @Resource
    private RoleService roleService;

    public JsonPageVO<RoleVO> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        PageResult<RoleDO> pageResult = roleService.queryPageRole(pageCondition);
        List<RoleVO> list = RoleVOConverter.convertList(pageResult.getData());
        JsonPageVO<RoleVO> jvo = new JsonPageVO<RoleVO>();
        jvo.setRows(list);
        jvo.setTotal(pageResult.getTotalCount());
        return jvo;
    }
}
