package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleDanUser;

public interface BattleDanUserPkDao extends CrudRepository<BattleDanUser, String>{

}
