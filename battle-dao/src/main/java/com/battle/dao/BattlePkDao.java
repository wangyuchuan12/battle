package com.battle.dao;

import javax.persistence.LockModeType;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattlePk;

public interface BattlePkDao extends CrudRepository<BattlePk, String>{

	//@Cacheable(value="userCache",keyGenerator="sessionKeyGenerator")
	BattlePk findOneByHomeUserId(String userId);
	
	//@Cacheable(value="userCache")
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public BattlePk findOne(String id);

}
