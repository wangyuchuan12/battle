package com.battle.service;

import java.util.List;
import java.util.UUID;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.battle.dao.BattleRoomGroupDao;
import com.battle.domain.BattleRoomGroup;

@Service
public class BattleRoomGroupService {

	@Autowired
	private BattleRoomGroupDao battleRoomGroupDao;

	public BattleRoomGroup findOneByTypeAndCreaterUserId(Integer type, String userId) {
		
		List<BattleRoomGroup> battleRoomGroups = battleRoomGroupDao.findAllByTypeAndCreaterUserIdAndIsDel(type,userId,0);
	
		BattleRoomGroup battleRoomGroup = null;
		if(battleRoomGroups.size()>0){
			battleRoomGroup = battleRoomGroups.get(0);
		}
		
		if(battleRoomGroups.size()>1){
			for(int i=1;i<battleRoomGroups.size();i++){
				BattleRoomGroup delBattleRoomGroup = battleRoomGroups.get(i);
				delBattleRoomGroup.setIsDel(1);
				update(delBattleRoomGroup);
			}
		}
		return battleRoomGroup;
	}

	public void add(BattleRoomGroup battleRoomGroup) {
		battleRoomGroup.setId(UUID.randomUUID().toString());
		battleRoomGroup.setUpdateAt(new DateTime());
		battleRoomGroup.setCreateAt(new DateTime());
		
		battleRoomGroupDao.save(battleRoomGroup);
		
	}

	public void update(BattleRoomGroup battleRoomGroup) {
		
		battleRoomGroup.setUpdateAt(new DateTime());
		
		battleRoomGroupDao.save(battleRoomGroup);
		
	}
}
