package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleSubject;

public interface BattleSubjectDao extends CrudRepository<BattleSubject, String>{

	List<BattleSubject> findAllByBattleIdOrderBySeqAsc(String battleId);

	List<BattleSubject> findAllByIdIn(String[] subjectIds);

}
