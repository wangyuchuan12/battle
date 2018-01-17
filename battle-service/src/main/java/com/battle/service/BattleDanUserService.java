package com.battle.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDanUserDao;
import com.battle.domain.BattleDanUser;

@Service
public class BattleDanUserService {

	@Autowired
	private BattleDanUserDao battleDanUserDao;

	public List<BattleDanUser> findAllByUserIdAndPointIdOrderByLevelAsc(String userId, String pointId) {
		
		List<BattleDanUser> battleDanUsers = battleDanUserDao.findAllByUserIdAndPointId(userId,pointId);
		
		Collections.sort(battleDanUsers, new Comparator<BattleDanUser>() {
			public int compare(BattleDanUser battleDanUser, BattleDanUser battleDanUser2) {  
                return battleDanUser.getLevel().compareTo(battleDanUser2.getLevel());
            }  
		});
		
		return battleDanUsers;
	}

	public void add(BattleDanUser battleDanUser) {
		
		battleDanUser.setId(UUID.randomUUID().toString());
		battleDanUser.setCreateAt(new DateTime());
		battleDanUser.setUpdateAt(new DateTime());
		battleDanUserDao.save(battleDanUser);
		
		battleDanUserDao.save(battleDanUser);
		
	}

	public void update(BattleDanUser battleDanUser) {
		
		battleDanUser.setUpdateAt(new DateTime());
		
		battleDanUserDao.save(battleDanUser);
		
	}

	public BattleDanUser findOne(String id) {
		
		return battleDanUserDao.findOne(id);
	}

	public List<BattleDanUser> findAllByDanIdAndUserId(String danId, String userId) {
		return battleDanUserDao.findAllByDanIdAndUserId(danId,userId);
	}

	public List<BattleDanUser> findAllByRoomId(String roomId) {
		
		return battleDanUserDao.findAllByRoomId(roomId);
	}

	public BattleDanUser findOneByUserIdAndPointIdAndLevel(String userId, String pointId, int level) {
		
		List<BattleDanUser> battleDanUsers = battleDanUserDao.findAllByUserIdAndPointIdAndLevel(userId,pointId,level);
		
		BattleDanUser battleDanUser = null;
		if(battleDanUsers!=null&&battleDanUsers.size()>0){
			battleDanUser = battleDanUsers.get(0);
		}
		
		if(battleDanUsers!=null&&battleDanUsers.size()>1){
			for(int i =1;i<battleDanUsers.size();i++){
				BattleDanUser battleDanUser2 = battleDanUsers.get(i);
				battleDanUser2.setIsDel(1);
				battleDanUserDao.save(battleDanUser2);
			}
		}
		return battleDanUser;
	}
}
