package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleMessage;

public interface BattleMessageDao extends CrudRepository<BattleMessage, String>{

}
