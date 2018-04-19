package com.battle.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.UserStatus;
import com.battle.service.UserStatusService;
import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.wx.domain.UserInfo;

@Service
public class OnlineListener {

	@Autowired
	private WxUserInfoService userInfoService;
	
	@Autowired
	private UserStatusService userStatusService;
	public void onLine(String id){
		
		UserInfo userInfo = userInfoService.findOne(id);
		
		UserStatus userStatus = userStatusService.findOne(userInfo.getStatusId());
		if(userStatus==null){
			userStatus = new UserStatus();
			userStatus.setIsLine(1);
			userStatus.setToken(userInfo.getToken());
			userStatus.setUserId(userInfo.getId());
			userStatusService.add(userStatus);
			
			userInfoService.update(userInfo);
		}
		
		userStatus.setIsLine(1);
		
		userInfoService.update(userInfo);
	}
	
	public void downLine(String id){
		
		
		UserInfo userInfo = userInfoService.findOne(id);
		
		UserStatus userStatus = userStatusService.findOne(userInfo.getStatusId());
		
		userStatus.setIsLine(1);
		
		userInfoService.update(userInfo);
	}
}
