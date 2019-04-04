package com.autoserve.abc.web.module.screen.banel.json;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.dataobject.BanelDO;
import com.autoserve.abc.service.biz.intf.banel.BanelService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 类AddArticleView.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月18日 下午3:13:19
 */
public class AddBanelView {

    @Resource
    private BanelService banelService;

    public JsonBaseVO execute(@Params BanelDO banel) {
    	
        BaseResult result = this.banelService.createBanel(banel);
        JsonBaseVO jsonBaseVO = new JsonBaseVO();
        jsonBaseVO.setSuccess(result.isSuccess());
        jsonBaseVO.setMessage(result.getMessage());
        return jsonBaseVO;
    }
}
