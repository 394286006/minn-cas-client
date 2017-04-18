package p.minn.security.cas.springsecurity.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import p.minn.common.type.LoginType;
import p.minn.privilege.entity.Account;
import p.minn.privilege.entity.Department;
import p.minn.security.service.IAccountService;
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
          	return new UsernamePasswordAuthenticationToken(new User(ud.getId(),ud.getName(),password,details.getLanguage(),ud.getType(),roles,gas,deptments), password, gas);
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
            return new UsernamePasswordAuthenticationToken(new User(ud.getId(),ud.getName(),password,key.substring(idx+1,key.length()),ud.getType(),roles,gas,deptments), password, gas);
          }else{
            throw new AuthenticationException("Unable to auth aainst third party systems"){};
          }
        }else if(details.getLoginType()==LoginType.THIRDPART){
          ud=  accountService.findAccountByThirdPart(name,details.getKey());
          if(ud!=null){
            List<String> roles=accountService.getRoleRealmListByAccountId(ud.getId());
            List<GrantedAuthority> gas=getGrantedAuthorities(roles);
            List<Department> deptments=accountService.getDepartmentByAcountId(ud.getId());
            return new UsernamePasswordAuthenticationToken(new User(ud.getId(),ud.getName(),password,details.getLanguage(),ud.getType(),roles,gas,deptments), password, gas);
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
