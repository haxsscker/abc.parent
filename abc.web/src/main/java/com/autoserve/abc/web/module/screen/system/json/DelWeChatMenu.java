package com.autoserve.abc.web.module.screen.system.json;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.wechat.WeChatMenuService;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class DelWeChatMenu {
    @Resource
    private WeChatMenuService weChatMenuService;

    private static Logger     logger = LoggerFactory.getLogger(DelWeChatMenu.class);

    public JsonBaseVO execute(ParameterParser params) {
        logger.info("删除菜单...");
        Integer id = params.getInt("id");
        return ResultMapper.toBaseVO(weChatMenuService.removeMenu(id));
    }
}
