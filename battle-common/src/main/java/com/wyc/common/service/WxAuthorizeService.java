package com.wyc.common.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.repositories.WxAuthorizeRepository;
import com.wyc.common.wx.domain.Authorize;

@Service
public class WxAuthorizeService {
    @Autowired
    private WxAuthorizeRepository wxAuthorizeRepository;
    public void add(Authorize authorize){
        authorize.setId(UUID.randomUUID().toString());
        authorize.setCreateAt(new DateTime());
        authorize.setUpdateAt(new DateTime());
        wxAuthorizeRepository.save(authorize);
    }
    
    public void save(Authorize authorize){
        authorize.setUpdateAt(new DateTime());
        wxAuthorizeRepository.save(authorize);
    }
    
    public Authorize findByToken(String token){
        return wxAuthorizeRepository.findByToken(token);
    }

    public Authorize findByOpenid(String openid) {
        return wxAuthorizeRepository.findByOpenid(openid);
    }
}
