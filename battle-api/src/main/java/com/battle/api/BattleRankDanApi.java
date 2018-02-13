package com.battle.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattleAccountResult;
import com.battle.domain.UserFriend;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleAccountResultService;
import com.battle.service.UserFrendService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.session.SessionManager;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/battleRankDan/")
public class BattleRankDanApi {
	
	@Autowired
	private UserFrendService userFrendService;
	
	@Autowired
	private BattleAccountResultService battleAccountResultService;

	@RequestMapping(value="ranks")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object ranks(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		List<UserFriend> userFriends = userFrendService.findAllByUserId(userInfo.getId());
		
		List<BattleAccountResult> battleAccountResults = battleAccountResultService.findAllByUserFrendUserId(userInfo.getId());
		
		
		return null;
		
	}
}
