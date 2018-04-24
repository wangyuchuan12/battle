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
		
		battleRoom.setSpeedCoolBean(60);
		battleRoom.setSpeedCoolSecond(60);
		
		battleRoom.setRedPackNum(0);
		
		battleRoom.setRoomScore(0);
		
		battleRoom.setFullRightAddScore(10);
		
		battleRoom.setRight1AddProcess(1);
		
		battleRoom.setRight2AddProcess(2);
		
		battleRoom.setRight3AddProcess(5);
		
		battleRoom.setRight4AddProcess(10);
		
		battleRoom.setRight5AddProcess(15);
		
		battleRoom.setRight6AddProcess(20);
		
		battleRoom.setRightAddScore(5);
		
		battleRoom.setWrongSubScore(2);
		
		battleRoom.setIsRedpack(0);
		
		battleRoom.setRedpackAmount(new BigDecimal(0));
		
		battleRoom.setRedpackBean(0);
		
		battleRoom.setRedpackMasonry(0);
		
		battleRoom.setCostBean(0);
		
		battleRoom.setCostMasonry(0);
		
		battleRoom.setHot(0);
		
		battleRoom.setIsDel(0);
		
		battleRoom.setEndEnable(1);
		
		battleRoom.setScrollGogal(100);
		
		battleRoom.setIsDekorn(0);
		
		battleRoom.setIsIncrease(1);
		
		battleRoom.setIsInit(0);
		
		battleRoom.setMaxIndex(0);
		
		battleRoom.setIsEndHandle(0);
		
		battleRoom.setIsStart(0);
		
		return battleRoom;
	}
}
