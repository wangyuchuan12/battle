package com.battle.service;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.battle.dao.BattleRoomDao;
import com.battle.domain.BattleRoom;
import com.wyc.common.service.RedisService;

@Service
public class BattleRoomService {
	private final String  BATTLE_ROOM_KEY = "battle_room_key";
	final static Logger logger = LoggerFactory.getLogger(BattleRoomService.class);
	private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	@Autowired
	private BattleRoomDao battleRoomDao;
	@Autowired
	private RedisService redisService;
	public BattleRoom findOne(String id) {
		
		/*String key = BATTLE_ROOM_KEY;
		key = key+"_"+id;
		try{
			readWriteLock.readLock().lock();
			
			BattleRoom battleRoom = redisService.getObject(key, BattleRoom.class);
			if(battleRoom!=null){
				return battleRoom;
			}
		}catch(Exception e){
			logger.error("BattleRoomService的findOne从redis获取BattleRoom对象失败，从数据库中获取,roomId为："+id);
		}finally{
			readWriteLock.readLock().unlock();
			
		}
		
		
		try{
			redisService.setObject(key, BattleRoom.class);
		}catch(Exception e){
			logger.error("BattleRoomService的findOne方法存储BattleRoom对象到redis失败");
		}*/
		
		BattleRoom battleRoom = battleRoomDao.findOne(id);
		
		return battleRoom;
	}
	public void add(BattleRoom battleRoom) {
		/*try{
			readWriteLock.writeLock().lock();
			
			battleRoom.setId(UUID.randomUUID().toString());
			String key = BATTLE_ROOM_KEY;
			key = key+"_"+battleRoom.getId();
			battleRoom.setCreateAt(new DateTime());
			battleRoom.setUpdateAt(new DateTime());
			battleRoomDao.save(battleRoom);
			
			redisService.setObject(key,battleRoom);
		}catch(Exception e){
			logger.error("BattleRoomService的add方法存储BattleRoom对象到redis失败");
		}finally{
			readWriteLock.writeLock().unlock();
			
		}*/
		
		battleRoom.setId(UUID.randomUUID().toString());
		battleRoom.setCreateAt(new DateTime());
		battleRoom.setUpdateAt(new DateTime());
		battleRoomDao.save(battleRoom);
		
	}
	
	public void update(BattleRoom battleRoom) {
		/*String key = BATTLE_ROOM_KEY;
		key = key+"_"+battleRoom.getId();
		try{
			readWriteLock.writeLock().lock();
			battleRoom.setUpdateAt(new DateTime());
			battleRoomDao.save(battleRoom);
			redisService.setObject(key,battleRoom);
		}catch(Exception e){
			logger.error("BattleRoomService的update方法存储BattleRoom对象到redis失败");
		}finally{
			readWriteLock.writeLock().unlock();
			
		}*/
		
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
	public List<BattleRoom> findAllByIsDanRoomAndBattleIdAndPeriodIdAndStatusInAndStartTimeGreaterThan(int isDanRoom,String battleId,String periodId, List<Integer> statuses, DateTime now,Pageable pageable) {
		
		return battleRoomDao.findAllByIsDanRoomAndBattleIdAndPeriodIdAndStatusInAndStartTimeGreaterThan(isDanRoom,battleId,periodId,statuses,now,pageable);
	}
	
	public List<BattleRoom> findAllByIsDanRoomAndBattleIdAndPeriodIdAndStatusIn(int isDanRoom,String battleId,String periodId, List<Integer> statuses,Pageable pageable){
		
		return battleRoomDao.findAllByIsDanRoomAndBattleIdAndPeriodIdAndStatusIn(isDanRoom,battleId,periodId,statuses,pageable);
	}
	public List<BattleRoom> findAllByDekornIdAndStatusIn(String dekornId, List<Integer> statuses, Pageable pageable) {
		
		return battleRoomDao.findAllByDekornIdAndStatusIn(dekornId,statuses,pageable);
	}
	public List<BattleRoom> findAllByIsDanRoomAndStatusIn(int isDanRoom, List<Integer> statuses, Pageable pageable) {
		
		return battleRoomDao.findAllByIsDanRoomAndStatusIn(isDanRoom,statuses,pageable);
	}

}
