package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleStepFlag;

public interface BattleStepFlagDao extends CrudRepository<BattleStepFlag, String>{

	BattleStepFlag findOneByIndex(Integer index);

}
