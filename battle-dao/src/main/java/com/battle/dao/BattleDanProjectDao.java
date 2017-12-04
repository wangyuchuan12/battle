package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleDanProject;

public interface BattleDanProjectDao extends CrudRepository<BattleDanProject, String>{

	List<BattleDanProject> findAllByDanIdOrderByIndexAsc(String danId);

}
