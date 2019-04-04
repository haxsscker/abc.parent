package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.SysLinkInfoDO;
import com.autoserve.abc.web.vo.syslinkinfo.SysLinkInfoVO;

public class SysLinkInfoVOConverter {

    public static SysLinkInfoVO toSysLinkInfoVO(SysLinkInfoDO sysLinkInfoDO) {
        SysLinkInfoVO linkInfo = new SysLinkInfoVO();
        linkInfo.setSys_link_id(sysLinkInfoDO.getSlId());
        linkInfo.setSys_link_title(sysLinkInfoDO.getSlTitle());
        linkInfo.setSys_link_logo(sysLinkInfoDO.getSlLogo());
        linkInfo.setSys_link_mark(sysLinkInfoDO.getSlMark());
        linkInfo.setSys_link_width(sysLinkInfoDO.getSlWidth());
        linkInfo.setSys_link_height(sysLinkInfoDO.getSlHeight());
        linkInfo.setSys_link_address(sysLinkInfoDO.getSlAddress());
        linkInfo.setSys_link_order(sysLinkInfoDO.getSlOrder());
        return linkInfo;

    }

}
