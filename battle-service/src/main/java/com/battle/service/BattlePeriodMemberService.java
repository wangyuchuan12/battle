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

	public BattlePeriodMember findOneByBattleIdAndBattleUserIdAndPeriodIdAndRoomIdAndIsDel(String battleId, String battleUserId,
			String periodId,String roomId,Integer isDel) {
		return battlePeriodMemberDao.findOneByBattleIdAndBattleUserIdAndPeriodIdAndRoomIdAndIsDel(battleId,battleUserId,periodId,roomId,isDel);
	}
	
	public BattlePeriodMember findOneByRoomIdAndIsDel(String roomId,Integer isDel){
		return battlePeriodMemberDao.findOneByRoomIdAndIsDel(roomId,isDel);
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

	public List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDel(String battleId, String periodId, String roomId,List<Integer> statuses,Integer isDel) {
		
		return battlePeriodMemberDao.findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDel(battleId,periodId,roomId,statuses,isDel);
		
	}

	public BattlePeriodMember findOne(String id) {
		
		return battlePeriodMemberDao.findOne(id);
	}
}
