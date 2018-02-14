package com.battle.filter.element;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.BattleAccountResult;
import com.battle.service.BattleAccountResultService;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;
import com.wyc.common.wx.domain.UserInfo;

public class CurrentAccountResultFilter extends Filter{

	@Autowired
	private BattleAccountResultService battleAccountResultService;
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		BattleAccountResult battleAccountResult = battleAccountResultService.findOneByUserId(userInfo.getId());
		
		if(battleAccountResult==null){
			battleAccountResult = new BattleAccountResult();
			battleAccountResult.setAccountId(userInfo.getAccountId());
			battleAccountResult.setExp(0L);
			battleAccountResult.setFailTime(0L);
			battleAccountResult.setFightTime(0L);
			battleAccountResult.setLevel(1);
			battleAccountResult.setUserId(userInfo.getId());
			battleAccountResult.setImgUrl(userInfo.getHeadimgurl());
			battleAccountResult.setNickname(userInfo.getNickname());
			battleAccountResult.setWinTime(0l);
			
			battleAccountResultService.add(battleAccountResult);
		}
		return battleAccountResult;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		List<Class<? extends Filter>> classes = new ArrayList<>();
		classes.add(LoginStatusFilter.class);
		return classes;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
