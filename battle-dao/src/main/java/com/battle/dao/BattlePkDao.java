package com.battle.dao;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattlePk;

public interface BattlePkDao extends CrudRepository<BattlePk, String>{

	BattlePk findOneByHomeUserId(String userId);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public BattlePk findOne(String id);

}
