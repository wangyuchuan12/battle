package com.battle.filter.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.Battle;
import com.battle.domain.BattlePeriodMember;
import com.battle.filter.element.CurrentBattlePeriodFilter;
import com.battle.filter.element.CurrentBattlePeriodMemberFilter;
import com.battle.filter.element.CurrentBattleUserFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleService;
import com.wyc.AttrEnum;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

public class BattleTakepartApiFilter extends Filter{
	
	@Autowired
	private BattleService battleService;

	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		String periodMemberId = (String)sessionManager.getAttribute(AttrEnum.periodMemberId);
		
		BattlePeriodMember battlePeriodMember = sessionManager.findOne(BattlePeriodMember.class, periodMemberId);
		
		if(battlePeriodMember.getStatus()==BattlePeriodMember.STATUS_FREE){
			battlePeriodMember.setStatus(BattlePeriodMember.STATUS_IN);
			sessionManager.update(battlePeriodMember);
			ResultVo resultVo = new ResultVo();
			
			resultVo.setSuccess(true);
			
			resultVo.setMsg("成功");
			
			resultVo.setData(battlePeriodMember);
			
			return resultVo;
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("状态不对");
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			return null;
		}
		
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		String battleId = httpServletRequest.getParameter("battleId");
		if(!CommonUtil.isEmpty(battleId)){
			sessionManager.setAttribute(AttrEnum.battleId, battleId);
		}
		
		Battle battle = battleService.findOne(battleId);
		
		
		
		sessionManager.save(battle);
		
 
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
