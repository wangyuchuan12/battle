package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleCreateDetailDao;
import com.battle.domain.BattleCreateDetail;

@Service
public class BattleCreateDetailService {

	@Autowired
	private BattleCreateDetailDao battleCreateDetailDao;

	public List<BattleCreateDetail> findAllByIsDefault(int isDefault) {
		
		return battleCreateDetailDao.findAllByIsDefault(isDefault);
	}

	public BattleCreateDetail findOneByCode(String code) {
		
		return battleCreateDetailDao.findOneByCode(code);
	}
}
