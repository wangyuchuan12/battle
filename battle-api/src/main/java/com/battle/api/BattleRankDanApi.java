package com.battle.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattleAccountResult;
import com.battle.domain.UserFriend;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleAccountResultService;
import com.battle.service.UserFrendService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
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
		
		List<BattleAccountResult> frendAccountResults = battleAccountResultService.findAllByUserFrendUserId(userInfo.getId());
	
		
		Map<String, UserFriend> userFrendMap = new HashMap<>();
		
		for(UserFriend userFriend:userFriends){
			userFrendMap.put(userFriend.getFriendUserId(), userFriend);
		}
		
		BattleAccountResult myAccountResult = battleAccountResultService.findOneByUserId(userInfo.getId());
		
		List<Map<String, Object>> frendResults = new ArrayList<>();
		
		boolean flag = true;
		
		for(BattleAccountResult battleAccountResult:frendAccountResults){
			
			if(myAccountResult.getLevel()>battleAccountResult.getLevel()||
					(myAccountResult.getLevel()==battleAccountResult.getLevel()&&myAccountResult.getWinTime()>=battleAccountResult.getLevel())){
				Map<String, Object> result = new HashMap<>();
				
				result.put("nickname", myAccountResult.getNickname());
				
				result.put("headImg", myAccountResult.getImgUrl());
				
				result.put("level", myAccountResult.getLevel());
				
				frendResults.add(result);
				flag = false;
			}
			
			UserFriend userFriend = userFrendMap.get(battleAccountResult.getUserId());
			
			
			Map<String, Object> result = new HashMap<>();
			
			result.put("nickname", userFriend.getFrendUserName());
			
			result.put("headImg", userFriend.getFrendUserImg());
			
			result.put("level", battleAccountResult.getLevel());
			
			frendResults.add(result);
		}
		
		if(flag){
			Map<String, Object> result = new HashMap<>();
			
			result.put("nickname", myAccountResult.getNickname());
			
			result.put("headImg", myAccountResult.getImgUrl());
			
			result.put("level", myAccountResult.getLevel());
			
			frendResults.add(result);
		}
		
		Sort sort = new Sort(Direction.DESC,"level");
		Pageable pageable = new PageRequest(0,100,sort);
		List<BattleAccountResult> allAccountResults = battleAccountResultService.findAll(pageable);
		
		List<Map<String, Object>> allResults = new ArrayList<>();
		for(BattleAccountResult battleAccountResult:allAccountResults){
			Map<String, Object> allResultItem = new HashMap<>();
			allResultItem.put("nickname", battleAccountResult.getNickname());
			allResultItem.put("headImg", battleAccountResult.getImgUrl());
			allResultItem.put("level", battleAccountResult.getLevel());
			
			allResults.add(allResultItem);
		}
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("allMembers", allResults);
		
		data.put("frendMembers", frendResults);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(data);
		
		return resultVo;
		
	}
}
