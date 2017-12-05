package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleDanUserPassThrough;

public interface BattleDanUserPassThroughDao extends CrudRepository<BattleDanUserPassThrough, String>{

	BattleDanUserPassThrough findOneByBattleDanUserIdAndProjectId(String battleDanUserId, String projectId);

}
