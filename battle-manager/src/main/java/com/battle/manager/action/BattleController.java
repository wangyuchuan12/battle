package com.battle.manager.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.battle.domain.Battle;
import com.battle.service.BattleService;

@Controller
@RequestMapping("/battle")
public class BattleController {

	@Autowired
	private BattleService battleService;
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest){
		
		List<Battle> battles = battleService.findAllByStatusOrderByIndexAsc(Battle.IN_STATUS);
		
		httpServletRequest.setAttribute("battles", battles);
		return "battle/list";
	}
	
	
	@RequestMapping("/add")
	public Object add(HttpServletRequest httpServletRequest){
		return "battle/add";
	}
	
	
	
	@RequestMapping("/update")
	public Object update(HttpServletRequest httpServletRequest){
		
		String id = httpServletRequest.getParameter("id");
		Battle battle = battleService.findOne(id);
		
		httpServletRequest.setAttribute("battle", battle);
		return "battle/update";
	}
}
