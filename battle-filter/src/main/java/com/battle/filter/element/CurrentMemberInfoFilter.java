package com.battle.filter.element;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.Battle;
import com.battle.domain.BattlePeriod;
import com.battle.service.BattlePeriodService;
import com.wyc.AttrEnum;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class CurrentMemberInfoFilter extends Filter{

	@Autowired
	private BattlePeriodService battlePeriodService;
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		String battleId = httpServletRequest.getParameter("battleId");
		
		Battle battle = sessionManager.findOne(Battle.class, battleId);
		
		Integer periodIndex = battle.getCurrentPeriodIndex();
		
		BattlePeriod battlePeriod = battlePeriodService.findOneByBattleIdAndIndex(battleId, periodIndex);
		
		sessionManager.setAttribute(AttrEnum.periodId, battlePeriod.getId());
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		List<Class<? extends Filter>> classes = new ArrayList<>();
		classes.add(LoginStatusFilter.class);
		classes.add(CurrentBattleUserFilter.class);
		classes.add(CurrentBattlePeriodMemberFilter.class);
		return classes;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
