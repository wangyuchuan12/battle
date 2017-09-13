package com.battle.dao;

import org.springframework.data.repository.CrudRepository;
import com.battle.domain.BattlePeriodStage;

public interface BattlePeriodStageDao extends CrudRepository<BattlePeriodStage, String>{

	BattlePeriodStage findOneByBattleIdAndPeriodIdAndIndex(String battleId, String periodId, Integer stageIndex);

}
