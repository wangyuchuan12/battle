package com.battle.socket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattleRoomService;
import com.battle.socket.MessageHandler;
import com.battle.socket.MessageVo;
import com.battle.socket.vo.ProgressStatusVo;

@Service
public class ProgressStatusSocketService {

	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	@Autowired
	private MessageHandler messageHandler;
	
	@Autowired
	private BattleRoomService battleRoomService;
	
	
	public Object statusPublish(String roomId,BattlePeriodMember battlePeriodMember)throws Exception{
		List<ProgressStatusVo> progressStatusVos = new ArrayList<>();
		
		ProgressStatusVo progressStatusVo = new ProgressStatusVo();
		progressStatusVo.setLoveCount(battlePeriodMember.getLoveResidule());
		progressStatusVo.setMemberId(battlePeriodMember.getId());
		progressStatusVo.setProcess(battlePeriodMember.getProcess());
		progressStatusVo.setScore(battlePeriodMember.getScore());
		progressStatusVo.setStatus(battlePeriodMember.getStatus());
		
		progressStatusVos.add(progressStatusVo);
		
		MessageVo messageVo = new MessageVo();
		
		messageVo.setCode(MessageVo.PROGRESS_CODE);
		messageVo.setType(MessageVo.ROOM_TYPE);
		messageVo.setRoomId(roomId);
		messageVo.setData(progressStatusVos);
		
		messageHandler.sendMessage(messageVo);
		
		return messageVo;
	}
	
	public Object statusPublish(String roomId)throws Exception{
		
		BattleRoom battleRoom = battleRoomService.findOne(roomId);
		List<BattlePeriodMember> battlePeriodMembers = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomId(battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId());
		List<ProgressStatusVo> progressStatusVos = new ArrayList<>();
		
		for(BattlePeriodMember battlePeriodMember:battlePeriodMembers){
			ProgressStatusVo progressStatusVo = new ProgressStatusVo();
			progressStatusVo.setLoveCount(battlePeriodMember.getLoveResidule());
			progressStatusVo.setMemberId(battlePeriodMember.getId());
			progressStatusVo.setProcess(battlePeriodMember.getProcess());
			progressStatusVo.setScore(battlePeriodMember.getScore());
			progressStatusVo.setStatus(battlePeriodMember.getStatus());
			progressStatusVos.add(progressStatusVo);
		}
		
		MessageVo messageVo = new MessageVo();
		
		messageVo.setCode(MessageVo.PROGRESS_CODE);
		messageVo.setType(MessageVo.ROOM_TYPE);
		messageVo.setRoomId(roomId);
		messageVo.setData(progressStatusVos);
		
		messageHandler.sendMessage(messageVo);
		
		return messageVo;
	}
}
