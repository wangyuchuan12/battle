package com.wyc.common.service;

import java.net.URLDecoder;
import java.net.URLEncoder;
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
        String nickname = userInfo.getNickname();
        try{
        	nickname = URLEncoder.encode(nickname,"utf-8");
        	userInfo.setNickname(nickname);
        }catch(Exception e){
        	
        }
        
        
        return userInfoRepository.save(userInfo);
    }
    
    public void update(UserInfo userInfo){
        userInfo.setUpdateAt(new DateTime());
        String nickname = userInfo.getNickname();
        try{
        	nickname = URLEncoder.encode(nickname,"utf-8");
        	userInfo.setNickname(nickname);
        }catch(Exception e){
        	
        }
        userInfoRepository.save(userInfo);
    }
    
    public UserInfo findByToken(String token){
        UserInfo userInfo = userInfoRepository.findByToken(token);
        if(userInfo==null){
        	return null;
        }
        String nickname = userInfo.getNickname();
        try{
        	nickname = URLDecoder.decode(nickname, "utf-8");
        	userInfo.setNickname(nickname);
        }catch(Exception e){
        	
        }
        return userInfo;
        
    }
    
    public UserInfo findByOpenid(String openid){
        UserInfo userInfo =  userInfoRepository.findByOpenid(openid);
        if(userInfo==null){
        	return null;
        }
        String nickname = userInfo.getNickname();
        try{
        	nickname = URLDecoder.decode(nickname, "utf-8");
        	userInfo.setNickname(nickname);
        }catch(Exception e){
        	
        }
        return userInfo;
    }

	public UserInfo findByOpenidAndSource(String openid, int source) {
		UserInfo userInfo = userInfoRepository.findByOpenidAndSource(openid,source);
		if(userInfo==null){
        	return null;
        }
		String nickname = userInfo.getNickname();
        try{
        	nickname = URLDecoder.decode(nickname, "utf-8");
        	userInfo.setNickname(nickname);
        }catch(Exception e){
        	
        }
        return userInfo;
	}
}
