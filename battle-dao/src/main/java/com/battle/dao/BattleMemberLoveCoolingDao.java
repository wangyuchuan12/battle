package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleMemberLoveCooling;

public interface BattleMemberLoveCoolingDao extends CrudRepository<BattleMemberLoveCooling, String>{

	BattleMemberLoveCooling findOneByBattleMemberId(String battleMemberId);

}
