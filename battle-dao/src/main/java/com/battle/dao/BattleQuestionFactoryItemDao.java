package com.battle.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.battle.domain.BattleQuestionFactoryItem;

public interface BattleQuestionFactoryItemDao extends CrudRepository<BattleQuestionFactoryItem, String>{

	List<BattleQuestionFactoryItem> findAllByUserIdAndStatusInAndIsDel(String userId,List<Integer> statuses, int isDel, Pageable pageable);

	@Query("from com.battle.domain.BattleQuestionFactoryItem bqf where bqf.battleId=:battleId and bqf.status=:status order by rand()")
	List<BattleQuestionFactoryItem> findAllByBattleIdAndStatusRandom(@Param("battleId")String battleId,@Param("status")int status, Pageable pageable);

}
