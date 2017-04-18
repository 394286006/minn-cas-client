package p.minn.security.service;


import java.util.List;

import p.minn.common.utils.Page;
import p.minn.privilege.entity.Account;
import p.minn.privilege.entity.Department;
import p.minn.privilege.entity.IdEntity;
import p.minn.security.cas.springsecurity.auth.User;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */

public interface IAccountService {

	public Account findAccountByLoginName(String loginName);
	
	public Account findAccountByLoginName(String loginName,String password);
	
	public Account findAccountByRandomKey(String randomKey);
	
	public Account findAccountByThirdPart(String name,String secretkey);
	
	public boolean checkQrCodeByRandomKey(String randomKey);
	
	public void updateKey(String name, String randomKey);
	
	public String getCurrentAccountName() ;
	
	public List<String> getRoleListByAccountId(Integer accountid);
	
	public List<String> getRoleRealmListByAccountId(Integer accountid);

	public Object getAccountRole(String lang, String messageBody) throws Exception;

	public Page query(String messageBody, String lang);

	public void update(User user,String messageBody, String lang);

	public void save(User user,String messageBody, String lang);

	public void delete(String messageBody);

	public void saveAccountRole(String messageBody);

    public List<Department> getDepartmentByAcountId(Integer accountid);
}
