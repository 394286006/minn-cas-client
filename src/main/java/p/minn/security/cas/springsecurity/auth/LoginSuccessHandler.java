package p.minn.security.cas.springsecurity.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

  private String defaultTargetUrl;
  
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    // TODO Auto-generated method stub
    response.sendRedirect(defaultTargetUrl);

  }

  public void setDefaultTargetUrl(String defaultTargetUrl) {
    this.defaultTargetUrl = defaultTargetUrl;
  }
  
  

}
