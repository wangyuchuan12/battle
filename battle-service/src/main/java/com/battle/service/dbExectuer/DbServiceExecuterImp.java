package com.battle.service.dbExectuer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.Battle;
import com.battle.domain.BattleMemberLoveCooling;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.domain.QuestionAnswer;
import com.battle.service.BattleMemberLoveCoolingService;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattlePeriodService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleService;
import com.battle.service.QuestionAnswerService;
import com.wyc.common.session.DbServiceExecuter;

@Service
public class DbServiceExecuterImp implements DbServiceExecuter{

	@Autowired
	private BattleService battleService;
	
	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Autowired
	private QuestionAnswerService questionAnswerService;
	

	@Autowired
	private BattlePeriodService battlePeriodService;
	
	@Autowired
	private BattleMemberLoveCoolingService battleMemberLoveCoolingService;
	
	
	@Autowired
	private BattleRoomService battleRoomService;
	@Override
	public void update(List<Object> objs) {
		if(objs!=null&&objs.size()>0){
			for(Object target:objs){
				if(target!=null){
					Class<?> type = target.getClass();
					if(type.equals(Battle.class)){
						battleService.update((Battle)target);
					}else if(type.equals(BattlePeriodMember.class)){
						battlePeriodMemberService.update((BattlePeriodMember)target);
					}else if(type.equals(QuestionAnswer.class)){
						questionAnswerService.update((QuestionAnswer)target);
					}else if(type.equals(BattleRoom.class)){
						battleRoomService.update((BattleRoom)target);
					}else if(type.equals(BattleMemberLoveCooling.class)){
						battleMemberLoveCoolingService.update((BattleMemberLoveCooling)target);
					}
				}
			}
		}
	}

	@Override
	public <T> T findOne(Class<T> clazz, String id) {
		if(clazz.equals(Battle.class)){
			Battle battle = battleService.findOne(id);
			return (T)battle;
		}else if(clazz.equals(QuestionAnswer.class)){
			QuestionAnswer questionAnswer = questionAnswerService.findOne(id);
			
			return (T)questionAnswer;
		}else if(clazz.equals(BattlePeriodMember.class)){
			BattlePeriodMember battlePeriodMember = battlePeriodMemberService.findOne(id);
			
			return (T)battlePeriodMember;
		}else if(clazz.equals(BattlePeriod.class)){
			BattlePeriod battlePeriod = battlePeriodService.findOne(id);
			
			return (T)battlePeriod;
		}else if(clazz.equals(BattleRoom.class)){
			BattleRoom battleRoom = battleRoomService.findOne(id);
			return (T)battleRoom;
		
		}else if(clazz.equals(BattleMemberLoveCooling.class)){
			BattleMemberLoveCooling battleMemberLoveCooling = battleMemberLoveCoolingService.findOne(id);
		}
		return null;
	}

}
