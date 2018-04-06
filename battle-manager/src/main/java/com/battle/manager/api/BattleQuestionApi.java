package com.battle.manager.api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodStage;
import com.battle.domain.BattleQuestion;
import com.battle.domain.BattleSubject;
import com.battle.service.BattlePeriodService;
import com.battle.service.BattlePeriodStageService;
import com.battle.service.BattleQuestionService;
import com.battle.service.BattleSubjectService;
import com.wyc.common.domain.vo.ResultVo;

@Controller
@RequestMapping("/api/battle/question")
public class BattleQuestionApi {

	@Autowired
	private BattlePeriodStageService battlePeriodStageService;
	
	@Autowired
	private BattleSubjectService battleSubjectService;
	
	@Autowired
	private BattlePeriodService battlePeriodService;
	
	@Autowired
	private BattleQuestionService battleQuestionService;
	
	
	@RequestMapping("/stages")
	@ResponseBody
	public Object stages(HttpServletRequest httpServletRequest){
		
		String periodId = httpServletRequest.getParameter("periodId");
		List<BattlePeriodStage> battlePeriodStages = battlePeriodStageService.findAllByPeriodIdOrderByIndexAsc(periodId);
		
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battlePeriodStages);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="queryQuestionCount")
	@ResponseBody
	@Transactional
	public ResultVo queryQuestionCount(HttpServletRequest httpServletRequest)throws Exception{
		
		String battleId = httpServletRequest.getParameter("battleId");
		
		//String periodId = httpServletRequest.getParameter("periodId");
		
		String stageId = httpServletRequest.getParameter("stageId");
		
		List<String> stageIds = new ArrayList<>();
		
		stageIds.add(stageId);
		
		List<String> subjectIds = battleSubjectService.getIdsByBattleId(battleId);
		
		List<Object[]> stageSubjectQuestionNums = battleQuestionService.getQuestionNumByStageIdsAndSubjectIds(stageIds,subjectIds);
		
		
		List<Map<String, Object>> data = new ArrayList<>();
		
		for(Object[] list:stageSubjectQuestionNums){
			Map<String, Object> map = new HashMap<>();
			
			map.put("num", list[0]);
			map.put("stageId", list[1]);
			map.put("subjectId", list[2]);
			
			data.add(map);
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(data);
		
		return resultVo;
	}
	
	@RequestMapping("/subjects")
	@ResponseBody
	public Object subjects(HttpServletRequest httpServletRequest){
		
		String battleId = httpServletRequest.getParameter("battleId");
		List<BattleSubject> battleSubjects = battleSubjectService.findAllByBattleIdAndIsDelOrderBySeqAsc(battleId, 0);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleSubjects);
		return resultVo;
	}
	
	@RequestMapping("/periods")
	@ResponseBody
	public Object periods(HttpServletRequest httpServletRequest){
		
		String battleId = httpServletRequest.getParameter("battleId");
		List<BattlePeriod> battlePeriods = battlePeriodService.findAllByBattleIdOrderByIndexAsc(battleId);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battlePeriods);
		return resultVo;
	}
	
	
	@RequestMapping("/questions")
	@ResponseBody
	public Object questions(HttpServletRequest httpServletRequest){
		
		String battleId = httpServletRequest.getParameter("battleId");
		
		String stageId = httpServletRequest.getParameter("stageId");
		
		String subjectId = httpServletRequest.getParameter("subjectId");
		
		String[] subjectIds = new String[]{
				subjectId
		};
		List<BattleQuestion> battleQuestions = battleQuestionService.findAllByBattleIdAndPeriodStageIdAndBattleSubjectIdInAndIsDel(battleId, stageId, subjectIds,0);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleQuestions);
		return resultVo;
	}
	
}
