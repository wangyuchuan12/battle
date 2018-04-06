package com.battle.manager.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.battle.domain.Battle;
import com.battle.domain.BattleSubject;
import com.battle.service.BattleService;
import com.battle.service.BattleSubjectService;

@Controller
@RequestMapping("/battle/subject")
public class BattleSubjectController {

	@Autowired
	private BattleSubjectService battleSubjectService;
	
	@Autowired
	private BattleService battleService;
	
	
	@RequestMapping("/list")
	public String subjects(HttpServletRequest httpServletRequest){
		
		String battleId = httpServletRequest.getParameter("battleId");
		
		List<BattleSubject> battleSubjects = battleSubjectService.findAllByBattleIdAndIsDelOrderBySeqAsc(battleId,0);
		
		Battle battle = battleService.findOne(battleId);
		
		httpServletRequest.setAttribute("subjects", battleSubjects);
		httpServletRequest.setAttribute("battle", battle);
		
		return "subject/subjects";
	}
}
