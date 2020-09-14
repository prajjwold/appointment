package com.notablehealth.appointment.swagger;

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

@Configuration
@EnableSwagger2
//@EnableSwagger2WebMvc
//@Import({SpringDataRestConfiguration.class, BeanValidatorPluginsConfiguration.class})
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //.apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.notablehealth.appointment"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.getApiInfo());
    }
    
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Doctors Appointment API")
                .version("1.0")
                .description("A basic calendar API for managing a list of physicians and a list of appointments for the selected physician")
                .contact(new Contact("Prajjwol Dandekhya", "http://pdandekhya.com", "dprajjwol@gmail.com"))
                .license("Apache License Version 2.0")
                .build();
    }
}