package com.battle.socket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.BattleRoom;
import com.battle.service.BattleRoomService;
import com.battle.socket.task.RoomStartTask;


@Service
public class InitRoomService {
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private RoomStartTask roomStartTask;
	
	public BattleRoom addRoom(BattleRoom battleRoom){
		
		battleRoomService.add(battleRoom);
		
		roomStartTask.run(battleRoom);
		
		return battleRoom;
	}
}
