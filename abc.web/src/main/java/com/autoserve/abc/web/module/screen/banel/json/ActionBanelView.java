package com.autoserve.abc.web.module.screen.banel.json;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.BanelDO;
import com.autoserve.abc.service.biz.intf.banel.BanelService;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 类ActionArticleView.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月18日 下午3:13:15
 */
public class ActionBanelView {

    @Resource
    private BanelService banelService;
    
    public Map<String, Object> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        
        PageResult<BanelDO> result = banelService.queryListByPage(pageCondition);
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("total", result.getCount());
        map.put("rows", result.getData());
        return map;
    }
    
}
