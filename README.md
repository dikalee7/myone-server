# myone-server

> Security Login Form 인증 과정

`- Form 로그인 request`  
 - UsernamePasswordAuthenticationFilter -> AntPathRequestMatcher(요청 정보가 매칭되는지 확인) NO? -> chain.doFilter YES? 다음단계로... 
 - Authentication(Username + Password)
 - AuthenticationManager - (위임) -> AuthenticationProvider(실제 인증 처리) <-> UserDetailsService
 - Authentication(User+ Authorities) SecurityContext에 저장 
 - 인증성공 : AuthenticationSucessHandler
 - 인증실패 : AuthenticationFailureHandler

`- 페이지 접근`  
 - FilterSecurityInterceptor - AuthenticationException or AccessDeniedException 발생 시
 - ExceptionTranslationFilter
 - 인증실패 : AuthenticationEntryPoint
 - 접근거부 : AccessDeniedHandler

> 커스터마이징
 - DB 인증 구현 시 실제 인증 처리하는 AuthenticationProvider 구현 객체를 생성하여 적용  
 `MoAuthenticationProvider`  
 - 사용자 추가 파라미터 사용이 필요하다면 AuthenticationDetailsSource 구현 객체를 생성하여 적용  
 `MoAuthenticationDetailsSource`  
 `MoWebAuthenticationDetails`  
 - 인증 성공 Handler  
 `MoAuthenticationSuccessHandler`  
 - 인증 실 Handler  
 `MoAuthenticationFailureHandler`  
 - 접근 권한 처리 Handler  
 `MoAccessDeniedHandler`


>  Security API 인증 과정  

`- API 로그인 요청`  
 - ApiAuthenticationFilter
 - ApiAuthenticationToken
 - AuthenticationManager - (위임) -> ApiAuthenticationProvider(실제 인증 처리) <-> UserDetailsService
 - 인증성공 : ApiAuthenticationSucessHandler
 - 인증실패 : ApiAuthenticationFailureHandler

`- API 접근`  
 - FilterSecurityInterceptor - AuthenticationException or AccessDeniedException 발생 시
 - ExceptionTranslationFilter
 - 인증없이 접근 : AuthenticationEntryPoint
 - 인증은 하였으나 권한 없음 : ApiAccessDeniedHandler

>  커스터마이징  

 - 인증 필터 ApiLoginProcessingFilter  
 ` - AbstractAuthenticationProcessingFilter 상속`  
 ` - ApiAuthenticationToken 생성하여 AuthenticationManager에게 전달하여 인증처리`  
 
 - 인증 처리자 ApiAuthenticationProvider  
 ` - AuthenticationProvider 구현체`   
 
 - 인증 핸들러 ApiAuthenticationSuccessHandler, ApiAuthenticationFailureHandler  
 ` - AuthenticationSuccessHandler 구현체`  
 ` - AuthenticationFailureHandler 구현체`  

 - SC_UNAUTHORIZED ApiLoginAuthenticationEntryPoint  
 ` - AuthenticationEntryPoint 구현체`   
  
 - SC_FORBIDDEN ApiAccessDeniedHandler  
 ` - AccessDeniedHandler 구현체`   
 

