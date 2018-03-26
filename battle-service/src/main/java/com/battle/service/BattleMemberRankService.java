package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleMemberRankDao;
import com.battle.domain.BattleMemberRank;

@Service
public class BattleMemberRankService {

	@Autowired
	private BattleMemberRankDao battleMemberRankDao;

	public void add(BattleMemberRank battleMemberRank) {
		
		battleMemberRank.setId(UUID.randomUUID().toString());
		battleMemberRank.setCreateAt(new DateTime());
		battleMemberRank.setUpdateAt(new DateTime());
		
		battleMemberRankDao.save(battleMemberRank);
		
	}

	public BattleMemberRank findOneByMemberId(String memberId) {
		
		return battleMemberRankDao.findOneByMemberId(memberId);
	}

	public List<BattleMemberRank> findAllByRoomId(String roomId) {
		
		return battleMemberRankDao.findAllByRoomId(roomId);
		
	}

	public Page<BattleMemberRank> findAllByRoomId(String roomId, Pageable pageable) {
		
		return battleMemberRankDao.findAllByRoomId(roomId,pageable);
	}
}
