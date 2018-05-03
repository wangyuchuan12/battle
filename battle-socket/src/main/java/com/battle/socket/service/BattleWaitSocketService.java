package com.battle.socket.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.BattleRoom;
import com.battle.domain.BattleWaitUser;
import com.battle.service.BattleWaitUserService;
import com.battle.socket.MessageHandler;
import com.battle.socket.MessageVo;

@Service
public class BattleWaitSocketService {

	@Autowired
	private  MessageHandler messageHandler;
	
	final static Logger logger = LoggerFactory.getLogger(BattleWaitSocketService.class);
	@Autowired
	private BattleWaitUserService battleWaitUserService;
	
	
	public void waitEndPublish(final BattleRoom battleRoom,final String waitId)throws Exception{
		Timer timer = new Timer();		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try{
					
					List<BattleWaitUser> battleWaitUsers = battleWaitUserService.findAllByWaitId(waitId);
					List<String> userIds = new ArrayList<>();
					for(BattleWaitUser battleWaitUser:battleWaitUsers){
						String userId = battleWaitUser.getUserId();
						userIds.add(userId);
					}
					final MessageVo messageVo = new MessageVo();
					messageVo.setData(battleRoom);
					messageVo.setType(MessageVo.USERS_TYPE);
					messageVo.setUserIds(userIds);
					messageVo.setCode(MessageVo.WAIT_END_CODE);
					messageHandler.sendMessage(messageVo);
					
				}catch(Exception e){
					logger.error("{}",e);
				}
			}
		},1000);
	}
	
	public void waitPublish(final BattleWaitUser user)throws Exception{

		Timer timer = new Timer();		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try{
					List<BattleWaitUser> battleWaitUsers = battleWaitUserService.findAllByWaitId(user.getWaitId());
					List<String> userIds = new ArrayList<>();
					for(BattleWaitUser battleWaitUser:battleWaitUsers){
						String userId = battleWaitUser.getUserId();
						if(!user.getUserId().equals(userId)){
							userIds.add(userId);
						}
					}
					
					final MessageVo messageVo = new MessageVo();
					messageVo.setData(user);
					messageVo.setType(MessageVo.USERS_TYPE);
					messageVo.setUserIds(userIds);
					messageVo.setCode(MessageVo.WAIT_STATUS_CODE);
					messageHandler.sendMessage(messageVo);
				}catch(Exception e){
					logger.error("{}",e);
				}
			}
		},1000);
		
	}
	
}
