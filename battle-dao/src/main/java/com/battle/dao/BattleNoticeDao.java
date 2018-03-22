package com.battle.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.battle.domain.BattleNotice;

public interface BattleNoticeDao extends CrudRepository<BattleNotice, String>{

	
	@Query(value="from com.battle.domain.BattleNotice bn where bn.toUser=:userId and bn.type=:type and bn.roomId=:roomId and bn.isRead=:isRead group by memberId")
	List<BattleNotice> findAllByToUserAndTypeAndRoomIdAndIsReadGroupByMemberId(@Param("userId")String userId ,@Param("type")Integer type, @Param("roomId")String roomId, @Param("isRead")int isRead, Pageable pageable);

}
