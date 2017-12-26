package com.mybatis.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * Created by 51667 on 2017/12/26.
 *
 *  测试效果无，或location设置为绝对路径；不要用相对路径
 * fileSizeThreshold	int	是	当数据量大于该值时，内容将被写入文件。
 * location	String	是	存放生成的文件地址。
 * maxFileSize	long	是	允许上传的文件最大值。默认值为 -1，表示没有限制。
 * maxRequestSize	long	是	针对该 multipart/form-data 请求的最大数量，默认值为 -1，表示没有限制。
 */
@Configuration
public class MultipartConfig {

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location="/";
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }
}
