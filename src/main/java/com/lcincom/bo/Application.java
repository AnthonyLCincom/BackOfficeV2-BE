package com.lcincom.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;
import java.util.Map;

@SpringBootApplication
@EnableMongoRepositories
@EnableElasticsearchRepositories
public class Application extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext ctx) throws Exception {
		return args -> {

			ConfigurableEnvironment env = (ConfigurableEnvironment) ctx.getEnvironment();

			printSources(env);
			System.out.println("---- System properties -----");
			printMap(env.getSystemProperties());
			System.out.println("---- System Env properties -----");
			printMap(env.getSystemEnvironment());

			System.out.println("Let's inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				logger.info(beanName);
			}
			RequestMappingHandlerMapping mapping = (RequestMappingHandlerMapping) ctx
					.getBean("requestMappingHandlerMapping");
			Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

			for (Map.Entry entry : map.entrySet()) {
				logger.info(entry.getKey() + ", " + entry.getValue());
			}
		};
	}

	private static void printSources(ConfigurableEnvironment env) {
		System.out.println("---- property sources ----");
		for (PropertySource<?> propertySource : env.getPropertySources()) {
			System.out.println("name =  " + propertySource.getName() + "\nsource = "
					+ propertySource.getSource().getClass() + "\n");
		}
	}

	private static void printMap(Map<?, ?> map) {
		map.entrySet().stream().forEach(e -> System.out.println(e.getKey() + " = " + e.getValue()));

	}

	@Bean
	public ErrorPageFilter errorPageFilter() {
		return new ErrorPageFilter();
	}

	@Bean
	public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(filter);
		filterRegistrationBean.setEnabled(false);
		return filterRegistrationBean;
	}

}
