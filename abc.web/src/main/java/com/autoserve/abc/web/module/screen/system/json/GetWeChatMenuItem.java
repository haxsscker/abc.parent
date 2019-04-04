package com.autoserve.abc.web.module.screen.system.json;

import java.util.List;

import javax.annotation.Resource;

import com.autoserve.abc.service.biz.entity.WeChatMenuItem;
import com.autoserve.abc.service.biz.intf.wechat.WeChatMenuService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.menu.WeChatMenuItemVO;

public class GetWeChatMenuItem {
    @Resource
    private WeChatMenuService weChatMenuService;

    public WeChatMenuItemVO execute() {
        PlainResult<WeChatMenuItem> root = weChatMenuService.queryAllMenu();
        List<WeChatMenuItem> list = root.getData().getChildren();
        WeChatMenuItemVO vo = new WeChatMenuItemVO();
        vo.setTotal(list.size());
        vo.setRows(list);
        return vo;
    }
}
