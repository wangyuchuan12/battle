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
		
		if(battlePeriodMember.getIsIncrease()!=null&&battlePeriodMember.getIsIncrease()!=1){
			return null;
		}
		
		BattleMemberLoveCooling battleMemberLoveCooling = battleMemberLoveCoolingService.findOneByBattleMemberId(battlePeriodMember.getId());
		
		
		
		
		if(battleMemberLoveCooling==null){
			Long millisec = 1000L;
			Integer unit = 1;
			Long upperLimit = 100L;
			battleMemberLoveCooling = new BattleMemberLoveCooling();
			battleMemberLoveCooling.setBattleMemberId(battlePeriodMember.getId());
			battleMemberLoveCooling.setUnit(unit);
			battleMemberLoveCooling.setUpperLimit(upperLimit);
			battleMemberLoveCooling.setSchedule(0L);
			battleMemberLoveCooling.setMillisec(millisec);
			battleMemberLoveCooling.setStatus(BattlePeriodMember.STATUS_FREE);
			battleMemberLoveCoolingService.add(battleMemberLoveCooling);
			
			System.out.println("***********************************************");
		}else{
			
		}
		
		if(battleMemberLoveCooling.getStatus()!=BattlePeriodMember.STATUS_IN){
			battleMemberLoveCooling.setStartDatetime(new DateTime());
		}
		
		Integer loveCount = battlePeriodMember.getLoveCount();
		
		Integer loveResidule = battlePeriodMember.getLoveResidule();
		if(loveResidule==null||loveResidule<0){
			loveResidule = 0;
		}
		if(loveCount>loveResidule){
			
			battleMemberLoveCooling.setCoolLoveSeq(loveResidule+1);
			Long millisec = battleMemberLoveCooling.getMillisec();
			Integer unit = battleMemberLoveCooling.getUnit();
			DateTime startDatetime = battleMemberLoveCooling.getStartDatetime();
			Long upperLimit = battleMemberLoveCooling.getUpperLimit();
			
			DateTime nowDatetime = new DateTime();
			
			Long differ = nowDatetime.getMillis()-startDatetime.getMillis();
			
			long schedule = (differ/millisec)*unit+battleMemberLoveCooling.getSchedule();
			
			long time = schedule/upperLimit;
			
			schedule = schedule - upperLimit*time;
			
			battleMemberLoveCooling.setStartDatetime(new DateTime());
			
			Long loveResidule2 = time+loveResidule;
			
			if(loveResidule2<loveCount){
				battleMemberLoveCooling.setStatus(BattlePeriodMember.STATUS_IN);
				battleMemberLoveCooling.setSchedule(schedule);
				battlePeriodMember.setLoveResidule(loveResidule2.intValue());
				battleMemberLoveCooling.setCoolLoveSeq(loveResidule2.intValue()+1);
				battleMemberLoveCooling.setStartDatetime(new DateTime());
			}else{
				battleMemberLoveCooling.setStatus(BattlePeriodMember.STATUS_COMPLETE);
				battleMemberLoveCooling.setSchedule(upperLimit);
				battleMemberLoveCooling.setSchedule(0L);
				battleMemberLoveCooling.setCoolLoveSeq(0);
				battlePeriodMember.setLoveResidule(loveCount);
			}
		}else{
			battleMemberLoveCooling.setSchedule(battleMemberLoveCooling.getUpperLimit());
			battleMemberLoveCooling.setStatus(BattlePeriodMember.STATUS_COMPLETE);
			battleMemberLoveCooling.setSchedule(0L);
			battleMemberLoveCooling.setCoolLoveSeq(0);
		}
		
		sessionManager.update(battleMemberLoveCooling);
		
		sessionManager.update(battlePeriodMember);
		
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
