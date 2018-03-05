package com.battle.service.other;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.BattleMessage;
import com.battle.service.BattleMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyc.common.util.Response;
import com.wyc.common.smart.service.AccessTokenSmartService;
import com.wyc.common.util.Request;
import com.wyc.common.util.RequestFactory;
import com.wyc.common.wx.domain.AccessTokenBean;

@Service
public class MessageHandleService {

	@Autowired
	private BattleMessageService battleMessageService;
	
	@Autowired
	private RequestFactory requestFactory;
	
	@Autowired
	private AccessTokenSmartService accessTokenSmartService;
	
	public String send(String toUser,String smgtype,Object content)throws Exception{
		
		AccessTokenBean accessTokenBean = accessTokenSmartService.get();
		
		String token = accessTokenBean.getAccessToken();

		
		Request request = requestFactory.templateSend(token);
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("touser", toUser);
		data.put("msgtype",smgtype);
		
		if(smgtype.equals("text")){
			data.put("text", content);
		}else if(smgtype.equals("image")){
			data.put("image",content);
		}else if(smgtype.equals("link")){
			data.put("link",content);
		}
		
		
		
		String message = objectMapper.writeValueAsString(data);
		Response response = request.post(message);
		
		String msg = response.read();
		
		return msg;
	}
}
