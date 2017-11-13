package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

	public BattleRedPacketAmountDistribution findOneByRedPacketIdAndStatusAndMemberId(String redPackId ,Integer status, String memberId) {
		
		return battleRedPacketAmountDistributionDao.findOneByRedPacketIdAndStatusAndMemberId(redPackId,status,memberId);
	}

	public List<BattleRedPacketAmountDistribution> findAllByRedPacketIdAndStatus(String redPackId, Integer status,Pageable pageable) {
		
		return battleRedPacketAmountDistributionDao.findAllByRedPacketIdAndStatus(redPackId,status,pageable);
	}

	public void update(BattleRedPacketAmountDistribution battleRedPacketAmountDistribution) {
		
		battleRedPacketAmountDistribution.setUpdateAt(new DateTime());
		
		battleRedPacketAmountDistributionDao.save(battleRedPacketAmountDistribution);
		
	}
}
