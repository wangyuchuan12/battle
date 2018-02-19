package com.battle.service.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.BattleDekornUser;
import com.battle.domain.BattleMemberPaperAnswer;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.service.BattleDekornUserService;

@Service
public class BattleRoomEndListener {

	@Autowired
	private BattleDekornUserService battleDekornUserService;
	public void answerPass(BattleRoom battleRoom,BattleMemberPaperAnswer battleMemberPaperAnswer,BattlePeriodMember battlePeriodMember){
		
	}
	
	public void answerUnPass(BattleRoom battleRoom,BattleMemberPaperAnswer battleMemberPaperAnswer,BattlePeriodMember battlePeriodMember){
		
	}
	
	public void answerEnd(BattleRoom battleRoom,BattleMemberPaperAnswer battleMemberPaperAnswer,BattlePeriodMember battlePeriodMember){
		
	}
	
	
	//比赛通过
	public void roomEnd(BattleRoom battleRoom,List<BattlePeriodMember> battlePeriodMembers){
		
		Map<String, BattleDekornUser> battleDekornUserMap = new HashMap<>();
		
		if(battleRoom.getIsDekorn()!=null&&battleRoom.getIsDekorn()==1){
			List<BattleDekornUser> battleDekornUsers = battleDekornUserService.findAllByDekornIdAndIsDel(battleRoom.getDekornId(), 0);
			for(BattleDekornUser battleDekornUser:battleDekornUsers){
				
				battleDekornUserMap.put(battleDekornUser.getUserId(), battleDekornUser);
			}
		}
		
		for(BattlePeriodMember battlePeriodMember:battlePeriodMembers){
			//成功
			if(battlePeriodMember.getScore()>=battlePeriodMember.getScrollGogal()){
				
				if(battleRoom.getIsDekorn()!=null&&battleRoom.getIsDekorn()==1){
					BattleDekornUser battleDekornUser = battleDekornUserMap.get(battlePeriodMember.getUserId());
					battleDekornUser.setStatus(BattleDekornUser.SUCCESS_STATUS);
					
					battleDekornUserService.update(battleDekornUser);
				}
				
				
			
			}else{
				//失败
				if(battleRoom.getIsDekorn()!=null&&battleRoom.getIsDekorn()==1){
					BattleDekornUser battleDekornUser = battleDekornUserMap.get(battlePeriodMember.getUserId());
					battleDekornUser.setStatus(BattleDekornUser.FAIL_STATUS);
					
					battleDekornUserService.update(battleDekornUser);
				}
			}
		}
	}
}
