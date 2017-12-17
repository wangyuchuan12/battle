package com.battle.filter.element;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.BattleExpert;
import com.battle.domain.BattleUser;
import com.battle.service.BattleExpertService;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;
import com.wyc.common.wx.domain.UserInfo;

public class BattleManagerFilter extends Filter{

	@Autowired
	private BattleExpertService battleExpertService;
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		BattleUser battleUser = sessionManager.getObject(BattleUser.class);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		BattleExpert battleExpert = battleExpertService.findOneByBattleIdAndBattleUserId(battleUser.getBattleId(), battleUser.getId());
		
		
		if((battleUser.getIsManager()!=null&&battleUser.getIsManager()==1)||(userInfo.getIsGod()!=null&&userInfo.getIsGod()==1)){
			
		}else{
			if(battleExpert==null||battleExpert.getStatus()!=BattleExpert.AUDIT_STATUS){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("没有权限");
				sessionManager.setEnd(true);
				return resultVo;
			}
		}
		return null;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		List<Class<? extends Filter>> classes = new ArrayList<>();
		classes.add(CurrentBattleUserFilter.class);
		return classes;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
