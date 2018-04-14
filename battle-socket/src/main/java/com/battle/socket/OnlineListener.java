package com.battle.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.wx.domain.UserInfo;

@Service
public class OnlineListener {

	@Autowired
	private WxUserInfoService userInfoService;
	public void onLine(String token){
		
		UserInfo userInfo = userInfoService.findByToken(token);
		
		userInfo.setIsLine(1);
		
		userInfoService.update(userInfo);
	}
	
	public void downLine(String token){
		
		
		UserInfo userInfo = userInfoService.findByToken(token);
		
		userInfo.setIsLine(0);
		
		userInfoService.update(userInfo);
	}
}
