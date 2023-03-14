package org.srn.web.Recruiter.Configuration.swagger_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Configuration_Swagger extends WebMvcConfigurationSupport {

	@Bean
	public Docket postsApi() {

		Docket docket = new Docket(DocumentationType.SWAGGER_2).groupName("Recruiter Microservice").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("org.srn.web.Recruiter"))
				.paths(PathSelectors.any()).build();
		docket.tags(new Tag("Recruiter Microservice Rest  Api", "Endpoints to perform Recruiter operation."));
		docket.useDefaultResponseMessages(false);
		return docket;

	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Recruiter Microservice Rest API")
				.contact(new Contact("Sroniyan", "http://www.sroniyan.com", "pankaj.sharma@sroniyan.com"))
				.description(" Micro service to facilitate recruitment process ")
				.termsOfServiceUrl("https://www.sroniyan.com/index.html").license("Sroniyan License")
				.licenseUrl("pankaj.sharma@sroniyan.com").version("2.0.0").build();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
