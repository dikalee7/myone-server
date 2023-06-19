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

import info.myone.security.filter.ApiLoginProcessingFilter;
import info.myone.security.handler.ApiAccessDeniedHandler;
import info.myone.security.handler.ApiAuthenticationFailureHandler;
import info.myone.security.handler.ApiAuthenticationSuccessHandler;
import info.myone.security.provider.ApiAuthenticationProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(0)
public class ApiSecurityConfig {
	// AuthenticationSuccessHandler 구현체
	private final ApiAuthenticationSuccessHandler apiAuthenticationSuccessHandler;

	// AuthenticationFailureHandler 구현체
	private final ApiAuthenticationFailureHandler apiAuthenticationFailureHandler;
	
	// ApiAccessDeniedHandler 구현체
	private final ApiAccessDeniedHandler apiAccessDeniedHandler;

	// @formatter:off
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// HttpSecurity 인가 API
			http.antMatcher("/api/**").authorizeHttpRequests(authorizeRequests -> authorizeRequests
				.anyRequest().authenticated());
			http.addFilterBefore(ApiLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
			
			/*
	         exceptionHandle
	        */
	        http.exceptionHandling(handling -> handling.accessDeniedHandler(apiAccessDeniedHandler));
	        
	        http.csrf().disable();
	        
        return http.build();
	}
	// @formatter:on

	@Bean
	ApiLoginProcessingFilter ApiLoginProcessingFilter() {
		ApiLoginProcessingFilter ApiLoginProcessingFilter = new ApiLoginProcessingFilter();
		ApiLoginProcessingFilter.setAuthenticationManager(apiAuthenticationManager());
		ApiLoginProcessingFilter.setAuthenticationSuccessHandler(apiAuthenticationSuccessHandler);
		ApiLoginProcessingFilter.setAuthenticationFailureHandler(apiAuthenticationFailureHandler);
		return ApiLoginProcessingFilter;
	}

	@Bean
	AuthenticationProvider apiAuthenticationProvider() {
		return new ApiAuthenticationProvider();
	}

	@Bean
	AuthenticationManager apiAuthenticationManager() {
		return new ProviderManager(apiAuthenticationProvider());
	}
}
