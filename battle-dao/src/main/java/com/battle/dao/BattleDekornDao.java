package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleDekorn;

public interface BattleDekornDao extends CrudRepository<BattleDekorn, String>{

	List<BattleDekorn> findAllByIsDel(int isDel);

}
