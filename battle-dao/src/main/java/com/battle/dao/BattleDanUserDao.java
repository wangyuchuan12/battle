package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.battle.domain.BattleDanUser;

public interface BattleDanUserDao extends CrudRepository<BattleDanUser, String>{

	List<BattleDanUser> findAllByUserIdAndPointIdOrderByLevelAsc(String userId, String pointId);

	BattleDanUser findOneByDanIdAndUserId(String danId, String userId);

	List<BattleDanUser> findAllByRoomId(String roomId);

	BattleDanUser findOneByUserIdAndPointIdAndLevel(String userId, String pointId, int level);

}
