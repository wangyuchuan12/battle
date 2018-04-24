package com.battle.filter.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.BattleMemberRank;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleUser;
import com.battle.filter.element.CurrentBattlePeriodMemberFilter;
import com.battle.filter.element.CurrentBattleUserFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleMemberRankService;
import com.wyc.AttrEnum;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class BattleMemberInfoApiFilter extends Filter{
	
	@Autowired
	private BattleMemberRankService battleMemberRankService;
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
		
		data.put("status", battlePeriodMember.getStatus());
		
		data.put("isCreater", battleUser.getIsCreater());
		data.put("isManager", battleUser.getIsManager());
		data.put("openId", battleUser.getOpenId());
		data.put("userId", battleUser.getUserId());
		
		data.put("roomId", battlePeriodMember.getRoomId());
		
		data.put("speedCoolBean", battleRoom.getSpeedCoolBean());
		data.put("speedCoolSecond", battleRoom.getSpeedCoolSecond());
		
		data.put("score", battlePeriodMember.getScore());
		
		data.put("scrollGogal", battlePeriodMember.getScrollGogal());
		
		data.put("roomProcess", battleRoom.getRoomProcess());
		
		data.put("isIncrease", battlePeriodMember.getIsIncrease());
		data.put("roomScore", battleRoom.getRoomScore());
		
		data.put("num", battleRoom.getNum());
		
		data.put("maxinum", battleRoom.getMaxinum());
		
		data.put("mininum", battleRoom.getMininum());
		
		data.put("shareTime", battlePeriodMember.getShareTime());
		
		data.put("roomStatus", battleRoom.getStatus());
		
		data.put("endType", battleRoom.getEndType());

		data.put("places",battleRoom.getPlaces());
		
		data.put("isDanRoom",battleRoom.getIsDanRoom());
		
		data.put("isDekorn", battleRoom.getIsDekorn());
		
		data.put("isFrendGroup",battleRoom.getIsFrendGroup());
		
		data.put("costBean",battleRoom.getCostBean());
		
		Long differ =(battleRoom.getStartTime().getMillis()-new DateTime().getMillis())/1000;
		
		data.put("timeDiffer",differ);
		
		if(differ<=0){
			battleRoom.setIsStart(1);
		}else{
			battleRoom.setIsStart(0);
		}
		
		sessionManager.update(battleRoom);
		
		data.put("isStart",battleRoom.getIsStart());
		
		if(battleRoom.getStatus()==BattleRoom.STATUS_END){
			BattleMemberRank battleMemberRank = battleMemberRankService.findOneByMemberId(battlePeriodMember.getId());
			if(battleMemberRank!=null){
				data.put("rank",battleMemberRank.getRank());
				data.put("rewardBean", battleMemberRank.getRewardBean());
			}
		}
		
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
		//classes.add(CurrentLoveCoolingFilter.class);
		return classes;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
