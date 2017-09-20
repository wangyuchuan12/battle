package com.battle.api;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.battle.domain.Battle;
import com.battle.domain.BattleSubject;
import com.battle.filter.api.BattleMemberInfoApiFilter;
import com.battle.filter.api.BattleMembersApiFilter;
import com.battle.filter.api.BattleSubjectApiFilter;
import com.battle.filter.api.BattleTakepartApiFilter;
import com.battle.service.BattleSubjectService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;

@Controller
@RequestMapping(value="/api/battle/")
public class BattleApi {
	
	@Autowired
	private BattleSubjectService battleSubjectService;
	

	@RequestMapping(value="info")
	@ResponseBody
	public Object info(HttpServletRequest httpServletRequest)throws Exception{
		String id = httpServletRequest.getParameter("id");
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		Battle battle = sessionManager.findOne(Battle.class, id);
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battle);
		return resultVo;
	}
	
	@RequestMapping(value="memberInfo")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=BattleMemberInfoApiFilter.class)
	public Object memberInfo(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		if(sessionManager.isReturn()){
			ResultVo resultVo = (ResultVo)sessionManager.getReturnValue();
			return resultVo;
		}else{
			ResultVo resultVo = (ResultVo)sessionManager.getObject(ResultVo.class);
			return resultVo;
		}
	}
	
	@RequestMapping(value="members")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=BattleMembersApiFilter.class)
	public Object members(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		if(sessionManager.isReturn()){
			ResultVo resultVo = (ResultVo)sessionManager.getReturnValue();
			return resultVo;
		}else{
			ResultVo resultVo = (ResultVo)sessionManager.getObject(ResultVo.class);
			return resultVo;
		}
	}
	
	
	@RequestMapping(value="battleSubjects")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=BattleSubjectApiFilter.class)
	public Object battleSubjects(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		if(sessionManager.isReturn()){
			ResultVo resultVo = (ResultVo)sessionManager.getReturnValue();
			return resultVo;
		}else{
			ResultVo resultVo = (ResultVo)sessionManager.getObject(ResultVo.class);
			return resultVo;
		}
	}
	
	
	@RequestMapping(value="takepart")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=BattleTakepartApiFilter.class)
	public Object takepart(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		if(sessionManager.isReturn()){
			ResultVo resultVo = (ResultVo)sessionManager.getReturnValue();
			return resultVo;
		}else{
			ResultVo resultVo = (ResultVo)sessionManager.getObject(ResultVo.class);
			return resultVo;
		}
		
	}
	
	@RequestMapping(value="battleQuestions")
	@ResponseBody
	@Transactional
	public Object battleQuestions(HttpServletRequest httpServletRequest)throws Exception{
		String subjectIdStr = httpServletRequest.getParameter("subjectIds");
		String[] subjectIds = subjectIdStr.split(",");
		List<BattleSubject> battleSubjects = battleSubjectService.findAllByIdIn(subjectIds);
		
		List<String> battleQuestionIds = new ArrayList<>();
		
		for(BattleSubject battleSubject:battleSubjects){
			String thisQuestionIdStr = battleSubject.getBattleQuestionIds();
			
			String[] thisQuestionIds = thisQuestionIdStr.split(",");
			if(thisQuestionIds!=null&&thisQuestionIds.length==1){
				
				battleQuestionIds.add(thisQuestionIds[0]);
				
				System.out.println("............1");
				
			}else if(thisQuestionIds!=null&&thisQuestionIds.length>1){
				
				
				Random random = new Random();
				Integer index = random.nextInt(thisQuestionIds.length-1);
				
				System.out.println("............index:"+index+",length"+thisQuestionIds.length);
				battleQuestionIds.add(thisQuestionIds[index]);
				
			}
		}
		
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		resultVo.setData(battleQuestionIds);
		
		return resultVo;
		
	}
	
}
