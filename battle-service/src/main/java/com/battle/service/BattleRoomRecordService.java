package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleRoomRecordDao;
import com.battle.domain.BattleRoomRecord;

@Service
public class BattleRoomRecordService {

	@Autowired
	private BattleRoomRecordDao battleRoomRecordDao;

	public void add(BattleRoomRecord battleRoomRecord) {
		
		battleRoomRecord.setId(UUID.randomUUID().toString());
		battleRoomRecord.setCreateAt(new DateTime());
		battleRoomRecord.setUpdateAt(new DateTime());
		battleRoomRecordDao.save(battleRoomRecord);
	}

	public List<BattleRoomRecord> findAllByRoomId(String roomId,Pageable pageable) {
		
		return battleRoomRecordDao.findAllByRoomId(roomId,pageable);
	}
}
