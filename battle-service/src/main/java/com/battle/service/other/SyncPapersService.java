package com.battle.service.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.battle.domain.BattleMemberPaperAnswer;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleRoomRecord;
import com.battle.service.BattleMemberPaperAnswerService;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattleRoomRecordService;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.util.CommonUtil;

@Service
public class SyncPapersService {
	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Autowired
	private BattleRoomRecordService battleRoomRecordService;
	
	@Autowired
	private BattleMemberPaperAnswerService battleMemberPaperAnswerService;
	public ResultVo roomEndResult(String groupId,BattleRoom battleRoom,BattlePeriodMember battlePeriodMember){
		Map<String, Object> data = new HashMap<>();
		
		Sort sort = new Sort(Direction.DESC,"score");
		Pageable pageable = new PageRequest(0, 100,sort);
		List<Integer> statuses = new ArrayList<>();
		statuses.add(BattlePeriodMember.STATUS_COMPLETE);
		statuses.add(BattlePeriodMember.STATUS_IN);
		List<BattlePeriodMember> battlePeriodMembers =  new ArrayList<>();
		
		if(CommonUtil.isEmpty(groupId)){
				
			battlePeriodMembers = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDel(battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId(), statuses, 0,pageable);
		}else{
			battlePeriodMembers = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndGroupIdAndIsDel(battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId(), statuses, groupId, 0, pageable);
		}
		data.put("status", battleRoom.getStatus());
		data.put("endType", battleRoom.getEndType());
		
		data.put("roomProcess", battleRoom.getRoomProcess());
		data.put("roomScore", battleRoom.getRoomScore());
		
		data.put("process", battlePeriodMember.getProcess());
		
		data.put("score", battlePeriodMember.getScore());
		
		data.put("scoreGogal", battlePeriodMember.getScrollGogal());
		
		data.put("rewardBean", battlePeriodMember.getRewardBean());
		
		data.put("members", battlePeriodMembers);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		resultVo.setData(data);
		
		resultVo.setErrorMsg("同步成功");
		
		return resultVo;
	}
	
	public void syncPaperAnswer(BattlePeriodMember battlePeriodMember,BattleRoom battleRoom){
		
		List<BattleMemberPaperAnswer> battleMemberPaperAnswers = battleMemberPaperAnswerService.
				findAllByBattlePeriodMemberIdAndIsSyncData(battlePeriodMember.getId(),0);
		
		Integer memberScore = battlePeriodMember.getScore();
		
		Integer memberScoreGogal = battlePeriodMember.getScrollGogal();
		
		if(memberScore==null){
			memberScore = 0;
		}
		
		if(memberScoreGogal==null){
			memberScoreGogal = 0;
		}
		
		for(BattleMemberPaperAnswer battleMemberPaperAnswer:battleMemberPaperAnswers){
			
			Integer isPass = battleMemberPaperAnswer.getIsPass();
			BattleRoomRecord battleRoomRecord = new BattleRoomRecord();
			battleRoomRecord.setHappenTime(new DateTime());
			battleRoomRecord.setImgUrl(battlePeriodMember.getHeadImg());
			battleRoomRecord.setNickname(battlePeriodMember.getNickname());
			battleRoomRecord.setMemberId(battlePeriodMember.getId());
			battleRoomRecord.setNickname(battlePeriodMember.getNickname());
			battleRoomRecord.setRoomId(battlePeriodMember.getRoomId());
			battleMemberPaperAnswer.setIsSyncData(1);
			Integer process = battleMemberPaperAnswer.getProcess();
			
			Integer score = battleMemberPaperAnswer.getScore();
			
			if(score==null){
				score = 0;
			}
			
			battleMemberPaperAnswerService.update(battleMemberPaperAnswer);
			if(process==null){
				process = 0;
			}
			
			Integer roomProcess = battleRoom.getRoomProcess();
			
			Integer roomScore = battleRoom.getRoomScore();
			
			
			
			if(roomProcess==null){
				roomProcess = 0;
			}
			
			if(roomScore==null){
				roomScore = 0;
			}
			
			
			
			roomScore = roomScore+score;
			
			memberScore = memberScore+score;
			
			roomProcess = roomProcess + process;
			
			battleRoom.setRoomProcess(roomProcess);
			
			battleRoom.setRoomScore(roomScore);
			
			if(isPass==1){
				
				roomScore = roomScore+battleRoom.getFullRightAddScore();
				
				memberScore = memberScore+battleRoom.getFullRightAddScore();
				
				battleRoom.setRoomScore(roomScore);
				

				StringBuffer sb = new StringBuffer();
				sb.append("["+battlePeriodMember.getNickname()+"]"+"挑战第"+battleMemberPaperAnswer.getStageIndex()+"关成功");
				sb.append(",答对"+battleMemberPaperAnswer.getRightSum()+"题");
				sb.append(",答错"+battleMemberPaperAnswer.getWrongSum()+"题");
				sb.append(",贡献房间分数"+score+"+"+battleMemberPaperAnswer.getFullRightAddScore()+"(通关加分)分");
				sb.append(",贡献距离:"+(process*10)+"米");
				battleRoomRecord.setLog(sb.toString());
				
				battleRoomRecordService.add(battleRoomRecord);
				
			}else{
				
				StringBuffer sb = new StringBuffer();
				sb.append("["+battlePeriodMember.getNickname()+"]"+"挑战第"+battleMemberPaperAnswer.getStageIndex()+"关失败");
				sb.append(",答对"+battleMemberPaperAnswer.getRightSum()+"题");
				sb.append(",答错"+battleMemberPaperAnswer.getWrongSum()+"题");
				if(score>0){
					sb.append(",贡献房间分数:"+battleMemberPaperAnswer.getScore()+"分");
				}else if(score<0){
					sb.append(",扣除房间分数:"+(-battleMemberPaperAnswer.getScore())+"分");
				}
				sb.append(",贡献距离："+(process*10)+"米");
				battleRoomRecord.setLog(sb.toString());
				
				battleRoomRecordService.add(battleRoomRecord);
			}
			
		}
		
		Integer scrollGogal = battleRoom.getScrollGogal();
		
		if(scrollGogal==null){
			scrollGogal = 0;
		}
		
		battlePeriodMember.setScore(memberScore);
		
		battlePeriodMemberService.update(battlePeriodMember);
		
		Integer roomScore = battleRoom.getRoomScore();
		
		
	}
}
