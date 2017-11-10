package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleRoomRecord;

public interface BattleRoomRecordDao extends CrudRepository<BattleRoomRecord, String>{

}
