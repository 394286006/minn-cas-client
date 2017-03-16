package p.minn.security.cas.springsecurity.auth;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import p.minn.privilege.entity.Department;
import p.minn.vo.MyUserDetails;
/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
public class User extends MyUserDetails  implements UserDetails{

	private List<GrantedAuthority> authorities;
	
	private List<Department> departments;
	
	public User(String username,String pwd) {
      super(username,pwd);
      
  }
	
	public User(Integer id,String username,String pwd,String language,Integer type,List<String> roles,List<GrantedAuthority> authorities,List<Department> departments) {
		super(id,username,pwd,language,type,roles);
		this.authorities=authorities;
		this.departments=departments;
		
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

  public List<Department> getDepartments() {
    return departments;
  }

  public void setDepartments(List<Department> departments) {
    this.departments = departments;
  }
	
	


}
