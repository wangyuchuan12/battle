package com.battle.service;

import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattlePkDao;
import com.battle.domain.BattlePk;
import com.battle.domain.BattleRoom;
import com.wyc.common.service.RedisService;

@Service
public class BattlePkService {

	private final String  BATTLE_PK_KEY = "battle_pk_key";
	
	final static Logger logger = LoggerFactory.getLogger(BattlePkService.class);
	
	private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	@Autowired
	private BattlePkDao battlePkDao;
	
	@Autowired
	private RedisService redisService; 

	public BattlePk findOneByHomeUserId(String homeUserId) {
		
		
		System.out.println("........................homeUserId:"+homeUserId);
		return battlePkDao.findOneByHomeUserId(homeUserId);
	}

	public void add(BattlePk battlePk) {
		
		/*
		try{
			readWriteLock.writeLock().lock();
			
			battlePk.setId(UUID.randomUUID().toString());
			String key = BATTLE_PK_KEY;
			key = key+"_"+battlePk.getId();
			battlePk.setCreateAt(new DateTime());
			battlePk.setUpdateAt(new DateTime());
			battlePkDao.save(battlePk);
			redisService.setObject(key,battlePk);
		}catch(Exception e){
			logger.error("BattlePkService的add方法存储BattlePk对象到redis失败");
		}finally{
			readWriteLock.writeLock().unlock();
			
		}*/
		
		battlePk.setId(UUID.randomUUID().toString());
		battlePk.setCreateAt(new DateTime());
		battlePk.setUpdateAt(new DateTime());
		battlePkDao.save(battlePk);
	}

	public BattlePk findOne(String id) {
		/*
		String key = BATTLE_PK_KEY;
		key = key+"_"+id;
		try{
			readWriteLock.readLock().lock();
			
			BattlePk battlePk = redisService.getObject(key, BattlePk.class);
			if(battlePk!=null){
				return battlePk;
			}
		}catch(Exception e){
			logger.error("BattlePkService的findOne从redis获取BattleRoom对象失败，从数据库中获取,id为："+id);
		}finally{
			readWriteLock.readLock().unlock();
			
		}
		
		BattlePk battlePk = battlePkDao.findOne(id);
		try{
			redisService.setObject(key, BattleRoom.class);
		}catch(Exception e){
			logger.error("BattlePkService的findOne方法存储BattlePk对象到redis失败");
		}*/
		BattlePk battlePk = battlePkDao.findOne(id);
		return battlePk;
		
	}

	public void update(BattlePk battlePk) {
		
		/*
		String key = BATTLE_PK_KEY;
		key = key+"_"+battlePk.getId();
		try{
			readWriteLock.writeLock().lock();
			battlePk.setUpdateAt(new DateTime());
			battlePkDao.save(battlePk);
			redisService.setObject(key,battlePk);
		}catch(Exception e){
			logger.error("BattlePkService的update方法存储BattlePk对象到redis失败");
		}finally{
			readWriteLock.writeLock().unlock();
			
		}*/
		
		battlePk.setUpdateAt(new DateTime());
		battlePkDao.save(battlePk);
	}
}
