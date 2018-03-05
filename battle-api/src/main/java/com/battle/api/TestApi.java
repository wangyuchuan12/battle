package com.battle.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.service.other.CustomService;
import com.battle.service.other.MessageHandleService;

@Controller
@RequestMapping(value="/api/test")
public class TestApi {

	@Autowired
	private MessageHandleService messageHandleService;
	
	@Autowired
	private CustomService customService;
	
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
}
