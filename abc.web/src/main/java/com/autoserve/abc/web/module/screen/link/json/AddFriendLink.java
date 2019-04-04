package com.autoserve.abc.web.module.screen.link.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.dataobject.SysLinkInfoDO;
import com.autoserve.abc.service.biz.intf.sys.SysLinkInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 类AddFriendLink.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月3日 下午1:21:02
 */
public class AddFriendLink {

    @Resource
    private SysLinkInfoService sysLinkInfoService;

    public JsonBaseVO execute(ParameterParser params) {
        String slTitle = params.getString("sys_link_title");
        String slAddress = params.getString("sys_link_address");
        Integer slOrder = params.getInt("sys_link_order");
        String slLogo = params.getString("sys_link_logo");

        SysLinkInfoDO sysLinkInfo = new SysLinkInfoDO();

        sysLinkInfo.setSlTitle(slTitle);
        sysLinkInfo.setSlAddress(slAddress);
        sysLinkInfo.setSlOrder(slOrder);
        sysLinkInfo.setSlLogo(slLogo);

        BaseResult result = this.sysLinkInfoService.createSyslinkInfo(sysLinkInfo);
        JsonBaseVO vo = new JsonBaseVO();
        vo.setSuccess(result.isSuccess());
        vo.setMessage(result.getMessage());
        return vo;
    }
}
