package com.enjoyor.bigdata.EnloopUtilXMLService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/26 15:39
 * @email aaron19940628@gmail.com
 * @date 2017/11/26 15:39.
 * @description
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket postRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.enjoyor.bigdata.EnloopUtilXMLService.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("XML-Service API Document")//大标题
                .description("")//描述信息
                .version("0.1")//版本
                .license("The Apache License, Version 2.0")//标准信息
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")//标准信息连接
                .contact(new Contact("AaronY", "www.enjoyor.net", "aaron19940628@gmail.com"))
                .build();
    }
}
