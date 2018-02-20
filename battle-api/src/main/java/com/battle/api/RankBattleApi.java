package com.battle.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.battle.domain.BattleGroupConfig;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoomGroup;
import com.battle.domain.BattleRoomGroupMember;
import com.battle.domain.UserFriend;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleGroupConfigService;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattleRoomGroupMemberService;
import com.battle.service.BattleRoomGroupService;
import com.battle.service.UserFrendService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/rankBattle/")
public class RankBattleApi {

	@Autowired
	private BattleGroupConfigService battleGroupConfigService;
	
	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;

	@Autowired
	private WxUserInfoService wxUserInfoService;
	
	@Autowired
	private BattleRoomGroupService battleRoomGroupService;
	
	@Autowired
	private BattleRoomGroupMemberService battleRoomGroupMemberService;
	
	@Autowired
	private UserFrendService userFrendService;
	
	
	@RequestMapping(value="registRankBattle")
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	@ResponseBody
	@Transactional
	public Object registRankBattle(HttpServletRequest httpServletRequest)throws Exception{

		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		
		UserFriend mySelfFriend = userFrendService.findOneByUserIdAndFriendUserId(userInfo.getId(), userInfo.getId());
		
		if(mySelfFriend==null){
			mySelfFriend = new UserFriend();
			mySelfFriend.setFriendUserId(userInfo.getId());
			mySelfFriend.setUserId(userInfo.getId());
			mySelfFriend.setUserName(userInfo.getNickname());
			mySelfFriend.setUserImg(userInfo.getHeadimgurl());
			mySelfFriend.setMeetTime(new DateTime());
			userFrendService.add(mySelfFriend);
		}
		
		
		BattleRoomGroup myBattleRoomGroup =null;
		BattleGroupConfig battleGroupConfig = null;
		if(userInfo.getIsCreateFrendGroup()==null||userInfo.getIsCreateFrendGroup()==0){
			List<BattleGroupConfig> battleGroupConfigs = battleGroupConfigService.findAllByCode(BattleGroupConfig.CURRENT_FREND_ROOM_CODE);
			
			if(battleGroupConfigs==null||battleGroupConfigs.size()==0){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				
				resultVo.setErrorMsg("群配置为空");
				
				resultVo.setErrorCode(400);
				
				return resultVo;
			}
			battleGroupConfig = battleGroupConfigs.get(0);
			myBattleRoomGroup = battleRoomGroupService.findOneByTypeAndCreaterUserId(BattleRoomGroup.FREND_TYPE, userInfo.getId());
			if(myBattleRoomGroup==null){
				myBattleRoomGroup = new BattleRoomGroup();
				myBattleRoomGroup.setCreaterUserId(userInfo.getId());
				myBattleRoomGroup.setImgUrl("");
				myBattleRoomGroup.setIndex(0);
				myBattleRoomGroup.setRoomId(battleGroupConfig.getRoomId());
				myBattleRoomGroup.setType(BattleRoomGroup.FREND_TYPE);
				myBattleRoomGroup.setBattleId(battleGroupConfig.getBattleId());
				myBattleRoomGroup.setPeriodId(battleGroupConfig.getPeriodId());
				myBattleRoomGroup.setIsDel(0);
				battleRoomGroupService.add(myBattleRoomGroup);
				
			}
			
		}
		
		if(myBattleRoomGroup==null){
			myBattleRoomGroup = battleRoomGroupService.findOneByTypeAndCreaterUserId(BattleRoomGroup.FREND_TYPE, userInfo.getId());
		}
		
		
		
		BattleRoomGroupMember myBattleRoomGroupMember2 = battleRoomGroupMemberService.findOneByGroupIdAndUserId(myBattleRoomGroup.getId(),userInfo.getId());
		if(myBattleRoomGroupMember2==null){
			myBattleRoomGroupMember2 = new BattleRoomGroupMember();
			myBattleRoomGroupMember2.setGroupId(myBattleRoomGroup.getId());
			myBattleRoomGroupMember2.setUserId(userInfo.getId());
			myBattleRoomGroupMember2.setHeadImg(userInfo.getHeadimgurl());
			myBattleRoomGroupMember2.setNickname(userInfo.getNickname());
			myBattleRoomGroupMember2.setCoverUrl(userInfo.getHeadimgurl());
			battleRoomGroupMemberService.add(myBattleRoomGroupMember2);
		}
				
		String recommendUserId = httpServletRequest.getParameter("recommendUserId");

		if(!CommonUtil.isEmpty(recommendUserId)){
			
			UserFriend userFriend = userFrendService.findOneByUserIdAndFriendUserId(userInfo.getId(),recommendUserId);
			
			
			
			UserInfo frendUserInfo = wxUserInfoService.findOne(recommendUserId);
			
			if(frendUserInfo==null){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				
				resultVo.setErrorMsg("朋友信息为空");
				
				resultVo.setErrorCode(300);
				
				return resultVo;
			}
			
			
			if(userFriend==null){
				userFriend = new UserFriend();
				userFriend.setFriendUserId(recommendUserId);
				userFriend.setUserId(userInfo.getId());
				userFriend.setUserName(userInfo.getNickname());
				userFriend.setUserImg(userInfo.getHeadimgurl());
				userFriend.setMeetTime(new DateTime());
				userFrendService.add(userFriend);
			}
			
			UserFriend userFriend2 = userFrendService.findOneByUserIdAndFriendUserId(recommendUserId,userInfo.getId());
			if(userFriend2==null){
				userFriend2 = new UserFriend();
				userFriend2.setFriendUserId(userInfo.getId());
				userFriend2.setUserId(recommendUserId);
				userFriend2.setMeetTime(new DateTime());
				userFriend2.setUserName(frendUserInfo.getNickname());
				userFriend2.setUserImg(frendUserInfo.getHeadimgurl());
				userFrendService.add(userFriend2);
			}
			
			
			
			
			if(battleGroupConfig==null){
				List<BattleGroupConfig> battleGroupConfigs = battleGroupConfigService.findAllByCode(BattleGroupConfig.CURRENT_FREND_ROOM_CODE);
				
				if(battleGroupConfigs==null||battleGroupConfigs.size()==0){
					ResultVo resultVo = new ResultVo();
					resultVo.setSuccess(false);
					
					resultVo.setErrorMsg("群配置为空");
					
					resultVo.setErrorCode(400);
					
					return resultVo;
				}
				battleGroupConfig = battleGroupConfigs.get(0);
			}
			
			BattleRoomGroup frendBattleRoomGroup = battleRoomGroupService.findOneByTypeAndCreaterUserId(BattleRoomGroup.FREND_TYPE, recommendUserId);
			if(frendBattleRoomGroup==null){
				frendBattleRoomGroup = new BattleRoomGroup();
				frendBattleRoomGroup.setCreaterUserId(recommendUserId);
				frendBattleRoomGroup.setImgUrl("");
				frendBattleRoomGroup.setIndex(0);
				frendBattleRoomGroup.setRoomId(battleGroupConfig.getRoomId());
				frendBattleRoomGroup.setType(BattleRoomGroup.FREND_TYPE);
				frendBattleRoomGroup.setBattleId(battleGroupConfig.getBattleId());
				frendBattleRoomGroup.setPeriodId(battleGroupConfig.getPeriodId());
				frendBattleRoomGroup.setIsDel(0);
				battleRoomGroupService.add(frendBattleRoomGroup);
			}
			
			
			BattleRoomGroupMember myBattleRoomGroupMember = battleRoomGroupMemberService.findOneByGroupIdAndUserId(frendBattleRoomGroup.getId(),userInfo.getId());
			if(myBattleRoomGroupMember==null){
				myBattleRoomGroupMember = new BattleRoomGroupMember();
				myBattleRoomGroupMember.setGroupId(frendBattleRoomGroup.getId());
				myBattleRoomGroupMember.setUserId(userInfo.getId());
				myBattleRoomGroupMember.setHeadImg(userInfo.getHeadimgurl());
				myBattleRoomGroupMember.setNickname(userInfo.getNickname());
				myBattleRoomGroupMember.setCoverUrl(userInfo.getHeadimgurl());
				battleRoomGroupMemberService.add(myBattleRoomGroupMember);
				
			}
			
			
			
			BattleRoomGroupMember frendBattleRoomGroupMember = battleRoomGroupMemberService.findOneByGroupIdAndUserId(myBattleRoomGroup.getId(),recommendUserId);
			if(frendBattleRoomGroupMember==null){
				frendBattleRoomGroupMember = new BattleRoomGroupMember();
				frendBattleRoomGroupMember.setGroupId(myBattleRoomGroup.getId());
				frendBattleRoomGroupMember.setUserId(recommendUserId);
				frendBattleRoomGroupMember.setNickname(frendUserInfo.getNickname());
				frendBattleRoomGroupMember.setHeadImg(frendUserInfo.getHeadimgurl());
				frendBattleRoomGroupMember.setCoverUrl(frendUserInfo.getHeadimgurl());
				battleRoomGroupMemberService.add(frendBattleRoomGroupMember);
			}
			
			BattleRoomGroupMember frendBattleRoomGroupMember2 = battleRoomGroupMemberService.findOneByGroupIdAndUserId(frendBattleRoomGroup.getId(),recommendUserId);
			if(frendBattleRoomGroupMember2==null){
				frendBattleRoomGroupMember2 = new BattleRoomGroupMember();
				frendBattleRoomGroupMember2.setGroupId(frendBattleRoomGroup.getId());
				frendBattleRoomGroupMember2.setUserId(recommendUserId);
				frendBattleRoomGroupMember2.setNickname(frendUserInfo.getNickname());
				frendBattleRoomGroupMember2.setHeadImg(frendUserInfo.getHeadimgurl());
				frendBattleRoomGroupMember2.setCoverUrl(frendUserInfo.getHeadimgurl());
				battleRoomGroupMemberService.add(frendBattleRoomGroupMember2);
			}
			
			
			frendUserInfo.setIsCreateFrendGroup(1);
			userInfo.setIsCreateFrendGroup(1);
			
			wxUserInfoService.update(frendUserInfo);
			wxUserInfoService.update(userInfo);
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="nextMyRankBattle")
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	@ResponseBody
	@Transactional
	public Object nextMyRankBattle(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		List<BattleGroupConfig> battleGroupConfigs = battleGroupConfigService.findAllByCode(BattleGroupConfig.CURRENT_FREND_ROOM_CODE);
		
		if(battleGroupConfigs==null||battleGroupConfigs.size()==0){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("朋友群组配置不能为空");
			return resultVo;
		}
		
		BattleGroupConfig battleGroupConfig = battleGroupConfigs.get(0);
		
		
		
		BattleRoomGroup battleRoomGroup = battleRoomGroupService.findOneByTypeAndCreaterUserId(BattleRoomGroup.FREND_TYPE,userInfo.getId());
	
		if(!battleGroupConfig.getRoomId().equals(battleRoomGroup.getRoomId())){
			battleRoomGroup.setRoomId(battleGroupConfig.getRoomId());
			battleRoomGroup.setBattleId(battleGroupConfig.getBattleId());
			battleRoomGroup.setPeriodId(battleGroupConfig.getPeriodId());
			
			battleRoomGroupService.update(battleRoomGroup);
			
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			resultVo.setData(battleRoomGroup);
			resultVo.setCode(0);
			
			return resultVo;
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			resultVo.setCode(1);
			resultVo.setMsg("请明天继续");
			return resultVo;
		}
	}
	
	
	@RequestMapping(value="myRankBattle")
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	@ResponseBody
	@Transactional
	public Object myRankBattle(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		List<BattleGroupConfig> battleGroupConfigs = battleGroupConfigService.findAllByCode(BattleGroupConfig.CURRENT_FREND_ROOM_CODE);
	
		if(battleGroupConfigs==null||battleGroupConfigs.size()==0){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("朋友群组配置不能为空");
			return resultVo;
		}
		
		BattleGroupConfig battleGroupConfig = battleGroupConfigs.get(0);
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		BattleRoomGroup battleRoomGroup = battleRoomGroupService.findOneByTypeAndCreaterUserId(BattleRoomGroup.FREND_TYPE,userInfo.getId());
		if(battleRoomGroup==null){
			/*
			battleRoomGroup = new BattleRoomGroup();
			battleRoomGroup.setCreaterUserId(userInfo.getId());
			battleRoomGroup.setImgUrl("");
			battleRoomGroup.setIndex(0);
			battleRoomGroup.setName("好友排位赛");
			battleRoomGroup.setRoomId(battleGroupConfig.getRoomId());
			battleRoomGroup.setType(BattleRoomGroup.FREND_TYPE);
			battleRoomGroupService.add(battleRoomGroup);*/
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("未初始化battleRoomGroup");
			return resultVo;
		}
		
		
		BattleRoomGroupMember battleRoomGroupMember = battleRoomGroupMemberService.findOneByGroupIdAndUserId(battleRoomGroup.getId(), userInfo.getId());
		
		String roomId = battleGroupConfig.getRoomId();
		
		
		
		Sort sort = new Sort(Direction.DESC,"score");
		Pageable pageable = new PageRequest(0, 100,sort);
		List<Integer> statuses = new ArrayList<>();
		statuses.add(BattlePeriodMember.STATUS_COMPLETE);
		statuses.add(BattlePeriodMember.STATUS_IN);
	
		List<Map<String, Object>> responseMembers = new ArrayList<>();
		
		List<BattlePeriodMember> battlePeriodMembers = battlePeriodMemberService.
				findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndGroupIdAndIsDel(battleGroupConfig.getBattleId(), 
						battleGroupConfig.getPeriodId(), roomId, statuses, battleRoomGroup.getId(), 0, pageable);
		
		
		List<BattlePeriodMember> allPeriodMembers = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomId(battleRoomGroup.getBattleId(), battleRoomGroup.getPeriodId(), roomId, pageable);
		
		Map<String, BattlePeriodMember> battlePeriodMemberMap = new HashMap<>();
		for(BattlePeriodMember battlePeriodMember:battlePeriodMembers){
			Map<String, Object> responseMember = new HashMap<>();
			responseMember.put("nickname", battlePeriodMember.getNickname());
			responseMember.put("headImg", battlePeriodMember.getHeadImg());
			responseMember.put("score", battlePeriodMember.getScore());
			responseMembers.add(responseMember);
			battlePeriodMemberMap.put(battlePeriodMember.getUserId(),battlePeriodMember);
		}
		
		
		
		List<BattleRoomGroupMember> battleRoomGroupMembers = battleRoomGroupMemberService.findAllByGroupId(battleRoomGroup.getId());
		
		for(BattleRoomGroupMember thisBattleRoomGroupMember:battleRoomGroupMembers){
			BattlePeriodMember battlePeriodMember = battlePeriodMemberMap.get(thisBattleRoomGroupMember.getUserId());
			if(battlePeriodMember==null){
				Map<String, Object> responseMember = new HashMap<>();
				responseMember.put("nickname", thisBattleRoomGroupMember.getNickname());
				responseMember.put("headImg", thisBattleRoomGroupMember.getHeadImg());
				responseMember.put("score",0);
				responseMembers.add(responseMember);
			}
		}
	
		
		
		
		BattlePeriodMember battlePeriodMember = battlePeriodMemberService.findOneByRoomIdAndUserIdAndIsDel(roomId, userInfo.getId(), 0);
		
		
		
		
		Map<String, Object> data = new HashMap<>();
		
		if(battlePeriodMember!=null){
			
			long frendRank = 0L;
			for(Integer i = 0;i<battlePeriodMembers.size();i++){
				BattlePeriodMember battlePeriodMember2 = battlePeriodMembers.get(i);
				if(battlePeriodMember.getId().equals(battlePeriodMember2.getId())){
					frendRank = Long.parseLong((i+1)+""); 
				}
			}
			data.put("memberInfo", battlePeriodMember);
			
			if(frendRank==0L){
				data.put("frendRank", battlePeriodMembers.size());
			}else{
				data.put("frendRank", frendRank);
			}
			
			long allRank = battlePeriodMemberService.rank(roomId, battlePeriodMember.getScore());
			data.put("allRank", allRank);
			
			
		}else{
			data.put("memberInfo", battleRoomGroupMember);
			data.put("frendRank", battlePeriodMembers.size());
			
			long allRank = battlePeriodMemberService.rank(roomId,0);
			data.put("allRank", allRank);
			
		}
		
		
		
		if(battlePeriodMembers!=null&&battlePeriodMembers.size()>0){
			BattlePeriodMember firstBattlePeriodMember = battlePeriodMembers.get(0);
			BattleRoomGroupMember firstBattleRoomGroupMember = battleRoomGroupMemberService.findOneByGroupIdAndUserId(battleRoomGroup.getId(), firstBattlePeriodMember.getUserId());
			
			data.put("firstMemberInfo", firstBattleRoomGroupMember);
		}else{
			BattleRoomGroupMember firstBattleRoomGroupMember = battleRoomGroupMemberService.findOneByGroupIdAndUserId(battleRoomGroup.getId(), userInfo.getId());
			
			data.put("firstMemberInfo", firstBattleRoomGroupMember);
		}
		
		data.put("frendMembers", responseMembers);
		
		data.put("allMembers", allPeriodMembers);
		
		data.put("groupInfo", battleRoomGroup);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(data);
		
		return resultVo;
	}
}
