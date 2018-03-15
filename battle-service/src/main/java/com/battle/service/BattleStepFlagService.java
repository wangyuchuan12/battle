package com.battle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleStepFlagDao;
import com.battle.domain.BattleStepFlag;

@Service
public class BattleStepFlagService {

	@Autowired
	private BattleStepFlagDao battleStepFlagDao;

	public BattleStepFlag findOneByIndex(Integer index) {
		
		return battleStepFlagDao.findOneByIndex(index);
		
	}
}
