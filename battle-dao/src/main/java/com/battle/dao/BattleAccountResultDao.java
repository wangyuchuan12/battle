package com.battle.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.battle.domain.BattleAccountResult;

public interface BattleAccountResultDao extends CrudRepository<BattleAccountResult, String>{

	BattleAccountResult findOneByUserId(@Param("userId")String userId);

	@Query(value="from com.battle.domain.BattleAccountResult bar where exists(select id from com.battle.domain.UserFriend uf where uf.userId=:userId and uf.friendUserId=bar.userId)")
	List<BattleAccountResult> findAllByUserFrendUserId(String userId);

}
