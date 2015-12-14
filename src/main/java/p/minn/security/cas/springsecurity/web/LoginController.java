package p.minn.security.cas.springsecurity.web;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import p.minn.common.utils.ConstantCommon;
import p.minn.privilege.entity.User;
import p.minn.security.service.IAccountService;


/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
@Controller
public class LoginController {
	
	private static final String DEFAULT_IDX="index";

	@Autowired
    private LocaleResolver localeResolver;  
	
	@Autowired
	private IAccountService accountService;

	@RequestMapping(value = { "/","idx**"}, method = RequestMethod.GET)
	public ModelAndView defaultIndex(HttpServletRequest req,HttpServletResponse rep, @RequestParam(required=false, defaultValue="zh") String lang){
		Locale local=new Locale(lang);
		localeResolver.setLocale(req, rep, local);
		ModelAndView model = new ModelAndView();
		model.setViewName(DEFAULT_IDX);
	
		String userName = null;
		UserDetails ud=null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			ud=(UserDetails)principal;
			userName = ud.getUsername();
		} else {
			userName = principal.toString();
			
		}
		if(!userName.equals(ConstantCommon.ANONYMOUSUSER)){
			User user=accountService.findUserByLoginName(userName);
			req.getSession().setAttribute(ConstantCommon.LOGINUSER,user);
		}
		return model;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){ 
			request.getSession().removeAttribute(ConstantCommon.LOGINUSER);
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		
		return "redirect:/";
	}

}
