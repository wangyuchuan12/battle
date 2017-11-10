package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleRedPacketAmountDistributionDao;
import com.battle.domain.BattleRedPacketAmountDistribution;

@Service
public class BattleRedPacketAmountDistributionService {

	@Autowired
	private BattleRedPacketAmountDistributionDao battleRedPacketAmountDistributionDao;

	public void add(BattleRedPacketAmountDistribution battleRedPacketAmountDistribution) {
		
		battleRedPacketAmountDistribution.setId(UUID.randomUUID().toString());
		
		battleRedPacketAmountDistribution.setUpdateAt(new DateTime());
		
		battleRedPacketAmountDistribution.setCreateAt(new DateTime());
		
		battleRedPacketAmountDistributionDao.save(battleRedPacketAmountDistribution);
		
	}
}
