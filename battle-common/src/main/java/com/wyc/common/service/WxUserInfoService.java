package com.wyc.common.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.repositories.WxUserInfoRepository;
import com.wyc.common.wx.domain.UserInfo;


@Service
public class WxUserInfoService {
    @Autowired
    private WxUserInfoRepository userInfoRepository;
    public UserInfo add(UserInfo userInfo){
        userInfo.setUpdateAt(new DateTime());
        userInfo.setCreateAt(new DateTime());
        userInfo.setId(UUID.randomUUID().toString());
        userInfo.setCount(1l);
        return userInfoRepository.save(userInfo);
    }
    
    public void update(UserInfo userInfo){
        userInfo.setUpdateAt(new DateTime());
        userInfoRepository.save(userInfo);
    }
    
    public UserInfo findByToken(String token){
        return userInfoRepository.findByToken(token);
    }
    
    public UserInfo findByOpenid(String openid){
        return userInfoRepository.findByOpenid(openid);
    }
}
