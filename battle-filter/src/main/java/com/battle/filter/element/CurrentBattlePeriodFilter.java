package com.battle.filter.element;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.BattlePeriod;
import com.battle.service.BattlePeriodService;
import com.wyc.AttrEnum;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;

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
		String battleId = (String)sessionManager.getAttribute(AttrEnum.battleId);
		Integer index = (Integer)sessionManager.getAttribute(AttrEnum.periodIndex);
		
		if(CommonUtil.isEmpty(battleId)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("battleId不能为空");
			
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			
			return null;
		}
		
		if(CommonUtil.isEmpty(index)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("index不能为空");
			
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			
			return null;
		}
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
