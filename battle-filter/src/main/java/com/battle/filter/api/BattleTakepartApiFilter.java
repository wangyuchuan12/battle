package com.battle.filter.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.Battle;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.filter.element.CurrentBattlePeriodMemberFilter;
import com.battle.filter.element.CurrentBattleUserFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleService;
import com.wyc.AttrEnum;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;

public class BattleTakepartApiFilter extends Filter{
	
	@Autowired
	private BattleService battleService;
	
	@Autowired
	private BattleRoomService battleRoomService;

	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		String periodMemberId = (String)sessionManager.getAttribute(AttrEnum.periodMemberId);
		
		String roomId = (String)sessionManager.getAttribute(AttrEnum.roomId);
		
		BattlePeriodMember battlePeriodMember = sessionManager.findOne(BattlePeriodMember.class, periodMemberId);
		
		if(CommonUtil.isEmpty(roomId)){
			ResultVo resultVo = new ResultVo();
			
			resultVo.setSuccess(false);
			
			resultVo.setMsg("roomId不能为空");
			
			resultVo.setData(battlePeriodMember);
			
			return resultVo;
		}
		
		if(battlePeriodMember.getStatus()==BattlePeriodMember.STATUS_FREE){
			battlePeriodMember.setStatus(BattlePeriodMember.STATUS_IN);
			battlePeriodMember.setRoomId(roomId);
			sessionManager.update(battlePeriodMember);
			ResultVo resultVo = new ResultVo();
			
			resultVo.setSuccess(true);
			
			resultVo.setMsg("成功");
			
			resultVo.setData(battlePeriodMember);
			
			return resultVo;
		}else if(battlePeriodMember.getStatus()==BattlePeriodMember.STATUS_IN){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorCode(0);
			resultVo.setErrorMsg("状态不对");
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			return null;
		}else if(battlePeriodMember.getStatus()==BattlePeriodMember.STATUS_COMPLETE){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorCode(1);
			resultVo.setErrorMsg("状态不对");
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			return null;
		}
		
		return null;
		
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		String battleId = httpServletRequest.getParameter("battleId");
		String roomId = httpServletRequest.getParameter("roomId");
		
		sessionManager.setAttribute(AttrEnum.roomId, roomId);
		
		BattleRoom battleRoom = sessionManager.findOne(BattleRoom.class, roomId);
		
		if(battleRoom.getStatus()==BattleRoom.STATUS_FULL){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("房间已满");
			resultVo.setErrorCode(0);
			sessionManager.setReturnValue(resultVo);
			sessionManager.setReturn(true);
			return null;
		}
		
		if(battleRoom.getStatus()==BattleRoom.STATUS_END){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("已经结束");
			resultVo.setErrorCode(1);
			sessionManager.setReturnValue(resultVo);
			sessionManager.setReturn(true);
			return null;
		}
		
		Integer num = battleRoom.getNum();
		num++;
		battleRoom.setNum(num);
		
		if(battleRoom.getNum()==battleRoom.getMaxinum()){
			battleRoom.setStatus(BattleRoom.STATUS_FULL);
		}else{
			
		}
		battleRoomService.update(battleRoom);
		
		sessionManager.setAttribute(AttrEnum.periodId, battleRoom.getPeriodId());
		
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
		classes.add(CurrentBattlePeriodMemberFilter.class);
		return classes;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		System.out.println("...............handlerAfter");
		return null;
	}

}
