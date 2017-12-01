package com.battle.service.other;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.battle.domain.Battle;
import com.battle.domain.BattleRoom;

@Service
public class BattleRoomHandleService {
	
	public BattleRoom initRoom(Battle battle){
		BattleRoom battleRoom = new BattleRoom();
		battleRoom.setBattleId(battle.getId());
		
		battleRoom.setCreationTime(new DateTime());
		battleRoom.setName(battle.getName());
		battleRoom.setInstruction(battle.getInstruction());
		battleRoom.setImgUrl(battle.getHeadImg());
		battleRoom.setStatus(BattleRoom.STATUS_FREE);
		battleRoom.setNum(0);
		
		battleRoom.setIsDisplay(0);
		
		battleRoom.setSpeedCoolBean(10);
		battleRoom.setSpeedCoolSecond(10);
		
		battleRoom.setRedPackNum(0);
		
		battleRoom.setRoomScore(0);
		
		battleRoom.setFullRightAddScore(10);
		
		battleRoom.setRightAddProcess(1);
		
		battleRoom.setRightAddScore(1);
		
		battleRoom.setWrongSubScore(1);
		
		battleRoom.setIsRedpack(0);
		
		battleRoom.setRedpackAmount(new BigDecimal(0));
		
		battleRoom.setRedpackBean(0);
		
		battleRoom.setRedpackMasonry(0);
		
		battleRoom.setCostBean(0);
		
		battleRoom.setCostMasonry(0);
		
		battleRoom.setHot(0);
		
		battleRoom.setIsDel(0);
		
		
		return battleRoom;
	}
}
