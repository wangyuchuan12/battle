package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleRoom;

public interface BattleRoomDao extends CrudRepository<BattleRoom, String>{

}
