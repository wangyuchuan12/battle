package com.battle.service;
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
	public BattleRoom findOneByBattleIdAndPeriodIdAndOwner(String battleId, String periodId, String battleUserId) {
		
		return battleRoomDao.findOneByBattleIdAndPeriodIdAndOwner(battleId,periodId,battleUserId);
	}
	public Page<BattleRoom> findAllByIsPublicOrderByCreationTimeAsc(Integer isPublic , Pageable pageable) {
		return battleRoomDao.findAllByIsPublicOrderByCreationTimeAsc(isPublic , pageable);
	}

	public Page<BattleRoom> findAllByBattleIdAndStatusAndIsPublic(String battleId, Integer status, int isPublic,
			Pageable pageable) {
		return battleRoomDao.findAllByBattleIdAndStatusAndIsPublic(battleId,status,isPublic,pageable);
	}
	public Page<BattleRoom> findAllByUserId(String userId,Pageable pageable) {
		return battleRoomDao.findAllByUserId(userId,pageable);
	}
	public void update(BattleRoom battleRoom) {
		
		battleRoom.setUpdateAt(new DateTime());
		
		battleRoomDao.save(battleRoom);
		
	}

}
