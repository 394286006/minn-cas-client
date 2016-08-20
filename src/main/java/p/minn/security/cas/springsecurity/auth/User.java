package p.minn.security.cas.springsecurity.auth;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import p.minn.vo.MyUserDetails;
/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
public class User extends MyUserDetails  implements UserDetails{

	List<GrantedAuthority> authorities;
	
	public User(Integer id,String username,String pwd,Integer type,List<String> roles,List<GrantedAuthority> authorities) {
		super(id,username,pwd,type,roles);
		this.authorities=authorities;
		
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return super.getPassword();
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return super.getLoginName();
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


}
