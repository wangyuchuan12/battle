package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleExpert;

public interface BattleExpertDao extends CrudRepository<BattleExpert, String>{

	BattleExpert findOneByBattleIdAndBattleUserId(String battleId, String battleUserId);

}
