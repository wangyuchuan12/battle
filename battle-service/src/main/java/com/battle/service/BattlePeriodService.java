package com.battle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattlePeriodDao;
import com.battle.domain.BattlePeriod;

@Service
public class BattlePeriodService {

	@Autowired
	private BattlePeriodDao battlePeriodDao;

	public BattlePeriod findOneByBattleIdAndIndex(String battleId, Integer index) {
		
		return battlePeriodDao.findOneByBattleIdAndIndex(battleId,index);
	}
}
