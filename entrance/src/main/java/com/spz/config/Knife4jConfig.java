package com.spz.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {
    @Bean
    public GroupedOpenApi adminApi() { // 创建了一个api接口的分组
        return GroupedOpenApi.builder()
                .group("admin-api") // 分组名称
                .pathsToMatch("/**") // 接口请求路径规则
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info() // 基本信息配置
                        .title("Knife4j整合Swagger3 Api接口文档") // 标题
                        .description("Knife4j后端接口服务...") // 描述Api接口文档的基本信息
                        .version("v1.0.0") // 版本
                );
    }
}