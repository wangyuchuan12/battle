package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.battle.domain.BattlePeriodMember;

public interface BattlePeriodMemberDao extends CrudRepository<BattlePeriodMember, String>{

	BattlePeriodMember findOneByBattleIdAndBattleUserIdAndPeriodIdAndRoomIdAndIsDel(String battleId, String battleUserId,
			String periodId,String roomId,Integer isDel);

	List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDel(String battleId, String periodId, String roomId,List<Integer> statuses,Integer isDel);

	BattlePeriodMember findOneByRoomIdAndIsDel(String roomId, Integer isDel);

}