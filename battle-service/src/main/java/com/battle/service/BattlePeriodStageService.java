package com.battle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattlePeriodStageDao;
import com.battle.domain.BattlePeriodStage;

@Service
public class BattlePeriodStageService {

	@Autowired
	private BattlePeriodStageDao battlePeriodStageDao;

	public BattlePeriodStage findOneByBattleIdAndPeriodIdAndIndex(String battleId, String periodId,
			Integer stageIndex) {
		
		return battlePeriodStageDao.findOneByBattleIdAndPeriodIdAndIndex(battleId,periodId,stageIndex);
	}
}
