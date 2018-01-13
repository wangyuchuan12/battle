package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleGroupConfig;

public interface BattleGroupConfigDao extends CrudRepository<BattleGroupConfig, String>{

	List<BattleGroupConfig> findAllByCode(String code);

}
