package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDanUserDao;
import com.battle.domain.BattleDanUser;

@Service
public class BattleDanUserService {

	@Autowired
	private BattleDanUserDao battleDanUserDao;

	public List<BattleDanUser> findAllByUserIdAndPointIdOrderByLevelAsc(String userId, String pointId) {
		
		return battleDanUserDao.findAllByUserIdAndPointIdOrderByLevelAsc(userId,pointId);
	}

	public void add(BattleDanUser battleDanUser) {
		
		battleDanUser.setId(UUID.randomUUID().toString());
		battleDanUser.setCreateAt(new DateTime());
		battleDanUser.setUpdateAt(new DateTime());
		battleDanUserDao.save(battleDanUser);
		
		battleDanUserDao.save(battleDanUser);
		
	}

	public void update(BattleDanUser battleDanUser) {
		
		battleDanUser.setUpdateAt(new DateTime());
		
		battleDanUserDao.save(battleDanUser);
		
	}

	public BattleDanUser findOne(String id) {
		
		return battleDanUserDao.findOne(id);
	}
}