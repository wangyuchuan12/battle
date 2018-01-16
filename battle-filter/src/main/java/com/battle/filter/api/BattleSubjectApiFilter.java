package com.battle.filter.api;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.battle.domain.BattlePeriodStage;
import com.battle.domain.BattleQuestion;
import com.battle.domain.BattleSubject;
import com.battle.filter.element.CurrentBattlePeriodMemberFilter;
import com.battle.filter.element.CurrentBattleUserFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattlePeriodStageService;
import com.battle.service.BattleQuestionService;
import com.battle.service.BattleSubjectService;
import com.wyc.AttrEnum;
import com.wyc.common.api.LoginApi;
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
	
	final static Logger logger = LoggerFactory.getLogger(BattleSubjectApiFilter.class);

	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		String battleId = (String)sessionManager.getAttribute(AttrEnum.battleId);
		Integer periodStageIndex = (Integer)sessionManager.getAttribute(AttrEnum.periodStageIndex);
		
		
		String periodId = (String)sessionManager.getAttribute(AttrEnum.periodId);
		
		BattlePeriodStage battlePeriodStage = battlePeriodStageService.findOneByBattleIdAndPeriodIdAndIndex(battleId,periodId,periodStageIndex);
		
		if(battlePeriodStage==null){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("返回的battlePeriodStage为空");
			
			logger.error("返回的battlePeriodStage为空");
			return resultVo;

			
		}
		
		
		List<BattleSubject> battleSubjects = battleSubjectService.findAllByBattleIdAndIsDelOrderBySeqAsc(battleId,0);
		
		Pageable pageable = new PageRequest(0, 20);
		
		
		List<BattleQuestion> battleQuestions = battleQuestionService.findAllByBattleIdAndPeriodStageIdRandom(battleId,battlePeriodStage.getId(),pageable);
		
		Map<String, List<String>> battleQuestionMap = new HashMap<>();
		for(BattleQuestion battleQuestion:battleQuestions){
			List<String> questions = battleQuestionMap.get(battleQuestion.getBattleSubjectId());
			
			if(questions==null){
				questions = new ArrayList<>();
			}
			
			questions.add(battleQuestion.getQuestionId());
			battleQuestionMap.put(battleQuestion.getBattleSubjectId(), questions);
		}
		
		List<Map<String, Object>> battleSubjectsData = new ArrayList<>();
		for(BattleSubject battleSubject:battleSubjects){
			Map<String, Object> battleSubjectMap = new HashMap<>();
			battleSubjectMap.put("id", battleSubject.getId());
			battleSubjectMap.put("battleId", battleSubject.getBattleId());
			battleSubjectMap.put("imgUrl", battleSubject.getImgUrl());
			battleSubjectMap.put("name", battleSubject.getName());
			battleSubjectMap.put("seq", battleSubject.getSeq());
			
			List<String> questions = battleQuestionMap.get(battleSubject.getId());
			
			if(questions!=null){
				battleSubjectMap.put("num", 1);
				battleSubjectMap.put("questions", questions);
				battleSubjectsData.add(battleSubjectMap);
			}else{
				//battleSubjectMap.put("num", 0);
			}
		}
		
		//如果大于6随机打乱抽取6个
		if(battleSubjectsData.size()>6){
			Collections.shuffle(battleSubjectsData);
			battleSubjectsData = battleSubjectsData.subList(0, 6);
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
		
		String roomId = httpServletRequest.getParameter("roomId");
		
		String memberId = httpServletRequest.getParameter("memberId");
		
		String periodId = httpServletRequest.getParameter("periodId");
		
		sessionManager.setAttribute(AttrEnum.roomId, roomId);
		
		sessionManager.setAttribute(AttrEnum.periodId, periodId);
		
		
		sessionManager.setAttribute(AttrEnum.battleId, battleId);
		
		sessionManager.setAttribute(AttrEnum.periodMemberId, memberId);
		
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
