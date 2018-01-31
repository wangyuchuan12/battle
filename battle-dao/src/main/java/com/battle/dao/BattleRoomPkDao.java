package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleRoomPk;

public interface BattleRoomPkDao extends CrudRepository<BattleRoomPk, String>{

	BattleRoomPk findOneByUserId(String userId);

}
