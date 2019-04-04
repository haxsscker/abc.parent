package com.autoserve.abc.web.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;


/**
 * 因为部署的原因，需要将contextPath 替换为 ""
 */
public class PortalFilter implements Filter {

    //private static final Logger log = LoggerFactory.getLogger(PortalFilter.class);

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        
        /*
        log.info("do filter getContextPath :" + req.getContextPath());
        log.info("do filter getPathInfo :" + req.getPathInfo());
        log.info("do filter getRequestURL :" + req.getRequestURL());
        log.info("do filter getServletPath :" + req.getServletPath());
        log.info("do filter getQueryString :" + req.getQueryString());
        log.info("do filter getRequestURI:" + req.getRequestURI());
        */
        
        HttpServletRequestWrapper reqw = new HttpServletRequestWrapper((HttpServletRequest) request) {
            @Override
            public String getContextPath() {
                return "";
            }

            @Override
            public String getRequestURI() {
                return req.getRequestURI().substring(req.getContextPath().length());
            }

            @Override
            public StringBuffer getRequestURL() {
                int start = req.getRequestURL().indexOf(req.getContextPath(), 0);
                if (start > 0) {
                    return req.getRequestURL().replace(start, start + req.getContextPath().length(), "");
                } else {
                    return req.getRequestURL();
                }
            }
        };
        chain.doFilter(reqw, response);
    }

    @Override
    public void destroy() {
    
    }

}
