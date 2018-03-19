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

	public List<BattleStepIndexModel> findAllByIsDel(Pageable pageable,Integer isDel) {
		
		return battleStepIndexModelDao.findAllByIsDel(pageable,isDel);
	}

	public List<BattleStepIndexModel> findAllByModelIdAndIsDel(String modelId,Integer isDel) {
	
		return battleStepIndexModelDao.findAllByModelIdAndIsDel(modelId,isDel);
		
	}
}
