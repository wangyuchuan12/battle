package com.battle.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.Battle;
import com.battle.service.BattleService;

@Controller
@RequestMapping(value="/api/battle/")
public class BattleApi {

	@Autowired
	private BattleService battleService;
	@RequestMapping(value="info")
	@ResponseBody
	public Object info(HttpServletRequest httpServletRequest){
		String id = httpServletRequest.getParameter("id");
		Battle battle = battleService.findOne(id);
		return battle;
	}
}
