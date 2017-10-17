package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattlePeriodDao;
import com.battle.domain.BattlePeriod;

@Service
public class BattlePeriodService {

	@Autowired
	private BattlePeriodDao battlePeriodDao;

	public BattlePeriod findOneByBattleIdAndIndex(String battleId, Integer index) {
		
		return battlePeriodDao.findOneByBattleIdAndIndex(battleId,index);
	}

	public BattlePeriod findOne(String id) {
		
		return battlePeriodDao.findOne(id);
	}

	public List<BattlePeriod> findAllByBattleIdAndStatusAndIsPublicOrderByIndexAsc(String battleId,Integer status,Integer isPublic) {
		return battlePeriodDao.findAllByBattleIdAndStatusAndIsPublicOrderByIndexAsc(battleId,status,isPublic);
	}
	
	public List<BattlePeriod> findAllByBattleIdOrderByIndexAsc(String battleId){
		return battlePeriodDao.findAllByBattleIdOrderByIndexAsc(battleId);
	}

	public void update(BattlePeriod battlePeriod) {
		
		battlePeriod.setUpdateAt(new DateTime());
		battlePeriodDao.save(battlePeriod);
		
	}

	public void add(BattlePeriod battlePeriod) {
		
		battlePeriod.setId(UUID.randomUUID().toString());
		
		battlePeriod.setUpdateAt(new DateTime());
		battlePeriod.setCreateAt(new DateTime());
		
		battlePeriodDao.save(battlePeriod);
		
	}

	public List<BattlePeriod> findAllByBattleIdAndAuthorBattleUserIdAndStatus(String battleId, String battleUserId,Integer status) {
		
		return battlePeriodDao.findAllByBattleIdAndAuthorBattleUserIdAndStatus(battleId,battleUserId,status);
	}

	public BattlePeriod findOneByBattleIdAndAuthorBattleUserIdAndStatus(String battleId, String battleUserId,
			Integer status) {
		
		return battlePeriodDao.findOneByBattleIdAndAuthorBattleUserIdAndStatus(battleId,battleUserId,status);
	}

	public List<BattlePeriod> findAllByBattleIdAndStatusAndAuthorBattleUserIdAndIsPublicOrderByIndexAsc(String battleId,
			Integer status, String battleUserId, int isPublic) {
		
		return battlePeriodDao.findAllByBattleIdAndStatusAndAuthorBattleUserIdAndIsPublicOrderByIndexAsc(battleId,status,battleUserId,isPublic);
	}
}
