package p.minn.cas.service;


import java.util.List;


import p.minn.privilege.entity.User;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */

public interface IAccountService {

	public User findUserByLoginName(String loginName);

	public String getCurrentUserName() ;
	
	public List<String> getRoleListByUserId(Integer id);
}
