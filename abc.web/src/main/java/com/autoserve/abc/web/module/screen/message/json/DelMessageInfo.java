package com.autoserve.abc.web.module.screen.message.json;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class DelMessageInfo {
	@Autowired
	private SysMessageInfoService messageInfoService;
	
	public JsonBaseVO execute(@Params SysMessageInfoDO messageDO) {
		JsonBaseVO vo = new JsonBaseVO();
		
		int result = this.messageInfoService.deleteById(messageDO.getSysMessageId());
		if(result!=1){
			vo.setSuccess(false);
			vo.setMessage("删除失败");
		}
		return vo;
	}
}
