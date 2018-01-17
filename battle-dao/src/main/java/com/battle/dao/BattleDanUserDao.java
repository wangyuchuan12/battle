package com.battle.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.battle.domain.BattleDanUser;

public interface BattleDanUserDao extends CrudRepository<BattleDanUser, String>{

	List<BattleDanUser> findAllByUserIdAndPointIdOrderByLevelAsc(String userId, String pointId);

	List<BattleDanUser> findAllByDanIdAndUserId(String danId, String userId);

	List<BattleDanUser> findAllByRoomId(String roomId);

	List<BattleDanUser> findAllByUserIdAndPointIdAndLevel(String userId, String pointId, int level);

	List<BattleDanUser> findAllByUserIdAndPointId(String userId, String pointId);

}
