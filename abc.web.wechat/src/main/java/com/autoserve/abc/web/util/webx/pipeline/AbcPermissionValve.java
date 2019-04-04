//package com.autoserve.abc.web.util.webx.pipeline;
//
//import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.alibaba.citrus.service.pipeline.PipelineContext;
//import com.alibaba.citrus.service.pipeline.support.AbstractValve;
//import com.alibaba.citrus.turbine.TurbineRunDataInternal;
//import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
//import com.autoserve.abc.service.biz.result.PlainResult;
//import com.autoserve.abc.web.helper.LoginUserInfoHelper;
//
///**
// * 权限验证 (通过 abc 接口验证权限)
// */
//public class AbcPermissionValve extends AbstractValve {
//
//    private static final Logger    logger  = LoggerFactory.getLogger(AbcPermissionValve.class);
//
//    private static final String    appName = "abc-app";
//
//    @Autowired
//    private HttpServletRequest     request;
//
//    @Autowired
//    private HttpServletResponse    response;
//
//    @Autowired
//    private EmployeeService        employeeService;
//
//    @Autowired
//    private MinasConfiger          minasConfigHelper;
//
//    private TurbineRunDataInternal rundata;
//
//    @Override
//    public void invoke(PipelineContext pipelineContext) throws Exception {
//        try {
//            rundata = (TurbineRunDataInternal) getTurbineRunData(request);
//
//            String currentUri = getCurrentUri();
//            if (minasConfigHelper.getAbcPermissionEnable() == null || !minasConfigHelper.getAbcPermissionEnable()) {
//                logger.info("PermissionValve AbcPermissionEnable:off");
//                return;
//            }
//
//            if (isExcludesUri(currentUri)) {
//                logger.info("PermissionValve ExcludesUri:" + currentUri);
//                return;
//            }
//
//            if (LoginUserInfoHelper.getUserInfo() == null || StringUtil.isBlank(LoginUserInfoHelper.getUserInfo().getEmpId())) {
//                logger.error("aliyun-abc userinfo is null");
//                return;
//            }
//
//            boolean isPermission = isPermission(LoginUserInfoHelper.getUserInfo().getEmpId(), currentUri);
//            if (!isPermission) {
//                // 没有权限，则内部重定向到错误页面
//                rundata.forwardTo("error/noPermission").end();
//            }
//
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        } finally {
//            pipelineContext.invokeNext();
//        }
//    }
//
//    /**
//     * 判断当前url是否访问权限
//     */
//    private boolean isPermission(String empId, String uri) {
//        boolean isPermission = true;
//
//        uri = StringUtil.trimStart(uri, "/");
//        uri = StringUtil.trimEnd(uri, "/");
//        uri = trimEndStr(uri, ".htm");
//        uri = trimEndStr(uri, ".html");
//        uri = trimEndStr(uri, ".json");
//        uri = trimEndStr(uri, ".do");
//        uri = StringUtil.replace(uri, "/", ".");
//
//        String permissionToken = appName + ".";
//        permissionToken += uri;
//
//        PlainResult<AuthenticationResultModel> result = queryUserAccessControlService
//                .checkUserPermissionByEmpIdAndPermissionToken(empId, permissionToken);
//
//        if (result != null && result.isSuccess() && result.getData() != null
//                && result.getData().getIsPermission() == false) {
//            isPermission = false;
//        }
//
//        return isPermission;
//    }
//
//    private String trimEndStr(String str, String stripChars) {
//        if (str == null || stripChars == null) {
//            return "";
//        }
//
//        int occur = str.lastIndexOf(stripChars);
//        if (occur == -1) {
//            return str;
//        }
//
//        return str.substring(0, occur);
//    }
//
//    /**
//     * 忽略权限验证uri
//     *
//     * @return
//     */
//    private boolean isExcludesUri(String uri) {
//        // 不记录日志白名单
//        List<String> excludesUriList = new ArrayList<String>();
//        excludesUriList.add("/");
//        //excludesUriList.add("/admin/app_list.htm");
//        //excludesUriList.add("/admin/app_list.htm");
//
//        if (excludesUriList.contains(uri)) {
//            return true;
//        }
//
//        return false;
//    }
//
//    /**
//     * 获取当前uri
//     *
//     * @return
//     */
//    private String getCurrentUri() {
//        String curUri = rundata.getRequest().getRequestURI();
//        return curUri;
//    }
//
//    /**
//     * 获取当前url
//     *
//     * @return
//     */
//    private String getLocaleUrl() {
//        // 获取当前url根路径，如 http://abc.aliyun-inc.net
//        String curScheme = rundata.getRequest().getScheme(); // http
//        String curServerName = rundata.getRequest().getServerName(); // abc.aliyun-inc.net
//        int curServerPort = rundata.getRequest().getServerPort();
//        String curQueryString = rundata.getRequest().getQueryString();
//        String curUri = rundata.getRequest().getRequestURI();
//        String curlocaleUrlRoot = curScheme + "://" + curServerName;
//        if (curServerPort != 80) {
//            curlocaleUrlRoot += ":" + curServerPort;
//        }
//
//        String localeUrl = curlocaleUrlRoot + curUri;
//        if (StringUtil.isBlank(curQueryString) == false) {
//            localeUrl += "?" + curQueryString;
//        }
//
//        return localeUrl;
//    }
//
//}
