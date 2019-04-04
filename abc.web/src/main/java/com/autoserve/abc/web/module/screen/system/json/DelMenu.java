package com.autoserve.abc.web.module.screen.system.json;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.authority.MenuService;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class DelMenu {

    @Resource
    private MenuService   menuService;

    private static Logger logger = LoggerFactory.getLogger(DelMenu.class);

    public JsonBaseVO execute(ParameterParser params) {
        Integer id = params.getInt("id");
        return ResultMapper.toBaseVO(menuService.removeMenu(id));
    }
}
