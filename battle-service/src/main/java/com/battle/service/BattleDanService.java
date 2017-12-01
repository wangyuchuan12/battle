package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDanDao;
import com.battle.domain.BattleDan;

@Service
public class BattleDanService {

	@Autowired
	private BattleDanDao battleDanDao;

	public List<BattleDan> findAllByPointIdOrderByLevelAsc(String pointId) {
		
		return battleDanDao.findAllByPointIdOrderByLevelAsc(pointId);
	}
}