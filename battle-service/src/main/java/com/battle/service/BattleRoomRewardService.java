package com.battle.service;

import java.util.List;

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
}
