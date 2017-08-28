package com.wyc.common.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.common.wx.domain.Authorize;

public interface WxAuthorizeRepository extends CrudRepository<Authorize, String>{
    public Authorize findByToken(String token);

    public Authorize findByOpenid(String openid);
}
