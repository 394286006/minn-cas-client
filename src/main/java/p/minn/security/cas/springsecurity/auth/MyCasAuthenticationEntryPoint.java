package p.minn.security.cas.springsecurity.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.cas.web.CasAuthenticationEntryPoint;

public class MyCasAuthenticationEntryPoint extends CasAuthenticationEntryPoint {

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		super.afterPropertiesSet();
	}

	@Override
	protected String createRedirectUrl(String serviceUrl) {
		// TODO Auto-generated method stub
		
		String rd=super.createRedirectUrl(serviceUrl);
		return rd;
	}

	@Override
	protected String createServiceUrl(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return super.createServiceUrl(request, response);
	}

	@Override
	protected boolean getEncodeServiceUrlWithSessionId() {
		// TODO Auto-generated method stub
		return super.getEncodeServiceUrlWithSessionId();
	}

	@Override
	protected void preCommence(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		super.preCommence(request, response);
	}

	
}
