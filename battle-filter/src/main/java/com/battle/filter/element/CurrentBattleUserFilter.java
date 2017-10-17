package com.battle.filter.element;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import com.battle.domain.BattleUser;
import com.battle.service.BattleUserService;
import com.wyc.AttrEnum;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;


//当前比赛用户
public class CurrentBattleUserFilter extends Filter{

	@Autowired
	private BattleUserService battleUserService;
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		String battleId = (String)sessionManager.getAttribute(AttrEnum.battleId);
		String userId = (String)sessionManager.getAttribute(AttrEnum.userInfoId);
		String openId = (String)sessionManager.getAttribute(AttrEnum.userInfoOpenId);
		String defaultRoomId = (String)sessionManager.getAttribute(AttrEnum.defaultRoomId);
		BattleUser battleUser = battleUserService.findOneByUserIdAndBattleId(userId,battleId);
		
		
		if(CommonUtil.isEmpty(battleId)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("battleId不能为空");
			
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			
			return null;
		}
		
		if(CommonUtil.isEmpty(userId)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("userId不能为空");
			
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			
			return null;
		}
		
		if(CommonUtil.isEmpty(openId)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("openId不能为空");
			
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			
			return null;
		}
		
		if(battleUser==null){
			battleUser = new BattleUser();
			battleUser.setBattleId(battleId);
			battleUser.setUserId(userId);
			battleUser.setOpenId(openId);
			battleUser.setIsCreater(0);
			battleUser.setIsManager(0);
			battleUser.setCurrentRoomId(defaultRoomId);
			battleUserService.add(battleUser);
		}
		
		return battleUser;
	}

	//1 userId参数和UserInfo对象必须有一个在内存当中 2.battleId和Battle对象必须有一个在内存当中
	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
	
		String battleId = (String)sessionManager.getAttribute(AttrEnum.battleId);
		
		if(CommonUtil.isEmpty(battleId)){
			HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
			battleId = httpServletRequest.getParameter("battleId");
			sessionManager.setAttribute(AttrEnum.battleId, battleId);
		}
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
