package com.battle.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleDrawItem;

public interface BattleDrawItemDao extends CrudRepository<BattleDrawItem, String>{

	@Query("from com.battle.domain.BattleDrawItem bdt order by level asc")
	List<BattleDrawItem> findAllOrderByLevelAsc();

	List<BattleDrawItem> findAllByBattleIdAndPeriodId(String battleId, String periodId);

}
