package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleDanPoint;

public interface BattleDanPointDao extends CrudRepository<BattleDanPoint, String>{

	List<BattleDanPoint> findAllByIsRun(int isRun);

}
