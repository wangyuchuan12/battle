package com.battle.dao;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleWait;

public interface BattleWaitDao extends CrudRepository<BattleWait, String>{

	List<BattleWait> findAllByBattleId(String battleId);

	List<BattleWait> findAllByBattleIdAndStatus(String battleId, Integer status);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	BattleWait findOne(String waitId);

	List<BattleWait> findAllByStatus(Integer status);

}
