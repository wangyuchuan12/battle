package com.battle.service;
import java.util.List;
import java.util.UUID;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleRoomDao;
import com.battle.domain.BattleRoom;

@Service
public class BattleRoomService {

	@Autowired
	private BattleRoomDao battleRoomDao;
	public BattleRoom findOne(String id) {
		
		return battleRoomDao.findOne(id);
	}
	public void add(BattleRoom battleRoom) {
		
		battleRoom.setId(UUID.randomUUID().toString());
		battleRoom.setCreateAt(new DateTime());
		battleRoom.setUpdateAt(new DateTime());
		battleRoomDao.save(battleRoom);
		
	}
	public List<BattleRoom> findAllByBattleIdAndPeriodIdAndOwner(String battleId, String periodId, String battleUserId) {
		
		return battleRoomDao.findAllByBattleIdAndPeriodIdAndOwner(battleId,periodId,battleUserId);
	}
	public Page<BattleRoom> findAllByIsDisplayAndStatusIn(Integer isPublic , List<Integer> statuses ,Pageable pageable) {
		return battleRoomDao.findAllByIsDisplayAndStatusIn(isPublic , statuses, pageable);
	}

	public Page<BattleRoom> findAllByBattleIdAndStatusAndIsSearchAble(String battleId, Integer status, int isSearchAble,
			Pageable pageable) {
		return battleRoomDao.findAllByBattleIdAndStatusAndIsSearchAble(battleId,status,isSearchAble,pageable);
	}
	public Page<BattleRoom> findAllByUserId(String userId,Pageable pageable) {
		return battleRoomDao.findAllByUserId(userId,pageable);
	}
	public void update(BattleRoom battleRoom) {
		
		battleRoom.setUpdateAt(new DateTime());
		
		battleRoomDao.save(battleRoom);
		
	}
	public Page<BattleRoom> findAll(Pageable pageable) {
		
		return battleRoomDao.findAll(pageable);
	}

}
