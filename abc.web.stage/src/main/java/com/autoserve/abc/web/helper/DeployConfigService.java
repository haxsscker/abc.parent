package com.autoserve.abc.web.helper;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 部署配置，为web模块启动运行时提供配置参数
 */
public class DeployConfigService {
    private String env;
   
    
    @Autowired
	private SysConfigService sysConfigService;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
    /**
     * 获取当前urlRoot
     */
    private String getLocaleUrlRoot(HttpServletRequest request) {
        // 获取当前url根路径，如 http://abc.aliyun-inc.net
    	PlainResult<SysConfig> resultSysConfig = sysConfigService
				.querySysConfig(SysConfigEntry.WEB_PROTOCOL);
		SysConfig sysConfig = resultSysConfig.getData();
    	
        String curScheme = request.getScheme(); // http
        String curServerName = request.getServerName(); // abc.aliyun-inc.net
        int curServerPort = request.getServerPort();

        if (!curScheme.equals(sysConfig.getConfValue()))
        {
        	curScheme = sysConfig.getConfValue();
        }
        String curlocaleUrlRoot = curScheme + "://" + curServerName;
        if (curServerPort != 80) {
            curlocaleUrlRoot += ":" + curServerPort;
        }

        return curlocaleUrlRoot;
    }
    /**
     * 获取主页url
     */
    public String getHomeUrl(HttpServletRequest request) {
        return getLocaleUrlRoot(request);
    }

    /**
     * 获取登录url
     */
    public String getLoginUrl(HttpServletRequest request) {
        return getLocaleUrlRoot(request) + "/login/login.htm";
    }
    
    /**
     * 获取https地址
     */
    public String getHttpsUrl(HttpServletRequest request) {
        return getLocaleUrlRoot(request) + request.getRequestURI();
    }

}
