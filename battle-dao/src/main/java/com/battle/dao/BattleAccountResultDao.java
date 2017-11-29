package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleAccountResult;

public interface BattleAccountResultDao extends CrudRepository<BattleAccountResult, String>{

	BattleAccountResult findOneByUserId(String userId);

}
