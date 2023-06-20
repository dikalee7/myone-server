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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import info.myone.security.common.ApiLoginAuthenticationEntryPoint;
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
	
	@Bean
	AuthenticationProvider apiAuthenticationProvider() {
		return new ApiAuthenticationProvider();
	}

	@Bean
	AuthenticationManager apiAuthenticationManager() {
		return new ProviderManager(apiAuthenticationProvider());
	}
	
    // AuthenticationSuccessHandler 구현 객체
	private final ApiAuthenticationSuccessHandler apiAuthenticationSuccessHandler;

    //AuthenticationFailureHandler 구현 객체
    private final ApiAuthenticationFailureHandler apiAuthenticationFailureHandler;
	
	// AccessDeniedHandler 구현 객체
    private final ApiAccessDeniedHandler apiAccessDeniedHandler;


	// @formatter:off
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// HttpSecurity 인가 API
		http.antMatcher("/api/**").authorizeHttpRequests(authorizeRequests -> authorizeRequests
			.antMatchers("/api/messages").hasRole("MANAGER")
            .antMatchers("/api/login").permitAll()
			.anyRequest().authenticated());
		
		// http.addFilterBefore(apiLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
		
		/*
         exceptionHandle
        */
        http.exceptionHandling(handling -> handling
        		.authenticationEntryPoint(new ApiLoginAuthenticationEntryPoint())
        		.accessDeniedHandler(apiAccessDeniedHandler));
        
//        http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
        
        customConfigureApi(http);

	        
        return http.build();
	}
	// @formatter:on



	private void customConfigureApi(HttpSecurity http) throws Exception {
        http
                .apply(new ApiLoginConfigurer<>())
                .successHandlerApi(apiAuthenticationSuccessHandler)
                .failureHandlerApi(apiAuthenticationFailureHandler)
                .setAuthenticationManager(apiAuthenticationManager())
                .loginProcessingUrl("/api/login");
    }

//	@Bean
//	ApiLoginProcessingFilter apiLoginProcessingFilter() {
//		ApiLoginProcessingFilter ApiLoginProcessingFilter = new ApiLoginProcessingFilter();
//		ApiLoginProcessingFilter.setAuthenticationManager(apiAuthenticationManager());
//		ApiLoginProcessingFilter.setAuthenticationSuccessHandler(apiAuthenticationSuccessHandler);
//		ApiLoginProcessingFilter.setAuthenticationFailureHandler(apiAuthenticationFailureHandler);
//		return ApiLoginProcessingFilter;
//	}


}
