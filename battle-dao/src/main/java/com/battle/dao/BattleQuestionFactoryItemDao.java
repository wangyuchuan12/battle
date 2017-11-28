package com.battle.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleQuestionFactoryItem;

public interface BattleQuestionFactoryItemDao extends CrudRepository<BattleQuestionFactoryItem, String>{

	List<BattleQuestionFactoryItem> findAllByUserIdAndStatusInAndIsDel(String userId,List<Integer> statuses, int isDel, Pageable pageable);

}
