package com.autoserve.abc.web.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * 部署配置，为web模块启动运行时提供配置参数
 */
public class DeployConfigService {

    private String env;

    public void setEnv(String env) {
        this.env = env;
    }

    /**
     * 获取部署环境
     */
    public String getEnv() {
        return env;
    }

    /**
     * 获取当前urlRoot
     */
    private String getLocaleUrlRoot(HttpServletRequest request) {
        // 获取当前url根路径，如 http://abc.aliyun-inc.net
        String curScheme = request.getScheme(); // http
        String curServerName = request.getServerName(); // abc.aliyun-inc.net
        int curServerPort = request.getServerPort();

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

}
