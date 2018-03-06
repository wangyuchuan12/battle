package com.battle.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleStepIndexModel;

public interface BattleStepIndexModelDao extends CrudRepository<BattleStepIndexModel, String>{

	List<BattleStepIndexModel> findAll(Pageable pageable);

	List<BattleStepIndexModel> findAllByModelId(String modelId);

}
