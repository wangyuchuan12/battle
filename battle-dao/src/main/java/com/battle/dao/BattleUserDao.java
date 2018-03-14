package com.battle.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleUser;

public interface BattleUserDao extends CrudRepository<BattleUser, String>{

	@Cacheable(value="userCache")
	BattleUser findOneByUserIdAndBattleId(String userId, String battleId);

}
