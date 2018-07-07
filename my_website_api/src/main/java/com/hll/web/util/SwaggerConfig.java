package com.hll.web.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@EnableSwagger2
@Configuration
public class SwaggerConfig {
	 @Bean
	    public Docket createRestApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .apiInfo(apiInfo())
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.hll.web.controller")) // 注意修改此处的包名
	                .paths(PathSelectors.any())
	                .build();
	    }

	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("接口列表 v1.0.0") // 任意，请稍微规范点
	                .description("接口列表,注：成功code返回一般为1,错误返回code一般为0,如有特殊情况会注明返回code值") // 任意，请稍微规范点
	                .termsOfServiceUrl("http://localhost:8080/my_website_api/swagger-ui.html") // 将“url”换成自己的ip:port
	                .contact("lianglaing") // 无所谓（ 是作者的别称）
	                .version("1.0.0")
	                .license("版权归liangliang所有")
  	                .build();
	    }
}
 