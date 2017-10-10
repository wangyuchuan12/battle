package com.battle.service;

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

}
