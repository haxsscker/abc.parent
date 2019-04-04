package com.autoserve.abc.web.module.screen.banel.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.banel.MonthRptService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 类DelArticleView.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月18日 下午3:13:26
 */
public class DelMonthRptView {

	@Resource
    private MonthRptService monthRptService;

    public JsonBaseVO execute(ParameterParser params) {
        Integer id = params.getInt("id");
        BaseResult result = this.monthRptService.removeRpt(id);
        JsonBaseVO jsonBaseVO = new JsonBaseVO();
        jsonBaseVO.setSuccess(result.isSuccess());
        jsonBaseVO.setMessage(result.getMessage());
        return jsonBaseVO;
    }

}
