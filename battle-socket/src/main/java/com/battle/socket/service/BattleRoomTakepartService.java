package com.battle.socket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.BattlePeriodMember;
import com.battle.socket.MessageHandler;
import com.battle.socket.MessageVo;

@Service
public class BattleRoomTakepartService {
	
	@Autowired
	private MessageHandler messageHandler;
	public void takepartPublish(final BattlePeriodMember battlePeriodMember){
		
		new Thread(){
			public void run() {
				MessageVo messageVo = new MessageVo();
				messageVo.setCode(MessageVo.TAKEPART_CODE);
				messageVo.setRoomId(battlePeriodMember.getRoomId());
				messageVo.setType(MessageVo.ROOM_TYPE);
				messageVo.setData(battlePeriodMember);
				
				List<String> excludeUserIds = new ArrayList<>();
				excludeUserIds.add(battlePeriodMember.getUserId());
				messageVo.setExcludeUserIds(excludeUserIds);
				
				try{
					messageHandler.sendMessage(messageVo);
				}catch(Exception e){
					
				}
			}
		}.start();
		
	}
}
