package com.battle.dao;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleRoomGroup;

public interface BattleRoomGroupDao extends CrudRepository<BattleRoomGroup, String>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	BattleRoomGroup findOneByTypeAndCreaterUserId(Integer type, String userId);

}
