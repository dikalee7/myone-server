package info.myone.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import info.myone.security.provider.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/*
	 * 인증 API – Login Form 인증 과정 UsernamePasswordAuthenticationFilter
	 * AntPathRequestMatcher(/login) Authentication(Username + Password)
	 * AuthenticationManager -(위임)-> AuthenticationProvider(실제 인증 처리)
	 * Authentication(User+ Authorities) SecurityContext에 저장 SuccessHandler
	 * 
	 * !! DB 인증 구현 시 실제 인증 처리하는 AuthenticationProvider 구현 객체를 생성하여 적용
	 */

	/*
	 * DB 인증 AuthenticationProvider 구현 객체
	 */
	@Bean
	AuthenticationProvider authenticationProvider() {
		return new CustomAuthenticationProvider();
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
                .defaultSuccessUrl("/")
                .permitAll());
        
        return http.build();
	}
	// @formatter:on

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
