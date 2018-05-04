package com.battle.service.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.battle.domain.BattleAccountResult;
import com.battle.domain.BattleDan;
import com.battle.domain.BattleDanUser;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleRoomReward;
import com.battle.service.BattleAccountResultService;
import com.battle.service.BattleDanService;
import com.battle.service.BattleDanUserService;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattleRoomGroupMemberService;
import com.battle.service.BattleRoomGroupService;
import com.battle.service.BattleRoomRewardService;
import com.wyc.common.domain.Account;
import com.wyc.common.service.AccountService;
import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.util.CommonUtil;
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
	
	@Autowired
	private BattleDanUserService battleDanUserService;
	
	@Autowired
	private BattleDanService battleDanService;
	
	@Autowired
	private BattleAccountResultService battleAccountResultService;
	
	@Autowired
	private BattleRoomGroupService battleRoomGroupService;
	
	@Autowired
	private BattleRoomGroupMemberService battleRoomGroupMemberService;
	
	
	public List<BattlePeriodMember> rewardReceive(BattleRoom battleRoom){
		
		List<BattleRoomReward> battleRoomRewards = battleRoomRewardService.findAllByRoomIdAndIsReceiveOrderByRankAsc(battleRoom.getId(),0);

		List<BattleDanUser> battleDanUsers = battleDanUserService.findAllByRoomId(battleRoom.getId());
		
		
		System.out.println("battleDanUsers:"+battleDanUsers);

		Map<String, BattleDanUser> battleDanUserMap = new HashMap<>();
		
		for(BattleDanUser battleDanUser:battleDanUsers){
			
			battleDanUserMap.put(battleDanUser.getUserId(), battleDanUser);
			
		}
		
		
		Sort sort = new Sort(Direction.DESC,"score");
		
		Pageable pageable = new PageRequest(0, 100, sort);
		
		List<BattlePeriodMember> battlePeriodMembers = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomId(battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId(),pageable);
		
		Map<Integer, BattleRoomReward> battleRoomRewardMap = new HashMap<>();
		
		for(int i = 0;i<battleRoomRewards.size();i++){
			
			BattleRoomReward battleRoomReward = battleRoomRewards.get(i);
			battleRoomRewardMap.put(i, battleRoomReward);
		}
		
		
		System.out.println("battleRoomRewardMap:"+battleRoomRewardMap);
		
		
		if(battlePeriodMembers!=null&&battlePeriodMembers.size()>0){
			for(int i=0;i<battlePeriodMembers.size();i++){
				BattlePeriodMember battlePeriodMember = battlePeriodMembers.get(i);
				BattleRoomReward battleRoomReward = battleRoomRewardMap.get(i);
				if(battleRoomReward!=null){
					battleRoomReward.setIsReceive(1);
					battleRoomReward.setReceiveMemberId(battlePeriodMember.getId());
					battlePeriodMember.setRewardBean(battleRoomReward.getRewardBean());
									
					//battlePeriodMemberService.update(battlePeriodMember);
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
		//if(CommonUtil.isNotEmpty(battleRoom.getDanId())){
			for(Integer index = 0;index<battlePeriodMembers.size();index++){
				BattlePeriodMember battlePeriodMember = battlePeriodMembers.get(index);
				BattleDanUser battleDanUser = battleDanUserMap.get(battlePeriodMember.getUserId());
				if(battleDanUser!=null){
					BattleDan battleDan = battleDanService.findOne(battleDanUser.getDanId());
					Integer places = battleDan.getPlaces();
					battleDanUser.setRank(index+1);
					if(index<places){
						battleDanUser.setStatus(BattleDanUser.STATUS_SUCCESS);
						Integer level = battleDanUser.getLevel();
						
						BattleDanUser battleDanUserNext = battleDanUserService.findOneByUserIdAndPointIdAndLevel(battleDanUser.getUserId(), battleDanUser.getPointId(),level+1);
					
						
						BattleDan nextBattleDan = battleDanService.findOneByPointIdAndLevel(battleDanUser.getPointId(),level+1);
						
						BattleAccountResult battleAccountResult = battleAccountResultService.findOneByUserId(battleDanUser.getUserId());
						if(battleDanUserNext!=null&&battleDanUserNext.getStatus()==BattleDanUser.STATUS_FREE){
							if(battleAccountResult.getLevel()<battleDanUser.getLevel()+1){
								battleDanUserNext.setStatus(BattleDanUser.STATUS_IN);
								battleDanUserService.update(battleDanUserNext);
								if(nextBattleDan!=null){
									battleAccountResult.setDanName(nextBattleDan.getName());
								}
								battleAccountResult.setLevel(battleDanUser.getLevel()+1);
								battleAccountResultService.update(battleAccountResult);
							}
						}else if(battleDanUserNext==null){
							if(nextBattleDan!=null){
								battleAccountResult.setDanName(nextBattleDan.getName());
							}
							battleAccountResult.setLevel(battleDanUser.getLevel()+1);
							battleAccountResultService.update(battleAccountResult);
						}else if(battleDanUserNext.getStatus()==BattleDanUser.STATUS_SUCCESS){
							System.out.println("...........进到这里来了3");
						}
						
					}else{
						if(battleDanUser.getStatus()!=BattleDanUser.STATUS_SUCCESS){
							battleDanUser.setStatus(BattleDanUser.STATUS_FAIL);
							battleDanUser.setIsSign(0);
						}
					}
					
					battleDanUserService.update(battleDanUser);
				}
				
			}
		//}
		
		
		return battlePeriodMembers;
	}
}
