package com.battle.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleStepModel;

public interface BattleStepModelDao extends CrudRepository<BattleStepModel, String>{
	
	@Cacheable(value="userCache") 
	List<BattleStepModel> findAll(Pageable pageable);

	@Cacheable(value="userCache") 
	BattleStepModel findOneByCode(String code);

}
