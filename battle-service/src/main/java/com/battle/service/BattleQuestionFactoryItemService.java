package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleQuestionFactoryItemDao;
import com.battle.domain.BattleQuestionFactoryItem;

@Service
public class BattleQuestionFactoryItemService {

	@Autowired
	private BattleQuestionFactoryItemDao battleQuestionFactoryItemDao;

	public void add(BattleQuestionFactoryItem questionTarget) {
		
		questionTarget.setId(UUID.randomUUID().toString());
		questionTarget.setUpdateAt(new DateTime());
		questionTarget.setCreateAt(new DateTime());
		
		battleQuestionFactoryItemDao.save(questionTarget);
		
	}

	public List<BattleQuestionFactoryItem> findAllByUserIdAndStatusInAndIsDel(String userId,List<Integer> statuses, int isDel,Pageable pageable) {
		
		return battleQuestionFactoryItemDao.findAllByUserIdAndStatusInAndIsDel(userId,statuses,isDel,pageable);
	}

	public void update(BattleQuestionFactoryItem battleQuestionFactoryItem) {
		
		battleQuestionFactoryItem.setUpdateAt(new DateTime());;
		battleQuestionFactoryItemDao.save(battleQuestionFactoryItem);
		
	}

	public List<BattleQuestionFactoryItem> findAllByBattleIdAndStatusRandom(String battleId,int status, Pageable pageable) {
		
		return battleQuestionFactoryItemDao.findAllByBattleIdAndStatusRandom(battleId,status,pageable);
	}

	public BattleQuestionFactoryItem findOne(String id) {
		
		return battleQuestionFactoryItemDao.findOne(id);
	}
}
