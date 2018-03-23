package com.battle.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattleMemberPaperAnswer;
import com.battle.domain.BattleNotice;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.filter.element.CurrentMemberInfoFilter;
import com.battle.service.BattleMemberPaperAnswerService;
import com.battle.service.BattleNoticeService;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattleRoomService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;

@Controller
@RequestMapping(value="/api/battle/sync")
public class BattleSyncDataApi {

	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private BattleMemberPaperAnswerService battleMemberPaperAnswerService;
	
	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Autowired
	private BattleNoticeService battleNoticeService;
	
	final static Logger logger = LoggerFactory.getLogger(BattleSyncDataApi.class);
	
	@RequestMapping(value="syncPaperData")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	public ResultVo syncPaperData(HttpServletRequest httpServletRequest)throws Exception{
		
		
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		
		
		
		if(battlePeriodMember.getScore()>=battlePeriodMember.getScrollGogal()){
			battlePeriodMember.setStatus(BattlePeriodMember.STATUS_COMPLETE);
			battlePeriodMemberService.update(battlePeriodMember);
		}
		
		BattleRoom battleRoom = null;
		
		try{
			battleRoom = battleRoomService.findOne(battlePeriodMember.getRoomId());
		}catch(Exception e){
			logger.error("获取battleRoom错误第一次");
			try{
				battleRoom = battleRoomService.findOne(battlePeriodMember.getRoomId());
			}catch(Exception e2){
				logger.error("获取battleRoom错误第二次,返回结果");
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("获取battleRoom错误");
				return resultVo;
			}
			
		}
		
		if(battlePeriodMember.getLoveResidule()==0){
			battlePeriodMember.setStatus(BattlePeriodMember.STATUS_END);
			battlePeriodMemberService.update(battlePeriodMember);
		}
		
		if(battlePeriodMember.getStatus()==BattlePeriodMember.STATUS_COMPLETE){
			battleRoom.setStatus(BattleRoom.STATUS_END);
			battleRoomService.update(battleRoom);
		}
		
		
		
	
		
		Sort sort = new Sort(Direction.DESC,"score");
		
		Pageable pageable = new PageRequest(0, 100, sort);
		
		List<Integer> statuses = new ArrayList<>();
		statuses.add(BattlePeriodMember.STATUS_COMPLETE);
		statuses.add(BattlePeriodMember.STATUS_IN);
		statuses.add(BattlePeriodMember.STATUS_END);
		
		List<BattlePeriodMember> allBattlePeriodMembers = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDel(battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId(), statuses, 0, pageable);
		List<BattlePeriodMember> battlePeriodMembers = new ArrayList<>();
		
		for(BattlePeriodMember allBattlePeriodMember:allBattlePeriodMembers){
			if(allBattlePeriodMember.getStatus()==BattlePeriodMember.STATUS_IN){
				battlePeriodMembers.add(allBattlePeriodMember);
			}
		}
		if(battlePeriodMembers==null||battlePeriodMembers.size()==0){
			battleRoom.setStatus(BattleRoom.STATUS_END);
			battleRoomService.update(battleRoom);
		}
		
		List<BattleMemberPaperAnswer> battleMemberPaperAnswers = battleMemberPaperAnswerService.
				findAllByBattlePeriodMemberIdAndIsSyncData(battlePeriodMember.getId(),0);
		
		for(BattleMemberPaperAnswer battleMemberPaperAnswer:battleMemberPaperAnswers){
			
			battleMemberPaperAnswer.setIsSyncData(1);
			
			Integer startIndex = battleMemberPaperAnswer.getStartIndex();
			Integer endIndex = battleMemberPaperAnswer.getEndIndex();
			
			Integer process = battleMemberPaperAnswer.getProcess();
			
			if(startIndex==null){
				startIndex = 0;
			}
			
			
			if(process==null){
				process = 0;
			}
			
			
			battleMemberPaperAnswerService.update(battleMemberPaperAnswer);
			
			for(BattlePeriodMember allBattlePeriodMember:allBattlePeriodMembers){
				if(!allBattlePeriodMember.getId().equals(battlePeriodMember.getId())){
					BattleNotice battleNotice = new BattleNotice();
					battleNotice.setIsRead(0);
					battleNotice.setMemberId(battlePeriodMember.getId());
					battleNotice.setProcess(endIndex);
					battleNotice.setRoomId(battlePeriodMember.getRoomId());
					battleNotice.setToUser(allBattlePeriodMember.getUserId());
					battleNotice.setType(BattleNotice.MEMBER_TYPE);
					battleNoticeService.add(battleNotice);
				}
				
			}
			
		}
		
		
		
		//if(battleRoom.getStatus()==BattleRoom.STATUS_END){
			Map<String, Object> data = new HashMap<>();
			
			
			data.put("status", battleRoom.getStatus());
			data.put("endType", battleRoom.getEndType());
			
			data.put("roomProcess", battleRoom.getRoomProcess());
			data.put("roomScore", battleRoom.getRoomScore());
			
			data.put("process", battlePeriodMember.getProcess());
			
			data.put("score", battlePeriodMember.getScore());
			
			data.put("scoreGogal", battlePeriodMember.getScrollGogal());
			
			data.put("rewardBean", battlePeriodMember.getRewardBean());
			
			
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			
			resultVo.setData(data);
			
			resultVo.setErrorMsg("同步成功");
			
			return resultVo;
			
		//}
		//return null;
	}
}
