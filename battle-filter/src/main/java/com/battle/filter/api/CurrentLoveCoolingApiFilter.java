package com.battle.filter.api;

import java.util.ArrayList;
import java.util.List;

import com.battle.domain.BattleMemberLoveCooling;
import com.battle.filter.element.CurrentLoveCoolingFilter;
import com.battle.filter.element.CurrentMemberInfoFilter;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class CurrentLoveCoolingApiFilter extends Filter{

	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		BattleMemberLoveCooling battleMemberLoveCooling = sessionManager.getObject(BattleMemberLoveCooling.class);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleMemberLoveCooling);
		
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
