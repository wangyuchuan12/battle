package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleGiftModelDao;
import com.battle.domain.BattleGiftModel;

@Service
public class BattleGiftModelService {

	@Autowired
	private BattleGiftModelDao battleGiftModelDao;

	public List<BattleGiftModel> findAllByIsDel(int isDel) {
		
		return battleGiftModelDao.findAllByIsDel(isDel);
	}
}
