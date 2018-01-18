package com.battle.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDanUserDao;
import com.battle.domain.BattleDanUser;
import com.battle.domain.BattlePeriodMember;
import com.wyc.common.service.RedisService;

@Service
public class BattleDanUserService {
	
	private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	private final String  BATTLE_DAN_USERS_BY_USERID_POINTID_KEY = "key1";
	
	final static Logger logger = LoggerFactory.getLogger(BattleDanUserService.class);
	@Autowired
	private RedisService redisService;

	@Autowired
	private BattleDanUserDao battleDanUserDao;
	
	public void saveToRedisByUserIdAndPointId(String userId,String pointId,List<BattleDanUser> battleDanUsers){
		try{
			readWriteLock.writeLock().lock();
			String key = BATTLE_DAN_USERS_BY_USERID_POINTID_KEY;
			key = key+"_"+userId+"_"+pointId;
			redisService.setList(key, battleDanUsers,BattleDanUser.class);
		}catch(Exception e){
			logger.error("缓存List<BattleDanUser>到redis出错");
		}finally{
			readWriteLock.writeLock().unlock();
		}
		
	}
	
	public List<BattleDanUser> findBattleDanUsersByUserIdAndPointId(String userId,String pointId){
		
		try{
			readWriteLock.readLock().lock();
			String key =  BATTLE_DAN_USERS_BY_USERID_POINTID_KEY;;
			key = key+"_"+userId+"_"+pointId;
			List<BattleDanUser> battleDanUsers = redisService.getList(key);
			if(battleDanUsers!=null&&battleDanUsers.size()>0){
				Collections.sort(battleDanUsers, new Comparator<BattleDanUser>() {
					public int compare(BattleDanUser battleDanUser, BattleDanUser battleDanUser2) {  
		                return battleDanUser.getLevel().compareTo(battleDanUser2.getLevel());
		            }  
				});
			}
			return battleDanUsers;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("从redis得到List<BattleDanUser>数据出错");
		}finally{
			readWriteLock.readLock().unlock();
			
		}
		return null;
		
	}

	public List<BattleDanUser> findAllByUserIdAndPointIdOrderByLevelAsc(String userId, String pointId) {
		
		List<BattleDanUser> battleDanUsers = findBattleDanUsersByUserIdAndPointId(userId, pointId);
	
		
		if(battleDanUsers!=null&&battleDanUsers.size()>0){
			return battleDanUsers;
		}
				
		battleDanUsers = battleDanUserDao.findAllByUserIdAndPointId(userId,pointId);
		
		Collections.sort(battleDanUsers, new Comparator<BattleDanUser>() {
			public int compare(BattleDanUser battleDanUser, BattleDanUser battleDanUser2) {  
                return battleDanUser.getLevel().compareTo(battleDanUser2.getLevel());
            }  
		});
		
		saveToRedisByUserIdAndPointId(userId, pointId, battleDanUsers);
		
		return battleDanUsers;
		
	}

	public void add(BattleDanUser battleDanUser) {
		
		battleDanUser.setId(UUID.randomUUID().toString());
		battleDanUser.setCreateAt(new DateTime());
		battleDanUser.setUpdateAt(new DateTime());		
		battleDanUserDao.save(battleDanUser);
	}

	public void update(BattleDanUser battleDanUser) {
		battleDanUser.setUpdateAt(new DateTime());
		battleDanUserDao.save(battleDanUser);
		List<BattleDanUser> battleDanUsers = findAllByUserIdAndPointIdOrderByLevelAsc(battleDanUser.getUserId(), battleDanUser.getPointId());
		
		List<BattleDanUser> battleDanUsers2 = new ArrayList<>();
		
		for(BattleDanUser battleDanUser2:battleDanUsers){
			if(battleDanUser2.getId().equals(battleDanUser.getId())){
				battleDanUsers2.add(battleDanUser);
			}else{
				battleDanUsers2.add(battleDanUser2);
			}
		}
		
		saveToRedisByUserIdAndPointId(battleDanUser.getUserId(), battleDanUser.getPointId(), battleDanUsers2);
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
