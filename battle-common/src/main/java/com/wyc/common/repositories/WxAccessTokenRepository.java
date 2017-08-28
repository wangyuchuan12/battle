package com.wyc.common.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.common.wx.domain.AccessTokenBean;

public interface WxAccessTokenRepository extends CrudRepository<AccessTokenBean, String>{
    public AccessTokenBean findByToken(String token);

    public AccessTokenBean findByAccessToken(String accessToken);
}
