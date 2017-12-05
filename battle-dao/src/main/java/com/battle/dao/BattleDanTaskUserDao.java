package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleDanTaskUser;

public interface BattleDanTaskUserDao extends CrudRepository<BattleDanTaskUser, String>{

	List<BattleDanTaskUser> findAllByDanIdAndUserIdOrderByIndexAsc(String danId, String userId);

	BattleDanTaskUser findOneByRoomIdAndUserId(String roomId,String userId);

	BattleDanTaskUser findOneByDanIdAndProjectIdAndUserIdAndType(String danId, String projectId, String userId,Integer type);

}
