package com.autoserve.abc.web.module.screen.system.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.authority.MenuService;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class AllocatBtn {

    @Resource
    private MenuService menuService;

    public JsonBaseVO execute(ParameterParser params) {
        String btns = params.getString("btnids");
        Integer menuId = params.getInt("menuId");
        List<Integer> list = new ArrayList<Integer>();
        if (StringUtils.isNotBlank(btns)) {
            String arr[] = btns.split(",");
            for (String index : arr) {
                list.add(Integer.parseInt(index));
            }
        }

        return ResultMapper.toBaseVO(menuService.allocBtn(menuId, list));
    }
}
