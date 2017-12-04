package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDanTaskDao;
import com.battle.domain.BattleDanTask;

@Service
public class BattleDanTaskService {

	@Autowired
	private BattleDanTaskDao battleDanTaskDao;


	public List<BattleDanTask> findAllByDanIdOrderByIndexAsc(String danId) {
		
		return battleDanTaskDao.findAllByDanIdOrderByIndexAsc(danId);
	}
}
