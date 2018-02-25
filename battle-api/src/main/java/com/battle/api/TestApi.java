package com.battle.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.service.other.MessageHandleService;
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
		
		String templateId = httpServletRequest.getParameter("templateId");
		
		String page = httpServletRequest.getParameter("page");
		
		String formId = httpServletRequest.getParameter("formId");
		String msg = messageHandleService.send(toUser, templateId, Integer.parseInt(page), formId);
		
		return msg;
	}
}
