package com.battle.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDao;
import com.battle.domain.Battle;

@Service
public class BattleService {

	@Autowired
	private BattleDao battleDao;

	public Battle findOne(String id) {
		return battleDao.findOne(id);
	}

	public void update(Battle battle) {
		
		battle.setUpdateAt(new DateTime());
		battleDao.save(battle);
		
	}
}
