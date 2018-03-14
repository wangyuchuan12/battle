package com.battle.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.battle.domain.BattleSubject;

public interface BattleSubjectDao extends CrudRepository<BattleSubject, String>{

	@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	List<BattleSubject> findAllByBattleIdAndIsDelOrderBySeqAsc(String battleId,Integer isDel);

	@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	List<BattleSubject> findAllByIdIn(String[] subjectIds);

	@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	@Query("select id from com.battle.domain.BattleSubject bs where bs.battleId=:battleId")
	List<String> getIdsByBattleId(@Param("battleId")String battleId);

}
