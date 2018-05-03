package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleWaitUser;

public interface BattleWaitUserDao extends CrudRepository<BattleWaitUser, String>{

	BattleWaitUser findOneByWaitIdAndUserId(String waitId, String userId);

	List<BattleWaitUser> findAllByWaitId(String waitId);

	List<BattleWaitUser> findAllByWaitIdAndStatus(String waitId, Integer status);

}
