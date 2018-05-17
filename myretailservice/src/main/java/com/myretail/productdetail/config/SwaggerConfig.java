package com.myretail.productdetail.config;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/product.*"))
                .build()
                .pathMapping("/")
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        @SuppressWarnings("rawtypes")
		ApiInfo apiInfo = new ApiInfo("MyRetailAPI", "MyRetailAPI retrieves product information and updates price of the product.", "API TOS", "Terms of service", new Contact("Mahipal Narra", "www.myretail.com", "support@myretail.com"), "Licensed by My RETAIL", "www.license.myretail.com",new ArrayList<VendorExtension>());
        return apiInfo;
    }
}