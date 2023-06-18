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
import info.myone.security.provider.MoAuthenticationProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(0)
public class AjaxSecurityConfig {

	// @formatter:off
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**").authorizeHttpRequests(requests -> requests.anyRequest().authenticated());
        
        /*
        filter 추가
        */
        http.addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
       
        http.csrf().disable();
       
		return http.build();
	}
	// @formatter:on

	@Bean
	AjaxLoginProcessingFilter ajaxLoginProcessingFilter() {
		AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();
		ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManager());
		return ajaxLoginProcessingFilter;
	}

	// DB를 이용한 로그인을 위하여 인증 AuthenticationProvider 구현 객체
	@Bean
	AuthenticationProvider authenticationProvider() {
		return new MoAuthenticationProvider();
	}

    @Bean
    AuthenticationManager authenticationManager() {
		return new ProviderManager(authenticationProvider());
	}
}
