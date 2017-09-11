package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattlePeriod;

public interface BattlePeriodStageDao extends CrudRepository<BattlePeriod, String>{

}
