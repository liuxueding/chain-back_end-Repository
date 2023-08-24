package com.chain.chainfornewbackendapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // 配置类
@EnableSwagger2 // 开启 swagger2 的自动配置
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        // 创建一个 swagger 的 bean 实例
        return new Docket(DocumentationType.SWAGGER_2)
                // 配置基本信息
                .apiInfo(apiInfo())
                ;
    }

    // 基本信息设置
    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                "lxd", // 作者姓名
                "https://github.com/liuxueding", // 作者网址
                "3409329750@qq.com"); // 作者邮箱
        return new ApiInfoBuilder()
                .title("链时代招新网站-接口文档") // 标题
                .description("后端API-用户的登录注册、文件上传") // 描述
                .termsOfServiceUrl("https://www.baidu.com") // 跳转连接
                .version("1.0") // 版本
                .license("api仓库")
                .licenseUrl("https://github.com/liuxueding/chain-back_end-Repository")
                .contact(contact)
                .build();
    }



}
