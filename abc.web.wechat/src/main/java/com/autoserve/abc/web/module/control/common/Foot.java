package com.autoserve.abc.web.module.control.common;

import javax.annotation.Resource;
import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysLinkInfoDO;
import com.autoserve.abc.service.biz.intf.sys.SysLinkInfoService;
import com.autoserve.abc.service.biz.result.PageResult;
;
/**
 * 尾部
 * @author DS
 *
 * 2015上午10:34:30
 */
public class Foot {
	@Resource
	private SysLinkInfoService sysLinkInfoService;
    public void execute(Context context, ParameterParser params) {
    	//首页友情链接
    	PageResult<SysLinkInfoDO> linkResult = sysLinkInfoService.queryListByParam(new PageCondition(1, 8));
    	if(linkResult.isSuccess()) {
    		context.put("links", linkResult.getData());
    	}
    }
}
