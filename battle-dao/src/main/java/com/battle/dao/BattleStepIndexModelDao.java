package com.battle.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleStepIndexModel;

public interface BattleStepIndexModelDao extends CrudRepository<BattleStepIndexModel, String>{

	@Cacheable(value="userCache")
	List<BattleStepIndexModel> findAll(Pageable pageable);

	@Cacheable(value="userCache")
	List<BattleStepIndexModel> findAllByModelId(String modelId);

}
