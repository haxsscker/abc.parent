package com.autoserve.abc.web.util.webx.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 因为部署的原因，需要将contextPath 替换为 ""
 */
public class CleanContextPathFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(CleanContextPathFilter.class);

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;

        //log.info("do filter getContextPath :" + req.getContextPath());
        //log.info("do filter getPathInfo :" + req.getPathInfo());
        //log.info("do filter getRequestURL :" + req.getRequestURL());
        //log.info("do filter getServletPath :" + req.getServletPath());
        //log.info("do filter getQueryString :" + req.getQueryString());
        //log.info("do filter getRequestURI:" + req.getRequestURI());

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
