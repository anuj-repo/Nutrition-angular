package com.fertilizer.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author Dhiraj
 *
 */
@Configuration
public class LoggerFilterInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[0];
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[0];
	}

	@Override
	protected String[] getServletMappings() {
		return new String[0];
	}

	@Override
	protected Filter[] getServletFilters() {
		DelegatingFilterProxy filterProxy = new DelegatingFilterProxy();
		filterProxy.setTargetBeanName("myLoggerFilter");
		return new Filter[] { filterProxy };
	}

}
