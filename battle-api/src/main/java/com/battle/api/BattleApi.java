package com.battle.api;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.battle.domain.Battle;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattlePeriodStage;
import com.battle.domain.BattleQuestion;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleSubject;
import com.battle.filter.api.BattleMemberInfoApiFilter;
import com.battle.filter.api.BattleMembersApiFilter;
import com.battle.filter.api.BattleSubjectApiFilter;
import com.battle.filter.api.BattleTakepartApiFilter;
import com.battle.filter.element.CurrentMemberInfoFilter;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattlePeriodStageService;
import com.battle.service.BattleQuestionService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleSubjectService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;

@Controller
@RequestMapping(value="/api/battle/")
public class BattleApi {
	
	@Autowired
	private BattleSubjectService battleSubjectService;
	
	@Autowired
	private BattlePeriodStageService battlePeriodStageService;
	
	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Autowired
	private BattleQuestionService battleQuestionService;
	
	@Autowired
	private BattleRoomService battleRoomService;

	@RequestMapping(value="info")
	@ResponseBody
	public Object info(HttpServletRequest httpServletRequest)throws Exception{
		String id = httpServletRequest.getParameter("id");
		String roomId = httpServletRequest.getParameter("roomId");
		BattleRoom battleRoom = battleRoomService.findOne(roomId);
		
		Map<String, Object> data = new HashMap<>();
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		Battle battle = sessionManager.findOne(Battle.class, id);
		
		data.put("id", battle.getId());
		data.put("currentPeriodIndex", battle.getCurrentPeriodIndex());
		data.put("distance", battle.getDistance());
		data.put("headImg", battle.getHeadImg());
		data.put("instruction", battle.getInstruction());
		data.put("isActivation", battle.getIsActivation());
		data.put("name", battle.getName());
		data.put("maxinum", battleRoom.getMaxinum());
		data.put("mininum", battleRoom.getMininum());
		data.put("owner", battleRoom.getOwner());
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(data);
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
	
	
	
	//参加关卡
	@RequestMapping(value="stageTakepart")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	public Object stageTakepart(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		String subjectIdStr = httpServletRequest.getParameter("subjectIds");
		
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		Integer stageIndex = battlePeriodMember.getStageIndex();
		
		Integer stageCount = battlePeriodMember.getStageCount();
		
		Integer isLast = 0;
		if(stageIndex==stageCount){
			isLast = 1;
		}else if(stageIndex>stageCount){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("已经超出了");
			return resultVo;
		}
		
		if(battlePeriodMember.getStatus()==BattlePeriodMember.STATUS_IN){
			
			String[] subjectIds = subjectIdStr.split(",");
			
			BattlePeriodStage battlePeriodStage = battlePeriodStageService.findOneByBattleIdAndPeriodIdAndIndex(battlePeriodMember.getBattleId(), battlePeriodMember.getPeriodId(), stageIndex);
			
			Integer questionCount = battlePeriodStage.getQuestionCount();
			
			
			
			List<BattleQuestion> battleQuestions = battleQuestionService.findAllByBattleIdAndPeriodStageIdAndBattleSubjectIdIn(battlePeriodStage.getBattleId(), battlePeriodStage.getId(),subjectIds);
			
			
			
			List<String> battleQuestionIdArray = new ArrayList<>();
			for(BattleQuestion battleQuestion:battleQuestions){
				battleQuestionIdArray.add(battleQuestion.getQuestionId());
			}
			
			Collections.shuffle(battleQuestionIdArray);
			
			Integer length = battleQuestionIdArray.size();
			
			if(questionCount==null){
				questionCount = 0;
			}
			
			if(length>questionCount){
				length = questionCount;
			}
			
			battleQuestionIdArray = battleQuestionIdArray.subList(0, length);
			
			if(stageIndex<=stageCount){
				
				battlePeriodMemberService.update(battlePeriodMember);
				
			}else{
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("不是正在进行中状态");
				return resultVo;
				
			}
			
			Map<String, Object> data = new HashMap<>();
			
			data.put("isLast", isLast);
			
			data.put("questionIds", battleQuestionIdArray);
			
			
			
			ResultVo resultVo = new ResultVo();
			
			resultVo.setSuccess(true);
			
			resultVo.setData(data);
			
			return resultVo;
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("不是正在进行中状态");
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
				
			}else if(thisQuestionIds!=null&&thisQuestionIds.length>1){

				Random random = new Random();
				Integer index = random.nextInt(thisQuestionIds.length-1);				
				battleQuestionIds.add(thisQuestionIds[index]);
				
			}
		}
		
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		resultVo.setData(battleQuestionIds);
		
		return resultVo;
		
	}
	
}
