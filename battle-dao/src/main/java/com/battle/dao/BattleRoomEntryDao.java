package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleRoomEntry;

public interface BattleRoomEntryDao extends CrudRepository<BattleRoomEntry, String>{

	BattleRoomEntry findOneByKey(String key);

}
