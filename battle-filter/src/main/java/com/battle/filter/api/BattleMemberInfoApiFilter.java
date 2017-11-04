package com.battle.filter.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleUser;
import com.battle.filter.element.CurrentBattlePeriodMemberFilter;
import com.battle.filter.element.CurrentBattleUserFilter;
import com.battle.filter.element.CurrentLoveCoolingFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.wyc.AttrEnum;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class BattleMemberInfoApiFilter extends Filter{
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		BattleUser battleUser = sessionManager.getObject(BattleUser.class);
		
		BattleRoom battleRoom = sessionManager.findOne(BattleRoom.class, battlePeriodMember.getRoomId());
		
		Map<String, Object> data = new HashMap<>();
		data.put("battleId", battlePeriodMember.getBattleId());
		data.put("battleUserId", battlePeriodMember.getBattleUserId());
		data.put("headImg", battlePeriodMember.getHeadImg());
		data.put("id", battlePeriodMember.getId());
		data.put("loveCount", battlePeriodMember.getLoveCount());
		data.put("loveResidule", battlePeriodMember.getLoveResidule());
		data.put("nickname", battlePeriodMember.getNickname());
		data.put("periodId", battlePeriodMember.getPeriodId());
		data.put("process", battlePeriodMember.getProcess());
		data.put("stageCount", battlePeriodMember.getStageCount());
		data.put("stageIndex", battlePeriodMember.getStageIndex());
		
		data.put("loveCount", battlePeriodMember.getLoveCount());
		
		data.put("loveResidule", battlePeriodMember.getLoveResidule());
		
		data.put("status", battlePeriodMember.getStatus());
		
		data.put("isCreater", battleUser.getIsCreater());
		data.put("isManager", battleUser.getIsManager());
		data.put("openId", battleUser.getOpenId());
		data.put("userId", battleUser.getUserId());
		
		data.put("roomId", battlePeriodMember.getRoomId());
		
		data.put("speedCoolBean", battleRoom.getSpeedCoolBean());
		data.put("speedCoolSecond", battleRoom.getSpeedCoolSecond());
		
		ResultVo resultVo = new ResultVo();
		resultVo.setData(data);
		resultVo.setSuccess(true);
		resultVo.setMsg("返回数据");
		return resultVo;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		
		String battleId = httpServletRequest.getParameter("battleId");
		
		String roomId = httpServletRequest.getParameter("roomId");
		
		sessionManager.setAttribute(AttrEnum.battleId, battleId);
		
		sessionManager.setAttribute(AttrEnum.roomId, roomId);
		
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		List<Class<? extends Filter>> classes = new ArrayList<>();
		classes.add(LoginStatusFilter.class);
		classes.add(CurrentBattleUserFilter.class);
		classes.add(CurrentBattlePeriodMemberFilter.class);
		classes.add(CurrentLoveCoolingFilter.class);
		return classes;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
