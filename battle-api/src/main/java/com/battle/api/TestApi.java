package com.battle.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.service.other.MessageHandleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyc.common.domain.vo.ResultVo;

@Controller
@RequestMapping(value="/api/test")
public class TestApi {

	@Autowired
	private MessageHandleService messageHandleService;
	@ResponseBody
	@RequestMapping(value="test")
	public Object test(HttpServletRequest httpServletRequest)throws Exception{
		
		String toUser = httpServletRequest.getParameter("toUser");
		
		String smgtype = httpServletRequest.getParameter("smgtype");
		
		if(smgtype.equals("text")){
			String content = httpServletRequest.getParameter("content");
			
			Map<String, Object> data = new HashMap<>();
			
			data.put("content", content);
			return messageHandleService.send(toUser, smgtype, data);
			
		}else if(smgtype.equals("image")){
			String mediaId = httpServletRequest.getParameter("mediaId");
			Map<String, Object> content = new HashMap<>();
			content.put("media_id", mediaId);
			return messageHandleService.send(toUser, smgtype, content);
		}else if(smgtype.equals("link")){
			String title = httpServletRequest.getParameter("title");
			String description = httpServletRequest.getParameter("description");
			String url = httpServletRequest.getParameter("url");
			String thumbUrl = httpServletRequest.getParameter("thumbUrl");
			Map<String, Object> content = new HashMap<>();
			content.put("title", title);
			content.put("description", description);
			content.put("url", url);
			content.put("thumb_url", thumbUrl);
			return messageHandleService.send(toUser, smgtype, content);
		}
		
		return null;
	}
}
