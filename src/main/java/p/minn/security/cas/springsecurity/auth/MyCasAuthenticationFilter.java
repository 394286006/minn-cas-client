package p.minn.security.cas.springsecurity.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class MyCasAuthenticationFilter extends CasAuthenticationFilter {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.doFilter(arg0, arg1, arg2);
	}

	@Override
	protected AuthenticationFailureHandler getFailureHandler() {
		// TODO Auto-generated method stub
		return super.getFailureHandler();
	}

	@Override
	protected AuthenticationSuccessHandler getSuccessHandler() {
		// TODO Auto-generated method stub
		return super.getSuccessHandler();
	}

	
}
