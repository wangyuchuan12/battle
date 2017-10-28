package com.battle.api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.Battle;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodStage;
import com.battle.domain.BattleQuestion;
import com.battle.domain.BattleSubject;
import com.battle.domain.BattleUser;
import com.battle.domain.Context;
import com.battle.domain.Question;
import com.battle.domain.QuestionOption;
import com.battle.filter.element.BattleManagerFilter;
import com.battle.service.BattlePeriodService;
import com.battle.service.BattlePeriodStageService;
import com.battle.service.BattleQuestionService;
import com.battle.service.BattleService;
import com.battle.service.BattleSubjectService;
import com.battle.service.ContextService;
import com.battle.service.QuestionOptionService;
import com.battle.service.QuestionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/manager/")
public class ManagerApi {
	
	@Autowired
	private BattleSubjectService battleSubjectService;
	
	@Autowired
	private BattlePeriodService battlePeriodService;
	
	
	@Autowired
	private BattlePeriodStageService battlePeriodStageService;
	
	@Autowired
	private BattleQuestionService battleQuestionService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuestionOptionService questionOptionService;
	
	@Autowired
	private ContextService contextService;
	
	@Autowired
	private BattleService battleService;
	
	@RequestMapping(value="subjects")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=BattleManagerFilter.class)
	public Object subjects(HttpServletRequest httpServletRequest)throws Exception{
		String battleId = httpServletRequest.getParameter("battleId");
		List<BattleSubject> battleSubjects = battleSubjectService.findAllByBattleIdAndIsDelOrderBySeqAsc(battleId,0);
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(battleSubjects);
		
		return resultVo;
	}
	
	@RequestMapping(value="addBattleInfo")
	@ResponseBody
	public Object addBattleInfo(HttpServletRequest httpServletRequest)throws Exception{
		String name = httpServletRequest.getParameter("name");
		String instruction = httpServletRequest.getParameter("instruction");
		String headImg = httpServletRequest.getParameter("headImg");
		
		Battle battle = new Battle();
		battle.setHeadImg(headImg);
		battle.setInstruction(instruction);
		battle.setName(name);
		
		battleService.add(battle);
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(battle);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="updateBattleInfo")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=BattleManagerFilter.class)
	public Object updateBattleInfo(HttpServletRequest httpServletRequest)throws Exception{
		String battleId = httpServletRequest.getParameter("battleId");
		String name = httpServletRequest.getParameter("name");
		String instruction = httpServletRequest.getParameter("instruction");
		String headImg = httpServletRequest.getParameter("headImg");
		
		Battle battle = battleService.findOne(battleId);
		battle.setHeadImg(headImg);
		battle.setInstruction(instruction);
		battle.setName(name);
		
		battleService.update(battle);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		return resultVo;
	}
	
	@RequestMapping(value="battleInfo")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=BattleManagerFilter.class)
	public Object battleInfo(HttpServletRequest httpServletRequest)throws Exception{
		String battleId = httpServletRequest.getParameter("battleId");
		Battle battle = battleService.findOne(battleId);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battle);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="periods")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=BattleManagerFilter.class)
	public Object periods(HttpServletRequest httpServletRequest)throws Exception{
		String battleId = httpServletRequest.getParameter("battleId");
		List<BattlePeriod> battlePeriods = battlePeriodService.findAllByBattleIdOrderByIndexAsc(battleId);
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(battlePeriods);
		
		return resultVo;
	}

	@RequestMapping(value="addSubject")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=BattleManagerFilter.class)
	public Object addSubject(HttpServletRequest httpServletRequest)throws Exception{
		String battleId = httpServletRequest.getParameter("battleId");
		
		String name = httpServletRequest.getParameter("name");
		
		String imgUrl = httpServletRequest.getParameter("imgUrl");
		
		BattleSubject battleSubject = new BattleSubject();
		battleSubject.setBattleId(battleId);
		battleSubject.setImgUrl(imgUrl);
		battleSubject.setName(name);
		battleSubject.setIsDel(0);
		
		battleSubjectService.add(battleSubject);
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(battleSubject);
		
		return resultVo;
		
	}
	
	@RequestMapping(value="delSubject")
	@ResponseBody
	public Object delSubject(HttpServletRequest httpServletRequest){
		String subjectId = httpServletRequest.getParameter("subjectId");
		
		BattleSubject battleSubject = battleSubjectService.findOne(subjectId);
		
		battleSubject.setIsDel(1);
		
		battleSubjectService.update(battleSubject);
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		return resultVo;
	}
	
	@RequestMapping(value="stages")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=BattleManagerFilter.class)
	public Object stages(HttpServletRequest httpServletRequest)throws Exception{
		String periodId = httpServletRequest.getParameter("periodId");
		
		List<BattlePeriodStage> battlePeriodStages = battlePeriodStageService.findAllByPeriodIdOrderByIndexAsc(periodId);
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(battlePeriodStages);
		
		return resultVo;
	}
	
	@RequestMapping(value="questions")
	@ResponseBody
	public Object questions(HttpServletRequest httpServletRequest)throws Exception{
		String stageId = httpServletRequest.getParameter("stageId");
		String subjectId = httpServletRequest.getParameter("subjectId");
		
		List<BattleQuestion> battleQuestions = null;
		
		
		if(!CommonUtil.isEmpty(subjectId)){
			battleQuestions = battleQuestionService.findAllByPeriodStageIdAndBattleSubjectIdAndIsDelOrderBySeqAsc(stageId,subjectId,0);
		}else{
			battleQuestions = battleQuestionService.findAllByPeriodStageIdAndIsDelOrderBySeqAsc(stageId,0);
		}
		
	
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(battleQuestions);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="updateStage")
	@ResponseBody
	@Transactional
	public Object updateStage(HttpServletRequest httpServletRequest)throws Exception{
		String stageId = httpServletRequest.getParameter("stageId");
		String num = httpServletRequest.getParameter("num");
		
		BattlePeriodStage battlePeriodStage = battlePeriodStageService.findOne(stageId);
		
		battlePeriodStage.setQuestionCount(Integer.parseInt(num));
		
		battlePeriodStageService.update(battlePeriodStage);
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		
		return resultVo;
	}
	
	@RequestMapping(value="addStage")
	@ResponseBody
	@Transactional
	public Object addStage(HttpServletRequest httpServletRequest)throws Exception{
		String num = httpServletRequest.getParameter("num");
		String periodId = httpServletRequest.getParameter("periodId");
			
		BattlePeriod battlePeriod = battlePeriodService.findOne(periodId);
		
		Integer stageCount = battlePeriod.getStageCount();
		if(stageCount==null){
			stageCount=0;
		}
		
		stageCount++;
		
		battlePeriod.setStageCount(stageCount);
		
		battlePeriodService.update(battlePeriod);
	
		
		BattlePeriodStage battlePeriodStage = new BattlePeriodStage();
		battlePeriodStage.setBattleId(battlePeriod.getBattleId());
		battlePeriodStage.setPeriodId(periodId);
		battlePeriodStage.setQuestionCount(Integer.parseInt(num));
		battlePeriodStage.setIndex(stageCount);
		
		battlePeriodStageService.add(battlePeriodStage);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battlePeriodStage);
		return resultVo;
				
	}
	
	@RequestMapping(value="delQuestion")
	@ResponseBody
	@Transactional
	public Object deleteQuestion(HttpServletRequest httpServletRequest)throws Exception{
		String id = httpServletRequest.getParameter("id");
		
		
		BattleQuestion battleQuestion = battleQuestionService.findOne(id);
		Question question = questionService.findOne(battleQuestion.getQuestionId());
		
		battleQuestion.setIsDel(1);
		
		question.setIsDel(1);
		
		battleQuestionService.update(battleQuestion);
		
		questionService.update(question);
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="battleImgUpdate")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=BattleManagerFilter.class)
	public Object battleImgUpdate(HttpServletRequest httpServletRequest)throws Exception{
		String battleId = httpServletRequest.getParameter("battleId");
		String imgUrl = httpServletRequest.getParameter("imgUrl");
		Battle battle = battleService.findOne(battleId);
		battle.setHeadImg(imgUrl);
		
		battleService.update(battle);
		
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
		
		BattlePeriod battlePeriod = battlePeriodService.findOne(battlePeriodStage.getPeriodId());
		
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
		battleQuestion.setBattlePeriodIndex(battlePeriod.getIndex());
		battleQuestion.setBattleSubjectId(subjectId);
		battleQuestion.setImgUrl(imgUrl);
		battleQuestion.setName("");
		battleQuestion.setPeriodStageId(battlePeriodStage.getId());
		battleQuestion.setAnswer(answer);
		battleQuestion.setQuestion(question);
		
		battleQuestion.setQuestionId(questionTarget.getId());
		
		
		String oldSubjectId = battleQuestion.getBattleSubjectId();
		if(!oldSubjectId.equals(subjectId)){
			BattleSubject oldBattleSubject = battleSubjectService.findOne(oldSubjectId);
			
			String oldBattleQuestionIdStr = oldBattleSubject.getBattleQuestionIds();
			
			if(!CommonUtil.isEmpty(oldBattleQuestionIdStr)){
				String[] oldBattleQuestionIds = oldBattleQuestionIdStr.split(",");
				StringBuffer sb = new StringBuffer();
				for(String oldBattleQuestionId:oldBattleQuestionIds){
					if(!oldBattleQuestionId.equals(battleQuestionId)){
						sb.append(oldBattleQuestionId);
					}
				}
				
				oldBattleSubject.setBattleQuestionIds(sb.toString());
				
				battleSubjectService.update(oldBattleSubject);
			}else{
				BattleSubject battleSubject = battleSubjectService.findOne(subjectId);
				
				String battleQuestionIds = battleSubject.getBattleQuestionIds();
				
				if(CommonUtil.isEmpty(battleQuestionIds)){
					battleQuestionIds = questionTarget.getId();
				}else{
					battleQuestionIds = battleQuestionIds+","+questionTarget.getId();
				}
				
				battleSubject.setBattleQuestionIds(battleQuestionIds);
				
				battleSubjectService.update(battleSubject);
			}
			
		}
		
		
		
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
	
	
	@RequestMapping(value="startUpPeriod")
	@ResponseBody
	@Transactional
//	@HandlerAnnotation(hanlerFilter=CurrentBattleUserFilter.class)
	public Object startUpPeriod(HttpServletRequest httpServletRequest)throws Exception{

		String periodId = httpServletRequest.getParameter("periodId");
		
		BattlePeriod battlePeriod = battlePeriodService.findOne(periodId);
		
		List<BattlePeriodStage> battlePeriodStages = battlePeriodStageService.findAllByPeriodIdOrderByIndexAsc(periodId);
		
		List<BattleQuestion> battleQuestions = battleQuestionService.findAllByBattleIdAndBattlePeriodIdAndIsDel(battlePeriod.getBattleId(),battlePeriod.getId(),0);
		
		//第一键值是battleStageId,第二个建是subjectId
		Map<String, Map<String,List<BattleQuestion>>> battleQuestionMap = new HashMap<>();
		
		for(BattleQuestion battleQuestion: battleQuestions){
			Map<String, List<BattleQuestion>> subjectMap = battleQuestionMap.get(battleQuestion.getPeriodStageId());
			if(subjectMap==null){
				subjectMap = new HashMap<>();
				battleQuestionMap.put(battleQuestion.getPeriodStageId(), subjectMap);
			}
			List<BattleQuestion> subjectBattleQuestions = subjectMap.get(battleQuestion.getBattleSubjectId());
			if(subjectBattleQuestions==null){
				subjectBattleQuestions  = new ArrayList<>();
				subjectMap.put(battleQuestion.getBattleSubjectId(), subjectBattleQuestions);
			}
			subjectBattleQuestions.add(battleQuestion);
		}
		
		for(BattlePeriodStage battlePeriodStage:battlePeriodStages){
			Map<String, List<BattleQuestion>> subjectQuestionMap = battleQuestionMap.get(battlePeriodStage.getId());

			if(subjectQuestionMap==null){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("第"+battlePeriodStage.getIndex()+"阶段题目类别数量小于3");
				return resultVo;
			}
			
			if(subjectQuestionMap!=null&&subjectQuestionMap.size()<3){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("第"+battlePeriodStage.getIndex()+"阶段题目类别数量小于3");
				return resultVo;
			}
			
			Integer num = 0;
			for(Entry<String, List<BattleQuestion>> entry:subjectQuestionMap.entrySet()){
				List<BattleQuestion> subjectQuestions = entry.getValue();
				num = num + subjectQuestions.size();
			}
			
			System.out.println("num:"+battlePeriodStage.getQuestionCount()+",num2:"+num);
			
			if(battlePeriodStage.getQuestionCount()>num){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("第"+battlePeriodStage.getIndex()+"阶段题目数量小于"+battlePeriodStage.getQuestionCount());
				return resultVo;
			}
		}
		
		battlePeriod.setStatus(BattlePeriod.IN_STATUS);
		
		battlePeriodService.update(battlePeriod);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="addPeriod")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=BattleManagerFilter.class)
	public Object addPeriod(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		String battleId = httpServletRequest.getParameter("battleId");
		BattleUser battleUser = sessionManager.getObject(BattleUser.class);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		Battle battle = battleService.findOne(battleId);
		Integer maxPeriodIndex = battle.getMaxPeriodIndex();
		if(maxPeriodIndex==null){
			maxPeriodIndex = 0;
		}
		maxPeriodIndex++;
		
		BattlePeriod battlePeriod = battlePeriodService.findOneByBattleIdAndAuthorBattleUserIdAndStatus(battleId,battleUser.getId(),BattlePeriod.FREE_STATUS);
		
		if(battlePeriod!=null){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			resultVo.setData(battlePeriod);
			return resultVo;
		}
		battlePeriod = new BattlePeriod();
		battlePeriod.setBattleId(battleId);
		battlePeriod.setIndex(maxPeriodIndex);
		battlePeriod.setAuthorBattleUserId(battleUser.getId());
		battlePeriod.setStatus(BattlePeriod.FREE_STATUS);
		battlePeriod.setTakepartCount(0);
		
		battlePeriod.setIsDefault(0);
		
		battlePeriod.setOwnerImg(userInfo.getHeadimgurl());
		
		battlePeriod.setOwnerNickname(userInfo.getNickname());
		
		battlePeriod.setStageCount(1);
		
		battlePeriod.setUnit(1);
		
		battlePeriod.setRightCount(0);
		
		battlePeriod.setWrongCount(0);
		
		if(battleUser.getIsManager()==1){
			battlePeriod.setIsPublic(1);
		}else{
			battlePeriod.setIsPublic(0);
		}
		
		battle.setMaxPeriodIndex(maxPeriodIndex);
		
		battlePeriodService.add(battlePeriod);
		battleService.update(battle);
		
		
		for(Integer i=1;i<11;i++){
			BattlePeriodStage battlePeriodStage = new BattlePeriodStage();
			battlePeriodStage.setBattleId(battleId);
			battlePeriodStage.setIndex(i);
			battlePeriodStage.setPeriodId(battlePeriod.getId());
			battlePeriodStage.setQuestionCount(4);
			battlePeriodStageService.add(battlePeriodStage);
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battlePeriod);
		
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
		
		BattlePeriod battlePeriod = battlePeriodService.findOne(battlePeriodStage.getPeriodId());
		
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
		questionTarget.setIsImg(1);
		questionTarget.setIndex(Integer.parseInt(context.getValue()));
		questionTarget.setAnswer(answer);
		questionTarget.setFillWords(fillWords);
		questionTarget.setIsDel(0);
		
		questionService.add(questionTarget);
		
		BattleQuestion battleQuestion = new BattleQuestion();
		battleQuestion.setBattleId(battleId);
		battleQuestion.setBattlePeriodId(periodId);
		battleQuestion.setBattlePeriodIndex(battlePeriod.getIndex());
		battleQuestion.setBattleSubjectId(subjectId);
		battleQuestion.setImgUrl(imgUrl);
		battleQuestion.setName("");
		battleQuestion.setPeriodStageId(battlePeriodStage.getId());
		battleQuestion.setAnswer(answer);
		battleQuestion.setQuestion(question);
		battleQuestion.setIsDel(0);
		
		battleQuestion.setQuestionId(questionTarget.getId());
		battleQuestionService.add(battleQuestion);
		
		BattleSubject battleSubject = battleSubjectService.findOne(subjectId);
		
		String battleQuestionIds = battleSubject.getBattleQuestionIds();
		
		if(CommonUtil.isEmpty(battleQuestionIds)){
			battleQuestionIds = questionTarget.getId();
		}else{
			battleQuestionIds = battleQuestionIds+","+questionTarget.getId();
		}
		
		battleSubject.setBattleQuestionIds(battleQuestionIds);
		
		battleSubjectService.update(battleSubject);
		
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
}
