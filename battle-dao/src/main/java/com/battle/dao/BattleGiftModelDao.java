package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleGiftModel;

public interface BattleGiftModelDao extends CrudRepository<BattleGiftModel, String>{

	List<BattleGiftModel> findAllByIsDel(int isDel);

}
