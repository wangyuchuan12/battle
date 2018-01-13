package com.battle.service;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleGroupConfigDao;
import com.battle.domain.BattleGroupConfig;
import com.battle.domain.BattleRoom;

@Service
public class BattleGroupConfigService {

	@Autowired
	private BattleGroupConfigDao battleGroupConfigDao;

	public List<BattleGroupConfig> findAllByCode(String code) {
		
		return battleGroupConfigDao.findAllByCode(code);
	}

	public void update(BattleGroupConfig battleGroupConfig) {
		
		battleGroupConfig.setUpdateAt(new DateTime());
		
		battleGroupConfigDao.save(battleGroupConfig);
		
	}
}
