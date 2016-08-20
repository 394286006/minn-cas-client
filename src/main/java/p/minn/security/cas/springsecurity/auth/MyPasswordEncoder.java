package p.minn.security.cas.springsecurity.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
@Service
public class MyPasswordEncoder implements PasswordEncoder {

	public String encode(CharSequence rawPassword) {
		// TODO Auto-generated method stub
		String hexs=null;
		try{
			CharSequence newpwd=rawPassword.subSequence(0,rawPassword.length()/3);
		  byte[] salt=rawPassword.subSequence(rawPassword.length()-8>0 ? rawPassword.length()-8:0, rawPassword.length()).toString().getBytes();
		  MessageDigest digest = MessageDigest.getInstance("MD5");
          if (salt != null) {
              digest.update(salt);
          }
          byte[] result = digest.digest(newpwd.toString().getBytes());
          for (int i = 1; i < 1024; i++) {
              digest.reset();
              result = digest.digest(result);
          }
		   hexs=new String( Hex.encodeHex(result));
		} catch(NoSuchAlgorithmException e){
			throw new RuntimeException("password encoding error");
		}
		   return hexs;
	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// TODO Auto-generated method stub
		return rawPassword.equals(encodedPassword);
	}

}
