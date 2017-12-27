package com.battle.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.battle.domain.BattleSubject;

public interface BattleSubjectDao extends CrudRepository<BattleSubject, String>{

	List<BattleSubject> findAllByBattleIdAndIsDelOrderBySeqAsc(String battleId,Integer isDel);

	List<BattleSubject> findAllByIdIn(String[] subjectIds);

	@Query("select id from com.battle.domain.BattleSubject bs where bs.battleId=:battleId")
	List<String> getIdsByBattleId(@Param("battleId")String battleId);

}
