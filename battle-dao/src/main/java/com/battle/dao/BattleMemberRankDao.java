package com.battle.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleMemberRank;

public interface BattleMemberRankDao extends CrudRepository<BattleMemberRank, String>{

	BattleMemberRank findOneByMemberId(String memberId);

	List<BattleMemberRank> findAllByRoomId(String roomId);

	Page<BattleMemberRank> findAllByRoomId(String roomId, Pageable pageable);

}
