package p.minn.cas.shiro.auth;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Service;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
@Service
public class MyCredentialsMatcher implements CredentialsMatcher {

	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		try{
			return token.getCredentials().toString().equals(info.getCredentials().toString());
		}catch(Exception e){
			return false;
		}
	}

}
