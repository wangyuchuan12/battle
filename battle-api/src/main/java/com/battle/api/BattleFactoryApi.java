package com.battle.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.battle.domain.BattleQuestionFactoryItem;
import com.battle.domain.Context;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleQuestionFactoryItemService;
import com.battle.service.ContextService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/battleFactory/")
public class BattleFactoryApi {
	
	@Autowired
	private ContextService contextService;
	
	@Autowired
	private BattleQuestionFactoryItemService battleQuestionFactoryItemService;

	@RequestMapping(value="apply")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object apply(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		String question = httpServletRequest.getParameter("question");
		String imgUrl = httpServletRequest.getParameter("imgUrl");
		String answer = httpServletRequest.getParameter("answer");
		String fillWords = httpServletRequest.getParameter("fillWords");
		
		String battleId = httpServletRequest.getParameter("battleId");
		String type = httpServletRequest.getParameter("type");
		
		String options = httpServletRequest.getParameter("options");
		
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
		
		BattleQuestionFactoryItem questionTarget = new BattleQuestionFactoryItem();
		questionTarget.setQuestion(question);
		questionTarget.setImgUrl(imgUrl);
		questionTarget.setIsImg(1);
		questionTarget.setAnswer(answer);
		questionTarget.setFillWords(fillWords);
		questionTarget.setIsDel(0);
		questionTarget.setStatus(BattleQuestionFactoryItem.STATUS_AUDIT);
		questionTarget.setOptions(options);
		questionTarget.setBattleId(battleId);
		
		questionTarget.setType(Integer.parseInt(type));
		
		questionTarget.setUserId(userInfo.getId());
		
		battleQuestionFactoryItemService.add(questionTarget);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		return resultVo;
	}
	
	
	@RequestMapping(value="myQuestions")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo myQuestions(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		Sort sort = new Sort(Direction.DESC,"createAt");
		Pageable pageable = new PageRequest(0, 100, sort);
		
		List<BattleQuestionFactoryItem> battleQuestionFactoryItems = battleQuestionFactoryItemService.findAllByUserIdAndIsDel(userInfo.getId(),0,pageable);
	
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(battleQuestionFactoryItems);
		
		return resultVo;
	}
}
