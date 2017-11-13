package com.battle.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleRoomRecord;

public interface BattleRoomRecordDao extends CrudRepository<BattleRoomRecord, String>{

	List<BattleRoomRecord> findAllByRoomId(String roomId,Pageable pageable);

}
