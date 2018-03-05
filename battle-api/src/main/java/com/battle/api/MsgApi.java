package com.battle.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.service.other.CustomService;

@Controller
@RequestMapping(value="/api/battle/msg/")
public class MsgApi {
	@Autowired
	private CustomService customService;
	@RequestMapping(value="info")
	@ResponseBody
	public String send(HttpServletRequest httpServletRequest){
		String signature = httpServletRequest.getParameter("signature");
	    String timestamp = httpServletRequest.getParameter("timestamp");
	    String nonce = httpServletRequest.getParameter("nonce");
	    String echostr= httpServletRequest.getParameter("echostr");
	    final String openid = httpServletRequest.getParameter("openid");
	    
	    
	    Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
			 try{
				 Thread.sleep(1000);
				 customService.sendLinkMsg(openid,"答题闯关比赛", "点击关注", "http://mp.weixin.qq.com/s/sBuNYcQiTALnLt3Ri6GKMA", "http://ovqk5bop3.bkt.clouddn.com/03bf965642aeb4a91f217597b4751207.png");
			    }catch(Exception e){
			    	
			    }
			}
		});
	    
	    thread.start();
	   
	    return echostr;
	}
}