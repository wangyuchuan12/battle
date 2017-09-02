package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleUser;

public interface BattleUserDao extends CrudRepository<BattleUser, String>{

	BattleUser findOneByUserIdAndBattleId(String userId, String battleId);

}
