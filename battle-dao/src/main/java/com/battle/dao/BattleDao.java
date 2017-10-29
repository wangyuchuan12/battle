package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.Battle;

public interface BattleDao extends CrudRepository<Battle, String>{

	List<Battle> findAllByStatusOrderByIndexAsc(Integer status);

}
