package com.battle.socket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.BattleRoom;
import com.battle.socket.MessageHandler;
import com.battle.socket.MessageVo;

@Service
public class BattleRoomStartService {

	@Autowired
	private MessageHandler messageHandler;
	public void startPublish(final BattleRoom battleRoom){
		new Thread(){
			public void run() {
				MessageVo messageVo = new MessageVo();
				messageVo.setCode(MessageVo.ROOM_START_CODE);
				messageVo.setRoomId(battleRoom.getId());
				messageVo.setType(MessageVo.ROOM_TYPE);
				messageVo.setData(battleRoom);
				
				List<String> excludeUserIds = new ArrayList<>();
				
				messageVo.setExcludeUserIds(excludeUserIds);
				
				try{
					messageHandler.sendMessage(messageVo);
				}catch(Exception e){
					
				}
			}
		}.start();
	}
}
