package com.battle.filter.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.battle.domain.Battle;
import com.battle.domain.BattlePeriodMember;
import com.battle.filter.element.CurrentBattlePeriodFilter;
import com.battle.filter.element.CurrentBattlePeriodMemberFilter;
import com.battle.filter.element.CurrentBattleUserFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.wyc.AttrEnum;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class BattleMemberInfoApiFilter extends Filter{

	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		ResultVo resultVo = new ResultVo();
		resultVo.setData(battlePeriodMember);
		resultVo.setSuccess(true);
		resultVo.setMsg("返回数据");
		return resultVo;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		
		String battleId = httpServletRequest.getParameter("battleId");
		
		sessionManager.setAttribute(AttrEnum.battleId, battleId);
		
		Battle battle = sessionManager.findOne(Battle.class, battleId);
		
		sessionManager.setAttribute(AttrEnum.periodIndex, battle.getCurrentPeriodIndex());
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		List<Class<? extends Filter>> classes = new ArrayList<>();
		classes.add(LoginStatusFilter.class);
		classes.add(CurrentBattleUserFilter.class);
		classes.add(CurrentBattlePeriodFilter.class);
		classes.add(CurrentBattlePeriodMemberFilter.class);
		return classes;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
