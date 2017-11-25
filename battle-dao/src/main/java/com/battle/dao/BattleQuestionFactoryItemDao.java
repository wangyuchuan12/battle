package com.battle.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleQuestionFactoryItem;

public interface BattleQuestionFactoryItemDao extends CrudRepository<BattleQuestionFactoryItem, String>{

	List<BattleQuestionFactoryItem> findAllByUserIdAndIsDel(String userId, int isDel, Pageable pageable);

}
