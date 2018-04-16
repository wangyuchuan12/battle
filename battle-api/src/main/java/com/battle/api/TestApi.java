package com.battle.api;

import java.lang.reflect.Method;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.catalina.connector.RequestFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.service.other.CustomService;
import com.battle.service.other.MessageHandleService;
import com.battle.socket.MessageHandler;
//import com.wyc.common.config.scoket.CountWebSocketHandler;
import com.battle.socket.MessageVo;
import com.battle.socket.service.ProgressStatusSocketService;
import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/test")
public class TestApi {

	@Autowired
	private MessageHandleService messageHandleService;
	
	@Autowired
	private CustomService customService;
	
	
	@Autowired
	private MessageHandler messageHandler;
	
	@Autowired
	private ProgressStatusSocketService progressStatusSocketService;
	
	@Autowired
	private WxUserInfoService wxUserInfoService;
	
	
	//@Autowired
	//private CountWebSocketHandler countWebSocketHandler;
	
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
	
	@ResponseBody
	@RequestMapping(value="test3")
	public Object test3(HttpServletRequest httpServletRequest)throws Exception{
		
		String type = httpServletRequest.getParameter("type");
		
		Integer typeInt = Integer.parseInt(type);
		
		String roomId = httpServletRequest.getParameter("roomId");
		String userIds = httpServletRequest.getParameter("userIds");
		
		MessageVo messageVo = new MessageVo();
		messageVo.setType(typeInt);
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("id", 1);
		data.put("name", "wyc");
		data.put("age", "21");
		messageVo.setData(data);
		if(typeInt.intValue()==MessageVo.ALL_ONLINE_TYPE){
			messageHandler.sendMessage(messageVo);
		}else if(typeInt.intValue()==MessageVo.ROOM_TYPE){
			messageVo.setRoomId(roomId);
			messageHandler.sendMessage(messageVo);
		}else if(typeInt.intValue() == MessageVo.USERS_TYPE){
			List<String> arr = new ArrayList<>();
			
			
			for(String userId:userIds.split(",")){
				arr.add(userId);
			}
			messageVo.setUserIds(arr);
			messageHandler.sendMessage(messageVo);
		}
		
		
		return "4444";
	}
	
	@ResponseBody
	@RequestMapping(value="test4")
	public Object test4(HttpServletRequest httpServletRequest)throws Exception{
		String roomId = httpServletRequest.getParameter("roomId");
		
		progressStatusSocketService.statusPublish(roomId);
		
		return "success";
	}
	
	
	@Transactional
	@ResponseBody
	@RequestMapping(value="test5")
	public Object test5(HttpServletRequest httpServletRequest)throws Exception{
		UserInfo userInfo = wxUserInfoService.findByToken("bb8b8a92-132e-4c56-ad4a-386e149c0dad");
		
		System.out.println("userInfo1:"+userInfo);
		
		Thread.sleep(20000);
		
		System.out.println("userInfo2:"+userInfo);
		
		return userInfo;
	}
	
	
	@Transactional
	@ResponseBody
	@RequestMapping(value="test6")
	public Object test6(HttpServletRequest httpServletRequest)throws Exception{
		UserInfo userInfo = wxUserInfoService.findOne("ef346651-ed39-4f6a-9f91-bfe89d1b29da");
		
		System.out.println("userInfo3:"+userInfo);
	
		
		System.out.println("userInfo4:"+userInfo);
		
		return userInfo;
	}
	
}
