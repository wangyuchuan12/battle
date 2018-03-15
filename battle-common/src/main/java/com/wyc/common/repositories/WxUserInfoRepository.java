package com.wyc.common.repositories;

import javax.persistence.QueryHint;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import com.wyc.common.wx.domain.UserInfo;

public interface WxUserInfoRepository extends CrudRepository<UserInfo, String>{
	
	@Cacheable(value="userCache") 
    public UserInfo findByToken(String token);

	@Cacheable(value="userCache") 
    public UserInfo findByOpenid(String openid);

	@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	public UserInfo findByOpenidAndSource(String openid, int source);
}
