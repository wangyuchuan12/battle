package com.battle.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDekornDao;
import com.battle.domain.BattleDekorn;
@Service
public class BattleDekornService {

	@Autowired
	private BattleDekornDao battleDekornDao;

	public List<BattleDekorn> findAllByIsDel(int isDel) {
		
		return battleDekornDao.findAllByIsDel(isDel);
	}

	public BattleDekorn findOne(String id) {
		
		return battleDekornDao.findOne(id);
	}
}
