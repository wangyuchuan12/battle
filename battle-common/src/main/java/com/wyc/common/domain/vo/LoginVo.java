package com.wyc.common.domain.vo;

import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;
import com.wyc.common.wx.domain.UserInfo;


@ParamEntityAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
public class LoginVo {

	@IdAnnotation
	private String id;
	
	@ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
	private String token;
	
	@ParamAnnotation(type=ParamEntityAnnotation.SESSION_TYPE)
	private UserInfo userInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
