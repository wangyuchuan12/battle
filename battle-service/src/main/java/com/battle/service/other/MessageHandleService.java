package com.battle.service.other;

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
	
	public String send(String toUser,String templateId,Integer page,String formId)throws Exception{
		
		AccessTokenBean accessTokenBean = accessTokenSmartService.get();
		Request request = requestFactory.templateSendRequest(accessTokenBean.getAccessToken());
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		BattleMessage battleMessage = new BattleMessage();
		
		battleMessage.setTouser(toUser);
		
		battleMessage.setTemplateId(templateId);
		
		battleMessage.setPage(page+"");
		
		battleMessage.setFormId(formId);
		
		String message = objectMapper.writeValueAsString(battleMessage);
		Response response = request.post(message);
		
		String msg = response.read();
		
		return msg;
	}
}
