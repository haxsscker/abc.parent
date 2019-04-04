package com.autoserve.abc.web.module.screen.government;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.GetButtonUtils;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/17 21:24.
 */
public class GovModifyListView {
    @Resource
    private AuthorityService authorityService;

    public void execute(Context context, ParameterParser params) {
        GetButtonUtils.getButtons(authorityService, context, params);

        context.put("govId", LoginUserUtil.getEmpOrgId());
    }
}
