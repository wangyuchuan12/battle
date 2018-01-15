package com.battle.service.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.battle.service.BattleRoomRewardService;
import com.wyc.annotation.HandlerAnnotation;
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
	
	
	public List<BattlePeriodMember> rewardReceive(BattleRoom battleRoom){
		
		List<BattleRoomReward> battleRoomRewards = battleRoomRewardService.findAllByRoomIdAndIsReceiveOrderByRankAsc(battleRoom.getId(),0);
		
		
		List<BattleDanUser> battleDanUsers = battleDanUserService.findAllByRoomId(battleRoom.getId());
		
		Map<String, BattleDanUser> battleDanUserMap = new HashMap<>();
		
		for(BattleDanUser battleDanUser:battleDanUsers){
			
			battleDanUserMap.put(battleDanUser.getUserId(), battleDanUser);
			
		}
		
		
		if(CommonUtil.isNotEmpty(battleRoom.getDanId())){
			
			BattleDan battleDan = battleDanService.findOne(battleRoom.getDanId());
			
			if(battleDan==null){
				return new ArrayList<>();
			}
			
			Integer places = battleDan.getPlaces();
			

			
			Sort sort = new Sort(Direction.DESC,"score");
			
			Pageable pageable = new PageRequest(0, 100, sort);
			
			List<BattlePeriodMember> battlePeriodMembers = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomId(battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId(),pageable);
			
			for(Integer index = 0;index<battlePeriodMembers.size();index++){
				BattlePeriodMember battlePeriodMember = battlePeriodMembers.get(index);
				
				BattleDanUser battleDanUser = battleDanUserMap.get(battlePeriodMember.getUserId());
				
				if(battleDanUser!=null){
					battleDanUser.setRank(index+1);
					
					if(index<places){
						battleDanUser.setStatus(BattleDanUser.STATUS_SUCCESS);
						Integer level = battleDanUser.getLevel();
						
						BattleDanUser battleDanUserNext = battleDanUserService.findOneByUserIdAndPointIdAndLevel(battleDanUser.getUserId(), battleDanUser.getPointId(),level+1);
					
						if(battleDanUserNext!=null&&battleDanUserNext.getStatus()==BattleDanUser.STATUS_FREE){
							
							battleDanUserNext.setStatus(BattleDanUser.STATUS_IN);
							
							battleDanUserService.update(battleDanUserNext);
						}
						
						BattleAccountResult battleAccountResult = battleAccountResultService.findOneByUserId(battleDanUser.getUserId());
						battleAccountResult.setLevel(battleDanUser.getLevel());
						battleAccountResult.setDanName(battleDanUser.getDanName());
						
						battleAccountResultService.update(battleAccountResult);
					}else{
						battleDanUser.setStatus(BattleDanUser.STATUS_FAIL);
						battleDanUser.setIsSign(0);
					}
					
					battleDanUserService.update(battleDanUser);
				}
				
			}
			
			
			
			Map<Integer, BattleRoomReward> battleRoomRewardMap = new HashMap<>();
			
			for(Integer i = 0;i<battleRoomRewards.size();i++){
				
				BattleRoomReward battleRoomReward = battleRoomRewards.get(i);
				battleRoomRewardMap.put(i, battleRoomReward);
			}
			
			
			
			if(battlePeriodMembers!=null&&battlePeriodMembers.size()>0){
				for(Integer i=0;i<battlePeriodMembers.size();i++){
					BattlePeriodMember battlePeriodMember = battlePeriodMembers.get(i);
					BattleRoomReward battleRoomReward = battleRoomRewardMap.get(i);
					
					if(battleRoomReward!=null){
						battleRoomReward.setIsReceive(1);
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
		
		return new ArrayList<>();
		
		
	}
}
