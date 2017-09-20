package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.battle.domain.BattlePeriodStage;

public interface BattlePeriodStageDao extends CrudRepository<BattlePeriodStage, String>{

	BattlePeriodStage findOneByBattleIdAndPeriodIdAndIndex(String battleId, String periodId, Integer stageIndex);

	List<BattlePeriodStage> findAllByPeriodIdOrderByIndexAsc(String periodId);

}
