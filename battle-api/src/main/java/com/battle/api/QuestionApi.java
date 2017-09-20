package com.battle.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattleMemberPaperAnswer;
import com.battle.domain.BattleMemberQuestionAnswer;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.Question;
import com.battle.domain.QuestionAnswer;
import com.battle.domain.QuestionAnswerItem;
import com.battle.domain.QuestionOption;
import com.battle.filter.element.CurrentMemberInfoFilter;
import com.battle.service.BattleMemberPaperAnswerService;
import com.battle.service.BattleMemberQuestionAnswerService;
import com.battle.service.QuestionAnswerItemService;
import com.battle.service.QuestionAnswerService;
import com.battle.service.QuestionOptionService;
import com.battle.service.QuestionService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;

@Controller
@RequestMapping(value="/api/question/")
public class QuestionApi {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuestionOptionService questionOptionService;
	
	@Autowired
	private QuestionAnswerService questionAnswerService;
	
	@Autowired
	private BattleMemberPaperAnswerService battleMemberPaperAnswerService;
	
	@Autowired
	private QuestionAnswerItemService questionAnswerItemService;
	
	@Autowired
	private BattleMemberQuestionAnswerService battleMemberQuestionAnswerService;
	
	@RequestMapping(value="battleQuestionAnswer")
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	@ResponseBody
	@Transactional
	public Object battleQuestionAnswer(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
	
		
		String id = httpServletRequest.getParameter("id");
		
		String stageIndex = httpServletRequest.getParameter("stageIndex");
		
		
		Question question = questionService.findOne(id);
		
		QuestionAnswer questionAnswer = questionAnswerService.findOneByTargetIdAndType(battlePeriodMember.getId()+"_"+stageIndex, QuestionAnswer.BATTLE_TYPE);
		
		BattleMemberPaperAnswer battleMemberPaperAnswer = battleMemberPaperAnswerService.findOneByQuestionAnswerId(questionAnswer.getId());
		
		QuestionAnswerItem questionAnswerItem = new QuestionAnswerItem();
		
		questionAnswerItem.setQuestionAnswerId(questionAnswer.getId());
		questionAnswerItem.setQuestionId(question.getId());
		questionAnswerItem.setType(question.getType());
		
		BattleMemberQuestionAnswer battleMemberQuestionAnswer = new BattleMemberQuestionAnswer();
		
		battleMemberQuestionAnswer.setBattleMemberPaperAnswerId(battleMemberPaperAnswer.getId());
		battleMemberQuestionAnswer.setImgUrl(question.getImgUrl());
		battleMemberQuestionAnswer.setQuestion(question.getQuestion());
		battleMemberQuestionAnswer.setQuestionId(question.getId());
		battleMemberQuestionAnswer.setType(question.getType());
		
		Map<String, Object> result = new HashMap<>();
		
		
		ResultVo resultVo = new ResultVo();
		if(question.getType()==Question.SELECT_TYPE){
			String optionId = httpServletRequest.getParameter("optionId");
			
			QuestionOption myOption = questionOptionService.findOne(optionId);
			
			QuestionOption rightOption = questionOptionService.findOne(question.getRightOptionId());
			StringBuffer sb = new StringBuffer();
			List<QuestionOption> questionOptions = questionOptionService.findAllByQuestionId(question.getId());
			for(QuestionOption questionOption:questionOptions){
				sb.append(questionOption.getContent());
				sb.append(",");
			}
			
			if(questionOptions!=null&&questionOptions.size()>0){
				sb.deleteCharAt(sb.lastIndexOf(","));
			}
			
			questionAnswerItem.setMyAnswer(myOption.getContent());
			questionAnswerItem.setMyOptionId(optionId);
			questionAnswerItem.setRightAnswer(rightOption.getContent());
			questionAnswerItem.setRightOptionId(question.getRightOptionId());
			
			
			
			battleMemberQuestionAnswer.setAnswer(myOption.getContent());
			
			
			battleMemberQuestionAnswer.setOptions(sb.toString());
			
			battleMemberQuestionAnswer.setRightAnswer(rightOption.getContent());
			
			if(optionId.equals(question.getRightOptionId())){
				
				questionAnswerItem.setIsRight(1);
				
				result.put("right", true);
				
				questionAnswer.setRightSum(questionAnswer.getRightSum()+1);
				
				battleMemberPaperAnswer.setRightSum(battleMemberPaperAnswer.getRightSum()+1);
			}else{
				
				questionAnswerItem.setIsRight(0);
				result.put("right", false);
				
				questionAnswer.setWrongSum(questionAnswer.getWrongSum()+1);
				
				battleMemberPaperAnswer.setWrongSum(battleMemberPaperAnswer.getWrongSum()+1);
			}
			resultVo.setSuccess(true);
			resultVo.setData(result);
			
			
			
			
		}else if(question.getType()==Question.INPUT_TYPE){
			String answer = httpServletRequest.getParameter("answer");
			
			questionAnswerItem.setMyAnswer(answer);
			questionAnswerItem.setRightAnswer(question.getAnswer());
			battleMemberQuestionAnswer.setAnswer(answer);
			battleMemberQuestionAnswer.setRightAnswer(question.getAnswer());
			if(answer.equals(question.getAnswer())){
				
				questionAnswerItem.setIsRight(1);
				
				result.put("right", true);
				
				battleMemberPaperAnswer.setRightSum(battleMemberPaperAnswer.getRightSum()+1);
				questionAnswer.setRightSum(questionAnswer.getRightSum()+1);
			}else{
				
				questionAnswerItem.setIsRight(0);
				result.put("right", false);
				
				questionAnswer.setWrongSum(questionAnswer.getWrongSum()+1);
				battleMemberPaperAnswer.setWrongSum(battleMemberPaperAnswer.getWrongSum()+1);
			}
			resultVo.setSuccess(true);
			resultVo.setData(result);
			
		}else if(question.getType()==Question.FILL_TYPE){
			String answer = httpServletRequest.getParameter("answer");
			
			questionAnswerItem.setMyAnswer(answer);
			questionAnswerItem.setRightAnswer(question.getAnswer());
			battleMemberQuestionAnswer.setAnswer(answer);
			battleMemberQuestionAnswer.setRightAnswer(question.getAnswer());
			if(answer.equals(question.getAnswer())){
				
				questionAnswerItem.setIsRight(1);
				result.put("right", true);
				
				questionAnswer.setRightSum(questionAnswer.getRightSum()+1);
				battleMemberPaperAnswer.setRightSum(battleMemberPaperAnswer.getRightSum()+1);
			}else{
				result.put("right", false);
				
				questionAnswerItem.setIsRight(0);
				
				questionAnswer.setWrongSum(questionAnswer.getWrongSum()+1);
				battleMemberPaperAnswer.setWrongSum(battleMemberPaperAnswer.getWrongSum()+1);
			}
			
			
			resultVo.setSuccess(true);
			resultVo.setData(result);
			
		}
		
		battleMemberQuestionAnswerService.add(battleMemberQuestionAnswer);
		
		result.put("battleMemberQuestionAnswerId",battleMemberQuestionAnswer.getId());
		
		result.put("battleMemberPaperAnswerId",battleMemberPaperAnswer.getId());
		
		questionAnswerItemService.add(questionAnswerItem);
		
		sessionManager.update(questionAnswer);
		sessionManager.update(battleMemberPaperAnswer);
	
		return resultVo;
	}
	
	@RequestMapping(value="createPaperAnswer")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	public Object createPaperAnswer(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		String type = httpServletRequest.getParameter("type");

		String questions = httpServletRequest.getParameter("questions");
		
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		String memberId = battlePeriodMember.getId();
		
		Integer stageIndex = battlePeriodMember.getStageIndex();
		
		
		Map<String, Object> data = new HashMap<>();
		if(battlePeriodMember.getStageIndex()==battlePeriodMember.getStageCount()){
			battlePeriodMember.setStageIndex(stageIndex);
			data.put("isLast", 1);
			battlePeriodMember.setStatus(BattlePeriodMember.STATUS_COMPLETE);
			sessionManager.update(battlePeriodMember);
		}else if(battlePeriodMember.getStageIndex()<battlePeriodMember.getStageCount()){
			
			battlePeriodMember.setStageIndex(battlePeriodMember.getStageIndex()+1);
			battlePeriodMember.setStageIndex(stageIndex);
			data.put("isLast", 0);
			sessionManager.update(battlePeriodMember);
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorCode(1);
			resultVo.setErrorMsg("已经超过了，不能再创建了");
			return resultVo;
		}
		
		
		QuestionAnswer questionAnswer = new QuestionAnswer();
		
		questionAnswer.setQuestions(questions);
		questionAnswer.setRightSum(0);
		questionAnswer.setTargetId(memberId+"_"+stageIndex);
		questionAnswer.setType(Integer.parseInt(type));
		questionAnswer.setWrongSum(0);
		questionAnswer.setQuestionCount(questions.length());
		questionAnswer.setQuestionIndex(0);
		
		questionAnswerService.add(questionAnswer);
		
		BattleMemberPaperAnswer battleMemberPaperAnswer = new BattleMemberPaperAnswer();
		battleMemberPaperAnswer.setAddDistance(0);
		battleMemberPaperAnswer.setBattlePeriodMemberId(memberId);
		battleMemberPaperAnswer.setRightSum(0);
		battleMemberPaperAnswer.setStageIndex(stageIndex);
		battleMemberPaperAnswer.setSubLove(0);
		battleMemberPaperAnswer.setWrongSum(0);
		battleMemberPaperAnswer.setStatus(BattleMemberPaperAnswer.FREE_STATUS);
		battleMemberPaperAnswer.setQuestionAnswerId(questionAnswer.getId());
		
		battleMemberPaperAnswerService.add(battleMemberPaperAnswer);
		
		
		stageIndex++;
		
		
		
		
		ResultVo resultVo = new ResultVo();
		
		
		
		data.put("stageIndex", stageIndex);
		
		data.put("battleMemberPaperAnswerId", battleMemberPaperAnswer.getId());
		
		resultVo.setData(data);
		
		resultVo.setSuccess(true);
		
		return resultVo;
		
	}
	
	
	@RequestMapping(value="questionResults")
	@ResponseBody
	@Transactional
	public Object questionResults(HttpServletRequest httpServletRequest){
		
		String battleMemberPaperAnswerId = httpServletRequest.getParameter("battleMemberPaperAnswerId");
		
		
		System.out.println("battleMemberPaperAnswerId:"+battleMemberPaperAnswerId);
		
		List<BattleMemberQuestionAnswer> battleMemberQuestionAnswers = battleMemberQuestionAnswerService.findAllByBattleMemberPaperAnswerId(battleMemberPaperAnswerId);
	
		ResultVo resultVo = new ResultVo();
		resultVo.setData(battleMemberQuestionAnswers);
		resultVo.setSuccess(true);
		
		return resultVo;
	}
	
	@RequestMapping(value="info")
	@ResponseBody
	@Transactional
	public Object info(HttpServletRequest httpServletRequest){
		String id = httpServletRequest.getParameter("id");
		
		System.out.println("......................id:"+id);
		
		Question question = questionService.findOne(id);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("id", question.getId());
		responseData.put("answer", question.getAnswer());
		responseData.put("fillWords", question.getFillWords());
		responseData.put("imgUrl", question.getImgUrl());
		responseData.put("index", question.getIndex());
		responseData.put("instruction", question.getInstruction());
		responseData.put("isImg", question.getIsImg());
		responseData.put("question", question.getQuestion());
		responseData.put("type", question.getType());
		
		if(question.getType()==0){
			List<QuestionOption> questionOptions = questionOptionService.findAllByQuestionId(id);
			responseData.put("options", questionOptions);
		}
		ResultVo resultVo = new ResultVo();
		
		resultVo.setData(responseData);
		
		resultVo.setSuccess(true);
		
		return resultVo;
	}
}
