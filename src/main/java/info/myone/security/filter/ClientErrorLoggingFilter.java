//package info.myone.security.filter;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.filter.GenericFilterBean;
//
//public class ClientErrorLoggingFilter extends GenericFilterBean {
//
//	private static final Logger logger = LogManager.getLogger(ClientErrorLoggingFilter.class);
//	private List<HttpStatus> errorCodes;
//
//	// standard constructor
//
//	public ClientErrorLoggingFilter(List<HttpStatus> errorCodes) {
//		// TODO Auto-generated constructor stub
//		this.errorCodes = errorCodes;
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		// ...
//
//		chain.doFilter(request, response);
//	}
//}
