package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDanTaskUserDao;
import com.battle.domain.BattleDanTaskUser;

@Service
public class BattleDanTaskUserService {

	@Autowired
	private BattleDanTaskUserDao battleDanTaskUserDao;

	public List<BattleDanTaskUser> findAllByDanIdAndUserIdOrderByIndexAsc(String danId,String userId) {
	
		return battleDanTaskUserDao.findAllByDanIdAndUserIdOrderByIndexAsc(danId,userId);
	}

	public void add(BattleDanTaskUser battleDanTaskUser) {
		
		battleDanTaskUser.setId(UUID.randomUUID().toString());
		battleDanTaskUser.setUpdateAt(new DateTime());
		battleDanTaskUser.setCreateAt(new DateTime());
		battleDanTaskUserDao.save(battleDanTaskUser);
		
	}

	public BattleDanTaskUser findOneByRoomIdAndUserId(String roomId,String userId) {
		
		return battleDanTaskUserDao.findOneByRoomIdAndUserId(roomId,userId);
	}

	public void update(BattleDanTaskUser battleDanTaskUser) {
		
		battleDanTaskUser.setUpdateAt(new DateTime());
		
		battleDanTaskUserDao.save(battleDanTaskUser);
		
	}

	public BattleDanTaskUser findOneByDanIdAndProjectIdAndUserIdAndType(String danId, String projectId, String userId,Integer type) {
		
		return battleDanTaskUserDao.findOneByDanIdAndProjectIdAndUserIdAndType(danId,projectId,userId,type);
	}

	public BattleDanTaskUser findOne(String id) {
		
		return battleDanTaskUserDao.findOne(id);
	}
}
