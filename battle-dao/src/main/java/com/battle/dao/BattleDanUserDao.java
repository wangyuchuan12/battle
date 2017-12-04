package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.battle.domain.BattleDanUser;

public interface BattleDanUserDao extends CrudRepository<BattleDanUser, String>{

	List<BattleDanUser> findAllByUserIdAndPointIdOrderByLevelAsc(String userId, String pointId);

	BattleDanUser findOneByDanIdAndUserId(String danId, String userId);

}
