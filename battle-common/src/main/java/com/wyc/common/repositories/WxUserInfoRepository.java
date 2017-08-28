package com.wyc.common.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.common.wx.domain.UserInfo;

public interface WxUserInfoRepository extends CrudRepository<UserInfo, String>{
    public UserInfo findByToken(String token);

    public UserInfo findByOpenid(String openid);
}
