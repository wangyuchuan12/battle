package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleDan;

public interface BattleDanDao extends CrudRepository<BattleDan, String>{

	List<BattleDan> findAllByPointIdOrderByLevelAsc(String pointId);

	BattleDan findOneByPointIdAndLevel(String pointId, int level);

}
