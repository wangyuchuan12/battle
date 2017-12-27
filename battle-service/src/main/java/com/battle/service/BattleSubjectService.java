package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleSubjectDao;
import com.battle.domain.BattleSubject;

@Service
public class BattleSubjectService {

	@Autowired
	private BattleSubjectDao battleSubjectDao;

	public List<BattleSubject> findAllByBattleIdAndIsDelOrderBySeqAsc(String battleId,Integer isDel) {
		
		return battleSubjectDao.findAllByBattleIdAndIsDelOrderBySeqAsc(battleId,isDel);
	}

	public List<BattleSubject> findAllByIdIn(String[] subjectIds) {
		
		return battleSubjectDao.findAllByIdIn(subjectIds);
	}

	public void add(BattleSubject battleSubject) {
		
		battleSubject.setUpdateAt(new DateTime());
		battleSubject.setCreateAt(new DateTime());
		battleSubject.setId(UUID.randomUUID().toString());
		battleSubjectDao.save(battleSubject);
		
	}

	public BattleSubject findOne(String id) {
		return battleSubjectDao.findOne(id);
	}

	public void update(BattleSubject battleSubject) {
		
		battleSubject.setUpdateAt(new DateTime());
		
		battleSubjectDao.save(battleSubject);
		
	}

	public List<String> getIdsByBattleId(String battleId) {
		
		return battleSubjectDao.getIdsByBattleId(battleId);
	}
}
