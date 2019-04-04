package com.autoserve.abc.web.module.screen.score;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import com.autoserve.abc.web.util.GetButtonUtils;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/2 16:38.
 */
public class ScoreTypeListView {
    @Resource
    private AuthorityService authorityService;

    public void execute(Context context, ParameterParser params) {
        GetButtonUtils.getButtons(authorityService, context, params);
    }
}
