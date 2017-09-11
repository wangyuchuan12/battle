package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleSubjectDao;
import com.battle.domain.BattleSubject;

@Service
public class BattleSubjectService {

	@Autowired
	private BattleSubjectDao battleSubjectDao;

	public List<BattleSubject> findAllByBattleIdOrderBySeqAsc(String battleId) {
		
		return battleSubjectDao.findAllByBattleIdOrderBySeqAsc(battleId);
	}

	public List<BattleSubject> findAllByIdIn(String[] subjectIds) {
		
		return battleSubjectDao.findAllByIdIn(subjectIds);
	}
}
