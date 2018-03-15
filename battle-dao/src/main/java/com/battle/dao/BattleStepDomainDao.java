package com.battle.dao;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleStepDomain;

public interface BattleStepDomainDao extends CrudRepository<BattleStepDomain, String>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	BattleStepDomain findOneByRoomIdAndStepIndex(String roomId, Integer index);

}
