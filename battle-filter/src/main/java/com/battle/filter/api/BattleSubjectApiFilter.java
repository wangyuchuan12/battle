package com.battle.filter.api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.BattleQuestion;
import com.battle.domain.BattleSubject;
import com.battle.service.BattleQuestionService;
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
	

	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		String battleId = (String)sessionManager.getAttribute(AttrEnum.battleId);
		String periodStageId = (String)sessionManager.getAttribute(AttrEnum.periodStageId);
		List<BattleSubject> battleSubjects = battleSubjectService.findAllByBattleIdOrderBySeqAsc(battleId);
		
		
		List<BattleQuestion> battleQuestions = battleQuestionService.findAllByBattleIdAndPeriodStageId(battleId,periodStageId);
		
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
		
		String periodStageId = httpServletRequest.getParameter("periodStageId");
		
		sessionManager.setAttribute(AttrEnum.battleId, battleId);
		
		sessionManager.setAttribute(AttrEnum.periodStageId, periodStageId);
		
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		return null;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
