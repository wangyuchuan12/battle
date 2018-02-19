package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDekornUserDao;
import com.battle.domain.BattleDekornUser;

@Service
public class BattleDekornUserService {

	@Autowired
	private BattleDekornUserDao battleDekornUserDao;

	public List<BattleDekornUser> findAllByDekornIdAndUserIdAndIsDel(String dekornId, String userId,Integer isDel) {
		
		return battleDekornUserDao.findAllByDekornIdAndUserIdAndIsDel(dekornId,userId,isDel);
	}

	public void update(BattleDekornUser battleDekornUser) {
		
		battleDekornUser.setUpdateAt(new DateTime());
		
		battleDekornUserDao.save(battleDekornUser);
		
	}

	public void add(BattleDekornUser battleDekornUser) {
		
		battleDekornUser.setId(UUID.randomUUID().toString());
		battleDekornUser.setUpdateAt(new DateTime());
		battleDekornUser.setCreateAt(new DateTime());
		
		battleDekornUserDao.save(battleDekornUser);
		
	}

	public List<BattleDekornUser> findAllByUserIdAndIsDel(String userId, int isDel) {
		return battleDekornUserDao.findAllByUserIdAndIsDel(userId,isDel);
	}

	public List<BattleDekornUser> findAllByDekornIdAndIsDel(String dekornId, int isDel) {
		return battleDekornUserDao.findAllByDekornIdAndIsDel(dekornId,isDel);
	}
}
