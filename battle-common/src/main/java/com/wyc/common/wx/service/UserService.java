package com.wyc.common.wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.util.Request;
import com.wyc.common.util.RequestFactory;
import com.wyc.common.util.Response;
import com.wyc.common.wx.domain.UserInfo;
import com.wyc.common.wx.domain.WxContext;

@Service
public class UserService {
    @Autowired
    private WxContext wxContext;
    @Autowired
    private RequestFactory requestFactory;
    public UserInfo getUserInfo(String accessToken,String openid,int lang_type)throws Exception{
        String lang="zh_CN";
        if(lang_type==1){
            lang="zh_CN";
        }
        else if (lang_type==2) {
            lang="zh_TW";
        }
        else if (lang_type==3) {
            lang="en";
        }
        Request request = requestFactory.userInfoRequest(accessToken, openid, lang);
        Response response = request.get(null);
        return response.readObject(UserInfo.class);
    }
    
    public UserInfo getUserInfoFromWeb(String accessToken , String openid , int lang_type)throws Exception{
        String lang="zh_CN";
        if(lang_type==1){
            lang="zh_CN";
        }
        else if (lang_type==2) {
            lang="zh_TW";
        }
        else if (lang_type==3) {
            lang="en";
        }
        Request request = requestFactory.snsUserInfoRequest(accessToken, openid, lang);
        Response response = request.get(null);
        return response.readObject(UserInfo.class);
    }

	public void update(UserInfo userInfo) {
		// TODO Auto-generated method stub
		
	}

}
