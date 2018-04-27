package com.battle.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.domain.UserStatus;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattleRoomService;
import com.battle.service.UserStatusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

@Service
public class MessageHandler {

	@Autowired
	private SocketHandler socketHandler;
	
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private WxUserInfoService wxUserInfoService;
	
	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Autowired
	private UserStatusService userStatusService;


	public void sendMessage(MessageVo messageVo) throws IOException{
		
		List<String> excludeUserIds = messageVo.getExcludeUserIds();
		List<String> tokens = new ArrayList<>();
		if(messageVo.getType()==MessageVo.ALL_ONLINE_TYPE){
			Pageable pageable = new PageRequest(0, 100);
			Page<UserStatus> userInfoPage = userStatusService.findAllByIsLine(1,pageable);
			
			for(UserStatus userStatus:userInfoPage.getContent()){
				if(excludeUserIds==null||excludeUserIds.size()==0){
					tokens.add(userStatus.getToken());
				}else{
					if(!excludeUserIds.contains(userStatus.getId())){
						tokens.add(userStatus.getToken());
					}
				}
			}
		}
		
		if(messageVo.getType()==MessageVo.ROOM_TYPE){
			BattleRoom battleRoom = battleRoomService.findOne(messageVo.getRoomId());
			List<BattlePeriodMember> battlePeriodMembers = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomId(battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId());
			for(BattlePeriodMember battlePeriodMember:battlePeriodMembers){
				UserInfo userInfo = wxUserInfoService.findOne(battlePeriodMember.getUserId());
				
				UserStatus userStatus = null;
				if(!CommonUtil.isEmpty(userInfo.getStatusId())){
					userStatus = userStatusService.findOne(userInfo.getStatusId());
				}
				if(userStatus==null){
					userStatus = new UserStatus();
					userStatus.setIsLine(0);
					userStatus.setUserId(userInfo.getId());
					userStatus.setToken(userInfo.getToken());
					/*
					userStatusService.add(userStatus);
					
					userInfo.setStatusId(userStatus.getId());
					
					wxUserInfoService.update(userInfo);*/
				}
				if(userStatus.getIsLine()==null){
					userStatus.setIsLine(0);
					//userStatusService.update(userStatus);
				}
				if(userStatus.getIsLine()==1){
					if(excludeUserIds==null||excludeUserIds.size()==0){
						tokens.add(userInfo.getToken());
					}else{
						if(!excludeUserIds.contains(userInfo.getId())){
							tokens.add(userInfo.getToken());
						}
					}
				}
			}
		}
		
		if(messageVo.getType()==MessageVo.USERS_TYPE){
			List<String> userIds = messageVo.getUserIds();
			for(String userId:userIds){
				UserStatus userStatus = userStatusService.findOneByUserId(userId);
				if(userStatus==null){
					
					UserInfo userInfo = wxUserInfoService.findOne(userId);
					userStatus = new UserStatus();
					userStatus.setIsLine(0);
					userStatus.setUserId(userId);
					userStatus.setToken(userInfo.getToken());
					/*userStatusService.add(userStatus);
		
					userInfo.setStatusId(userStatus.getId());
					wxUserInfoService.update(userInfo);*/
				}
				if(userStatus.getIsLine()==null){
					userStatus.setIsLine(0);
					
				}
				if(userStatus.getIsLine()==1){
					if(excludeUserIds==null||excludeUserIds.size()==0){
						tokens.add(userStatus.getToken());
					}else{
						if(!excludeUserIds.contains(userStatus.getUserId())){
							tokens.add(userStatus.getToken());
						}
					}
				}
			}
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		String value = objectMapper.writeValueAsString(messageVo);
		
		if(tokens.size()>0){
			socketHandler.sendMessage(tokens, Arrays.asList(value));
		}
		
	}
}