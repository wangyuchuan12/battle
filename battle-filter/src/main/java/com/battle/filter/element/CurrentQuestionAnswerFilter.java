package com.battle.filter.element;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.QuestionAnswer;
import com.battle.service.QuestionAnswerService;
import com.wyc.AttrEnum;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class CurrentQuestionAnswerFilter extends Filter{

	@Autowired
	private QuestionAnswerService questionAnswerService;
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		String targetId = (String)sessionManager.getAttribute(AttrEnum.questionAnswerTargetId);
		Integer type = (Integer)sessionManager.getAttribute(AttrEnum.questionAnswerType);
		
		List<QuestionAnswer> questionAnswers = questionAnswerService.findAllByTargetIdAndType(targetId,type);
	
		QuestionAnswer questionAnswer = null;
		
		if(questionAnswers!=null&&questionAnswers.size()>0){
			questionAnswer = questionAnswers.get(0);
		}
		
		if(questionAnswer==null){
			questionAnswer = new QuestionAnswer();
			questionAnswer.setTargetId(targetId);
			questionAnswer.setType(type);
			questionAnswer.setRightSum(0);
			questionAnswer.setWrongSum(0);
			questionAnswerService.add(questionAnswer);
		}
		return questionAnswer;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		// TODO Auto-generated method stub
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
