package com.battle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleStepDomainDao;
import com.battle.domain.BattleStepDomain;

@Service
public class BattleStepDomainService {


	@Autowired
	private BattleStepDomainDao battleStepDomainDao;

	public BattleStepDomain findOneByRoomIdAndStepIndex(String roomId, Integer index) {
		
		return battleStepDomainDao.findOneByRoomIdAndStepIndex(roomId,index);
	}
}
