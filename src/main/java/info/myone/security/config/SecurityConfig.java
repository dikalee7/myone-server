package info.myone.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import info.myone.security.common.FormAuthenticationDetailsSource;
import info.myone.security.handler.MoAccessDeniedHandler;
import info.myone.security.handler.MoAuthenticationFailureHandler;
import info.myone.security.handler.MoAuthenticationSuccessHandler;
import info.myone.security.provider.MoAuthenticationProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(1)
public class SecurityConfig {
	
	/*
	 * 인증 API – Login Form 인증 과정 UsernamePasswordAuthenticationFilter
	 * AntPathRequestMatcher(/login) Authentication(Username + Password)
	 * AuthenticationManager -(위임)-> AuthenticationProvider(실제 인증 처리)
	 * Authentication(User+ Authorities) SecurityContext에 저장 SuccessHandler
	 * 
	 * DB 인증 구현 시 실제 인증 처리하는 AuthenticationProvider 구현 객체를 생성하여 적용
	 * 인증시 사용자 추가 파라미터 사용이 필요하다면 AuthenticationDetailsSource 구현 객체를 생성하여 적용
	 * 인증 성공 후 추가 처리를 정의 하기 위해서는 AuthenticationSuccessHandler 구현 객체를 생성하여 적용
	 * 인증 실패 후 추가 처리를 정의 하기 위해서는 AuthenticationFailureHandler 구현 객체를 생성하여 적용
	 * 인증 성겅 후 권한 접근 실패 처리를 정의하기 위해서는 AccessDeniedHandler 구현 객체를 생성하여 적용
	 */
	

	// AuthenticationDetailsSource 구현체
	private final FormAuthenticationDetailsSource formAuthenticationDetailsSource;
	
	// AuthenticationSuccessHandler 구현체
	private final MoAuthenticationSuccessHandler moAuthenticationSuccessHandler;
	
	//AuthenticationFailureHandler 구현체
	private final MoAuthenticationFailureHandler moAuthenticationFailureHandler;	


	// DB를 이용한 로그인을 위하여 인증 AuthenticationProvider 구현 객체
	@Bean
	AuthenticationProvider authenticationProvider() {
		return new MoAuthenticationProvider();
	}
	
	
	// @formatter:off
	/*
	 * 인증 API
	 * http.formLogin()
	 * http.logout()
	 * http.csrf()
	 * http.httpBasic()
	 * http.SessionManagement()
	 * http.RememberMe()
	 * http.ExceptionHandling()
	 * http.addFilter()
	 * 
	 * 인가 API
	 * http.authorizeRequests().antMatchers(/admin).hasRole(USER).permitAll().authenticated().fullyAuthentication().acess(hasRole(USER)).denyAll()
	 */
	@Bean
	SecurityFilterChain filterChan(HttpSecurity http) throws Exception {
		// HttpSecurity 인가 API
		http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
				.antMatchers("/","/users","user/login/**","/login*").permitAll()
				.antMatchers("/mypage").hasRole("USER")
				.antMatchers("/messages").hasRole("MANAGER")
				.antMatchers("/config").hasRole("ADMIN")
				.anyRequest().authenticated());
		
		/*
		 http.formLogin()
                .loginPage("/login.html")   				// 사용자 정의 로그인 페이지
                .defaultSuccessUrl("/home")					// 로그인 성공 후 이동 페이지
	         	.failureUrl("/login.html?error=true“)		// 로그인 실패 후 이동 페이지
                .usernameParameter("username")				// 아이디 파라미터명 설정
                .passwordParameter("password”)				// 패스워드 파라미터명 설정
                .loginProcessingUrl("/login")				// 로그인 Form Action Url
                .successHandler(loginSuccessHandler())		// 로그인 성공 후 핸들러
                .failureHandler(loginFailureHandler())		// 로그인 실패 후 핸들러
		 */
        http.formLogin(login -> login
        		.loginPage("/login")
        		.usernameParameter("userid")
        		.loginProcessingUrl("/login_proc")
        		.authenticationDetailsSource(formAuthenticationDetailsSource)
                .successHandler(moAuthenticationSuccessHandler)
                .failureHandler(moAuthenticationFailureHandler)
                .permitAll());
        
        /*
         exceptionHandle
        */
        http.exceptionHandling(handling -> handling.accessDeniedHandler(accessDeniedHandler()));
        
        
        
        return http.build();
	}
	// @formatter:on
	
	@Bean
	// AccessDeniedHandler 구현체
	AccessDeniedHandler accessDeniedHandler() {
		MoAccessDeniedHandler h = new MoAccessDeniedHandler();
		h.setErrorPage("/denied");
		return h;
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		// return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
		// js / css / image 파일 등 정적 자원에 대한 보안 필터 적용 제외
		return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	/*
	테스트용으로 inMemory 방식으로 사용시
    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		// @formatter:off
    	PasswordEncoder passwordEncoder = passwordEncoder();
    	UserDetails user = User.builder()
				.username("user")
				.password(passwordEncoder.encode("user1234"))
				.roles("USER")
				.build();
		
		UserDetails manager = User.builder()
				.username("manager")
				.password(passwordEncoder.encode("manager1234"))
				.roles("USER", "MANAGER")
				.build();
		
		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder.encode("admin1234"))
				.roles("USER", "MANAGER", "ADMIN")
				.build();
		// @formatter:on
		return new InMemoryUserDetailsManager(user, manager, admin);
    }

    @Bean
    DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(inMemoryUserDetailsManager());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }*/


	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	

}
