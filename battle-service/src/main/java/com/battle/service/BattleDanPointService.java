package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDanPointDao;
import com.battle.domain.BattleDanPoint;

@Service
public class BattleDanPointService {

	@Autowired
	private BattleDanPointDao battleDanPointDao;

	public List<BattleDanPoint> findAllByIsRun(int isRun) {
		
		return battleDanPointDao.findAllByIsRun(isRun);
	}
}
