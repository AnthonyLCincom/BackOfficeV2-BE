package com.lcincom.bo.configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Set;

@EnableSwagger2
@Configuration
// @Profile("dev") // profile dev only
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    protected RequestMethod[] mainApiMethods = {RequestMethod.GET, RequestMethod.POST,
            RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE};

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public Docket permissionApis() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("form")
                //.protocols(Collections.singleton("https"))
                //.host("abc.cc")
                .select()
                .apis(allowGetRequestHandlers())
                .paths(PathSelectors.ant("/controller/**")).build()
                .apiInfo(metadata());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("full-hub-api").select()
                .apis(RequestHandlerSelectors.any())
                // .apis(RequestHandlerSelectors.basePackage("com.dxc.integral.springhub.rest.web")
                .paths(PathSelectors.any()).build();
        // .pathMapping("/api-docs");
    }

    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("Permission Export APIs")
                .select()
                .apis(allowRequestHandlers(mainApiMethods))
                .apis(RequestHandlerSelectors.basePackage("com.lcincom.accessControl.controller"))
                .paths(PathSelectors.ant("/**"))
                .build().apiInfo(metadata());
    }

    @Bean
    public Docket customApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("Custom APIs")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.training.controller"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()))
                .apiInfo(metadata());
    }

    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("read", "for read operations"),
                new AuthorizationScope("write", "for write operations"),
                new AuthorizationScope("foo", "Access foo API") };
        return scopes;
    }

    private SecurityScheme securityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint("AUTH_SERVER" + "/token", "oauthtoken"))
                .tokenRequestEndpoint(
                        new TokenRequestEndpoint("AUTH_SERVER" + "/authorize", "CLIENT_ID", "CLIENT_ID"))
                .build();

        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
                .grantTypes(Arrays.asList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
        return oauth;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Arrays.asList(new SecurityReference("spring_oauth", scopes())))
                .forPaths(PathSelectors.regex("/trainingClassTests.*"))
                .build();
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId("CLIENT_ID")
                .clientSecret("CLIENT_SECRET")
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    @Bean
    public Docket allGetApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(allowGetRequestHandlers())
                .paths(PathSelectors.ant("/api/**")).build()
                .apiInfo(metadata());
    }

    @Bean
    public Docket agencyDevelopmentApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("Development API")
                .select()
                .paths(Predicates.not(PathSelectors.ant("/error")))
                .paths(Predicates.not(PathSelectors.ant("/api/school")))
                .build().apiInfo(metadata());
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder().title("Development APIs Service")
                .description("REST API for BE G2 resource").version("0.0.1-SNAPSHOT")
                .contact(new Contact("LCINCOM", "http://www.lcincom.com.vn", "dev.lcincom@gmail.com"))
                .build();
    }

    protected Predicate<RequestHandler> allowGetRequestHandlers() {
        return new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                Set<RequestMethod> methods = input.supportedMethods();
                return methods.contains(RequestMethod.GET);
            }
        };
    }

    protected Predicate<RequestHandler> allowRequestHandlers(RequestMethod... httpMethods) {
        return new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                Set<RequestMethod> methods = input.supportedMethods();
                return Arrays.asList(httpMethods).containsAll(methods);
            }
        };
    }
}