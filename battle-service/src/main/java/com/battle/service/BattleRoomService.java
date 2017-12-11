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
	public List<BattleRoom> findAllByBattleIdAndPeriodIdAndOwnerAndIsPk(String battleId, String periodId, String battleUserId,Integer isPk) {
		
		return battleRoomDao.findAllByBattleIdAndPeriodIdAndOwnerAndIsPk(battleId,periodId,battleUserId,isPk);
	}
	
	public List<BattleRoom> findAllByBattleIdAndPeriodIdAndOwner(String battleId, String periodId, String battleUserId) {
		
		return battleRoomDao.findAllByBattleIdAndPeriodIdAndOwner(battleId,periodId,battleUserId);
	}
	public Page<BattleRoom> findAllByIsDisplayAndStatusInAndIsDel(Integer isPublic , List<Integer> statuses ,Integer isDel,Pageable pageable) {
		return battleRoomDao.findAllByIsDisplayAndStatusInAndIsDel(isPublic , statuses,isDel,pageable);
	}

	public Page<BattleRoom> findAllByBattleIdAndStatusAndIsSearchAble(String battleId, Integer status, int isSearchAble,
			Pageable pageable) {
		return battleRoomDao.findAllByBattleIdAndStatusAndIsSearchAble(battleId,status,isSearchAble,pageable);
	}
	
	
	public Page<BattleRoom> findAllByBattleIdAndUserId(String battleId,String userId,Pageable pageable) {
		return battleRoomDao.findAllByBattleIdAndUserId(battleId,userId,pageable);
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
	public List<BattleRoom> findAllByBattleIdAndPeriodIdAndStatusAndIsPassThrough(String battleId, String periodId,
			Integer status, int isPassThrough, Pageable pageable) {
		
		return battleRoomDao.findAllByBattleIdAndPeriodIdAndStatusAndIsPassThrough(battleId,periodId,status,isPassThrough,pageable);
	}
	public List<BattleRoom> findAllByIsDanRoomAndStatus(int isDanRoom, Integer status,Pageable pageable) {
		
		return battleRoomDao.findAllByIsDanRoomAndStatus(isDanRoom,status,pageable);
	}
	public List<BattleRoom> findAllByIsDanRoomAndBattleIdAndPeriodIdAndStatusIn(int isDanRoom,String battleId,String periodId, List<Integer> statuses, Pageable pageable) {
		
		return battleRoomDao.findAllByIsDanRoomAndBattleIdAndPeriodIdAndStatusIn(isDanRoom,battleId,periodId,statuses,pageable);
	}

}
