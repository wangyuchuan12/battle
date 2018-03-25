package com.battle.filter.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattleRoomService;
import com.wyc.AttrEnum;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;

public class BattleMembersApiFilter extends Filter{

	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		String battleId = (String)sessionManager.getAttribute(AttrEnum.battleId);
		String periodId = (String)sessionManager.getAttribute(AttrEnum.periodId);
		
		String roomId = (String)sessionManager.getAttribute(AttrEnum.roomId);
		
		String groupId = (String)sessionManager.getAttribute(AttrEnum.groupId);
		
		Sort sort = new Sort(Direction.DESC,"score");
		Pageable pageable = new PageRequest(0,100,sort);
		
		List<Integer> statuses = new ArrayList<>();
		
		statuses.add(BattlePeriodMember.STATUS_IN);
		statuses.add(BattlePeriodMember.STATUS_COMPLETE);
		statuses.add(BattlePeriodMember.STATUS_END);
		statuses.add(BattlePeriodMember.STATUS_OUT);
		List<BattlePeriodMember> members = new ArrayList<>();
		
		if(!CommonUtil.isEmpty(groupId)){
			members = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndGroupIdAndIsDel(battleId, periodId, roomId, statuses, groupId, 0, pageable);
		}else{
			members = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDel(battleId,periodId,roomId,statuses,0,pageable);
		}
				
				
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(members);
		
		return resultVo;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		String battleId = httpServletRequest.getParameter("battleId");
		
		String roomId = httpServletRequest.getParameter("roomId");
		
		String groupId = httpServletRequest.getParameter("groupId");
		
		
		BattleRoom battleRoom = battleRoomService.findOne(roomId);
		
		
		sessionManager.setAttribute(AttrEnum.battleId, battleId);
		sessionManager.setAttribute(AttrEnum.roomId, roomId);
		sessionManager.setAttribute(AttrEnum.periodId, battleRoom.getPeriodId());
		sessionManager.setAttribute(AttrEnum.groupId, groupId);
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
