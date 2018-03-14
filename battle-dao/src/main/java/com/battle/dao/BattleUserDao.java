package com.battle.dao;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleUser;

public interface BattleUserDao extends CrudRepository<BattleUser, String>{

	@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	BattleUser findOneByUserIdAndBattleId(String userId, String battleId);

}
