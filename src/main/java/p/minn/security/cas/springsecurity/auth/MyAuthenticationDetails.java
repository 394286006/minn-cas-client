package p.minn.security.cas.springsecurity.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
public class MyAuthenticationDetails extends WebAuthenticationDetails {

 /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private LoginType loginType;
  
  private String language;
  
  private String key;

 
  public MyAuthenticationDetails(HttpServletRequest request) {
    super(request);
    // TODO Auto-generated constructor stub
    this.loginType=LoginType.valueOf(Integer.valueOf(request.getParameter("logintype").toString()));
    this.language=request.getParameter("lang");
    this.key=request.getParameter("key");
  }
  public LoginType getLoginType() {
    return this.loginType;
  }


  public String getKey() {
    return this.key;
  }
  public String getLanguage() {
    return language;
  }
  
  
}
