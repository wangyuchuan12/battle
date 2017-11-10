package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleRedPacketType;
import com.battle.domain.BattleRedpacket;

public interface BattleRedPacketTypeDao extends CrudRepository<BattleRedPacketType, String>{

	List<BattleRedpacket> findAllByIsDel(int isDel);

}
