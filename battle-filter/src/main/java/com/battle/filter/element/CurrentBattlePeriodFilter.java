package com.battle.filter.element;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.BattlePeriod;
import com.battle.service.BattlePeriodService;
import com.wyc.AttrEnum;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

//获取当前期数
public class CurrentBattlePeriodFilter extends Filter{

	@Autowired
	private BattlePeriodService battlePeriodService;
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		String battleId = (String)sessionManager.getAttribute(AttrEnum.battleId);
		Integer index = (Integer)sessionManager.getAttribute(AttrEnum.periodIndex);
		BattlePeriod battlePeriod = battlePeriodService.findOneByBattleIdAndIndex(battleId,index);
		return battlePeriod;
	}

	
	//有两种合法情况  1.battleId和index在内存里面 2.battle对象在内存里面
	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
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
