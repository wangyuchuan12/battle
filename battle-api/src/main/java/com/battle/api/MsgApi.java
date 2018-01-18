package com.battle.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/api/battle/msg/")
public class MsgApi {
	@RequestMapping(value="info")
	@ResponseBody
	public String send(HttpServletRequest httpServletRequest){
		String signature = httpServletRequest.getParameter("signature");
	    String timestamp = httpServletRequest.getParameter("timestamp");
	    String nonce = httpServletRequest.getParameter("nonce");
	    String echostr= httpServletRequest.getParameter("echostr");
	    return echostr;
	}
}
