package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleRoomEntryDao;
import com.battle.domain.BattleRoomEntry;

@Service
public class BattleRoomEntryService {

	@Autowired
	private BattleRoomEntryDao battleRoomEntryDao;

	public void add(BattleRoomEntry battleRoomEntry) {
		
		battleRoomEntry.setId(UUID.randomUUID().toString());
		battleRoomEntry.setUpdateAt(new DateTime());
		battleRoomEntry.setCreateAt(new DateTime());
		battleRoomEntryDao.save(battleRoomEntry);
		
	}

	public BattleRoomEntry findOneByKey(String key) {
		return battleRoomEntryDao.findOneByKey(key);
	}
}
