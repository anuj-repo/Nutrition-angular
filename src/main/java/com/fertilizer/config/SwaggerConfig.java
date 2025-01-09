package com.fertilizer.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

	@Autowired
	private MessageSource messageSource;

	@Value("${security.jwt.token.required}")
	private Boolean tokenrequired = false;

	@Autowired
	private LoggerInterceptor loggerInterceptor;

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.globalOperationParameters(Arrays.asList(new ParameterBuilder().name("Authorization")
						.description("Jwt token of header").modelRef(new ModelRef("string")).parameterType("header")
						.required(tokenrequired).build()))
				.select().apis(RequestHandlerSelectors.basePackage("com.fertilizer")).paths(PathSelectors.any())
				.build().apiInfo(apiEndPointsInfo());
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/documentation/v2/api-docs", "/v2/api-docs").setKeepQueryParams(true);
		registry.addRedirectViewController("/documentation/swagger-resources/configuration/ui",
				"/swagger-resources/configuration/ui");
		registry.addRedirectViewController("/documentation/swagger-resources/configuration/security",
				"/swagger-resources/configuration/security");
		registry.addRedirectViewController("/documentation/swagger-resources", "/swagger-resources");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/documentation/**").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/**").addResourceLocations("classpath:/resources/");
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource);
		return bean;
	}

	@Override
	public Validator getValidator() {
		return validator();
	}

	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title("Fertilizer REST API").description("Fertilizer REST API")
				.contact(new Contact("Mukul Chauhan", "https://www.mukulchauhan.in", "mukulchauhan664@gmail.com"))
				.license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.version("0.0.1-SNAPSHOT").build();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggerInterceptor).addPathPatterns("/api/**");
	}
}