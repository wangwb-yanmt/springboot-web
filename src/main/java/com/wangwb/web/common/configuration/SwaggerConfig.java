package com.wangwb.web.common.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.models.Contact;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置类
 * @author wangwb@sparknet.com.cn
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
     * 用于配置swagger2，包含文档基本信息
     * 指定swagger2的作用域（这里指定包路径下的所有API）
     * @return Docket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        			 .groupName("wangwb")
                .apiInfo(apiInfo())
                .select()
                	//指定需要扫描的controller
                .apis(RequestHandlerSelectors.basePackage("com.wangwb.web.controller"))
                .paths(PathSelectors.any())
                .build().securitySchemes(securitySchemes());
    }
 
    /**
     * 构建文档基本信息，用于页面显示，可以包含版本、
     * 联系人信息、服务地址、文档描述信息等
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                 //标题
                .title("springboot demo")
                .description("接口文档")
                .termsOfServiceUrl("http://192.168.120.208:7777/")
                .version("1.0")
                .build();
    }
    
    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeys;
    }

	
}
