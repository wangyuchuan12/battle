package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleRoomRewardDao;
import com.battle.domain.BattleRoomReward;

@Service
public class BattleRoomRewardService {

	@Autowired
	private BattleRoomRewardDao battleRoomRewardDao;

	public List<BattleRoomReward> findAllByRoomIdOrderByRankAsc(String roomId) {
		
		return battleRoomRewardDao.findAllByRoomIdOrderByRankAsc(roomId);
	}

	public void add(BattleRoomReward battleRoomReward) {
		battleRoomReward.setId(UUID.randomUUID().toString());
		battleRoomReward.setUpdateAt(new DateTime());
		battleRoomReward.setCreateAt(new DateTime());
		battleRoomRewardDao.save(battleRoomReward);
	}

	public void update(BattleRoomReward battleRoomReward) {
		
		battleRoomReward.setUpdateAt(new DateTime());
		
		battleRoomRewardDao.save(battleRoomReward);
		
	}

	public List<BattleRoomReward> findAllByRoomIdAndIsReceiveOrderByRankAsc(String roomId, int isReceive) {
		
		return battleRoomRewardDao.findAllByRoomIdAndIsReceiveOrderByRankAsc(roomId,isReceive);
	}

	public BattleRoomReward findOneByReceiveMemberId(String receiveMemberId) {
		
		return battleRoomRewardDao.findOneByReceiveMemberId(receiveMemberId);
		
	}
}
