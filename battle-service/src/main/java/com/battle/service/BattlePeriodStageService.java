package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
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

	public List<BattlePeriodStage> findAllByPeriodIdOrderByIndexAsc(String periodId) {
		return battlePeriodStageDao.findAllByPeriodIdOrderByIndexAsc(periodId);
	}

	public BattlePeriodStage findOne(String id) {
		return battlePeriodStageDao.findOne(id);
	}

	public void update(BattlePeriodStage battlePeriodStage) {
		
		battlePeriodStage.setUpdateAt(new DateTime());
		
		battlePeriodStageDao.save(battlePeriodStage);
		
	}

	public void add(BattlePeriodStage battlePeriodStage) {
		battlePeriodStage.setId(UUID.randomUUID().toString());
		
		battlePeriodStage.setUpdateAt(new DateTime());
		
		battlePeriodStage.setCreateAt(new DateTime());
		
		battlePeriodStageDao.save(battlePeriodStage);
		
	}

	public List<String> getIdsByBattleIdAndPeriodId(String battleId, String periodId) {
		return battlePeriodStageDao.findAll(battleId,periodId);
	}
}
