package p.minn.security.cas.springsecurity.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
public class MyAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, MyAuthenticationDetails> {

  public MyAuthenticationDetails buildDetails(HttpServletRequest context) {
    // TODO Auto-generated method stub
    return new MyAuthenticationDetails(context);
  }

}
