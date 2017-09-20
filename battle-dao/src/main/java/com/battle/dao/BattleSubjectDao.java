package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleSubject;

public interface BattleSubjectDao extends CrudRepository<BattleSubject, String>{

	List<BattleSubject> findAllByBattleIdAndIsDelOrderBySeqAsc(String battleId,Integer isDel);

	List<BattleSubject> findAllByIdIn(String[] subjectIds);

}
