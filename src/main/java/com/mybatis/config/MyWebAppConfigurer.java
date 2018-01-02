package com.mybatis.config;

import com.mybatis.config.Interceptors.MyInterceptor1;
import com.mybatis.config.Interceptors.MyInterceptor2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by zyf on 2018/1/2.
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter{
    /**
     * 1、创建我们自己的拦截器类并实现 HandlerInterceptor接口。
     2、创建一个Java类继承WebMvcConfigurerAdapter，并重写 addInterceptors 方法。
     2、实例化我们自定义的拦截器，然后将对像手动添加到拦截器链中（在addInterceptors方法中添加）
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        //注意拦截顺序 与处理后返回在经过拦截器的顺序 加入时 1->2  拦截进入2->1 拦截完成 1->2
        registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/**");
        registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
