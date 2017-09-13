package com.battle.filter.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodMember;
import com.battle.filter.element.CurrentBattlePeriodMemberFilter;
import com.battle.filter.element.CurrentBattleUserFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattlePeriodService;
import com.wyc.AttrEnum;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class BattleMembersApiFilter extends Filter{

	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Autowired
	private BattlePeriodService battlePeriodService;
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		String battleId = (String)sessionManager.getAttribute(AttrEnum.battleId);
		String periodId = (String)sessionManager.getAttribute(AttrEnum.periodId);
	
		List<BattlePeriodMember> members = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndStatus(battleId,periodId,BattlePeriodMember.STATUS_IN);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(members);
		
		return resultVo;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		String battleId = httpServletRequest.getParameter("battleId");
		
		String periodIndex = httpServletRequest.getParameter("periodIndex");
		sessionManager.setAttribute(AttrEnum.battleId, battleId);
		
		sessionManager.setAttribute(AttrEnum.periodIndex, Integer.parseInt(periodIndex));
		
		BattlePeriod battlePeriod = battlePeriodService.findOneByBattleIdAndIndex(battleId, Integer.parseInt(periodIndex));
		
		sessionManager.save(battlePeriod);
		
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
