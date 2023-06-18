package info.myone.security.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import info.myone.member.domain.dto.AccountDto;
import info.myone.security.token.AjaxAuthenticationToken;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter{
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public AjaxLoginProcessingFilter() {
		super(new AntPathRequestMatcher("/api/login"));	//필터 발동 조건
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		if(!isAjax(request)) {
			throw new IllegalStateException("Authentication is not supported");
		}
		
		AccountDto accountDto = objectMapper.readValue(request.getReader(), AccountDto.class);
		if(ObjectUtils.isEmpty(accountDto.getUserid()) || ObjectUtils.isEmpty(accountDto.getPassword())) {
			throw new IllegalArgumentException("Userid or Password is empty");
		}
		
		
		AjaxAuthenticationToken ajaxAuthenticationToken = new AjaxAuthenticationToken(accountDto.getUserid(), accountDto.getPassword());
		//생성해둔 토큰에 인증 요청 정보를 담아 AuthenticationManager에 전달
		return getAuthenticationManager().authenticate(ajaxAuthenticationToken);
	}

	private boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

}
