package com.battle.service.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.BattlePk;
import com.battle.service.BattlePkService;
import com.battle.service.redis.BattlePkRedisService;

@Service
public class BattlePkImmediateService {

	@Autowired
	private BattlePkRedisService battlePkRedisService;
	
	@Autowired
	private BattlePkService battlePkService;
	
	public BattlePk immediateUntilStatusChange(String id)throws Exception{
		BattlePk battlePk = null;
		
		try{
			battlePk = battlePkRedisService.get(id);
		}catch(Exception e){
			
		}
		
		
		if(battlePk==null){
			battlePk = battlePkService.findOne(id);
			
			battlePkRedisService.set(battlePk);
		}
		
		Integer beatStatus = battlePk.getBeatStatus();
		
		Integer homeStatus = battlePk.getHomeStatus();
		
		Integer roomStatus = battlePk.getRoomStatus();
		
		
		
		for(Integer i = 0;i<30;i++){
			battlePk = battlePkRedisService.get(id);
			if(!battlePk.getBeatStatus().equals(beatStatus)||
					!battlePk.getHomeStatus().equals(homeStatus)||
					!battlePk.getRoomStatus().equals(roomStatus)){
				return battlePk;
			}else{
				
				Thread.sleep(1000);
			}
		}
		
		return battlePk;
		
	}
}
