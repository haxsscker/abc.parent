package com.autoserve.abc.web.module.screen.system.json;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.MenuDO;
import com.autoserve.abc.service.biz.intf.authority.MenuService;
import com.autoserve.abc.web.convert.MenuVOConverter;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.menu.MenuVO;

public class AddMenu {

    @Resource
    private MenuService   menuService;

    private static Logger logger = LoggerFactory.getLogger(AddMenu.class);

    public JsonBaseVO execute(@Params MenuVO vo) {
        logger.info("menu vo is ： "+JSON.toJSONString(vo));
        MenuDO mdo = MenuVOConverter.convert(vo);
        return ResultMapper.toBaseVO(menuService.createMenu(mdo));
    }
}
