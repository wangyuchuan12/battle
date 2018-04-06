package com.battle.manager.action;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.battle.domain.Battle;
import com.battle.service.BattleService;
@Controller
@RequestMapping("/battle/question")
public class BattleQuestionController {

	@Autowired
	private BattleService battleService;
	@RequestMapping("/questionManager")
	public Object questionManager(HttpServletRequest httpServletRequest){
		
		String battleId = httpServletRequest.getParameter("battleId");
		
		Battle battle = battleService.findOne(battleId);
		
		httpServletRequest.setAttribute("battle", battle);
		return "question/questionManager";
	}
	
	
}
