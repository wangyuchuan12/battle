package com.battle.socket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class EchoCommandHandler {

	@RequestMapping("/websocket/send")
	public Object test()throws Exception{
		
		return null;
	}
}
