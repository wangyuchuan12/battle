package com.battle.dao;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import com.battle.domain.BattleDanUser;

public interface BattleDanUserDao extends CrudRepository<BattleDanUser, String>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<BattleDanUser> findAllByUserIdAndPointIdOrderByLevelAsc(String userId, String pointId);

	BattleDanUser findOneByDanIdAndUserId(String danId, String userId);

	List<BattleDanUser> findAllByRoomId(String roomId);

	BattleDanUser findOneByUserIdAndPointIdAndLevel(String userId, String pointId, int level);

}
