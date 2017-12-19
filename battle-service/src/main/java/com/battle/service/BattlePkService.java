package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattlePkDao;
import com.battle.domain.BattlePk;

@Service
public class BattlePkService {

	@Autowired
	private BattlePkDao battlePkDao;

	public BattlePk findOneByHomeUserId(String homeUserId) {
		
		return battlePkDao.findOneByHomeUserId(homeUserId);
	}

	public void add(BattlePk battlePk) {
		
		battlePk.setId(UUID.randomUUID().toString());
		
		battlePk.setUpdateAt(new DateTime());
		
		battlePk.setCreateAt(new DateTime());
		
		battlePkDao.save(battlePk);
		
	}

	public BattlePk findOne(String id) {
		
		return battlePkDao.findOne(id);
	}

	public void update(BattlePk battlePk) {
		
		battlePk.setCreateAt(new DateTime());
		battlePkDao.save(battlePk);
		
	}
}
