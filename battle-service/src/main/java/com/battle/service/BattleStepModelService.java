package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleStepModelDao;
import com.battle.domain.BattleStepModel;

@Service
public class BattleStepModelService {

	@Autowired
	private BattleStepModelDao battleStepModelDao;

	public Page<BattleStepModel> findAll(Pageable pageable) {
		
		return battleStepModelDao.findAll(pageable);
	}

	public BattleStepModel findOneByCode(String code) {
		
		return battleStepModelDao.findOneByCode(code);
		
	}
}
