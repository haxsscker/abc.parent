package com.autoserve.abc.web.module.screen.mobileIos;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.AreaDO;
import com.autoserve.abc.dao.dataobject.CardCityBaseDO;
import com.autoserve.abc.service.biz.intf.cash.CardCityBaseService;
import com.autoserve.abc.service.biz.intf.common.AreaService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.util.Md5Encrypt;

public class error {
	public JsonMobileVO execute(Context context, ParameterParser params) throws IOException {
		JsonMobileVO result = new JsonMobileVO();
		result.setResultCode("201");
		result.setResultMessage("解密请求异常");
		return result;
	}
}
