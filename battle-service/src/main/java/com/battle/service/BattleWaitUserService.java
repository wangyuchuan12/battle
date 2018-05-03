package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleWaitUserDao;
import com.battle.domain.BattleWaitUser;

@Service
public class BattleWaitUserService {

	@Autowired
	private BattleWaitUserDao battleWaitUserDao;

	public BattleWaitUser findOneByWaitIdAndUserId(String waitId, String userId) {
		
		return battleWaitUserDao.findOneByWaitIdAndUserId(waitId,userId);
	}

	public void add(BattleWaitUser battleWaitUser) {
		
		battleWaitUser.setId(UUID.randomUUID().toString());
		battleWaitUser.setCreateAt(new DateTime());
		battleWaitUser.setUpdateAt(new DateTime());
		
		battleWaitUserDao.save(battleWaitUser);
		
	}

	public void update(BattleWaitUser battleWaitUser) {
		
		battleWaitUser.setUpdateAt(new DateTime());
		battleWaitUserDao.save(battleWaitUser);
		
	}

	public List<BattleWaitUser> findAllByWaitId(String waitId) {
		
		return battleWaitUserDao.findAllByWaitId(waitId);
	}
	
	public List<BattleWaitUser> findAllByWaitIdAndStatus(String waitId,Integer status) {
		
		return battleWaitUserDao.findAllByWaitIdAndStatus(waitId,status);
	}
}
