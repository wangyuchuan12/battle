package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattlePeriod;

public interface BattlePeriodDao extends CrudRepository<BattlePeriod, String>{

	BattlePeriod findOneByBattleIdAndIndex(String battleId, Integer index);

	List<BattlePeriod> findAllByBattleIdAndStatusAndIsPublicOrderByIndexAsc(String battleId,Integer status,Integer isPublic);

	List<BattlePeriod> findAllByBattleIdOrderByIndexAsc(String battleId);

	List<BattlePeriod> findAllByBattleIdAndAuthorBattleUserIdAndStatus(String battleId, String battleUserId,Integer status);

	BattlePeriod findOneByBattleIdAndAuthorBattleUserIdAndStatus(String battleId, String battleUserId, Integer status);

	List<BattlePeriod> findAllByBattleIdAndStatusAndAuthorBattleUserIdAndIsPublicOrderByIndexAsc(String battleId,
			Integer status, String battleUserId, int isPublic);

}
