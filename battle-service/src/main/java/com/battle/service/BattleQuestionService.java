package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleQuestionDao;
import com.battle.domain.BattleQuestion;

@Service
public class BattleQuestionService {

	@Autowired
	private BattleQuestionDao battleQuestionDao;

	public List<BattleQuestion> findAllByBattleIdAndPeriodStageId(String battleId, String periodStageId) {
		return battleQuestionDao.findAllByBattleIdAndPeriodStageId(battleId,periodStageId);
	}

	public List<BattleQuestion> findAllByIdIn(List<String> ids) {
		
		return battleQuestionDao.findAllByIdIn(ids);
	}
}
