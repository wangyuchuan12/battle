package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleRedpacketDao;
import com.battle.domain.BattleRedpacket;

@Service
public class BattleRedpacketService {

	@Autowired
	private BattleRedpacketDao redpacketDao;

	public List<BattleRedpacket> findAllByIsRoomAndRoomIdOrderByHandTimeDesc(int isRoom, String roomId) {
		
		return redpacketDao.findAllByIsRoomAndRoomIdOrderByHandTimeDesc(isRoom,roomId);
	}

	public void add(BattleRedpacket battleRedpacket) {
		
		battleRedpacket.setId(UUID.randomUUID().toString());
		battleRedpacket.setUpdateAt(new DateTime());
		battleRedpacket.setCreateAt(new DateTime());
		
		redpacketDao.save(battleRedpacket);
		
	}

	public BattleRedpacket findOne(String id) {
		
		return redpacketDao.findOne(id);
	}

	public void update(BattleRedpacket battleRedpacket) {
		
		battleRedpacket.setUpdateAt(new DateTime());
		
		redpacketDao.save(battleRedpacket);
		
	}
}
