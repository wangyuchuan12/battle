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
import com.battle.domain.Context;
import com.battle.domain.Question;
import com.battle.domain.QuestionOption;
import com.battle.service.BattlePeriodService;
import com.battle.service.BattlePeriodStageService;
import com.battle.service.BattleQuestionService;
import com.battle.service.BattleSubjectService;
import com.battle.service.ContextService;
import com.battle.service.QuestionOptionService;
import com.battle.service.QuestionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.fabric.xmlrpc.base.Array;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.util.CommonUtil;

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
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuestionOptionService questionOptionService;
	
	@Autowired
	private ContextService contextService;
	
	
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
		
		List<Object[]> stageSubjectQuestionNums = new ArrayList<>();
		
		if(subjectIds!=null&&subjectIds.size()>0){
			stageSubjectQuestionNums = battleQuestionService.getQuestionNumByStageIdsAndSubjectIds(stageIds,subjectIds);
		}
		
		
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
		
		
		System.out.println(".................battleId:"+battleId+",battlePeriods:"+battlePeriods);
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
		List<BattleQuestion> battleQuestions = new ArrayList<>();
		
		if(!CommonUtil.isEmpty(battleId)&&!CommonUtil.isEmpty(battleId)&&!CommonUtil.isEmpty(subjectId)){
			battleQuestions = battleQuestionService.findAllByBattleIdAndPeriodStageIdAndBattleSubjectIdInAndIsDel(battleId, stageId, subjectIds,0);
		}
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleQuestions);
		return resultVo;
	}
	
	
	@RequestMapping(value="info")
	@ResponseBody
	@Transactional
	public Object info(HttpServletRequest httpServletRequest)throws Exception{
		String id = httpServletRequest.getParameter("id");
		
		BattleQuestion battleQuestion = battleQuestionService.findOne(id);
		
		Question question = questionService.findOne(battleQuestion.getQuestionId());
		
		List<QuestionOption> questionOptions = questionOptionService.findAllByQuestionId(question.getId());
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("id", battleQuestion.getId());
		data.put("answer", battleQuestion.getAnswer());
		data.put("battleId", battleQuestion.getBattleId());
		data.put("battlePeriodId", battleQuestion.getBattlePeriodId());
		data.put("battleSubjectId", battleQuestion.getBattleSubjectId());
		data.put("imgUrl", battleQuestion.getImgUrl());
		data.put("name", battleQuestion.getName());
		
		data.put("periodStageId", battleQuestion.getPeriodStageId());
		data.put("question", battleQuestion.getQuestion());
		data.put("questionId", battleQuestion.getQuestionId());
		data.put("rightAnswer", battleQuestion.getRightAnswer());
		data.put("seq", battleQuestion.getSeq());
		data.put("type", battleQuestion.getType());
		
		data.put("options", questionOptions);
		if(question!=null){
			data.put("fillWords", question.getFillWords());
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(data);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="addQuestion")
	@ResponseBody
	@Transactional
	public Object addQuestion(HttpServletRequest httpServletRequest)throws Exception{
		String stageId = httpServletRequest.getParameter("stageId");
		String subjectId = httpServletRequest.getParameter("subjectId");
		
		String questionType = httpServletRequest.getParameter("questionType");
		
		String question = httpServletRequest.getParameter("question");
		
		String imgUrl = httpServletRequest.getParameter("imgUrl");
		
		String answer = httpServletRequest.getParameter("answer");
		
		String fillWords = httpServletRequest.getParameter("fillWords");
		
		BattlePeriodStage battlePeriodStage = battlePeriodStageService.findOne(stageId);

		
		String periodId = battlePeriodStage.getPeriodId();
		
		String battleId = battlePeriodStage.getBattleId();
		
		Context context = contextService.findOneByCodeBySync(Context.QUESTION_MAX_INDEX_CODE);
		if(context==null){
			context = new Context();
			context.setCode(Context.QUESTION_MAX_INDEX_CODE);
			context.setValue("1");
			contextService.add(context);
		}else{
			String value = context.getValue();
			Integer index = Integer.parseInt(value);
			index++;
			context.setValue(index+"");
			contextService.update(context);
		}
		
		Question questionTarget = new Question();
		questionTarget.setQuestion(question);
		questionTarget.setImgUrl(imgUrl);
		if(!CommonUtil.isEmpty(imgUrl)){
			questionTarget.setIsImg(1);
		}else{
			questionTarget.setIsImg(0);
		}
		
		questionTarget.setIndex(Integer.parseInt(context.getValue()));
		questionTarget.setAnswer(answer);
		questionTarget.setFillWords(fillWords);
		questionTarget.setIsDel(0);
		questionTarget.setSource(Question.MANAGER_SOURCE);
		
		questionService.add(questionTarget);
		
		BattleQuestion battleQuestion = new BattleQuestion();
		battleQuestion.setBattleId(battleId);
		battleQuestion.setBattlePeriodId(periodId);
		battleQuestion.setBattleSubjectId(subjectId);
		battleQuestion.setImgUrl(imgUrl);
		battleQuestion.setName("");
		battleQuestion.setPeriodStageId(battlePeriodStage.getId());
		battleQuestion.setAnswer(answer);
		battleQuestion.setQuestion(question);
		battleQuestion.setIsDel(0);
		
		battleQuestion.setQuestionId(questionTarget.getId());
		battleQuestionService.add(battleQuestion);
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<List<Map<String, String>>> typeReference = new TypeReference<List<Map<String,String>>>() {
		};
		
		//选择题
		if(questionType.equals("0")){
			
			questionTarget.setType(0);
			battleQuestion.setType(0);
			
			String options = httpServletRequest.getParameter("options");
			List<Map<String, String>> questionOptions = objectMapper.readValue(options, typeReference);
			
			StringBuffer sbOptions = new StringBuffer();
			
			for(Map<String, String> questionOptionMap:questionOptions){
				QuestionOption questionOption = new QuestionOption();
				questionOption.setQuestionId(questionTarget.getId());
				questionOption.setSeq(Integer.parseInt(questionOptionMap.get("seq")));
				questionOption.setIsDel(0);
				questionOption.setContent(questionOptionMap.get("content"));
				questionOptionService.add(questionOption);
				String isRight = questionOptionMap.get("isRight");
				
				sbOptions.append(questionOption.getContent()+",");
				
				if(!CommonUtil.isEmpty(isRight)&&isRight.equals("1")){
					questionTarget.setRightOptionId(questionOption.getId());
					questionTarget.setAnswer(questionOption.getContent());
					
					battleQuestion.setRightAnswer(questionOption.getContent());
				}
			}
			
			if(questionOptions!=null&&questionOptions.size()>0){
				sbOptions.deleteCharAt(sbOptions.lastIndexOf(","));
			}
			battleQuestion.setOptions(sbOptions.toString());
		}else if(questionType.equals("1")){
			questionTarget.setType(1);
			battleQuestion.setType(1);
		}else if(questionType.equals("2")){
			questionTarget.setType(2);
			battleQuestion.setType(2);
		}
		battleQuestionService.update(battleQuestion);
		
		questionService.update(questionTarget);
		
		
	
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		return resultVo;
	}
	
	
	@RequestMapping(value="updateQuestion")
	@ResponseBody
	@Transactional
	public Object updateQuestion(HttpServletRequest httpServletRequest)throws Exception{
		String battleQuestionId = httpServletRequest.getParameter("battleQuestionId");
		
		String stageId = httpServletRequest.getParameter("stageId");
		String subjectId = httpServletRequest.getParameter("subjectId");
		
		String questionType = httpServletRequest.getParameter("questionType");
		
		String question = httpServletRequest.getParameter("question");
		
		String imgUrl = httpServletRequest.getParameter("imgUrl");
		
		String answer = httpServletRequest.getParameter("answer");
		
		String fillWords = httpServletRequest.getParameter("fillWords");
		
		BattlePeriodStage battlePeriodStage = battlePeriodStageService.findOne(stageId);
		
		//BattlePeriod battlePeriod = battlePeriodService.findOne(battlePeriodStage.getPeriodId());
		
		String periodId = battlePeriodStage.getPeriodId();
		
		String battleId = battlePeriodStage.getBattleId();
		
		
		BattleQuestion battleQuestion = battleQuestionService.findOne(battleQuestionId);
		
		
		Question questionTarget = questionService.findOne(battleQuestion.getQuestionId());
		
		questionTarget.setQuestion(question);
		questionTarget.setImgUrl(imgUrl);
		questionTarget.setIsImg(1);
		questionTarget.setAnswer(answer);
		questionTarget.setFillWords(fillWords);
		
		
		battleQuestion.setBattleId(battleId);
		battleQuestion.setBattlePeriodId(periodId);
		battleQuestion.setBattleSubjectId(subjectId);
		battleQuestion.setImgUrl(imgUrl);
		battleQuestion.setName("");
		battleQuestion.setPeriodStageId(battlePeriodStage.getId());
		battleQuestion.setAnswer(answer);
		battleQuestion.setQuestion(question);
		
		battleQuestion.setQuestionId(questionTarget.getId());
		
		
		
		
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<List<Map<String, String>>> typeReference = new TypeReference<List<Map<String,String>>>() {
		};
		
		//选择题
		if(questionType.equals("0")){
			
			questionTarget.setType(0);
			battleQuestion.setType(0);
			
			String options = httpServletRequest.getParameter("options");
			List<Map<String, String>> questionOptions = objectMapper.readValue(options, typeReference);
			
			StringBuffer sbOptions = new StringBuffer();
			
			for(Map<String, String> questionOptionMap:questionOptions){
				String id = questionOptionMap.get("id");
				QuestionOption questionOption = questionOptionService.findOne(id);
				questionOption.setQuestionId(questionTarget.getId());
				questionOption.setSeq(Integer.parseInt(questionOptionMap.get("seq")));
				questionOption.setIsDel(0);
				questionOption.setContent(questionOptionMap.get("content"));
				questionOptionService.update(questionOption);
				String isRight = questionOptionMap.get("isRight");
				
				sbOptions.append(questionOption.getContent()+",");
				
				if(!CommonUtil.isEmpty(isRight)&&isRight.equals("1")){
					questionTarget.setRightOptionId(questionOption.getId());
					questionTarget.setAnswer(questionOption.getContent());
					
					battleQuestion.setRightAnswer(questionOption.getContent());
				}
			}
			
			if(questionOptions!=null&&questionOptions.size()>0){
				sbOptions.deleteCharAt(sbOptions.lastIndexOf(","));
			}
			battleQuestion.setOptions(sbOptions.toString());
		}else if(questionType.equals("1")){
			questionTarget.setType(1);
			battleQuestion.setType(1);
		}else if(questionType.equals("2")){
			questionTarget.setType(2);
			battleQuestion.setType(2);
		}
		battleQuestionService.update(battleQuestion);
		
		questionService.update(questionTarget);

		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		return resultVo;
	}
	
}
