package p.minn.security.cas.springsecurity.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import p.minn.auth.entity.Account;
import p.minn.security.service.IAccountService;


/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */

@Service
public class SecurityDbRealm implements UserDetailsService {

	@Autowired
	private IAccountService accountService;
	
	public UserDetails loadUserByUsername(String loginName)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Account user=accountService.findAccountByLoginName(loginName);
		List<String> roles=accountService.getRoleRealmListByAccountId(user.getId());
		List<GrantedAuthority> gas=getGrantedAuthorities(roles);
		User realmuser=new User(user.getName(),user.getPwd(),true,true,true,true,gas);
		return realmuser;
	}
	
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();        
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}
