package p.minn.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import p.minn.auth.entity.Department;
import p.minn.common.utils.ConstantCommon;
import p.minn.security.cas.springsecurity.jwt.MyJwtAuthService;
import p.minn.security.service.IAccountService;
import p.minn.vo.User;

public class MyAuthInterceptor implements HandlerInterceptor {

	@Autowired
	private MyJwtAuthService myAuthService;
	@Autowired
	private IAccountService accountService;
	@Autowired
    private LocaleResolver localeResolver;
    
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object obj, Exception err)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object obj, ModelAndView mav) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object obj) throws Exception {
    	HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;  
		HttpSession sess= httpRequest.getSession(true);
        httpResponse.setCharacterEncoding("UTF-8");    
        httpResponse.setContentType("application/json; charset=utf-8"); 
        String accessKey = httpRequest.getParameter("accessKey");
        String secretKey=httpRequest.getParameter("secretKey");
        String id=httpRequest.getParameter("id");
        String userName=httpRequest.getParameter("userName");
   
        if(StringUtils.isBlank(accessKey)) {
        	  throw new RuntimeException("accessKey can not be null");
        }
        Map logininfo=myAuthService.getSub(secretKey, accessKey);
        String lang =logininfo.get("language").toString().split("_")[0];
        boolean isAuth=myAuthService.checkToken(logininfo, id, userName, lang);
        if(isAuth) {
          	 Locale local=new Locale(lang);
	         localeResolver.setLocale(httpRequest, httpResponse, local);
	         User user=queryUser(logininfo);
	         SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())); 
	         sess.setAttribute(ConstantCommon.LOGINUSER,user);
	         return true;
		}else {
			 throw new RuntimeException("auth fail");
		}
    }
    
    private User queryUser(Map logininfo) {
		Integer id=Double.valueOf(logininfo.get("id").toString()).intValue();
		List<String> roles=accountService.getRoleRealmListByAccountId(id);
      	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();        
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
      	List<Department> deptments=accountService.getDepartmentByAcountId(id);
      
      	return new User(id,logininfo.get("loginName").toString(),logininfo.get("password").toString(),logininfo.get("language").toString(),Double.valueOf(logininfo.get("type").toString()).intValue(),roles,authorities,deptments);
        
	}
}
