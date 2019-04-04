package com.autoserve.abc.service.biz.impl.invite;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.dao.intf.InviteDao;
import com.autoserve.abc.service.biz.enums.InviteUserType;
import com.autoserve.abc.service.biz.intf.invite.AppInviteService;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.Encryption;

public class AppInviteServiceImpl implements AppInviteService{
	 private static final Logger log = LoggerFactory.getLogger(AppInviteServiceImpl.class);
	 
	  @Resource
	    private InviteDao           inviteDao;

	    private String              appRegisterUrl;
	    
	    
	    @Override
	    public PlainResult<String> generateAppInviteUrl(Integer userId, InviteUserType userType) {
	        PlainResult<String> result = new PlainResult<String>();
	        if (userId == null || userType == null) {
	            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "参数不正确");
	            return result;
	        }
	        String invitationId = userId + "," + userType.getType();
	        //        result.setData(registerUrl + Base64.byteArrayToAltBase64(invitationId.getBytes()));
	        try {
	            result.setData(appRegisterUrl + URLEncoder.encode(Encryption.encrypt(invitationId), "utf-8"));
	        } catch (UnsupportedEncodingException e) {
	            log.error(e.getMessage());
	            e.printStackTrace();
	        }
	        return result;
	    }
	    
	    public void setAppRegisterUrl(String appRegisterUrl) {
	        this.appRegisterUrl = appRegisterUrl;
	    }


}
