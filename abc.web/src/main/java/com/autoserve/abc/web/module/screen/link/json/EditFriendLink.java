package com.autoserve.abc.web.module.screen.link.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.dataobject.SysLinkInfoDO;
import com.autoserve.abc.service.biz.intf.sys.SysLinkInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 类EditFriendLink.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月18日 下午3:11:22
 */
public class EditFriendLink {

    @Resource
    private SysLinkInfoService sysLinkInfoService;

    public JsonBaseVO execute(ParameterParser params) {
        String slId = params.getString("sys_link_id");
        String slTitle = params.getString("sys_link_title");
        String slAddress = params.getString("sys_link_address");
        Integer slOrder = params.getInt("sys_link_order");
        String slLogo = params.getString("sys_link_logo");

        SysLinkInfoDO sysLinkInfo = new SysLinkInfoDO();

        sysLinkInfo.setSlId(Integer.valueOf(slId));
        sysLinkInfo.setSlAddress(slAddress);
        sysLinkInfo.setSlTitle(slTitle);
        sysLinkInfo.setSlOrder(slOrder);
        sysLinkInfo.setSlLogo(slLogo);

        BaseResult result = this.sysLinkInfoService.modifySyslinkInfo(sysLinkInfo);
        JsonBaseVO vo = new JsonBaseVO();
        vo.setSuccess(result.isSuccess());
        vo.setMessage(result.getMessage());
        return vo;
    }

}
