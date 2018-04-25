package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleCreateDetail;

public interface BattleCreateDetailDao extends CrudRepository<BattleCreateDetail, String>{

	List<BattleCreateDetail> findAllByIsDefault(int isDefault);

	BattleCreateDetail findOneByCode(String code);

}
