package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleRedpacket;

public interface BattleRedpacketDao extends CrudRepository<BattleRedpacket, String>{

	List<BattleRedpacket> findAllByIsRoomAndRoomIdOrderByHandTimeDesc(int isRoom, String roomId);

}
