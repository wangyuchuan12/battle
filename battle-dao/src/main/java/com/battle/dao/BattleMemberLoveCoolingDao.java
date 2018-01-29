package com.battle.dao;

import javax.persistence.LockModeType;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleMemberLoveCooling;

public interface BattleMemberLoveCoolingDao extends CrudRepository<BattleMemberLoveCooling, String>{

	@Cacheable(value="userCache",keyGenerator="sessionKeyGenerator")
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	BattleMemberLoveCooling findOneByBattleMemberId(String battleMemberId);

}
