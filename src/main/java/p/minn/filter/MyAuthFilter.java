package p.minn.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.LocaleResolver;

import p.minn.auth.entity.Department;
import p.minn.common.utils.ConstantCommon;
import p.minn.security.cas.springsecurity.jwt.MyJwtAuthService;
import p.minn.security.service.IAccountService;
import p.minn.vo.User;


/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
//@Service
public class MyAuthFilter implements Filter {

	private MyJwtAuthService myAuthService;
	
	private IAccountService accountService;
	
    private LocaleResolver localeResolver;
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
       ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext()); 
       myAuthService=(MyJwtAuthService) ctx.getBean("myAuthService");
       accountService=(IAccountService) ctx.getBean("accountService");
       localeResolver=(LocaleResolver) ctx.getBean("localeResolver");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;  
		HttpSession ctx= httpRequest.getSession(false);
		System.out.println("doFilter:");
		if(ctx.getAttribute(ConstantCommon.LOGINUSER)!=null) {
			chain.doFilter(httpRequest, httpResponse);
		}else {
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
        	System.out.println("doFilter isAuth:"+isAuth);
          	 Locale local=new Locale(lang);
	         localeResolver.setLocale(httpRequest, httpResponse, local);
	         User user=queryUser(logininfo);
	         SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())); 
	         ctx.setAttribute(ConstantCommon.LOGINUSER,user);
	        
			chain.doFilter(httpRequest, httpResponse);
		}else {
			 throw new RuntimeException("auth fail");
		}
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
      	return new User(id,logininfo.get("name").toString(),logininfo.get("password").toString(),logininfo.get("language").toString(),Double.valueOf(logininfo.get("type").toString()).intValue(),roles,authorities,deptments);
        
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
