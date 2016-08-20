package p.minn.security.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import p.minn.common.utils.UtilCommon;
import p.minn.privilege.entity.Account;


/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
@Service
public class SecurityService {

	@Autowired
	private IAccountService accountService;
	
	public Object checkUser(String messageBody){
	
			Map map = UtilCommon.gson2Map(messageBody);
			Account account=accountService.findAccountByLoginName(map.get("username").toString());
			if(account==null){
				throw new RuntimeException("account_not_exists");
			}
			String cpwd=map.get("password").toString();
			String pwd=UtilCommon.getPwd(cpwd);
			if(!pwd.equals(account.getPwd()))
				throw new RuntimeException("account_password_incorrect");
			 return account;
		
	}
	
}
