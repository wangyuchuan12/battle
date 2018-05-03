package com.battle.filter.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.battle.domain.Battle;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.filter.element.CurrentBattlePeriodMemberFilter;
import com.battle.filter.element.CurrentBattleUserFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattlePeriodService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleService;
import com.battle.socket.service.BattleRoomStartService;
import com.battle.socket.service.BattleRoomTakepartService;
import com.battle.socket.task.RoomStartTask;
import com.wyc.AttrEnum;
import com.wyc.common.domain.Account;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.service.AccountService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

public class BattleTakepartApiFilter extends Filter{
	
	@Autowired
	private BattleService battleService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private BattlePeriodService BattlePeriodService;
	
	@Autowired
	private BattlePeriodService battlePeriodService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BattleRoomTakepartService battleRoomTakepartService;
	
	@Autowired
	private RoomStartTask roomStartTask;
	
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		String periodMemberId = (String)sessionManager.getAttribute(AttrEnum.periodMemberId);
		
		String roomId = (String)sessionManager.getAttribute(AttrEnum.roomId);
		
		String danId = (String)sessionManager.getAttribute(AttrEnum.danId);
		
		
		BattlePeriodMember battlePeriodMember = sessionManager.findOne(BattlePeriodMember.class, periodMemberId);
		
		if(!CommonUtil.isEmpty(danId)){
			battlePeriodMember.setDanId(danId);
		}

		
		BattleRoom battleRoom = sessionManager.getObject(BattleRoom.class);
		
		battlePeriodMember.setIsIncrease(battleRoom.getIsIncrease());
		
		if(battleRoom.getStatus()==BattleRoom.STATUS_END){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("已经结束");
			resultVo.setErrorCode(3);
			sessionManager.setReturnValue(resultVo);
			sessionManager.setReturn(true);
			return null;
		}
		
		Integer costBean = battleRoom.getCostBean();
		if(costBean==null){
			costBean = 0;
		}
		
		Integer costMasonry = battleRoom.getCostMasonry();
		if(costMasonry==null){
			costMasonry = 0;
		}
		
		if(CommonUtil.isEmpty(roomId)){
			ResultVo resultVo = new ResultVo();
			
			resultVo.setSuccess(false);
			
			resultVo.setMsg("roomId不能为空");
			
			resultVo.setData(battlePeriodMember);
			
			return resultVo;
		}
		
		/*if(battleRoom.getStatus()==BattleRoom.STATUS_FULL&&battlePeriodMember.getStatus()!=BattlePeriodMember.STATUS_IN){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("房间已满");
			resultVo.setErrorCode(2);
			sessionManager.setReturnValue(resultVo);
			sessionManager.setReturn(true);
			return null;
		}*/
		
		if(battlePeriodMember.getStatus()==BattlePeriodMember.STATUS_FREE||battlePeriodMember.getStatus()==BattlePeriodMember.STATUS_OUT){
			
			UserInfo userInfo = sessionManager.getObject(UserInfo.class);
			
			Account account = accountService.fineOneSync(userInfo.getAccountId());
			Long wisdomCount = account.getWisdomCount();
			if(wisdomCount==null){
				wisdomCount = 0L;
			}
			Integer masonry = account.getMasonry();
			if(masonry==null){
				masonry = 0;
			}
			
			if(wisdomCount<costBean){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("智慧豆不足");
				resultVo.setErrorCode(4);
				return resultVo;
			}
			
			if(masonry<costMasonry){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("砖石不足");
				resultVo.setErrorCode(5);
				return resultVo;
			}
			
			
			wisdomCount = wisdomCount-costBean;
			masonry = masonry-costMasonry;
			
			account.setWisdomCount(wisdomCount);
			account.setMasonry(masonry);
			
			accountService.update(account);
			
			
			battlePeriodMember.setStatus(BattlePeriodMember.STATUS_IN);
			battlePeriodMember.setRoomId(roomId);
			sessionManager.update(battlePeriodMember);
			
			BattlePeriod battlePeriod = battlePeriodService.findOne(battlePeriodMember.getPeriodId());
			Integer takepartCount = battlePeriod.getTakepartCount();
			if(takepartCount==null){
				takepartCount = 0;
			}
			takepartCount++;
			battlePeriod.setTakepartCount(takepartCount);
			BattlePeriodService.update(battlePeriod);
			
			battleRoomTakepartService.takepartPublish(battlePeriodMember);
			
			
			/*
			Long differ =(battleRoom.getStartTime().getMillis()-new DateTime().getMillis())/1000;
			Integer num = battleRoom.getNum();
			Integer mininum = battleRoom.getMininum();
			if(num==null){
				num  = 0;
			}
			if(mininum==null){
				mininum = 0;
			}
			if(differ<=0&&num>=mininum){
				
			}*/
			
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
		
		String danId = httpServletRequest.getParameter("danId");
		
		if(!CommonUtil.isEmpty(roomId)){
			sessionManager.setAttribute(AttrEnum.roomId, roomId);
		}
		
		if(!CommonUtil.isEmpty(danId)){
			sessionManager.setAttribute(AttrEnum.danId, danId);
		}
		
		BattleRoom battleRoom = sessionManager.findOne(BattleRoom.class, roomId);
		
		
		Integer num = battleRoom.getNum();
		num++;
		battleRoom.setNum(num);
		
		if(battleRoom.getNum()>=battleRoom.getMaxinum()){
			battleRoom.setStatus(BattleRoom.STATUS_FULL);
		}else if(battleRoom.getStatus()==BattleRoom.STATUS_FREE){
			battleRoom.setStatus(BattleRoom.STATUS_IN);
		}
		sessionManager.update(battleRoom);
		
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
		return null;
	}

}
