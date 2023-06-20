//package info.myone.security.common;
//
//import java.util.List;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
//
//import info.myone.security.filter.ClientErrorLoggingFilter;
//
//public class ClientErrorLoggingConfigurer 
//extends AbstractHttpConfigurer<ClientErrorLoggingConfigurer, HttpSecurity> {
//
//  private List<HttpStatus> errorCodes;
//  
//  // standard constructors
//  
//  @Override
//  public void init(HttpSecurity http) throws Exception {
//      // initialization code
//  }
//
//  @Override
//  public void configure(HttpSecurity http) throws Exception {
//     http.addFilterAfter(
//       new ClientErrorLoggingFilter(errorCodes), 
//       FilterSecurityInterceptor.class);
//  }
//}
