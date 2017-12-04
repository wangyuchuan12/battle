package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleDanTask;

public interface BattleDanTaskDao extends CrudRepository<BattleDanTask, String>{

	List<BattleDanTask> findAllByDanIdOrderByIndexAsc(String danId);

}
