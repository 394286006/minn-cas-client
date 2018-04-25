package p.minn.security.service;


import java.util.List;

import p.minn.auth.entity.Account;
import p.minn.auth.entity.Department;



/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */

public interface IAccountService {

	public Account findAccountByLoginName(String loginName);
	
	//public Account findAccountByLoginName(String loginName,String password);
	
	public Account findAccountByRandomKey(String randomKey);
	
	public Account findAccountByThirdPart(String name,String secretkey);
	
	public boolean checkQrCodeByRandomKey(String randomKey);
	
	public void updateKey(String name, String randomKey);
	
	public String getCurrentAccountName() ;
	
	public List<String> getRoleListByAccountId(Integer accountid);
	
	public List<String> getRoleRealmListByAccountId(Integer accountid);

	public Object getAccountRole(String lang, String messageBody) throws Exception;

    public List<Department> getDepartmentByAcountId(Integer accountid);
}
