package com.battle.dao;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleRoomGroup;

public interface BattleRoomGroupDao extends CrudRepository<BattleRoomGroup, String>{


	List<BattleRoomGroup> findAllByTypeAndCreaterUserIdAndIsDel(Integer type, String userId,Integer isDel);

}
