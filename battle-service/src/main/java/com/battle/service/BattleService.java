package com.battle.service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDao;
import com.battle.domain.Battle;

@Service
public class BattleService {

	@Autowired
	private BattleDao battleDao;

	public Battle findOne(String id) {
		Battle battle = battleDao.findOne(id);
		
		return battle;
	}

	public void update(Battle battle) {
		
		battle.setUpdateAt(new DateTime());
		
		battleDao.save(battle);
		
	}

	public void add(Battle battle) {
		
		battle.setId(UUID.randomUUID().toString());
		battle.setUpdateAt(new DateTime());
		battle.setCreateAt(new DateTime());
		
		battleDao.save(battle);
		
	}

	public List<Battle> findAllByStatusOrderByIndexAsc(Integer status) {
		
		return battleDao.findAllByStatusOrderByIndexAsc(status);
	}
}
