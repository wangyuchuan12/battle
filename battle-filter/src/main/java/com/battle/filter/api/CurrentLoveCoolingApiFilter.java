package com.battle.filter.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.battle.domain.BattleMemberLoveCooling;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodMember;
import com.battle.filter.element.CurrentLoveCoolingFilter;
import com.battle.filter.element.CurrentMemberInfoFilter;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class CurrentLoveCoolingApiFilter extends Filter{

	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		BattleMemberLoveCooling battleMemberLoveCooling = sessionManager.getObject(BattleMemberLoveCooling.class);
		
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("id", battleMemberLoveCooling.getId());
		data.put("battleMemberId", battleMemberLoveCooling.getBattleMemberId());
		data.put("coolLoveSeq", battleMemberLoveCooling.getCoolLoveSeq());
		data.put("millisec", battleMemberLoveCooling.getMillisec());
		data.put("schedule", battleMemberLoveCooling.getSchedule());
		data.put("startDatetime", battleMemberLoveCooling.getStartDatetime());
		data.put("status", battleMemberLoveCooling.getStatus());
		data.put("unit", battleMemberLoveCooling.getUnit());
		data.put("upperLimit", battleMemberLoveCooling.getUpperLimit());
		data.put("loveCount", battlePeriodMember.getLoveCount());
		data.put("loveResidule", battlePeriodMember.getLoveResidule());
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(data);
		
		return resultVo;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		List<Class<? extends Filter>> classes = new ArrayList<>();
		classes.add(CurrentMemberInfoFilter.class);
		classes.add(CurrentLoveCoolingFilter.class);
		return classes;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
