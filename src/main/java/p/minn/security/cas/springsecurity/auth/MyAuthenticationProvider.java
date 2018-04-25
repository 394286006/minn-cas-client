package p.minn.security.cas.springsecurity.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import p.minn.auth.entity.Account;
import p.minn.auth.entity.Department;
import p.minn.common.type.LoginType;
import p.minn.security.cas.springsecurity.jwt.MyJwtAuthService;
import p.minn.security.service.IAccountService;
import p.minn.vo.User;
/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
	private IAccountService accountService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private MyJwtAuthService myJwtAuthService;
	
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		// TODO Auto-generated method stub
	
		String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        MyAuthenticationDetails details=(MyAuthenticationDetails) authentication.getDetails();
        Account ud=null;
        if(details.getLoginType()==LoginType.PWD){
          ud=  accountService.findAccountByLoginName(name);
          if(ud.getPwd().equals(passwordEncoder.encode(password))){
          	List<String> roles=accountService.getRoleRealmListByAccountId(ud.getId());
          	List<GrantedAuthority> gas=getGrantedAuthorities(roles);
          	List<Department> deptments=accountService.getDepartmentByAcountId(ud.getId());
          	User user=new User(ud.getId(),ud.getName(),password,details.getLanguage(),ud.getType(),roles,gas,deptments);
          	myJwtAuthService.auth(user);
          	return new UsernamePasswordAuthenticationToken(user, password, gas);
          } else {
              throw new AuthenticationException("Unable to auth aainst third party systems"){};
          }
        }else if(details.getLoginType()==LoginType.QRCODE){
            ud=  accountService.findAccountByRandomKey(details.getKey());
          if(ud!=null){
            List<String> roles=accountService.getRoleRealmListByAccountId(ud.getId());
            List<GrantedAuthority> gas=getGrantedAuthorities(roles);
            List<Department> deptments=accountService.getDepartmentByAcountId(ud.getId());
            accountService.updateKey(ud.getName(), "");
            String key=ud.getRandomKey();
            int idx=key.indexOf("_");
            User user=new User(ud.getId(),ud.getName(),password,key.substring(idx+1,key.length()),ud.getType(),roles,gas,deptments);
        	    myJwtAuthService.auth(user);
            return new UsernamePasswordAuthenticationToken(user, password, gas);
          }else{
            throw new AuthenticationException("Unable to auth aainst third party systems"){};
          }
        }else if(details.getLoginType()==LoginType.THIRDPART){
          ud=  accountService.findAccountByThirdPart(name,details.getKey());
          if(ud!=null){
            List<String> roles=accountService.getRoleRealmListByAccountId(ud.getId());
            List<GrantedAuthority> gas=getGrantedAuthorities(roles);
            List<Department> deptments=accountService.getDepartmentByAcountId(ud.getId());
            User user=new User(ud.getId(),ud.getName(),password,details.getLanguage(),ud.getType(),roles,gas,deptments);
            myJwtAuthService.auth(user);
            return new UsernamePasswordAuthenticationToken(user, password, gas);
          }else{
            throw new AuthenticationException("Unable to auth aainst third party systems"){};
          }
        } else{
          throw new AuthenticationException("Undefined loginType[pwd|qrcode]:"+details.getLoginType()){};
        }
	}

	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();        
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
	
}
