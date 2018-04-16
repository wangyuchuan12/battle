package com.battle.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * 消息拦截处理类
 * @author xiaojf 2017/3/2 10:36.
 */
@Service
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	
	@Autowired
	private WxUserInfoService userInfoService;
	
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        //解决The extension [x-webkit-deflate-frame] is not supported问题
        if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
            request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }
        
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        
        String token = servletRequest.getParameter("token");
        
        
        if(CommonUtil.isEmpty(token)){
        	return false;
        }
        
        UserInfo userInfo = userInfoService.findByToken(token);
        
        if(userInfo==null){
        	return false;
        }
        
        attributes.put("token", token);
        attributes.put("userId", userInfo.getId());

        boolean b = super.beforeHandshake(request, response, wsHandler, attributes);
        
        return b;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {

        System.out.println("After Handshake");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}