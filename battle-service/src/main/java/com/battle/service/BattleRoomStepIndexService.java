package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleRoomStepIndexDao;
import com.battle.domain.BattleRoomStepIndex;

@Service
public class BattleRoomStepIndexService {

	@Autowired
	private BattleRoomStepIndexDao battleRoomStepIndexDao;

	public List<BattleRoomStepIndex> findAllByRoomIdOrderByStepIndexAsc(String roomId) {
		
		return battleRoomStepIndexDao.findAllByRoomIdOrderByStepIndexAsc(roomId);
	}

	public void add(BattleRoomStepIndex battleRoomStepIndex) {
		
		battleRoomStepIndex.setId(UUID.randomUUID().toString());
		
		battleRoomStepIndexDao.save(battleRoomStepIndex);
		
	}

	public List<BattleRoomStepIndex> findAllByRoomIdAndStepIndexGreaterThanAndStepIndexLessThanEqualOrderByStepIndexAsc(
			String roomId,Integer startIndex, Integer endIndex) {
		
		return battleRoomStepIndexDao.findAllByRoomIdAndStepIndexGreaterThanAndStepIndexLessThanEqualOrderByStepIndexAsc(roomId,startIndex,endIndex);
	}
}
