package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleRoomGroupDao;
import com.battle.domain.BattleRoomGroup;

@Service
public class BattleRoomGroupService {

	@Autowired
	private BattleRoomGroupDao battleRoomGroupDao;

	public BattleRoomGroup findOneByTypeAndCreaterUserId(Integer type, String userId) {
		
		return battleRoomGroupDao.findOneByTypeAndCreaterUserId(type,userId);
	}

	public void add(BattleRoomGroup battleRoomGroup) {
		battleRoomGroup.setId(UUID.randomUUID().toString());
		battleRoomGroup.setUpdateAt(new DateTime());
		battleRoomGroup.setCreateAt(new DateTime());
		
		battleRoomGroupDao.save(battleRoomGroup);
		
	}

	public void update(BattleRoomGroup battleRoomGroup) {
		
		battleRoomGroup.setUpdateAt(new DateTime());
		
		battleRoomGroupDao.save(battleRoomGroup);
		
	}
}
