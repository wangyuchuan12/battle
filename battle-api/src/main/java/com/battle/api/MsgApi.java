package com.battle.api;

import java.util.Map;

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
	    
	    Map<String, String[]> map = httpServletRequest.getParameterMap();
	    for(String key:map.keySet()){
	    	String value = httpServletRequest.getParameter(key);
	    	
	    	System.out.println("......key:"+key+",value:"+value);
	    }
	    
	    System.out.println("..............signature:"+signature+",timestamp:"+timestamp+",nonce:"+nonce+",echostr:"+echostr);
	    return echostr;
	}
}