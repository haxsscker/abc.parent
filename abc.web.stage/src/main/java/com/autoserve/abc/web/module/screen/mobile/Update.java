package com.autoserve.abc.web.module.screen.mobile;

import org.springframework.beans.factory.annotation.Autowired;



public class Update {
	@Autowired
	private MobileInfo mobileInfo;
	public JsonMobileVO execute(){
		JsonMobileVO result = new JsonMobileVO();
		result.setResult(mobileInfo);
		return result;
		//ss
	}
}
