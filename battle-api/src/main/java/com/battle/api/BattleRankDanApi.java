package com.battle.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattleAccountResult;
import com.battle.domain.UserFriend;
import com.battle.filter.element.CurrentAccountResultFilter;
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
	@HandlerAnnotation(hanlerFilter=CurrentAccountResultFilter.class)
	public Object ranks(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		List<UserFriend> userFriends = userFrendService.findAllByUserId(userInfo.getId());
		
		List<BattleAccountResult> frendAccountResults = battleAccountResultService.findAllByUserFrendUserId(userInfo.getId());
		
		BattleAccountResult myAccountResult = battleAccountResultService.findOneByUserId(userInfo.getId());
		
		List<Map<String, Object>> frendResults = new ArrayList<>();
		
		boolean flag = true;
		
		for(BattleAccountResult battleAccountResult:frendAccountResults){
			/*System.out.println("..........nickname:"+battleAccountResult.getNickname());
			if(flag&&myAccountResult.getLevel()>battleAccountResult.getLevel()||
					(myAccountResult.getLevel()==battleAccountResult.getLevel()&&myAccountResult.getWinTime()>=battleAccountResult.getLevel())){
				Map<String, Object> result = new HashMap<>();
				
				result.put("nickname", myAccountResult.getNickname());
				
				result.put("headImg", myAccountResult.getImgUrl());
				
				result.put("level", myAccountResult.getLevel());
				
				
				if(myAccountResult.getLevel()>1){
					result.put("levelName", myAccountResult.getDanName());
				}else{
					result.put("levelName","原始人");
				}
				
				frendResults.add(result);
				flag = false;
			}*/
			
			
			Map<String, Object> result = new HashMap<>();
			
			result.put("nickname", battleAccountResult.getNickname());
			
			result.put("headImg", battleAccountResult.getImgUrl());
			
			result.put("level", battleAccountResult.getLevel());
			if(battleAccountResult.getLevel()>1){
				result.put("levelName", battleAccountResult.getDanName());
			}else{
				result.put("levelName","原始人");
			}
			
			frendResults.add(result);
		}
		
		/*if(flag){
			if(myAccountResult!=null){
				Map<String, Object> result = new HashMap<>();
				
				result.put("nickname", myAccountResult.getNickname());
				
				result.put("headImg", myAccountResult.getImgUrl());
				
				result.put("level", myAccountResult.getLevel());
				
				if(myAccountResult.getLevel()>1){
					result.put("levelName", myAccountResult.getDanName());
				}else{
					result.put("levelName","原始人");
				}
				
				frendResults.add(result);
			}else{
				
			}
		}*/
		
		Sort sort = new Sort(Direction.DESC,"level");
		Pageable pageable = new PageRequest(0,100,sort);
		Page<BattleAccountResult> allAccountResults = battleAccountResultService.findAll(pageable);
		
		List<Map<String, Object>> allResults = new ArrayList<>();
		for(BattleAccountResult battleAccountResult:allAccountResults.getContent()){
			Map<String, Object> allResultItem = new HashMap<>();
			allResultItem.put("nickname", battleAccountResult.getNickname());
			allResultItem.put("headImg", battleAccountResult.getImgUrl());
			allResultItem.put("level", battleAccountResult.getLevel());
			if(battleAccountResult.getLevel()>1){
				allResultItem.put("levelName", battleAccountResult.getDanName());
			}else{
				allResultItem.put("levelName","原始人");
			}
			allResults.add(allResultItem);
		}
		
		
		Map<String, Object> memberInfo = new HashMap<>();
		
		if(myAccountResult!=null){
			memberInfo.put("nickname", myAccountResult.getNickname());
			
			memberInfo.put("userId", myAccountResult.getUserId());
			
			memberInfo.put("headImg", myAccountResult.getImgUrl());
			
			memberInfo.put("level", myAccountResult.getLevel());
			
			if(myAccountResult.getLevel()>1){
				memberInfo.put("levelName", myAccountResult.getDanName());
			}else{
				memberInfo.put("levelName","原始人");
			}
		}else{
			memberInfo.put("nickname", userInfo.getNickname());
			
			memberInfo.put("userId", myAccountResult.getUserId());
			
			memberInfo.put("headImg", userInfo.getHeadimgurl());
			
			memberInfo.put("level", 0);
			
			memberInfo.put("levelName", "原始人");
		}
		
		
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("memberInfo", memberInfo);
		
		data.put("allMembers", allResults);
		
		data.put("frendMembers", frendResults);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(data);
		
		return resultVo;
		
	}
}
