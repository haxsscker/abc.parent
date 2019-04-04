package com.autoserve.abc.web.module.screen.redrewardmanage;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;

/**
 * 获取红包id保存在context中
 * 
 * @author fangrui 2015年1月8日 上午10:01:36
 */
public class PersonalRewardLookUpView {

    public void execute(Context context, ParameterParser params) {
        Integer redId = params.getInt("redId");
        context.put("redId", redId);
    }

}
