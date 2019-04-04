package com.autoserve.abc.web.module.screen.system.json;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.WeChatMenuDO;
import com.autoserve.abc.service.biz.intf.wechat.WeChatMenuService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.WeChatMenuVOConverter;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.menu.WeChatMenuVO;

public class EditWeChatMenu {
    @Resource
    private WeChatMenuService weChatMenuService;

    private static Logger     logger = LoggerFactory.getLogger(EditWeChatMenu.class);

    public JsonBaseVO execute(@Params WeChatMenuVO vo) {
    	String key = vo.getMenuKey();
    	PlainResult<WeChatMenuDO> result = weChatMenuService.queryWeChatMenuByKey(key);
    	if(!key.equals(vo.getMenKey())){
    		if(result.getData()!=null){
    			if(result.getData().getMenuKey().equals(key)){
    				return new JsonBaseVO(false,"提交的按钮关键字重复");
    			}
    		}
    	}
        logger.info("menu vo is : " + JSON.toJSONString(vo));
        WeChatMenuDO mdo = WeChatMenuVOConverter.convert(vo);
        return ResultMapper.toBaseVO(weChatMenuService.modifyMenu(mdo));

    }
}
