package com.battle.filter.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.Battle;
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
	
		List<Integer> statuses = new ArrayList<>();
		
		statuses.add(BattlePeriodMember.STATUS_IN);
		statuses.add(BattlePeriodMember.STATUS_COMPLETE);
		List<BattlePeriodMember> members = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndStatusIn(battleId,periodId,statuses);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(members);
		
		return resultVo;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		String battleId = httpServletRequest.getParameter("battleId");
		
		Battle battle = sessionManager.findOne(Battle.class, battleId);
	
		sessionManager.setAttribute(AttrEnum.battleId, battleId);
		
		sessionManager.setAttribute(AttrEnum.periodIndex, battle.getCurrentPeriodIndex());
		
		BattlePeriod battlePeriod = battlePeriodService.findOneByBattleIdAndIndex(battleId, battle.getCurrentPeriodIndex());
		
		sessionManager.save(battlePeriod);
		
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		return null;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
