package com.battle.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattleMemberPaperAnswer;
import com.battle.domain.BattleMemberRank;
import com.battle.domain.BattleNotice;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleRoomReward;
import com.battle.filter.element.CurrentMemberInfoFilter;
import com.battle.service.BattleMemberPaperAnswerService;
import com.battle.service.BattleMemberRankService;
import com.battle.service.BattleNoticeService;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattleRoomRewardService;
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
	
	@Autowired
	private BattleMemberRankService battleMemberRankService;
	
	@Autowired
	private BattleRoomRewardService battleRoomRewardService;
	
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
		
		if(battlePeriodMember.getLoveResidule()<=0){
			battlePeriodMember.setStatus(BattlePeriodMember.STATUS_END);
			battlePeriodMemberService.update(battlePeriodMember);
		}
		
		
		Sort sort = new Sort(Direction.DESC,"score");
		
		Pageable pageable = new PageRequest(0, 100, sort);
		
		List<Integer> statuses = new ArrayList<>();
		statuses.add(BattlePeriodMember.STATUS_COMPLETE);
		statuses.add(BattlePeriodMember.STATUS_IN);
		statuses.add(BattlePeriodMember.STATUS_END);
		
		
		
		List<BattlePeriodMember> allBattlePeriodMembers = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDel(battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId(), statuses, 0, pageable);
		List<BattlePeriodMember> battlePeriodMembers = new ArrayList<>();
		
		List<BattlePeriodMember> validMembers = new ArrayList<>();
		
		Collections.sort(allBattlePeriodMembers, new Comparator<BattlePeriodMember>() {

			@Override
			public int compare(BattlePeriodMember member1, BattlePeriodMember member2) {
				return member1.getScore().compareTo(member2.getScore());
			}
		});
		
		for(BattlePeriodMember allBattlePeriodMember:allBattlePeriodMembers){
			int status = allBattlePeriodMember.getStatus();
			if(status==BattlePeriodMember.STATUS_IN){
				battlePeriodMembers.add(allBattlePeriodMember);
			}
			
			if(status==BattlePeriodMember.STATUS_COMPLETE||status==BattlePeriodMember.STATUS_END||status==BattlePeriodMember.STATUS_IN){
				validMembers.add(allBattlePeriodMember);
			}
		}
		
		if(battlePeriodMember.getStatus()==BattlePeriodMember.STATUS_COMPLETE){
			battleRoom.setStatus(BattleRoom.STATUS_END);
			battleRoomService.update(battleRoom);
		}
		
		if(battlePeriodMembers==null||battlePeriodMembers.size()==0){
			battleRoom.setStatus(BattleRoom.STATUS_END);
			battleRoomService.update(battleRoom);
		}
		
		
		
		Map<String, Object> data = new HashMap<>();
		
		//游戏结束了,结束之后的一些数据处理
		if(battleRoom.getStatus()==BattleRoom.STATUS_END){
			List<BattleMemberRank> battleMemberRanks = new ArrayList<>();
			//是否处理过数据
			if(battleRoom.getIsEndHandle()==0){
				List<BattleRoomReward> battleRoomRewards = battleRoomRewardService.findAllByRoomIdOrderByRankAsc(battleRoom.getId());
				Map<String, BattleRoomReward> battleRoomRewardMap = new HashMap<>();
				for(BattleRoomReward battleRoomReward:battleRoomRewards){
					battleRoomRewardMap.put(battleRoomReward.getRank()+"", battleRoomReward);
				}
				for(int i = 0;i<validMembers.size();i++){
					BattlePeriodMember vaildMember = validMembers.get(i);
					BattleMemberRank battleMemberRank = new BattleMemberRank();
					battleMemberRank.setRank(i+1);
					battleMemberRank.setImgUrl(vaildMember.getHeadImg());
					battleMemberRank.setMemberId(vaildMember.getId());
					battleMemberRank.setNickname(vaildMember.getNickname());
					battleMemberRank.setProcess(vaildMember.getProcess());
					battleMemberRank.setScore(vaildMember.getScore());
					
					
					BattleRoomReward battleRoomReward = battleRoomRewardMap.get(battleMemberRank.getRank()+"");
					
					if(battleRoomReward!=null){
						battleMemberRank.setRewardBean(battleRoomReward.getRewardBean());
					}else{
						battleMemberRank.setRewardBean(0);
					}
					
					battleMemberRankService.add(battleMemberRank);
					battleMemberRanks.add(battleMemberRank);
					
					
					if(vaildMember.getId().equals(battlePeriodMember.getId())){
						BattleNotice battleNotice = new BattleNotice();
						battleNotice.setIsRead(0);
						battleNotice.setMemberId(battlePeriodMember.getId());
	
						battleNotice.setRoomId(battlePeriodMember.getRoomId());
						battleNotice.setToUser(vaildMember.getUserId());
						battleNotice.setType(BattleNotice.ROOM_TYPE);
						
						battleNotice.setRoomStatus(battleRoom.getStatus());
						
						
						battleNotice.setScore(battlePeriodMember.getScore());
						battleNoticeService.add(battleNotice);
					}
					
				}
				
				battleRoom.setIsEndHandle(1);
				
				battleRoomService.update(battleRoom);
				
			//已经经过了结束处理
			}else{
				Sort sort2 = new Sort(Direction.ASC,"rank");
				Pageable pageable2 = new PageRequest(0,10,sort2);
				Page<BattleMemberRank> battleMemberRankPage = battleMemberRankService.findAllByRoomId(battleRoom.getId(),pageable2);
				battleMemberRanks = battleMemberRankPage.getContent();
			}
			
			data.put("ranks", battleMemberRanks);
			
			BattleMemberRank battleMemberRank = battleMemberRankService.findOneByMemberId(battlePeriodMember.getId());
			data.put("rank", battleMemberRank.getRank());
			
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
			
			for(BattlePeriodMember validMember:validMembers){
				if(!validMember.getId().equals(battlePeriodMember.getId())){
					BattleNotice battleNotice = new BattleNotice();
					battleNotice.setIsRead(0);
					battleNotice.setMemberId(battlePeriodMember.getId());
					battleNotice.setProcess(endIndex);
					battleNotice.setRoomId(battlePeriodMember.getRoomId());
					battleNotice.setToUser(validMember.getUserId());
					battleNotice.setType(BattleNotice.MEMBER_TYPE);
					
					battleNotice.setLoveResidule(battlePeriodMember.getLoveResidule());
					
					battleNotice.setScore(battlePeriodMember.getScore());
					battleNoticeService.add(battleNotice);
				}
				
			}
			
		}
		
		
		
		//if(battleRoom.getStatus()==BattleRoom.STATUS_END){
			
			
			data.put("roomStatus", battleRoom.getStatus());
			data.put("status", battlePeriodMember.getStatus());
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
