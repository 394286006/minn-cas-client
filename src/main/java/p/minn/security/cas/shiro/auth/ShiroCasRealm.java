package p.minn.security.cas.shiro.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import p.minn.common.utils.ConstantCommon;
import p.minn.privilege.entity.User;
import p.minn.security.service.IAccountService;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
public class ShiroCasRealm extends CasRealm {

	@Autowired
	private IAccountService accountService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		CasToken ct = (CasToken) authcToken;
		if (ct.getPrincipal() == null) {
			return null;
		}
		User user = accountService.findUserByLoginName(ct.getPrincipal()
				.toString());
		if (user != null) {
			Subject cuser = SecurityUtils.getSubject();
			cuser.getSession().setAttribute(ConstantCommon.LOGINUSER, user);
			if (ct.getCredentials() == null) {
				return null;
			} else {
				return new SimpleAccount(authcToken, ct.getCredentials()
						.toString(), getName());
			}

		} else {
			return null;
		}

	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		CasToken ct = (CasToken) principals.getPrimaryPrincipal();
		User user = accountService.findUserByLoginName(ct.getPrincipal()
				.toString());
		SimpleAuthorizationInfo info = (SimpleAuthorizationInfo) ct
				.getPrincipal();
		info.addRoles(accountService.getRoleListByUserId(user.getId()));
		return info;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}

}
