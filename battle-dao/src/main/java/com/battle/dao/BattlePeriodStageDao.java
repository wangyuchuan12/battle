package com.battle.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.battle.domain.BattlePeriodStage;

public interface BattlePeriodStageDao extends CrudRepository<BattlePeriodStage, String>{

	BattlePeriodStage findOneByBattleIdAndPeriodIdAndIndex(String battleId, String periodId, Integer stageIndex);

	List<BattlePeriodStage> findAllByPeriodIdOrderByIndexAsc(String periodId);

	
	@Query("select id from com.battle.domain.BattlePeriodStage bpg where bpg.battleId=:battleId and bpg.periodId=:periodId")
	List<String> findAll(@Param("battleId")String battleId, @Param("periodId")String periodId);

}
