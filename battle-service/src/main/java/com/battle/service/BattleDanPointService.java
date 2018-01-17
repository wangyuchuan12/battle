package com.battle.service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDanPointDao;
import com.battle.domain.BattleDanPoint;
import com.wyc.common.service.RedisService;

@Service
public class BattleDanPointService {

	private final String  BATTLE_DAN_POINT_KEY = "battle_dan_point_key";
	@Autowired
	private BattleDanPointDao battleDanPointDao;
	
	final static Logger logger = LoggerFactory.getLogger(BattleDanPointService.class);
	@Autowired
	private RedisService redisService;

	public List<BattleDanPoint> findAllByIsRun(int isRun) {
		
		String key = BATTLE_DAN_POINT_KEY;
		try{
			
			List<BattleDanPoint> battleDanPoints = redisService.getList(key);
			
			if(battleDanPoints!=null){
				return battleDanPoints;
			}
			
		}catch(Exception e){
			logger.error("BattleDanPointService从缓存获取数据出错，只能从数据库中获取");
		}finally{
			
		}
		try{
			List<BattleDanPoint> battleDanPoints = battleDanPointDao.findAllByIsRun(isRun);
			redisService.setList(key, battleDanPoints, BattleDanPoint.class);
			return battleDanPoints;
		}catch(Exception e){
			logger.error("BattleDanPointService存入缓存出错");
		}
		return null;
	}
}
