package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleExpertDao;
import com.battle.domain.BattleExpert;

@Service
public class BattleExpertService {

	@Autowired
	private BattleExpertDao battleExpertDao;

	public BattleExpert findOneByBattleIdAndBattleUserId(String battleId, String battleUserId) {
		return battleExpertDao.findOneByBattleIdAndBattleUserId(battleId,battleUserId);
	}

	public void add(BattleExpert battleExpert) {
		
		battleExpert.setId(UUID.randomUUID().toString());
		battleExpert.setUpdateAt(new DateTime());
		battleExpert.setCreateAt(new DateTime());
		
		battleExpertDao.save(battleExpert);
		
	}

	public BattleExpert findOne(String id) {
		return battleExpertDao.findOne(id);
	}

	public void update(BattleExpert battleExpert) {
		
		battleExpert.setUpdateAt(new DateTime());
		
		battleExpertDao.save(battleExpert);
		
	}
}
