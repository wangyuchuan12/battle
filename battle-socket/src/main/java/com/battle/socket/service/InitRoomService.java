package com.battle.socket.service;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.Battle;
import com.battle.domain.BattleCreateDetail;
import com.battle.domain.BattleRoom;
import com.battle.service.BattleCreateDetailService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleService;
import com.battle.service.other.BattleRoomHandleService;
import com.battle.service.other.RoomTakapertService;
import com.battle.socket.task.RoomStartTask;


@Service
public class InitRoomService {
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private RoomStartTask roomStartTask;
	
	@Autowired
	private BattleCreateDetailService battleCreateDetailServie;
	
	@Autowired
	private BattleRoomHandleService battleRoomHandleService;
	
	@Autowired
	private RoomTakapertService roomTakapertService;
	
	@Autowired
	private BattleService battleService;
	
	public BattleRoom addRoom(BattleRoom battleRoom){
		
		battleRoomService.add(battleRoom);
		
		roomStartTask.run(battleRoom);
		
		return battleRoom;
	}
	
	
	public BattleRoom addPkRoom(String...userIds){
		
		BattleCreateDetail battleCreateDetail = battleCreateDetailServie.findOneByCode(BattleCreateDetail.PK_CODE);
		Battle battle = battleService.findOne(battleCreateDetail.getBattleId());
		BattleRoom battleRoom = battleRoomHandleService.initRoom(battle);
		battleRoom.setIsPk(1);
		battleRoom.setPeriodId(battleCreateDetail.getPeriodId());
		battleRoom.setMaxinum(2);
		battleRoom.setMininum(2);
		battleRoom.setIsSearchAble(0);
		battleRoom.setScrollGogal(battleCreateDetail.getScrollGogal());
		battleRoom.setPlaces(1);
		battleRoom.setIsDanRoom(0);
		battleRoom.setIsIncrease(0);
		battleRoom.setStartTime(new DateTime());
		battleRoom.setLoveCount(battleCreateDetail.getLoveCount());
		
		addRoom(battleRoom);
		
		roomTakapertService.takepart(battleRoom, userIds);
		
		return battleRoom;
		
	}
}
