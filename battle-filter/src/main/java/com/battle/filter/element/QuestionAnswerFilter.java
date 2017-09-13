package com.battle.filter.element;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.Question;
import com.battle.domain.QuestionAnswer;
import com.battle.domain.QuestionAnswerItem;
import com.battle.domain.QuestionOption;
import com.battle.service.QuestionAnswerItemService;
import com.battle.service.QuestionOptionService;
import com.battle.service.QuestionService;
import com.wyc.AttrEnum;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class QuestionAnswerFilter extends Filter{

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuestionAnswerItemService questionAnswerItemService;
	
	@Autowired
	private QuestionOptionService questionOptionService;
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		QuestionAnswer questionAnswer = sessionManager.getObject(QuestionAnswer.class);
		Question question = sessionManager.getObject(Question.class);
		QuestionAnswerItem questionAnswerItem = new QuestionAnswerItem();
		questionAnswerItem.setType(question.getType());
		questionAnswerItem.setQuestionId(question.getId());
		if(question.getType()==Question.INPUT_TYPE){
			String answer = (String)sessionManager.getAttribute(AttrEnum.questionAnswer);
			questionAnswerItem.setMyAnswer(answer);
			questionAnswerItem.setRightAnswer(answer);
			if(question.getAnswer().equals(answer)){
				questionAnswerItem.setIsRight(1);
				questionAnswer.setRightSum(questionAnswer.getRightSum()+1);
				
			}else{
				questionAnswerItem.setIsRight(0);
				questionAnswer.setWrongSum(questionAnswer.getWrongSum()+1);
			}
		}else if(question.getType()==Question.SELECT_TYPE){
			String optionId = (String)sessionManager.getAttribute(AttrEnum.questionOptionId);
			
			QuestionOption questionOption = questionOptionService.findOne(optionId);
			
			QuestionOption rightQuestionOption = questionOptionService.findOne(question.getRightOptionId());
			
			questionAnswerItem.setMyAnswer(questionOption.getContent());
			questionAnswerItem.setRightAnswer(rightQuestionOption.getContent());
			
			questionAnswerItem.setMyOptionId(optionId);
			questionAnswerItem.setRightOptionId(question.getRightOptionId());
			if(question.getRightOptionId().equals(optionId)){
				questionAnswerItem.setIsRight(1);
				questionAnswer.setRightSum(questionAnswer.getRightSum()+1);
			}else{
				questionAnswerItem.setIsRight(0);
				questionAnswer.setWrongSum(questionAnswer.getWrongSum()+1);
			}
		}else if(question.getType()==Question.FILL_TYPE){
			String answer = (String)sessionManager.getAttribute(AttrEnum.questionAnswer);
			questionAnswerItem.setMyAnswer(answer);
			questionAnswerItem.setRightAnswer(answer);
			if(question.getAnswer().equals(answer)){
				questionAnswerItem.setIsRight(1);
				questionAnswer.setRightSum(questionAnswer.getRightSum()+1);
				
			}else{
				questionAnswerItem.setIsRight(0);
				questionAnswer.setWrongSum(questionAnswer.getWrongSum()+1);
			}
		}
		
		questionAnswerItemService.add(questionAnswerItem);
		
		sessionManager.update(questionAnswer);
		sessionManager.save(questionAnswerItem);
		return null;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		String questionId = (String)sessionManager.getAttribute(AttrEnum.questionId);
		Question question = questionService.findOne(questionId);
		sessionManager.save(question);
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
