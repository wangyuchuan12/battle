package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.battle.domain.BattlePeriodMember;

public interface BattlePeriodMemberDao extends CrudRepository<BattlePeriodMember, String>{

	BattlePeriodMember findOneByBattleIdAndBattleUserIdAndPeriodId(String battleId, String battleUserId,
			String periodId);

	List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndStatusIn(String battleId, String periodId, List<Integer> statuses);

}
