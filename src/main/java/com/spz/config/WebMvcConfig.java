package com.spz.config;

import com.spz.common.JacksonObjectMapper;
import com.spz.controller.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
//    重定向 http -> https

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Value("${spzStore.save}")
    private String savePath;

    /*
     *   设置静态资源映射
     * */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        String storePath = "file:///"+savePath.substring(0,savePath.length()-6);
        log.info("开始静态资源映射...");
        log.info("资源路径:{}", storePath);
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
//        registry.addResourceHandler("/store/**").addResourceLocations("classpath:/store/");
        registry.addResourceHandler("/store/**").addResourceLocations(storePath);

    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("拓展消息转换器...");
        // 创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器 底层使用Jackson将java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的转换器追加到MVC框架的转换器容器集合中
        converters.add(0, messageConverter);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 登录注册放行
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/spz/**")
                .excludePathPatterns("/spz/user/login","/spz/user/register");
    }


}
