package com.mybatis.config.Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zyf on 2018/1/2.
 *  作用：过滤
 *  例如：比如过拦截掉我们不需要的接口请求[过滤指定的url]
         修改请求（request）和响应（response）内容
         完成CORS跨域请求等等
 */
@WebFilter(filterName = "myfilter", urlPatterns = "/*")
public class MyFilter implements Filter{

    public void init(FilterConfig filterConfig) {
        System.out.println("过滤器初始化");
    }

    public void destroy() {
        System.out.println("过滤器销毁");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = req.getRequestURI();
        System.out.println("执行过滤操作"+requestURI);
        //转换request和response类型至HttpServlet 有人说可能需要转换req/resp如上，否则报错，此处无问题

        //解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authentication");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
