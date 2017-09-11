package com.battle.filter.api;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.battle.domain.QuestionAnswerItem;
import com.battle.filter.element.CurrentQuestionAnswerFilter;
import com.battle.filter.element.QuestionAnswerFilter;
import com.wyc.AttrEnum;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class BattleQuestionAnswerApiFilter extends Filter{

	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		QuestionAnswerItem questionAnswerItem  = sessionManager.getObject(QuestionAnswerItem.class);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(questionAnswerItem);
		return resultVo;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		String questionId  = httpServletRequest.getParameter("questionId");
		String type = httpServletRequest.getParameter("type");
		String targetId = httpServletRequest.getParameter("targetId");
		String answer = httpServletRequest.getParameter("answer");
		String optionId = httpServletRequest.getParameter("optionId");
	
		sessionManager.setAttribute(AttrEnum.questionAnswerTargetId,targetId);
		sessionManager.setAttribute(AttrEnum.questionAnswerType,Integer.parseInt(type));
		sessionManager.setAttribute(AttrEnum.questionId,questionId);
		
		sessionManager.setAttribute(AttrEnum.questionAnswer,answer);
		sessionManager.setAttribute(AttrEnum.questionOptionId,optionId);
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		
		List<Class<? extends Filter>> classes = new ArrayList<>();
		classes.add(CurrentQuestionAnswerFilter.class);
		classes.add(QuestionAnswerFilter.class);
		return classes;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
