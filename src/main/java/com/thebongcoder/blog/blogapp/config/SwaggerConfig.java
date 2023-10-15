package com.thebongcoder.blog.blogapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SwaggerConfig  {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.thebongcoder.blog.blogapp"))
                .paths(PathSelectors.regex("/.*"))
                .build().apiInfo(apiInfoMetaData());
    }
    private ApiInfo apiInfoMetaData() {
        return new ApiInfoBuilder().title("Blog-App")
                .description("Blog web app")
                .contact(new Contact("Blog - App", "https://www.thebongcoder.com/", "thebongcoder@gmail.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }

}
