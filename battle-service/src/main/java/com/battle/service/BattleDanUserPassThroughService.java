package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDanUserPassThroughDao;
import com.battle.domain.BattleDanUserPassThrough;

@Service
public class BattleDanUserPassThroughService {

	@Autowired
	private BattleDanUserPassThroughDao battleDanUserPassThroughDao;

	public BattleDanUserPassThrough findOneByBattleDanUserIdAndProjectId(String battleDanUserId, String projectId) {
		
		
		return battleDanUserPassThroughDao.findOneByBattleDanUserIdAndProjectId(battleDanUserId,projectId);
	}

	public void add(BattleDanUserPassThrough battleDanUserPassThrough) {
		
		battleDanUserPassThrough.setId(UUID.randomUUID().toString());
		battleDanUserPassThrough.setUpdateAt(new DateTime());
		battleDanUserPassThrough.setCreateAt(new DateTime());
		
		battleDanUserPassThroughDao.save(battleDanUserPassThrough);
		
	}

	public BattleDanUserPassThrough fineOne(String id) {
		
		return battleDanUserPassThroughDao.findOne(id);
	}
}
