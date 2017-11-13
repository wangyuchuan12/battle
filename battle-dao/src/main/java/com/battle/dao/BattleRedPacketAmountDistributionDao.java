package com.battle.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleRedPacketAmountDistribution;

public interface BattleRedPacketAmountDistributionDao extends CrudRepository<BattleRedPacketAmountDistribution, String>{

	BattleRedPacketAmountDistribution findOneByRedPacketIdAndStatusAndMemberId(String redPackId,Integer status, String memberId);

	List<BattleRedPacketAmountDistribution> findAllByRedPacketIdAndStatus(String redPackId, Integer status,
			Pageable pageable);

}
