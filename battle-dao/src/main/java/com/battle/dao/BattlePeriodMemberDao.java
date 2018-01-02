package com.battle.dao;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import com.battle.domain.BattlePeriodMember;

public interface BattlePeriodMemberDao extends CrudRepository<BattlePeriodMember, String>{

	BattlePeriodMember findOneByBattleIdAndBattleUserIdAndPeriodIdAndRoomIdAndIsDel(String battleId, String battleUserId,
			String periodId,String roomId,Integer isDel);

	List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDelOrderByCreateAtAsc(String battleId, String periodId, String roomId,List<Integer> statuses,Integer isDel);

	BattlePeriodMember findOneByRoomIdAndBattleUserIdAndIsDel(String roomId,String battleUserId,Integer isDel);

	List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndRoomIdOrderByCreateAtAsc(String battleId, String periodId, String roomId);

	List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndRoomId(String battleId, String periodId, String roomId,
			Pageable pageable);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	BattlePeriodMember findOne(String id);

}
