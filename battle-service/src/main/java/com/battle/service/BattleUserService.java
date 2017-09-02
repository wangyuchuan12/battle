package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleUserDao;
import com.battle.domain.BattleUser;

@Service
public class BattleUserService {

	@Autowired
	private BattleUserDao battleUserDao;

	public BattleUser findOneByUserIdAndBattleId(String userId, String battleId) {
		return battleUserDao.findOneByUserIdAndBattleId(userId,battleId);
	}

	public void add(BattleUser battleUser) {
		
		battleUser.setUpdateAt(new DateTime());
		battleUser.setCreateAt(new DateTime());
		
		battleUser.setId(UUID.randomUUID().toString());
		
		battleUserDao.save(battleUser);
		
		
	}
}
