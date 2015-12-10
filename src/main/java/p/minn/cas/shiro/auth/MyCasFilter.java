package p.minn.cas.shiro.auth;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.Subject;
import org.jasig.cas.client.validation.Assertion;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */

public class MyCasFilter extends CasFilter {

	
	@Override
	protected boolean executeLogin(ServletRequest request,
			ServletResponse response) throws Exception {
		
		 AuthenticationToken token = createToken(request, response);
	        if (token == null) {
	            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " +
	                    "must be created in order to execute a login attempt.";
	            throw new IllegalStateException(msg);
	        }
	        try {
                 Subject subject = getSubject(request, response);
             	 CasToken castoken=(CasToken) token;
            	 Object object = subject.getSession().getAttribute("_const_cas_assertion_");
            	 if(object==null){
            		 throw new AuthenticationException("not validate!");
            	 }
            	 Assertion assertion=(Assertion) object;
        		 castoken.setUserId(assertion.getPrincipal().getName());
	             subject.login(castoken);
	            return onLoginSuccess(token, subject, request, response);
	        } catch (AuthenticationException e) {
	        	 Subject subject = getSubject(request, response);
	        	 subject.logout();
	            return onLoginFailure(token, e, request, response);
	        }
	}

	
}
