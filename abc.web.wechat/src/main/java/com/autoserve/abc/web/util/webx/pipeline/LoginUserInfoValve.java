package com.autoserve.abc.web.util.webx.pipeline;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValve;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.WeChatMenuDO;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.authority.RoleService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeRoleService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.intf.wechat.WeChatMenuService;
import com.autoserve.abc.service.biz.intf.wxproxy.WxProxyService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.util.QueryStringBuilder;

/**
 * 获取登录用户信息
 */
public class LoginUserInfoValve extends AbstractValve {

    private static final Logger log = LoggerFactory.getLogger(LoginUserInfoValve.class);

    @Autowired
    private UserService         userService;

    @Autowired
    private RoleService         roleService;

    @Autowired
    private DeployConfigService deployConfigService;

    @Autowired
    private HttpServletRequest  request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private EmployeeRoleService employeeRoleService;

    @Autowired
    private HttpSession         session;
    @Autowired
    private SysConfigService    sysConfigService;

    @Resource
    private WeChatMenuService   weChatMenuService;

    @Resource
    private WxProxyService      wxProxyService;

    @Override
    public void invoke(PipelineContext pipelineContext) throws Exception {

        try {
            TurbineRunDataInternal rundata = (TurbineRunDataInternal) getTurbineRunData(request);
            //查询站点是否关闭
            PlainResult<SysConfig> resultSysConfig = sysConfigService.querySysConfig(SysConfigEntry.SHUTDOWN_SITE);
            SysConfig sysConfig = resultSysConfig.getData();
            if (sysConfig != null && "1".equals(sysConfig.getConfValue())
                    && !URLDecoder.decode(request.getRequestURI(), "utf-8").startsWith("/offSite/")) {
                PlainResult<SysConfig> sysConfigInfo = sysConfigService.querySysConfig(SysConfigEntry.SHUTDOWN_INFO);
                rundata.getContext().put("shutdownInfo", sysConfigInfo.getData().getConfValue());
                rundata.forwardTo("/offSite").end();
            } else {

                final String loginUrl = deployConfigService.getLoginUrl(request) + "?redirectUrl="
                        + URLEncoder.encode(getLocaleUrl(rundata), "utf-8");

                //过滤来自微信的跳转
                if (URLDecoder.decode(request.getRequestURI(), "utf-8").startsWith("/wechat")) {

                    String wechatCode = request.getParameter("code");
                    String menuKey = request.getParameter("state");
                    //若授权请求Code为空则跳转登陆页面
                    if (wechatCode != null && !"".equals(wechatCode)) {
                        //获取应用名ID和应用密钥
                        String appid = SystemGetPropeties.getBossString("AppID");
                        String secret = SystemGetPropeties.getBossString("AppSecret");
                        //获取访问用户的openid
                        PlainResult<Map<String, String>> plainResult = wxProxyService.getOpenidByCode(appid, secret,
                                wechatCode);
                        Map<String, String> resultMap = plainResult.getData();
                        String openid = resultMap.get("openid");

                        System.out.println("进入微信跳转链接.........." + openid + "........" + menuKey + "......"
                                + URLDecoder.decode(request.getRequestURI(), "utf-8"));
                        // 判断是否为免登陆或绑定平台账号
                        Integer state = 1;
                        if ("FREE_LOGIN".equals(menuKey)) {
                            state = -1;
                        }
                        PlainResult<UserDO> result = userService.queryUserByWeChatId(openid, state);
                        UserDO userDO = result.getData();
                        //验证用户是否存在
                        if (userDO != null || "BIND_ACCOUNT".equals(menuKey) || "DOWNLOAD_APP".equals(menuKey)
                                || "ABOUT_US".equals(menuKey) || "INVEST".equals(menuKey)) {
                            //用户跳转
                            if (menuKey != null && !"".equals(menuKey)) {
                                //查询menuKey对应的跳转链接
                                WeChatMenuDO weChatMenuDO = weChatMenuService.queryWeChatMenuByKey(menuKey).getData();
                                if (weChatMenuDO != null && weChatMenuDO.getMenuUrl() != null) {
                                    if ((!"BIND_ACCOUNT".equals(menuKey) && !"DOWNLOAD_APP".equals(menuKey)
                                            && !"ABOUT_US".equals(menuKey) && !"INVEST".equals(menuKey))
                                            || userDO != null) {
                                        PlainResult<User> findResult = userService.login(userDO.getUserName(),
                                                userDO.getUserPwd(), IPUtil.getUserIpAddr(request),"Wechat");
                                        //用户信息放入session
                                        session.setAttribute("user", findResult.getData());
                                    }
                                    //页面跳转
                                    if ("/".equals(weChatMenuDO.getMenuUrl())) {
                                        rundata.forwardTo("/index/index").end();
                                    } else if ("/account/seCenter/UserFreeLogin?userWechatId=".equals(weChatMenuDO
                                            .getMenuUrl())) {//免登陆功能
                                        rundata.setRedirectLocation(weChatMenuDO.getMenuUrl() + openid);
                                    } else if ("/account/seCenter/BindWechat?userWechatId=".equals(weChatMenuDO
                                            .getMenuUrl())) {//绑定微信账号
                                        rundata.setRedirectLocation(weChatMenuDO.getMenuUrl() + openid);
                                    } else {
                                        rundata.forwardTo(weChatMenuDO.getMenuUrl()).end();
                                    }
                                } else {
                                    rundata.setRedirectLocation(loginUrl);//跳转登陆页面
                                }
                            } else {
                                rundata.setRedirectLocation(loginUrl);//跳转登陆页面
                            }
                        } else {
                            rundata.setRedirectLocation(loginUrl);//跳转登陆页面
                        }
                    } else {
                        rundata.setRedirectLocation(loginUrl);//跳转登陆页面
                    }

                } else if (URLDecoder.decode(request.getRequestURI(), "utf-8").startsWith("/login")) {
                    System.out.println("进入登陆跳转链接........................");
                    //登陆不进行拦截
                } else if ("/".equals(URLDecoder.decode(request.getRequestURI(), "utf-8"))) {
                    System.out.println("进入/跳转链接........................"
                            + URLDecoder.decode(request.getRequestURI(), "utf-8"));
                } else if (URLDecoder.decode(request.getRequestURI(), "utf-8").startsWith("/register/")) {
                    //用户注册不拦截
                } else if (URLDecoder.decode(request.getRequestURI(), "utf-8").startsWith(
                        "/account/seCenter/BindWechat")
                        || URLDecoder.decode(request.getRequestURI(), "utf-8").startsWith(
                                "/account/myAccount/json/BindingWechat")
                        || URLDecoder.decode(request.getRequestURI(), "utf-8").startsWith(
                                "/account/seCenter/bindSuccess")) {
                    //用户信息绑定不拦截
                } else if (URLDecoder.decode(request.getRequestURI(), "utf-8").startsWith("/account/")
                        || URLDecoder.decode(request.getRequestURI(), "utf-8").startsWith("/apply/pledgeApply")
                        || URLDecoder.decode(request.getRequestURI(), "utf-8").startsWith("/apply/securedApply")) {
                    //获取用户信息
                    User user = (User) session.getAttribute("user");
                    //若用户信息为空则跳转登陆页面 
                    if (user == null || user.getUserId() == null) {
                        rundata.setRedirectLocation(loginUrl);
                    }
                } else {

                }
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            pipelineContext.invokeNext();
        }
    }

    /**
     * 获取当前url
     */
    private String getLocaleUrl(TurbineRunDataInternal rundata) {
        // 获取当前url根路径，如 http://abc.aliyun-inc.net
        String curScheme = rundata.getRequest().getScheme(); // http
        String curServerName = rundata.getRequest().getServerName(); // abc.aliyun-inc.net
        int curServerPort = rundata.getRequest().getServerPort();
        String curUri = rundata.getRequest().getRequestURI();
        String curQueryString = rundata.getRequest().getQueryString();

        String curlocaleUrlRoot = curScheme + "://" + curServerName;
        if (curServerPort != 80) {
            curlocaleUrlRoot += ":" + curServerPort;
        }

        String localeUrl = curlocaleUrlRoot;
        if (StringUtils.isNotBlank(curUri)) {
            localeUrl = localeUrl + curUri;
        }

        if (StringUtils.isNotBlank(curQueryString)) {
            Map<String, String> queryPairs = splitQuery(curQueryString);
            QueryStringBuilder queryStrBuilder = new QueryStringBuilder();
            for (String key : queryPairs.keySet()) {
                queryStrBuilder.addQueryParameter(key, queryPairs.get(key));
            }

            try {
                localeUrl = localeUrl + queryStrBuilder.encode("UTF-8");
            } catch (UnsupportedEncodingException e) {
            }
        }

        return localeUrl;
    }

    private Map<String, String> splitQuery(String queryString) {
        Map<String, String> queryPairs = new LinkedHashMap<String, String>();
        try {
            String[] pairs = queryString.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                queryPairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                        URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }

            return queryPairs;
        } catch (Exception e) {
            return queryPairs;
        }
    }

}
