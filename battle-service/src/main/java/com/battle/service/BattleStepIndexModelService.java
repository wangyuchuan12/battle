package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleStepIndexModelDao;
import com.battle.domain.BattleStepIndexModel;

@Service
public class BattleStepIndexModelService {

	@Autowired
	private BattleStepIndexModelDao battleStepIndexModelDao;

	public List<BattleStepIndexModel> findAll(Pageable pageable) {
		
		return battleStepIndexModelDao.findAll(pageable);
	}

	public List<BattleStepIndexModel> findAllByModelId(String modelId) {
	
		return battleStepIndexModelDao.findAllByModelId(modelId);
		
	}
}
