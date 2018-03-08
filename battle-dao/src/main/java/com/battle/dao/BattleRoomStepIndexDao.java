package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleRoomStepIndex;

public interface BattleRoomStepIndexDao extends CrudRepository<BattleRoomStepIndex, String>{

	List<BattleRoomStepIndex> findAllByRoomIdOrderByStepIndexAsc(String roomId);

	List<BattleRoomStepIndex> findAllByRoomIdAndStepIndexGreaterThanAndStepIndexLessThanEqualOrderByStageIndex(
			String roomId,Integer startIndex, Integer endIndex);

}
