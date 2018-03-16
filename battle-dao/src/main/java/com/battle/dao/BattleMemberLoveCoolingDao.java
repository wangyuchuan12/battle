package com.battle.dao;

import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleMemberLoveCooling;

public interface BattleMemberLoveCoolingDao extends CrudRepository<BattleMemberLoveCooling, String>{
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	BattleMemberLoveCooling findOneByBattleMemberId(String battleMemberId);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	BattleMemberLoveCooling findOne(String id);

}
