# myone-server

> 인증 API – Security Login Form 인증 과정

 - UsernamePasswordAuthenticationFilter
 - AntPathRequestMatcher(/login) 
 - Authentication(Username + Password)
 - AuthenticationManager -(위임)-> AuthenticationProvider(실제 인증 처리)
 - Authentication(User+ Authorities) SecurityContext에 저장 
 - SuccessHandler


> 커스터마이징
 - DB 인증 구현 시 실제 인증 처리하는 AuthenticationProvider 구현 객체를 생성하여 적용  
 `- CustomAuthenticationProvider`  
 - 사용자 추가 파라미터 사용이 필요하다면 AuthenticationDetailsSource 구현 객체를 생성하여 적용  
 `- FormAuthenticationDetailsSource`  
 `- FormWebAuthenticationDetails`  
 - 인증 성공 Handler  
 `- CustomAuthenticationSuccessHandler`  
 - 인증 실 Handler  
 `- CustomAuthenticationFailureHandler`  
 - 접근 권한 처리 Handler  
 `- CustomAccessDeniedHandler`  
