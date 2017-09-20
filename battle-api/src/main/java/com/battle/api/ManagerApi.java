package com.battle.api;
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
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.util.CommonUtil;

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
	
	@RequestMapping(value="subjects")
	@ResponseBody
	public Object subjects(HttpServletRequest httpServletRequest)throws Exception{
		String battleId = httpServletRequest.getParameter("battleId");
		List<BattleSubject> battleSubjects = battleSubjectService.findAllByBattleIdAndIsDelOrderBySeqAsc(battleId,0);
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(battleSubjects);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="periods")
	@ResponseBody
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
			battleQuestions = battleQuestionService.findAllByPeriodStageIdAndBattleSubjectIdOrderBySeqAsc(stageId,subjectId);
		}else{
			battleQuestions = battleQuestionService.findAllByPeriodStageIdOrderBySeqAsc(stageId);
		}
		
	
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(battleQuestions);
		
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
		questionTarget.setIsImg(1);
		questionTarget.setIndex(Integer.parseInt(context.getValue()));
		questionService.add(questionTarget);
		
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<List<Map<String, String>>> typeReference = new TypeReference<List<Map<String,String>>>() {
		};
		//选择题
		if(questionType.equals("0")){
			
			
			
			questionTarget.setType(0);
			
			String options = httpServletRequest.getParameter("options");
			List<Map<String, String>> questionOptions = objectMapper.readValue(options, typeReference);
			
			for(Map<String, String> questionOptionMap:questionOptions){
				QuestionOption questionOption = new QuestionOption();
				questionOption.setQuestionId(questionTarget.getId());
				questionOption.setSeq(Integer.parseInt(questionOptionMap.get("seq")));
				questionOption.setIsDel(0);
				questionOption.setContent(questionOptionMap.get("content"));
				questionOptionService.add(questionOption);
				String isRight = questionOptionMap.get("isRight");
				if(!CommonUtil.isEmpty(isRight)&&isRight.equals("1")){
					questionTarget.setRightOptionId(questionOption.getId());
					questionTarget.setAnswer(questionOption.getContent());
				}
			}
		}
		
		questionService.update(questionTarget);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		return resultVo;
	}
}
