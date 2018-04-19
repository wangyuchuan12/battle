package com.wyc.common.repositories;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import com.wyc.common.wx.domain.UserInfo;

public interface WxUserInfoRepository extends CrudRepository<UserInfo, String>{
	

    public UserInfo findByToken(String token);


    public UserInfo findByOpenid(String openid);
	
	//@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Cacheable(value="userCache")
	public UserInfo findOne(String id);

	@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	public UserInfo findByOpenidAndSource(String openid, int source);

	public Page<UserInfo> findAllByIsLine(int isLine, Pageable pageable);
}
