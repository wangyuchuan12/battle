package com.battle.filter.element;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.BattleMemberLoveCooling;
import com.battle.domain.BattlePeriodMember;
import com.battle.service.BattleMemberLoveCoolingService;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class CurrentLoveCoolingFilter extends Filter{

	@Autowired
	private BattleMemberLoveCoolingService battleMemberLoveCoolingService;
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		BattleMemberLoveCooling battleMemberLoveCooling = battleMemberLoveCoolingService.findOneByBattleMemberId(battlePeriodMember.getId());
		
		
		
		
		if(battleMemberLoveCooling==null){
			Long millisec = 1000L;
			Integer unit = 1;
			Long upperLimit = 10000L;
			battleMemberLoveCooling = new BattleMemberLoveCooling();
			battleMemberLoveCooling.setBattleMemberId(battlePeriodMember.getId());
			battleMemberLoveCooling.setUnit(unit);
			battleMemberLoveCooling.setUpperLimit(upperLimit);
			battleMemberLoveCooling.setSchedule(0L);
			battleMemberLoveCooling.setMillisec(millisec);
			battleMemberLoveCooling.setStatus(BattlePeriodMember.STATUS_FREE);
			battleMemberLoveCoolingService.add(battleMemberLoveCooling);
		}else{
			
		}
		
		if(battlePeriodMember.getLoveCount()>battlePeriodMember.getLoveResidule()){
			Long millisec = battleMemberLoveCooling.getMillisec();
			Integer unit = battleMemberLoveCooling.getUnit();
			DateTime startDatetime = battleMemberLoveCooling.getStartDatetime();
			Long upperLimit = battleMemberLoveCooling.getUpperLimit();
			
			DateTime nowDatetime = new DateTime();
			
			Long differ = nowDatetime.getMillis()-startDatetime.getMillis();
			
			long schedule = (differ/millisec)*unit+battleMemberLoveCooling.getSchedule();
			
			battleMemberLoveCooling.setStartDatetime(new DateTime());
			
			if(schedule<upperLimit){
				battleMemberLoveCooling.setStatus(BattlePeriodMember.STATUS_IN);
				battleMemberLoveCooling.setSchedule(schedule);
			}else{
				battleMemberLoveCooling.setStatus(BattlePeriodMember.STATUS_COMPLETE);
				battleMemberLoveCooling.setSchedule(upperLimit);
			}
		}else{
			battleMemberLoveCooling.setSchedule(battleMemberLoveCooling.getUpperLimit());
		}
		
		
		return battleMemberLoveCooling;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
