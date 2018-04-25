package p.minn.security.cas.springsecurity.jwt;

import java.security.Key;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import p.minn.common.utils.UtilCommon;
import p.minn.vo.User;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
@Service
public class MyJwtAuthService {

	public void auth(User user) {
		String sk = UtilCommon.getSecurityKey();
		Key signingKey = new SecretKeySpec(sk.getBytes(), SignatureAlgorithm.HS512.getJcaName());
		String compactJws = Jwts.builder()
				.setSubject(UtilCommon.gson2Str(user))
				.signWith(SignatureAlgorithm.HS512, signingKey).compact();
		user.setAccessKey(compactJws);
		user.setSecretKey(sk);
	}

	public Boolean checkToken(Map logininfo, String id, String userName, String lang) {
		boolean isAuth = false;
		if (logininfo.get("loginName").toString().equals(userName)&&Double.valueOf(logininfo.get("id").toString()).intValue()==Double.valueOf(id).intValue()) {
			isAuth = true;
		}
		return isAuth;
	}
	
	public Map getSub(String secretkey, String accessKey) {
		Key signingKey = new SecretKeySpec(secretkey.getBytes(), SignatureAlgorithm.HS512.getJcaName());
		String sub = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(accessKey).getBody().getSubject();
		Map logininfo=UtilCommon.gson2Map(sub);
		return logininfo;
	}
	

}
