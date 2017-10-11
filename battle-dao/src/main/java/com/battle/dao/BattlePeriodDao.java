package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattlePeriod;

public interface BattlePeriodDao extends CrudRepository<BattlePeriod, String>{

	BattlePeriod findOneByBattleIdAndIndex(String battleId, Integer index);

	List<BattlePeriod> findAllByBattleIdOrderByIndexAsc(String battleId);

}