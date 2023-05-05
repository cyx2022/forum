package com.cyx.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(meta())
                .select().apis(RequestHandlerSelectors
                        .withClassAnnotation(RestController.class))
                .build();
    }
    private ApiInfo meta() {
        return new ApiInfoBuilder()
                .title("我的API")
                .description("这是我的接口文档")
                .version("1.0.1")
                .build();
    }

}
