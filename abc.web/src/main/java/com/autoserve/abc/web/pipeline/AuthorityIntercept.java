package com.autoserve.abc.web.pipeline;

import com.autoserve.abc.service.biz.intf.authority.RoleService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.AuthorityUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class AuthorityIntercept extends HandlerInterceptorAdapter {

    private static String MENU_PARAMETER    = "menuId";
    private static String ROLE_PARAMETER    = "roleId";
    public static String  BTN_TAG_PARAMETER = "btnTag";

    @Resource
    private RoleService   roleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        boolean need = AuthorityUtils.isNeedVerify(method);
        if (need == false) //方法不需要验证权限
            return true;
        else { //方法需要验证权限
            String menuId = request.getParameter(MENU_PARAMETER);
            String roleId = request.getParameter(ROLE_PARAMETER);
            String btnTag = request.getParameter(BTN_TAG_PARAMETER);
            if (menuId == null || roleId == null || btnTag == null) {
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html;charset=UTF-8");
                OutputStream out = response.getOutputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
                pw.println("{\"result\":false,\"errorMessage\":\"" + "参数不完整" + "\"}");
                pw.flush();
                pw.close();
            }
            PlainResult<Boolean> result = null;// = //roleService.queryRoleAndMenuAndBtn(Integer.parseInt(menuId), Integer
            //.parseInt(roleId), btnTag);
            if (result.getData()) { //有权限
                return true;
            } else { //权限不够
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html;charset=UTF-8");
                OutputStream out = response.getOutputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
                pw.println("{\"result\":false,\"errorMessage\":\"" + "权限不够" + "\"}");
                pw.flush();
                pw.close();
            }
        }
        return false;
    }
}
