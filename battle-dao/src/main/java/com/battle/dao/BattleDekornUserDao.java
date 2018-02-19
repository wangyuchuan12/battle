package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleDekornUser;

public interface BattleDekornUserDao extends CrudRepository<BattleDekornUser, String>{

	List<BattleDekornUser> findAllByDekornIdAndUserIdAndIsDel(String dekornId, String userId,Integer isDel);

	List<BattleDekornUser> findAllByUserIdAndIsDel(String userId, int isDel);

	List<BattleDekornUser> findAllByDekornIdAndIsDel(String dekornId, int isDel);

}
