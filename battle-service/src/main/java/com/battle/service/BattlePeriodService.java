package com.battle.service;

import java.util.List;

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

	public BattlePeriod findOne(String id) {
		
		return battlePeriodDao.findOne(id);
	}

	public List<BattlePeriod> findAllByBattleIdOrderByIndexAsc(String battleId) {
		return battlePeriodDao.findAllByBattleIdOrderByIndexAsc(battleId);
	}
}
