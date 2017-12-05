package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDanProjectDao;
import com.battle.domain.BattleDanProject;

@Service
public class BattleDanProjectService {

	@Autowired
	private BattleDanProjectDao battleDanProjectDao;

	public List<BattleDanProject> findAllByDanIdOrderByIndexAsc(String danId) {
		
		return battleDanProjectDao.findAllByDanIdOrderByIndexAsc(danId);
	}

	public BattleDanProject findOne(String id) {
		
		return battleDanProjectDao.findOne(id);
	}
}
