package com.battle.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.battle.dao.BattleGroupConfigDao;
import com.battle.domain.BattleGroupConfig;
import com.wyc.common.service.RedisService;

@Service
public class BattleGroupConfigService {

	private final String  BATTLE_GROUP_CONFIG_KEY = "battle_group_config_key";
	
	final static Logger logger = LoggerFactory.getLogger(BattleGroupConfigService.class);
	@Autowired
	private BattleGroupConfigDao battleGroupConfigDao;

	private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	@Autowired
	private RedisService redisService;
	
	public List<BattleGroupConfig> findAllByCodeFromRedis(String code){
		String key = BATTLE_GROUP_CONFIG_KEY;
		key = key+"_"+code;
		try{
			readWriteLock.readLock().lock();
			
			List<BattleGroupConfig> battleGroupConfigs = redisService.getList(key);
			if(battleGroupConfigs!=null){
				return battleGroupConfigs;
			}
		}catch(Exception e){
			logger.error("BattleGroupConfigService的findAllByCodeFromRedis从redis获取BattleRoom对象失败，code为："+code);
		}finally{
			readWriteLock.readLock().unlock();
			
		}
		return null;
	}
	
	public void saveToRedis(String code ,List<BattleGroupConfig> battleGroupConfigs){
		try{
			readWriteLock.writeLock().lock();
			String key = BATTLE_GROUP_CONFIG_KEY;
			key = key+"_"+code;
			redisService.setList(key, battleGroupConfigs,BattleGroupConfig.class);
		}catch(Exception e){
			
		}finally{
			readWriteLock.writeLock().unlock();
		}
	}
	
	public List<BattleGroupConfig> findAllByCode(String code) {
		
		List<BattleGroupConfig> battleGroupConfigs = findAllByCodeFromRedis(code);
		if(battleGroupConfigs!=null){
			return battleGroupConfigs;
		}else{
			battleGroupConfigs =  battleGroupConfigDao.findAllByCode(code);
			saveToRedis(code, battleGroupConfigs);
			return battleGroupConfigs;
		}	
	}

	public void update(BattleGroupConfig battleGroupConfig) {
		
		List<BattleGroupConfig> battleGroupConfigs = findAllByCode(battleGroupConfig.getCode());
		List<BattleGroupConfig> battleGroupConfigs2 = new ArrayList<>();
		if(battleGroupConfigs!=null&&battleGroupConfigs.size()>0){
			for(BattleGroupConfig battleGroupConfig2:battleGroupConfigs){
				if(battleGroupConfig.getId().equals(battleGroupConfig2.getId())){
					battleGroupConfigs2.add(battleGroupConfig);
				}else{
					battleGroupConfigs2.add(battleGroupConfig2);
				}
			}
		}
		
		battleGroupConfig.setUpdateAt(new DateTime());
		
		battleGroupConfigDao.save(battleGroupConfig);
		
		saveToRedis(battleGroupConfig.getCode(), battleGroupConfigs2);
		
	}
}
