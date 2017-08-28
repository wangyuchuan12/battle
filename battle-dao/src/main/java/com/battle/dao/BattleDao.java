package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.Battle;

public interface BattleDao extends CrudRepository<Battle, String>{

}
