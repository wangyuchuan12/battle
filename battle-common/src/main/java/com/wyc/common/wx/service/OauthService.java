package com.wyc.common.wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.util.Request;
import com.wyc.common.util.RequestFactory;
import com.wyc.common.util.Response;
import com.wyc.common.wx.domain.Authorize;
import com.wyc.common.wx.domain.WxContext;

@Service
public class OauthService {
    @Autowired
    private WxContext wxContext;
    @Autowired
    private RequestFactory requestFactory;
    public String getWebpageAuthorizeUrl(String redirectUri,int scope,String state)throws Exception{
        StringBuffer sb = new StringBuffer();
        sb.append("https://open.weixin.qq.com/connect/oauth2/authorize");
        sb.append("?");
        sb.append("appid=");
        sb.append(wxContext.getAppid());
        sb.append("&");
        sb.append("redirect_uri=");
        sb.append(redirectUri);
        sb.append("&");
        sb.append("response_type=code&");
        if(scope==1){
            sb.append("scope="+"snsapi_base");
        }else{
            sb.append("scope="+"snsapi_userinfo");
        }
        sb.append("&");
        sb.append("state=");
        sb.append(state);
        sb.append("#wechat_redirect");
        return sb.toString();
    }
    
    //通过code 获取access_token
    public Authorize getAuthorizeByCode(String code)throws Exception{
        String appid = wxContext.getAppid();
        String secret = wxContext.getAppsecret();
        String grantType = "authorization_code";
        Request request = requestFactory.oauth2AccessTokenRequest(appid, secret, code, grantType);
        Response response = request.get(null);
        Authorize authorize = response.readObject(Authorize.class);
        return authorize;
    }
    
    
    //刷新access_token
    public Authorize refreshToken(String refreshToken)throws Exception{
        String appid = wxContext.getAppid();
        Request request = requestFactory.oauth2RefreshToken(appid, refreshToken);
        Response response = request.get(null);
        Authorize authorize = response.readObject(Authorize.class);
        return authorize;
    }
}
