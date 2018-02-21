package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDrawItemDao;
import com.battle.domain.BattleDrawItem;

@Service
public class BattleDrawItemService {

	@Autowired
	private BattleDrawItemDao battleDrawItemDao;

	public List<BattleDrawItem> findAllOrderByLevel() {
		
		return battleDrawItemDao.findAllOrderByLevel();
		
	}
}
