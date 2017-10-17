package com.battle.filter.element;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattlePeriodService;
import com.battle.service.BattleRoomService;
import com.wyc.AttrEnum;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

public class CurrentBattlePeriodMemberFilter extends Filter{

	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Autowired
	private BattlePeriodService battlePeriodService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		String memberId = (String)sessionManager.getAttribute(AttrEnum.periodMemberId);
		
		
		
		
		BattlePeriodMember battlePeriodMember = null;
		if(!CommonUtil.isEmpty(memberId)){
			battlePeriodMember = battlePeriodMemberService.findOne(memberId);
		}
		
		if(battlePeriodMember!=null){
			return battlePeriodMember;
		}
		
		String battleUserId = (String)sessionManager.getAttribute(AttrEnum.battleUserId);
		String roomId = (String)sessionManager.getAttribute(AttrEnum.roomId);
		UserInfo userInfo = (UserInfo)sessionManager.getObject(UserInfo.class);
		String nickname = userInfo.getNickname();
		String imgUrl = userInfo.getHeadimgurl();
		
		battlePeriodMember = battlePeriodMemberService.findOneByRoomIdAndBattleUserIdAndIsDel(roomId,battleUserId,0);
		if(battlePeriodMember==null){
			BattleRoom battleRoom = battleRoomService.findOne(roomId);
			String periodId = battleRoom.getPeriodId();
			String battleId = battleRoom.getBattleId();
			BattlePeriod battlePeriod = battlePeriodService.findOne(periodId);
			battlePeriodMember = new BattlePeriodMember();
			battlePeriodMember.setBattleId(battleId);
			battlePeriodMember.setBattleUserId(battleUserId);
			battlePeriodMember.setPeriodId(periodId);
			battlePeriodMember.setProcess(0);
			battlePeriodMember.setNickname(nickname);
			battlePeriodMember.setHeadImg(imgUrl);
			battlePeriodMember.setStatus(BattlePeriodMember.STATUS_FREE);
			battlePeriodMember.setLoveCount(5);
			battlePeriodMember.setLoveResidule(5);
			battlePeriodMember.setStageIndex(1);
			battlePeriodMember.setStageCount(battlePeriod.getStageCount());
			battlePeriodMember.setIsDel(0);
			battlePeriodMember.setRoomId(roomId);
			
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
