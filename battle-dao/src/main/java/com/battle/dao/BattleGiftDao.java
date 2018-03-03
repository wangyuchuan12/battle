package com.battle.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.battle.domain.BattleGift;

public interface BattleGiftDao extends CrudRepository<BattleGift, String>{

	List<BattleGift> findAllByAccountIdAndLevel(String accountId, int level);

	List<BattleGift> findAllByAccountIdAndLevelAndIsReceive(String accountId, int level, int isReceive);

	@Modifying
	@Query(value="update com.battle.domain.BattleGift bg set bg.isReceive=:isReceive")
	void setIsReceive(@Param("isReceive")int isReceive);

}
