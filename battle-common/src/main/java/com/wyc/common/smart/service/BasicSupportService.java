package com.wyc.common.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.util.Request;
import com.wyc.common.util.RequestFactory;
import com.wyc.common.util.Response;
import com.wyc.common.wx.domain.AccessTokenBean;
import com.wyc.common.wx.domain.WxContext;


@Service
public class BasicSupportService {
    @Autowired
    private RequestFactory requestFactory;
    @Autowired
    private WxContext wxContext;
    public AccessTokenBean getAccessTokenBean()throws Exception{
        String appid = wxContext.getAppid();
        String secret = wxContext.getAppsecret();
        Request request = requestFactory.accessTokenRequest(appid, secret);
        Response response = request.get(null);
        AccessTokenBean accessTokenBean = response.readObject(AccessTokenBean.class);
        return accessTokenBean;
    }
}
