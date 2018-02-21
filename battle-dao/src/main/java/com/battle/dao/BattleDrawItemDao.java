package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleDrawItem;

public interface BattleDrawItemDao extends CrudRepository<BattleDrawItem, String>{

	List<BattleDrawItem> findAllOrderByLevelAsc();

}
