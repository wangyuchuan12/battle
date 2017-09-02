package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattlePeriodMemberDao;
import com.battle.domain.BattlePeriodMember;

@Service
public class BattlePeriodMemberService {

	@Autowired
	private BattlePeriodMemberDao battlePeriodMemberDao;

	public BattlePeriodMember findOneByBattleIdAndBattleUserIdAndPeriodId(String battleId, String battleUserId,
			String periodId) {
		return battlePeriodMemberDao.findOneByBattleIdAndBattleUserIdAndPeriodId(battleId,battleUserId,periodId);
	}

	public void add(BattlePeriodMember battlePeriodMember) {
		
		battlePeriodMember.setId(UUID.randomUUID().toString());
		battlePeriodMember.setUpdateAt(new DateTime());
		battlePeriodMember.setCreateAt(new DateTime());
		
		battlePeriodMemberDao.save(battlePeriodMember);
		
	}

	public void update(BattlePeriodMember battlePeriodMember) {
		
		battlePeriodMember.setUpdateAt(new DateTime());
		
		battlePeriodMemberDao.save(battlePeriodMember);
		
	}

	public List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndStatus(String battleId, String periodId, Integer status) {
		
		return battlePeriodMemberDao.findAllByBattleIdAndPeriodIdAndStatus(battleId,periodId,status);
		
	}
}
