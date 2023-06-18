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


>  Security Ajax 인증 과정  

`- Ajax 로그인 요청`  
 - AjaxAuthenticationFilter
 - AjaxAuthenticationToken
 - AuthenticationManager - (위임) -> AjaxAuthenticationProvider(실제 인증 처리) <-> UserDetailsService
 - 인증성공 : AjaxAuthenticationSucessHandler
 - 인증실패 : AjaxAuthenticationFailureHandler

`- API 접근`  
 - FilterSecurityInterceptor - AuthenticationException or AccessDeniedException 발생 시
 - ExceptionTranslationFilter
 - 인증실패 : AjaxUrlAuthenticationEntryPoint
 - 접근거부 : AjaxAccessDeniedHandler

> 커스터마이징  
 - AjaxAuthenticationFilter  
 ` - AbstractAuthenticationProcessingFilter 상속`  
 ` - 필터 작동 조건 : AntPathRequestMatcher("/api/login")로 요청정보와 매칭하고 요청방식이 Ajax 이면 필터 작동`  
 ` - AjaxAuthenticationToken 생성하여 AuthenticationManager에게 전달하여 인증처리`  
 ` - 필터 추가`  
