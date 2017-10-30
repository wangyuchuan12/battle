package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleMemberLoveCoolingDao;
import com.battle.domain.BattleMemberLoveCooling;

@Service
public class BattleMemberLoveCoolingService {

	@Autowired
	private BattleMemberLoveCoolingDao battleMemberLoveCoolingDao;

	public BattleMemberLoveCooling findOneByBattleMemberId(String battleMemberId) {
		
		return battleMemberLoveCoolingDao.findOneByBattleMemberId(battleMemberId);
	}

	public void add(BattleMemberLoveCooling battleMemberLoveCooling) {
		
		battleMemberLoveCooling.setId(UUID.randomUUID().toString());
		battleMemberLoveCooling.setUpdateAt(new DateTime());
		battleMemberLoveCooling.setCreateAt(new DateTime());
		
		battleMemberLoveCoolingDao.save(battleMemberLoveCooling);
		
	}

	public void update(BattleMemberLoveCooling battleMemberLoveCooling) {
		
		battleMemberLoveCooling.setUpdateAt(new DateTime());
		
		battleMemberLoveCoolingDao.save(battleMemberLoveCooling);
		
	}

	public BattleMemberLoveCooling findOne(String id) {
		
		return battleMemberLoveCoolingDao.findOne(id);
	}
}
