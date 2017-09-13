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

import com.battle.domain.Question;
import com.battle.domain.QuestionOption;
import com.battle.filter.api.BattleQuestionAnswerApiFilter;
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
	
	
	@RequestMapping(value="battleQuestionAnswer")
	@HandlerAnnotation(hanlerFilter=BattleQuestionAnswerApiFilter.class)
	@ResponseBody
	@Transactional
	public Object battleQuestionAnswer(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		if(sessionManager.isReturn()){
			ResultVo resultVo = (ResultVo)sessionManager.getReturnValue();
			return resultVo;
		}else{
			ResultVo resultVo = (ResultVo)sessionManager.getObject(ResultVo.class);
			return resultVo;
		}
	}
	
	@RequestMapping(value="info")
	@ResponseBody
	@Transactional
	public Object info(HttpServletRequest httpServletRequest){
		String id = httpServletRequest.getParameter("id");
		
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
