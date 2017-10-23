package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.battle.domain.BattlePeriodMember;

public interface BattlePeriodMemberDao extends CrudRepository<BattlePeriodMember, String>{

	BattlePeriodMember findOneByBattleIdAndBattleUserIdAndPeriodIdAndRoomIdAndIsDel(String battleId, String battleUserId,
			String periodId,String roomId,Integer isDel);

	List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDel(String battleId, String periodId, String roomId,List<Integer> statuses,Integer isDel);

	BattlePeriodMember findOneByRoomIdAndBattleUserIdAndIsDel(String roomId,String battleUserId,Integer isDel);

	List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndRoomId(String battleId, String periodId, String roomId);

}
