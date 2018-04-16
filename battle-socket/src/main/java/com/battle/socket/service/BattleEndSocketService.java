package com.battle.socket.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattleRoomService;
import com.battle.socket.BattleEndRankVo;
import com.battle.socket.MessageHandler;
import com.battle.socket.MessageVo;

@Service
public class BattleEndSocketService {

	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	@Autowired
	private MessageHandler messageHandler;
	
	@Autowired
	private BattleRoomService battleRoomService;
	public void endPublish(String roomId) throws IOException{
		
		Sort sort = new Sort(Direction.DESC,"score");
		Pageable pageable = new PageRequest(0, 100, sort);
		BattleRoom battleRoom = battleRoomService.findOne(roomId);
		List<BattlePeriodMember> battlePeriodMembers = battlePeriodMemberService.
				findAllByBattleIdAndPeriodIdAndRoomId(battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId(), pageable);
		
		if(battlePeriodMembers==null||battlePeriodMembers.size()==0){
			return;
		}
		MessageVo messageVo = new MessageVo();
		messageVo.setCode(MessageVo.BATTLE_END_CODE);
		messageVo.setRoomId(battleRoom.getId());
		messageVo.setType(MessageVo.ROOM_TYPE);
		
		List<BattleEndRankVo> battleEndRankVos = new ArrayList<>();
		
		for(int i = 0;i<battlePeriodMembers.size();i++){
			BattlePeriodMember battlePeriodMember = battlePeriodMembers.get(i);
			BattleEndRankVo battleEndVo = new BattleEndRankVo();
			battleEndVo.setHeadImg(battlePeriodMember.getHeadImg());
			battleEndVo.setMemberId(battlePeriodMember.getId());
			battleEndVo.setNickname(battlePeriodMember.getNickname());
			battleEndVo.setProcess(battlePeriodMember.getProcess());
			battleEndVo.setRank(i+1);
			battleEndVo.setRoomId(battlePeriodMember.getRoomId());
			battleEndVo.setScore(battlePeriodMember.getScore());
			battleEndRankVos.add(battleEndVo);
		}
		messageVo.setData(battleEndRankVos);
		
		messageHandler.sendMessage(messageVo);
	}
}
