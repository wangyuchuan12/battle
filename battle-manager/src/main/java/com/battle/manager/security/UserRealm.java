package com.battle.manager.security;

import com.battle.domain.Admin;
import com.battle.service.AdminService;
import com.google.common.base.Splitter;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    final static Logger logger = LoggerFactory.getLogger(UserRealm.class);
    @Autowired
    AdminService adminService;
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        logger.debug("Authorization username : {}", username);

        Admin admin = adminService.getAdminByName(username);
        String roles_str = admin.getRoles();
        Iterable<String> roles = Splitter.on(',')
            .trimResults()
            .omitEmptyStrings()
            .split(roles_str);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        for (String role: roles) {
            authorizationInfo.addRole(role);
        }
        //authorizationInfo.addRole("member");

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
                     throws AuthenticationException {
        String username = (String)token.getPrincipal();
        logger.debug("Authentication username: {}", username);
        Admin admin = adminService.getAdminByName(username);
        logger.debug("Database username: {}, password: {}", admin.getUsername(), admin.getPassword());

        AuthenticationInfo ai = new SimpleAuthenticationInfo(admin.getUsername(),
                                                             admin.getPassword(),
                                                             ByteSource.Util.bytes(admin.getSalt()),
                                                             getName());
        //AuthenticationInfo ai = new SimpleAuthenticationInfo("admin", "admin", getName());

        return ai;
    }
}
