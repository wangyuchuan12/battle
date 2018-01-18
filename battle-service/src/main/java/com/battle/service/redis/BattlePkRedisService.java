package com.battle.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.BattlePk;
import com.wyc.common.service.RedisService;

@Service
public class BattlePkRedisService {

	@Autowired
	private RedisService redisService;
	
	private static String sigleKey = "battle_pk";
	
	public void set(BattlePk battlePk)throws Exception{
		redisService.setObject(sigleKey+"_"+battlePk.getId(), battlePk);
	}
	
	public BattlePk get(String id)throws Exception{
		
		return redisService.getObject(sigleKey+"_"+id, BattlePk.class);
	}
	
}
