package com.battle.service.other;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleUser;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattlePeriodService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleUserService;
import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

@Service
public class RoomTakapertService {

	
	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	
	@Autowired
	private BattleUserService battleUserService;
	
	@Autowired
	private WxUserInfoService userInfoService;
	
	@Autowired
	private BattlePeriodService battlePeriodService;
	public void takepart(BattleRoom battleRoom,List<Map<String, Object>> users){
		
		Integer num = battleRoom.getNum();
		if(num==null){
			num = 0;
		}
		
		BattlePeriod battlePeriod = battlePeriodService.findOne(battleRoom.getPeriodId());
		
		for(Map<String, Object> user:users){
			String userId = user.get("userId").toString();
			
			Object danId = user.get("danId");
			num++;
			UserInfo userInfo = userInfoService.findOne(userId);
			BattleUser battleUser = battleUserService.findOneByUserIdAndBattleId(userId, battleRoom.getBattleId());
			if(battleUser==null){
				battleUser = new BattleUser();
				battleUser.setBattleId(battleRoom.getBattleId());
				battleUser.setIsCreater(0);
				battleUser.setIsManager(0);
				battleUser.setOpenId(userInfo.getOpenid());
				battleUser.setUserId(userId);
				battleUserService.add(battleUser);
			}
			BattlePeriodMember battlePeriodMember = new BattlePeriodMember();
			battlePeriodMember.setBattleId(battleRoom.getBattleId());
			battlePeriodMember.setBattleUserId(battleUser.getId());
			battlePeriodMember.setHeadImg(userInfo.getHeadimgurl());
			battlePeriodMember.setIndex(0);
			battlePeriodMember.setIsDel(0);
			battlePeriodMember.setIsIncrease(battleRoom.getIsIncrease());
			battlePeriodMember.setLoveCount(battleRoom.getLoveCount());
			battlePeriodMember.setLoveResidule(battleRoom.getLoveCount());
			battlePeriodMember.setNickname(userInfo.getNickname());
			battlePeriodMember.setPeriodId(battleRoom.getPeriodId());
			battlePeriodMember.setProcess(0);
			battlePeriodMember.setRewardBean(0);
			battlePeriodMember.setRewardLove(0);
			battlePeriodMember.setRoomId(battleRoom.getId());
			battlePeriodMember.setScore(0);
			battlePeriodMember.setScrollGogal(battleRoom.getScrollGogal());
			battlePeriodMember.setStageCount(battlePeriod.getStageCount());
			battlePeriodMember.setStageIndex(1);
			battlePeriodMember.setStatus(BattlePeriodMember.STATUS_IN);
			battlePeriodMember.setTakepartAt(new DateTime());
			battlePeriodMember.setUserId(userId);
			
			if(CommonUtil.isNotEmpty(danId)){
				battlePeriodMember.setDanId(danId.toString());
			}
			
			battlePeriodMemberService.add(battlePeriodMember);
			
			System.out.println("..........add battlePeriodMember:"+battlePeriodMember);
		}
	}
	
}
