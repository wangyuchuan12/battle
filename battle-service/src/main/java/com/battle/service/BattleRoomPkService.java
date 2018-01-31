package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleRoomPkDao;
import com.battle.domain.BattleRoomPk;

@Service
public class BattleRoomPkService {

	@Autowired
	private BattleRoomPkDao battleRoomPkDao;

	public BattleRoomPk findOneByUserId(String userId) {
		
		return battleRoomPkDao.findOneByUserId(userId);
	}

	public void add(BattleRoomPk battleRoomPk) {
		
		battleRoomPk.setId(UUID.randomUUID().toString());
		battleRoomPk.setUpdateAt(new DateTime());
		battleRoomPk.setCreateAt(new DateTime());
		
		battleRoomPkDao.save(battleRoomPk);
		
	}

	public void update(BattleRoomPk battleRoomPk) {
		
		battleRoomPk.setUpdateAt(new DateTime());
		
		battleRoomPkDao.save(battleRoomPk);
		
	}
}
