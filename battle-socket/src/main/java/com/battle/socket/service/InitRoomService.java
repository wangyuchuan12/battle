package com.battle.socket.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.battle.domain.Battle;
import com.battle.domain.BattleCreateDetail;
import com.battle.domain.BattleDan;
import com.battle.domain.BattleDanUser;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleWait;
import com.battle.domain.BattleWaitUser;
import com.battle.service.BattleCreateDetailService;
import com.battle.service.BattleDanService;
import com.battle.service.BattleDanUserService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleService;
import com.battle.service.BattleWaitService;
import com.battle.service.BattleWaitUserService;
import com.battle.service.other.BattleRoomHandleService;
import com.battle.service.other.RoomTakapertService;
import com.battle.socket.task.RoomStartTask;
import com.wyc.common.util.CommonUtil;


@Service
public class InitRoomService {
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private RoomStartTask roomStartTask;
	
	@Autowired
	private BattleCreateDetailService battleCreateDetailServie;
	
	@Autowired
	private BattleRoomHandleService battleRoomHandleService;
	
	@Autowired
	private RoomTakapertService roomTakapertService;
	
	@Autowired
	private BattleService battleService;
	
	@Autowired
	private BattleWaitUserService battleWaitUserService;
	
	@Autowired
	private BattleWaitService battleWaitService;
	
	@Autowired
	private BattleDanUserService battleDanUserService;
	
	@Autowired
	private BattleWaitSocketService battleWaitSocketService;
	
	@Autowired
	private BattleDanService battleDanService;
	
	final static Logger logger = LoggerFactory.getLogger(InitRoomService.class);
	
	public BattleRoom addRoom(BattleRoom battleRoom){
		
		battleRoomService.add(battleRoom);
		
		roomStartTask.run(battleRoom);
		
		return battleRoom;
	}
	
	
	public BattleRoom addDanRoom(final String waitId)throws Exception{
		
		BattleWait battleWait = battleWaitService.findOne(waitId);
		
		String danId = battleWait.getDanId();
		Battle battle = battleService.findOne(battleWait.getBattleId());
		final BattleRoom battleRoom = battleRoomHandleService.initRoom(battle);
		
		if(CommonUtil.isEmpty(danId)){
			battleRoom.setIsPk(0);
			battleRoom.setPeriodId(battleWait.getPeriodId());
			battleRoom.setMaxinum(2);
			battleRoom.setMininum(2);
			battleRoom.setIsSearchAble(0);
			battleRoom.setScrollGogal(200);
			battleRoom.setPlaces(1);
			battleRoom.setIsDanRoom(0);
			battleRoom.setIsIncrease(0);
			battleRoom.setStartTime(new DateTime());
			battleRoom.setLoveCount(10);
		}else{
			BattleDan battleDan = battleDanService.findOne(danId);
			battleRoom.setIsPk(0);
			battleRoom.setPeriodId(battleWait.getPeriodId());
			battleRoom.setMaxinum(battleDan.getMaxNum());
			battleRoom.setMininum(battleDan.getMinNum());
			battleRoom.setIsSearchAble(0);
			battleRoom.setScrollGogal(battleDan.getScoreGogal());
			battleRoom.setPlaces(battleDan.getPlaces());
			battleRoom.setIsDanRoom(1);
			battleRoom.setIsIncrease(0);
			battleRoom.setStartTime(new DateTime());
			battleRoom.setLoveCount(battleDan.getLoveCount());
		}
		addRoom(battleRoom);
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				List<BattleWaitUser> battleWaitUsers = battleWaitUserService.findAllByWaitId(waitId);
				List<Map<String, Object>> users = new ArrayList<>();
				
				for(BattleWaitUser battleWaitUser:battleWaitUsers){
					
					Map<String, Object> user = new HashMap<>();
					user.put("userId", battleWaitUser.getUserId());
					String danUserId = battleWaitUser.getDanUserId();
					if(CommonUtil.isNotEmpty(danUserId)){
						BattleDanUser battleDanUser = battleDanUserService.findOne(danUserId);
						user.put("danId", battleDanUser.getDanId());
						
						battleDanUser.setRoomId(battleRoom.getId());
						
						battleDanUserService.update(battleDanUser);
					}

					users.add(user);
				}
				try{
					battleWaitSocketService.waitEndPublish(battleRoom, waitId);
				}catch(Exception e){
					logger.error("{}",e);
				}
				
				System.out.println("............roomTakepart:"+users);
				
				roomTakapertService.takepart(battleRoom, users);
				
			}
		}, 4000);

		return battleRoom;
		
	}
	
	public BattleRoom addPkRoom(String...userIds){
		
		BattleCreateDetail battleCreateDetail = battleCreateDetailServie.findOneByCode(BattleCreateDetail.PK_CODE);
		Battle battle = battleService.findOne(battleCreateDetail.getBattleId());
		BattleRoom battleRoom = battleRoomHandleService.initRoom(battle);
		battleRoom.setIsPk(1);
		battleRoom.setPeriodId(battleCreateDetail.getPeriodId());
		battleRoom.setMaxinum(2);
		battleRoom.setMininum(2);
		battleRoom.setIsSearchAble(0);
		battleRoom.setScrollGogal(battleCreateDetail.getScrollGogal());
		battleRoom.setPlaces(1);
		battleRoom.setIsDanRoom(0);
		battleRoom.setIsIncrease(0);
		battleRoom.setStartTime(new DateTime());
		battleRoom.setLoveCount(battleCreateDetail.getLoveCount());
		
		addRoom(battleRoom);
		
		List<Map<String, Object>> users = new ArrayList<>();
		for(String userId:userIds){
			Map<String, Object> user = new HashMap<>();
			user.put("userId", userId);
			users.add(user);
		}
		
		roomTakapertService.takepart(battleRoom, users);
		
		return battleRoom;
		
	}
}
