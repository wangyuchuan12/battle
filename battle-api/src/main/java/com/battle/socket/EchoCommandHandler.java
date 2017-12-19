package com.battle.socket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.apache.catalina.connector.RequestFacade;

@Controller
public class EchoCommandHandler {

	@RequestMapping("/websocket/send")
	public Object test()throws Exception{
		
		System.out.println("............................sss");
		new RequestFacade(null).upgrade(null);
		return null;
	}
}
