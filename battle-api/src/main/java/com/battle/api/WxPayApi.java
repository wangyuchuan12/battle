package com.battle.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.filter.api.WxPayFilterApi;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;


@Controller
@RequestMapping(value="/api/battle/")
public class WxPayApi{

	@RequestMapping(value="wxPayConfig")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=WxPayFilterApi.class)
	public Object wxPayConfig(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		ResultVo resultVo = (ResultVo)sessionManager.getObject(ResultVo.class);
		
		return resultVo;
		
	}
}
