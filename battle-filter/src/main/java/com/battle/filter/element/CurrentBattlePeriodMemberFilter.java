package com.battle.filter.element;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.Battle;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodMember;
import com.battle.service.BattlePeriodMemberService;
import com.wyc.AttrEnum;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;
import com.wyc.common.wx.domain.UserInfo;

public class CurrentBattlePeriodMemberFilter extends Filter{

	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		BattlePeriod battlePeriod = sessionManager.getObject(BattlePeriod.class);
		Battle battle = sessionManager.getObject(Battle.class);
		String battleId = (String)sessionManager.getAttribute(AttrEnum.battleId);
		String battleUserId = (String)sessionManager.getAttribute(AttrEnum.battleUserId);
		String periodId = (String)sessionManager.getAttribute(AttrEnum.periodId);
		UserInfo userInfo = (UserInfo)sessionManager.getObject(UserInfo.class);
		String nickname = userInfo.getNickname();
		String imgUrl = userInfo.getHeadimgurl();
		
		BattlePeriodMember battlePeriodMember = battlePeriodMemberService.findOneByBattleIdAndBattleUserIdAndPeriodId(battleId,battleUserId,periodId);
		if(battlePeriodMember==null){
			battlePeriodMember = new BattlePeriodMember();
			battlePeriodMember.setBattleId(battleId);
			battlePeriodMember.setBattleUserId(battleUserId);
			battlePeriodMember.setPeriodId(periodId);
			battlePeriodMember.setProcess(0);
			battlePeriodMember.setNickname(nickname);
			battlePeriodMember.setHeadImg(imgUrl);
			battlePeriodMember.setStatus(BattlePeriodMember.STATUS_FREE);
			battlePeriodMemberService.add(battlePeriodMember);
		}
		
		return battlePeriodMember;
	}

	//1.battleId参数或者battle对象在内存中 2.battleUserId或者BattleUser在内存中 3.periodId或者BattlePeriod在内存中
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
