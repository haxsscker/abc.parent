package com.autoserve.abc.web.module.screen.link;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.SysLinkInfoDO;
import com.autoserve.abc.service.biz.intf.sys.SysLinkInfoService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 
 * @author liuwei
 *
 */
public class FriendLinkAddView {
	@Resource
    private SysLinkInfoService sysLinkInfoService;

    public void execute(Context context, ParameterParser params) {
    	int linkId = params.getInt("sys_link_id");
    	
    	PlainResult<SysLinkInfoDO> result = this.sysLinkInfoService.queryById(linkId);
    	
    	SysLinkInfoDO sysLinkInfoDO = result.getData();
    	
    	context.put("link", sysLinkInfoDO);
    }
}
