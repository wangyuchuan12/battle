package com.battle.manager.config;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.battle.manager.security.OrRolesAuthorizationFilter;
import com.battle.manager.security.UserRealm;

import javax.servlet.Filter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class SecurityConfig {
    
	@Bean
	public CredentialsMatcher credentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("SHA-256");
		matcher.setHashIterations(1);
		matcher.setStoredCredentialsHexEncoded(true);

		return matcher;
	}

	
	@Bean
	@Autowired
	public UserRealm userRealm(CredentialsMatcher credentialsMatcher) {
		UserRealm realm = new UserRealm();
		realm.setCredentialsMatcher(credentialsMatcher);
		return realm;
	}

	@Bean
	@Autowired
	public ShiroFilterFactoryBean shiroFilter(WebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setLoginUrl("/admin/login");
		shiroFilter.setSuccessUrl("/battle/list");

		Map<String, String> definitionsMap = new LinkedHashMap<>();
		definitionsMap.put("/admin/login", "anon");
		definitionsMap.put("/admin/list/**", "authc,roles[god]");
		
		definitionsMap.put("/admin/add_default_admin", "anon");
		// 添加用户
		definitionsMap.put("/admin/add", "authc,roles[admin]");

		// 修改用户基本信息
		definitionsMap.put("/admin/update", "authc,roles[admin]");

		// 修改用户密码
		definitionsMap.put("/admin/reset", "authc,roles[admin]");
		//注册用户
		definitionsMap.put("/admin/add_admin", "authc,roles[god]");
		// 删除用户
		definitionsMap.put("/admin/delete", "authc,roles[admin]");
		//执行退款
		definitionsMap.put("/manager/api/refund", "authc,roles[god]");
		definitionsMap.put("/**", "authc,roles[admin,god]");
		
		shiroFilter.setFilterChainDefinitionMap(definitionsMap);

		Map<String, Filter> filters = new HashMap<>();
		filters.put("anon", new AnonymousFilter());
		filters.put("authc", new FormAuthenticationFilter());
		filters.put("logout", new LogoutFilter());
		filters.put("roles", new OrRolesAuthorizationFilter());
		filters.put("user", new UserFilter());
		shiroFilter.setFilters(filters);

		shiroFilter.setSecurityManager(securityManager);
		return shiroFilter;
	}

	@Bean
	@Autowired
	public WebSecurityManager securityManager(UserRealm userRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(userRealm);
		return securityManager;
	}
	
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	

	@Bean
	@Autowired
	public MethodInvokingFactoryBean methodInvokingFactoryBean(
			WebSecurityManager securityManager) {
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean
				.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		methodInvokingFactoryBean
				.setArguments(new Object[] { securityManager });
		return methodInvokingFactoryBean;
	}

	/*
	@Bean
	@DependsOn(value = "lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		return new DefaultAdvisorAutoProxyCreator();
	}*/

	
	@Bean
	@Autowired
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			WebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

}
