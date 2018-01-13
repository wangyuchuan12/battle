package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleRoomGroupMember;

public interface BattleRoomGroupMemberDao extends CrudRepository<BattleRoomGroupMember, String>{

	BattleRoomGroupMember findOneByGroupIdAndUserId(String groupId, String userId);

}
