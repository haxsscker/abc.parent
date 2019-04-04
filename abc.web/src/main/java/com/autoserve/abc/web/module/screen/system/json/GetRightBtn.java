package com.autoserve.abc.web.module.screen.system.json;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.authority.MenuService;
import com.autoserve.abc.web.convert.ButtonVOConverter;
import com.autoserve.abc.web.vo.button.AllocatBtnVO;

public class GetRightBtn {
    @Resource
    private MenuService   menuService;

    private static Logger logger = LoggerFactory.getLogger(GetRightBtn.class);

    public List<AllocatBtnVO> execute(ParameterParser params) {
        Integer menuId = params.getInt("menuId");
        return ButtonVOConverter.convertList(menuService.queryAllocatedButton(menuId).getData());

    }
}
