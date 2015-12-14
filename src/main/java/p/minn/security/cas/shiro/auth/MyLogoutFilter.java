package p.minn.security.cas.shiro.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

public class MyLogoutFilter extends LogoutFilter {

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		String redirectUrl=null;
		 try {
			 System.out.println("logout prehand");
		  Subject subject = getSubject(request, response);
	         redirectUrl = getRedirectUrl(request, response, subject);
	        //try/catch added for SHIRO-298:
	       
	        	
	            subject.logout();
	        } catch (SessionException ise) {
	        	ise.printStackTrace();
	        }
	        issueRedirect(request, response, redirectUrl);
	        return false;
	}

	

	
}
