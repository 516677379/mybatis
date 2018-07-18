package com.mybatis.config.Filters;

import org.springframework.web.bind.annotation.RequestMethod;

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
   解决跨域带cookie
      1.服务端添加请求信息
      2.客户端需添加 withCredentials=true
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

        String origin = req.getHeader("Origin");
        if(origin == null) {
            origin = req.getHeader("Referer");
        }

        String requestURI = req.getRequestURI();
        System.out.println("执行过滤操作"+requestURI);
        //转换request和response类型至HttpServlet 有人说可能需要转换req/resp如上，否则报错，此处无问题

        //解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", origin);// origin或* // 允许指定域访问跨域资源
        response.setHeader("Access-Control-Allow-Headers", "Authentication");
        response.setHeader("Access-Control-Allow-Credentials", "true");// 允许客户端携带跨域cookie，此时origin值不能为“*”，只能为指定单一域名

        if(RequestMethod.OPTIONS.toString().equals(req.getMethod())) {
            String allowMethod = req.getHeader("Access-Control-Request-Method");
            String allowHeaders = req.getHeader("Access-Control-Request-Headers");
            response.setHeader("Access-Control-Max-Age", "86400");            // 浏览器缓存预检请求结果时间,单位:秒
            response.setHeader("Access-Control-Allow-Methods", allowMethod);  // 允许浏览器在预检请求成功之后发送的实际请求方法名
            response.setHeader("Access-Control-Allow-Headers", allowHeaders); // 允许浏览器发送的请求消息头
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
