package com.battle.service.other;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomService {

	@Autowired
	private MessageHandleService messageHandleService;
	public Object sendLinkMsg(String toUser,String title,String description,String url,String thumbUrl)throws Exception{
	
		Map<String, Object> content = new HashMap<>();
		content.put("title", title);
		content.put("description", description);
		content.put("url", url);
		content.put("thumb_url", thumbUrl);
		String msg = messageHandleService.send(toUser, "link", content);
		return msg;
	}
}
