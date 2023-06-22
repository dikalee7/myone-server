package info.myone.api.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import info.myone.api.config.jwt.JwtAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;
import info.myone.api.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

//@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;
    
    /**
     * 패스워드 인코딩
     * @return PasswordEncoder
     */
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	/**
     * 정적 자원(Resource)에 대해서 인증된 사용자가  정적 자원의 접근에 대해 ‘인가’에 대한 설정을 담당하는 메서드이다.
     *
     * @return WebSecurityCustomizer
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 자원에 대해서 Security를 적용하지 않음으로 설정
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(httpBasic->httpBasic.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .antMatchers("/api/v1/users/sign-up", "/api/v1/users/login", "/api/v1/users/authority", "/api/v1/users/reissue", "/api/v1/users/logout").permitAll()
                        .antMatchers("/api/v1/users/userTest").hasRole("USER")
                        .antMatchers("/api/v1/users/adminTest").hasRole("ADMIN"))
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate), UsernamePasswordAuthenticationFilter.class);
         // JwtAuthenticationFilter를 UsernamePasswordAuthentictaionFilter 전에 적용시킨다.
        
        return http.build();
    }
}
