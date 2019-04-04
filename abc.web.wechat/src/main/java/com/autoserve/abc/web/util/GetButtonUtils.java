package com.autoserve.abc.web.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.ButtonDO;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserUtil;

public class GetButtonUtils {
    private static final Logger log = LoggerFactory.getLogger(GetButtonUtils.class);

    public static void getButtons(AuthorityService authorityService, Context context, ParameterParser params) {
        Integer empId = LoginUserUtil.getUserId();
        Integer menuId = params.getInt("MenuId");

        PlainResult<List<ButtonDO>> authResult = authorityService.queryAllocatedButton(empId, menuId);
        if (authResult.isSuccess()) {
            context.put("buttonTags", authResult.getData());
        } else {
            log.warn("用户菜单按钮信息查询失败", authResult.getMessage());
        }
    }
}
