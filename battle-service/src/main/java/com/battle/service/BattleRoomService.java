package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleRoomDao;
import com.battle.domain.BattleRoom;

@Service
public class BattleRoomService {

	@Autowired
	private BattleRoomDao battleRoomDao;
	public BattleRoom findOne(String id) {
		
		return battleRoomDao.findOne(id);
	}
	public void add(BattleRoom battleRoom) {
		
		battleRoom.setId(UUID.randomUUID().toString());
		battleRoom.setCreateAt(new DateTime());
		battleRoom.setUpdateAt(new DateTime());
		battleRoomDao.save(battleRoom);
		
	}
	public BattleRoom findOneByBattleIdAndPeriodIdAndOwner(String battleId, String periodId, String battleUserId) {
		
		return battleRoomDao.findOneByBattleIdAndPeriodIdAndOwner(battleId,periodId,battleUserId);
	}

}
