package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleGiftDao;
import com.battle.domain.BattleGift;

@Service
public class BattleGiftService {

	@Autowired
	private BattleGiftDao battleGiftDao;

	public void add(BattleGift battleGift) {
		
		battleGift.setId(UUID.randomUUID().toString());
		
		battleGift.setUpdateAt(new DateTime());
		battleGift.setCreateAt(new DateTime());
		
		battleGiftDao.save(battleGift);
	}

	public List<BattleGift> findAllByAccountIdAndLevel(String accountId, int level) {
		
		return battleGiftDao.findAllByAccountIdAndLevel(accountId,level);
	}

	public void update(BattleGift battleGift) {
		
		battleGift.setUpdateAt(new DateTime());
		
		battleGiftDao.save(battleGift);
		
	}

	public List<BattleGift> findAllByAccountIdAndLevelAndIsReceive(String accountId, int level, int isReceive) {
		
		return battleGiftDao.findAllByAccountIdAndLevelAndIsReceive(accountId,level,isReceive);
	}

	public void setIsReceive(int isReceive) {
		
		battleGiftDao.setIsReceive(isReceive);
		
	}
}
