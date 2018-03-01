package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleGift;

public interface BattleGiftDao extends CrudRepository<BattleGift, String>{

	List<BattleGift> findAllByAccountIdAndLevel(String accountId, int level);

	List<BattleGift> findAllByAccountIdAndLevelAndIsReceive(String accountId, int level, int isReceive);

}
