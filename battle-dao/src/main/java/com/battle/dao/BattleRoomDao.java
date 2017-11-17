package com.battle.dao;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.battle.domain.BattleRoom;

public interface BattleRoomDao extends CrudRepository<BattleRoom, String>{

	List<BattleRoom> findAllByBattleIdAndPeriodIdAndOwner(String battleId, String periodId, String battleUserId);

	Page<BattleRoom> findAllByIsDisplayAndStatusIn(Integer isPublic,List<Integer> statuses,Pageable pageable);


	Page<BattleRoom> findAllByBattleIdAndStatusAndIsSearchAble(String battleId, Integer status, int isSearchAble,
			Pageable pageable);

	@Query("from com.battle.domain.BattleRoom br where  br.id in (select bpm.roomId from com.battle.domain.BattlePeriodMember bpm where bpm.userId=:userId and bpm.status=1 or bpm.status=2 order by bpm.takepartAt desc)")
	Page<BattleRoom> findAllByUserId(@Param("userId") String userId,Pageable pageable);

	Page<BattleRoom> findAll(Pageable pageable);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	BattleRoom findOne(String id);
	
}
