package com.tenniscourts.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.tenniscourts"))
                .paths(PathSelectors.any())
                .build();
    }
    
        private ApiKey apiKey() {
	    return new ApiKey("mykey", "api_key", "header");
	}
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Api")
                .build();
    }
	
	private SecurityContext securityContext() {
	    return SecurityContext.builder()
	        .securityReferences(defaultAuth())
	        .forPaths(PathSelectors.regex("/anyPath.*"))
	        .build();
	}
	
	List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope
	        = new AuthorizationScope("global", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return newArrayList(
	        new SecurityReference("mykey", authorizationScopes));
	}
	
	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration(
	        "test-app-guest-id",
	        "test-app-realm",
	        "test-app",
	        "apiKey");
	}
     
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
