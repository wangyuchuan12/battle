package com.battle.filter.api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.Battle;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodStage;
import com.battle.domain.BattleQuestion;
import com.battle.domain.BattleSubject;
import com.battle.filter.element.CurrentBattlePeriodMemberFilter;
import com.battle.filter.element.CurrentBattleUserFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattlePeriodService;
import com.battle.service.BattlePeriodStageService;
import com.battle.service.BattleQuestionService;
import com.battle.service.BattleService;
import com.battle.service.BattleSubjectService;
import com.wyc.AttrEnum;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class BattleSubjectApiFilter extends Filter{

	@Autowired
	private BattleSubjectService battleSubjectService;
	
	@Autowired
	private BattleQuestionService battleQuestionService;
	
	@Autowired
	private BattlePeriodStageService battlePeriodStageService;
	
	@Autowired
	private BattlePeriodService battlePeriodService;
	
	@Autowired
	private BattleService battleService;

	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		String battleId = (String)sessionManager.getAttribute(AttrEnum.battleId);
		Integer periodStageIndex = (Integer)sessionManager.getAttribute(AttrEnum.periodStageIndex);
		
		String periodId = (String)sessionManager.getAttribute(AttrEnum.periodId);
		
		System.out.println("battleId:"+battleId+",periodStageIndex:"+periodStageIndex+",periodId:"+periodId);
		
		BattlePeriodStage battlePeriodStage = battlePeriodStageService.findOneByBattleIdAndPeriodIdAndIndex(battleId,periodId,periodStageIndex);
		List<BattleSubject> battleSubjects = battleSubjectService.findAllByBattleIdOrderBySeqAsc(battleId);
		
		
		List<BattleQuestion> battleQuestions = battleQuestionService.findAllByBattleIdAndPeriodStageId(battleId,battlePeriodStage.getId());
		
		Map<String, Integer> battleQuestionMap = new HashMap<>();
		for(BattleQuestion battleQuestion:battleQuestions){
			Integer num = battleQuestionMap.get(battleQuestion.getBattleSubjectId());
			if(num==null){
				num = 0;
			}
			num++;
			battleQuestionMap.put(battleQuestion.getBattleSubjectId(), num);
		}
		
		List<Map<String, Object>> battleSubjectsData = new ArrayList<>();
		for(BattleSubject battleSubject:battleSubjects){
			Map<String, Object> battleSubjectMap = new HashMap<>();
			battleSubjectMap.put("id", battleSubject.getId());
			battleSubjectMap.put("battleId", battleSubject.getBattleId());
			battleSubjectMap.put("imgUrl", battleSubject.getImgUrl());
			battleSubjectMap.put("name", battleSubject.getName());
			battleSubjectMap.put("seq", battleSubject.getSeq());
			
			Integer num = battleQuestionMap.get(battleSubject.getId());
			if(num==null){
				num = 0;
			}
			
			battleSubjectMap.put("num", num);
			
			battleSubjectsData.add(battleSubjectMap);
		}
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleSubjectsData);
		return resultVo;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		
		String battleId = httpServletRequest.getParameter("battleId");
		
		
		sessionManager.setAttribute(AttrEnum.battleId, battleId);
		
		Battle battle = battleService.findOne(battleId);
		
		BattlePeriod battlePeriod = battlePeriodService.findOneByBattleIdAndIndex(battle.getId(), battle.getCurrentPeriodIndex());
		
		sessionManager.save(battlePeriod);
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		List<Class<? extends Filter>> classes = new ArrayList<>();
		classes.add(LoginStatusFilter.class);
		classes.add(CurrentBattleUserFilter.class);
		classes.add(CurrentBattlePeriodMemberFilter.class);
		return classes;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
