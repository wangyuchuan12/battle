package com.battle.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleStepModel;

public interface BattleStepModelDao extends CrudRepository<BattleStepModel, String>{
	List<BattleStepModel> findAll(Pageable pageable);

	BattleStepModel findOneByCode(String code);

}
