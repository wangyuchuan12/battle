package com.battle.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleNotice;

public interface BattleNoticeDao extends CrudRepository<BattleNotice, String>{

	Page<BattleNotice> findAllByUserIdAndTypeAndRoomIdAndIsRead(String userId ,Integer type, String roomId, int isRead, Pageable pageable);

}
