package org.linbo.demo.swagger2.config;

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
 * @author LinBo
 * @date 2019-07-11 16:17
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot集成Swagger2例子")
                .description("一个账户新增服务例子")
                .contact(new Contact("bob", "", "710850609@qq.com"))
                .version("1.0")
                .build();
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.linbo.demo.swagger2.service"))
                .paths(PathSelectors.any())
                .build();
    }
}
