package br.hackathon.com.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.hackathon.com.application.components.AuthenticationFilter;

@Configuration
public class AuthConfiguration {

	@Autowired AuthenticationFilter authenticationFilter;
	
	@Bean
	FilterRegistrationBean<AuthenticationFilter> registrationFilter() {
		
		var filter = new FilterRegistrationBean<AuthenticationFilter>();
		filter.setFilter(authenticationFilter);
		
		filter.addUrlPatterns("/api/*");
		
		return filter;
	}
}
