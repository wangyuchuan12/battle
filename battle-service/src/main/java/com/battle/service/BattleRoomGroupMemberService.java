package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleRoomGroupMemberDao;
import com.battle.domain.BattleRoomGroupMember;

@Service
public class BattleRoomGroupMemberService {

	@Autowired
	private BattleRoomGroupMemberDao battleRoomGroupMemberDao;

	public BattleRoomGroupMember findOneByGroupIdAndUserId(String groupId, String userId) {
		
		return battleRoomGroupMemberDao.findOneByGroupIdAndUserId(groupId,userId);
	}

	public void add(BattleRoomGroupMember battleRoomGroupMember) {
		
		battleRoomGroupMember.setId(UUID.randomUUID().toString());
		battleRoomGroupMember.setUpdateAt(new DateTime());
		battleRoomGroupMember.setCreateAt(new DateTime());
		
		battleRoomGroupMemberDao.save(battleRoomGroupMember);
		
	}

	public List<BattleRoomGroupMember> findAllByGroupId(String groupId) {
		
		return battleRoomGroupMemberDao.findAllByGroupId(groupId);
	}
}
