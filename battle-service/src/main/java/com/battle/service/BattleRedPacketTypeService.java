package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleRedPacketTypeDao;
import com.battle.domain.BattleRedPacketType;
import com.battle.domain.BattleRedpacket;

@Service
public class BattleRedPacketTypeService {

	@Autowired
	private BattleRedPacketTypeDao battleRedPacketTypeDao;

	public BattleRedPacketType findOne(String id) {
		
		return battleRedPacketTypeDao.findOne(id);
	}

	public List<BattleRedpacket> findAllByIsDel(int isDel) {
		
		return battleRedPacketTypeDao.findAllByIsDel(isDel);
	}
}
