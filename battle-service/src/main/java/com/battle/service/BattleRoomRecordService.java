package com.battle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleRoomRecordDao;

@Service
public class BattleRoomRecordService {

	@Autowired
	private BattleRoomRecordDao battleRoomRecordDao;
}
