package com.battle.api;

import java.lang.reflect.Method;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.RequestFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.service.other.CustomService;
import com.battle.service.other.MessageHandleService;
import com.wyc.common.config.scoket.CountWebSocketHandler;

@Controller
@RequestMapping(value="/api/test")
public class TestApi {

	@Autowired
	private MessageHandleService messageHandleService;
	
	@Autowired
	private CustomService customService;
	
	@Autowired
	private CountWebSocketHandler countWebSocketHandler;
	
	@ResponseBody
	@RequestMapping(value="test")
	public Object test(HttpServletRequest httpServletRequest)throws Exception{
		
		String toUser = httpServletRequest.getParameter("toUser");
		
		String smgtype = httpServletRequest.getParameter("smgtype");
		
		String title = httpServletRequest.getParameter("title");
		String description = httpServletRequest.getParameter("description");
		String url = httpServletRequest.getParameter("url");
		String thumbUrl = httpServletRequest.getParameter("thumbUrl");
		
		customService.sendLinkMsg(toUser, title, description, url, thumbUrl);
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="test2")
	public Object test2(HttpServletRequest httpServletRequest)throws Exception{
		Class<?> rc = org.apache.catalina.connector.RequestFacade.class;
		
		ProtectionDomain protectionDomain = rc.getProtectionDomain();
		
		CodeSource codeSource = protectionDomain.getCodeSource();
		
		System.out.println(codeSource.getLocation());
		
		
		
		return "1232";
	}
	
}
