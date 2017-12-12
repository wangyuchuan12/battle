package com.battle.service.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleRoomReward;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattleRoomRewardService;
import com.wyc.common.domain.Account;
import com.wyc.common.service.AccountService;
import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.wx.domain.UserInfo;

@Service
public class BattleDanHandleService {
	
	@Autowired
	private BattleRoomRewardService battleRoomRewardService;
	
	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private WxUserInfoService userInfoService;
	
	public List<BattlePeriodMember> rewardReceive(BattleRoom battleRoom){
		
		List<BattleRoomReward> battleRoomRewards = battleRoomRewardService.findAllByRoomIdOrderByRankAsc(battleRoom.getId());
		
		Map<Integer, BattleRoomReward> battleRoomRewardMap = new HashMap<>();
		
		for(BattleRoomReward battleRoomReward:battleRoomRewards){
			
			battleRoomRewardMap.put(battleRoomReward.getRank(), battleRoomReward);
		}
		
		List<Integer> statuses = new ArrayList<>();
		
		statuses.add(BattleRoom.STATUS_IN);
		statuses.add(BattleRoom.STATUS_END);
		List<BattlePeriodMember> battlePeriodMembers = battlePeriodMemberService.
		findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDel(battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId(), statuses, 0);
		
		if(battlePeriodMembers!=null&&battlePeriodMembers.size()>0){
			for(Integer i=0;i<battlePeriodMembers.size();i++){
				BattlePeriodMember battlePeriodMember = battlePeriodMembers.get(i);
				BattleRoomReward battleRoomReward = battleRoomRewardMap.get(i+1);
				
				if(battleRoomReward!=null){
					battleRoomReward.setReceiveMemberId(battlePeriodMember.getId());
					battlePeriodMember.setRewardBean(battleRoomReward.getRewardBean());
					battlePeriodMemberService.update(battlePeriodMember);
					battleRoomRewardService.update(battleRoomReward);
					
					UserInfo userInfo = userInfoService.findOne(battlePeriodMember.getUserId());
					
					Account account = accountService.fineOneSync(userInfo.getAccountId());
					
					Long wisdomCount = account.getWisdomCount();
					if(wisdomCount==null){
						wisdomCount = 0L;
					}
					
					Integer rewardBean = battleRoomReward.getRewardBean();
					if(rewardBean==null){
						rewardBean = 0;
					}
					
					wisdomCount = wisdomCount + rewardBean;
					
					account.setWisdomCount(wisdomCount);
					
					accountService.update(account);
				}
			}
		}
		return battlePeriodMembers;
	}
}
