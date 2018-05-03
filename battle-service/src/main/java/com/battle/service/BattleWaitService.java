package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleWaitDao;
import com.battle.domain.BattleWait;

@Service
public class BattleWaitService {

	@Autowired
	private BattleWaitDao battleWaitDao;

	public List<BattleWait> findAllByBattleId(String battleId) {
		
		return battleWaitDao.findAllByBattleId(battleId);
	}

	public BattleWait findOne(String id) {
		
		return battleWaitDao.findOne(id);
	}

	public List<BattleWait> findAllByBattleIdAndStatus(String battleId, Integer status) {
		
		return battleWaitDao.findAllByBattleIdAndStatus(battleId,status);
	}

	public void add(BattleWait battleWait) {
		
		battleWait.setId(UUID.randomUUID().toString());
		battleWait.setUpdateAt(new DateTime());
		battleWait.setCreateAt(new DateTime());
		
		battleWaitDao.save(battleWait);
		
	}

	public void update(BattleWait battleWait) {
		
		battleWait.setUpdateAt(new DateTime());
		
		battleWaitDao.save(battleWait);
		
	}

	public List<BattleWait> findAllByStatus(Integer status) {
		
		return battleWaitDao.findAllByStatus(status);
	}
}
