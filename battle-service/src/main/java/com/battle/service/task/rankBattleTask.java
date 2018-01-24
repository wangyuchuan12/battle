package com.battle.service.task;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.battle.domain.Battle;
import com.battle.domain.BattleGroupConfig;
import com.battle.domain.BattleRoom;
import com.battle.service.BattleGroupConfigService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleService;
import com.battle.service.other.BattleRoomHandleService;
import com.wyc.common.util.CommonUtil;
@Service
public class rankBattleTask {
	
	@Autowired
	private BattleGroupConfigService battleGroupConfigService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private BattleRoomHandleService battleRoomHandleService;
	
	@Autowired
	private BattleService battleService;
	
	@Scheduled(cron = "0 0 22 * * ?")
	public void battleRoomEnd(){
		List<BattleGroupConfig> battleGroupConfigs = battleGroupConfigService.findAllByCode(BattleGroupConfig.CURRENT_FREND_ROOM_CODE);
		if(battleGroupConfigs==null||battleGroupConfigs.size()==0){
			return;
		}
		BattleGroupConfig battleGroupConfig = battleGroupConfigs.get(0);
		
		if(!CommonUtil.isEmpty(battleGroupConfig.getRoomId())){
			BattleRoom battleRoom = battleRoomService.findOne(battleGroupConfig.getRoomId());
			
			if(battleRoom!=null){
				battleRoom.setStatus(BattleRoom.STATUS_END);
				battleRoom.setEndType(BattleRoom.SYSTEM_END_TYPE);
				
				battleRoomService.update(battleRoom);
			}
		}
	}
	
	//定时执行转行失败的任务，凌晨两点更新
	@Scheduled(cron = "0 23 3 * * ?")
	public void battleRoomInit(){
		
		List<BattleGroupConfig> battleGroupConfigs = battleGroupConfigService.findAllByCode(BattleGroupConfig.CURRENT_FREND_ROOM_CODE);
		if(battleGroupConfigs==null||battleGroupConfigs.size()==0){
			return;
		}
		BattleGroupConfig battleGroupConfig = battleGroupConfigs.get(0);
		
		if(!CommonUtil.isEmpty(battleGroupConfig.getRoomId())){
		
			BattleRoom battleRoom = battleRoomService.findOne(battleGroupConfig.getRoomId());
			
			if(battleRoom!=null){
			
				battleRoom.setStatus(BattleRoom.STATUS_END);
				
				battleRoom.setEndType(BattleRoom.SYSTEM_END_TYPE);
				
				battleRoom.setIsDel(1);
				
				battleRoomService.update(battleRoom);
			}
		}
		
		battleGroupConfig.setBattleId("9");
		battleGroupConfig.setPeriodId("adc24368-647f-4d9f-9964-22ed359440ac");
		
		Battle battle = battleService.findOne(battleGroupConfig.getBattleId());
		
		BattleRoom battleRoom = battleRoomHandleService.initRoom(battle);
		
		battleRoom.setScrollGogal(1000);
		
		battleRoom.setEndEnable(0);
		
		battleRoom.setBattleId(battleGroupConfig.getBattleId());
		
		battleRoom.setPeriodId(battleGroupConfig.getPeriodId());
		
		battleRoom.setMininum(1);
		battleRoom.setMaxinum(1000000000);
		battleRoom.setIsDanRoom(0);
		
		battleRoom.setIsFrendGroup(1);
		
		battleRoom.setPlaces(1000000000);
		
		
		battleRoom.setStartTime(new DateTime());
		
		
		battleRoomService.add(battleRoom);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		calendar.add(Calendar.HOUR,24);
		
		battleGroupConfig.setRoomId(battleRoom.getId());
		
		battleGroupConfig.setNextDateTime(new DateTime(calendar.getTime()));
		
		battleGroupConfigService.update(battleGroupConfig);
		
		
	}
}
