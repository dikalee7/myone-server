package info.myone.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import info.myone.security.filter.AjaxLoginProcessingFilter;
import info.myone.security.handler.AjaxAccessDeniedHandler;
import info.myone.security.handler.AjaxAuthenticationFailureHandler;
import info.myone.security.handler.AjaxAuthenticationSuccessHandler;
import info.myone.security.provider.AjaxAuthenticationProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(0)
public class AjaxSecurityConfig {
	// AuthenticationSuccessHandler 구현체
	private final AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

	// AuthenticationFailureHandler 구현체
	private final AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;
	
	// AjaxAccessDeniedHandler 구현체
	private final AjaxAccessDeniedHandler ajaxAccessDeniedHandler;

	// @formatter:off
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// HttpSecurity 인가 API
			http.antMatcher("/api/**").authorizeHttpRequests(authorizeRequests -> authorizeRequests
				.anyRequest().authenticated());
			http.addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
			
			/*
	         exceptionHandle
	        */
	        http.exceptionHandling(handling -> handling.accessDeniedHandler(ajaxAccessDeniedHandler));
	        
	        http.csrf().disable();
        return http.build();
	}
	// @formatter:on

	@Bean
	AjaxLoginProcessingFilter ajaxLoginProcessingFilter() {
		AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();
		ajaxLoginProcessingFilter.setAuthenticationManager(ajaxAuthenticationManager());
		ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
		ajaxLoginProcessingFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
		return ajaxLoginProcessingFilter;
	}

	@Bean
	AuthenticationProvider ajaxAuthenticationProvider() {
		return new AjaxAuthenticationProvider();
	}

	@Bean
	AuthenticationManager ajaxAuthenticationManager() {
		return new ProviderManager(ajaxAuthenticationProvider());
	}
}
