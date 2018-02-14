package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleAccountResultDao;
import com.battle.domain.BattleAccountResult;

@Service
public class BattleAccountResultService {

	@Autowired
	private BattleAccountResultDao battleAccountResultDao;

	public BattleAccountResult findOneByUserId(String userId) {
		
		return battleAccountResultDao.findOneByUserId(userId);
	}

	public void add(BattleAccountResult battleAccountResult) {
		
		battleAccountResult.setId(UUID.randomUUID().toString());
		battleAccountResult.setUpdateAt(new DateTime());
		battleAccountResult.setCreateAt(new DateTime());
		
		battleAccountResultDao.save(battleAccountResult);
	}

	public void update(BattleAccountResult battleAccountResult) {
		
		battleAccountResult.setUpdateAt(new DateTime());
		battleAccountResultDao.save(battleAccountResult);
		
	}

	public List<BattleAccountResult> findAllByUserFrendUserId(String userId) {
		
		return battleAccountResultDao.findAllByUserFrendUserId(userId);
	}

	public Page<BattleAccountResult> findAll(Pageable pageable) {
		return battleAccountResultDao.findAll(pageable);
	}
}
