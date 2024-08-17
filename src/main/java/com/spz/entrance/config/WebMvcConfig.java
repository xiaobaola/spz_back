package com.spz.entrance.config;

import com.spz.public_resource.common.JacksonObjectMapper;
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
    @Value("${spzStore.path}")
    private String basePath;

    /*
     *   设置静态资源映射
     * */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        log.info("开始静态资源映射...");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");

        // 方案1 项目相对路径 安全
//        registry.addResourceHandler("/store/**").addResourceLocations("classpath:/store/");

        // 方案2 直接用磁盘的路径  自然不用 区分是tomcat服务器还是java项目文件中的 缺点：不安全
        // 路径格式一定要正确
        String storePath = "file:///"+ basePath.substring(0, basePath.length()-6);
        log.info("资源路径:{}", storePath);
        registry.addResourceHandler("/store/**").addResourceLocations(storePath);
    }

    // 未使用
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
                .excludePathPatterns("/**/login","/**/logout","/spz/user/register","/spz/common/**");
    }


}
