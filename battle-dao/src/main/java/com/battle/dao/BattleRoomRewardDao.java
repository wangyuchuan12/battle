package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleRoomReward;

public interface BattleRoomRewardDao extends CrudRepository<BattleRoomReward, String>{

	List<BattleRoomReward> findAllByRoomIdOrderByRankAsc(String roomId);

	List<BattleRoomReward> findAllByRoomIdAndIsReceiveOrderByRankAsc(String roomId, int isReceive);

	BattleRoomReward findOneByReceiveMemberId(String receiveMemberId);

}
