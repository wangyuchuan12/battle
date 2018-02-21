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
import com.battle.domain.Battle;
import com.battle.domain.BattleDekorn;
import com.battle.domain.BattleDekornUser;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleRoomReward;
import com.battle.filter.api.BattleMemberInfoApiFilter;
import com.battle.filter.element.CurrentBattlePeriodMemberFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleDekornService;
import com.battle.service.BattleDekornUserService;
import com.battle.service.BattleRoomRewardService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleService;
import com.battle.service.other.BattleRoomHandleService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/battle/dekorn")
public class BattleDekornApi {

	@Autowired
	private BattleDekornService battleDekornService;
	
	@Autowired
	private BattleDekornUserService battleDekornUserService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private BattleRoomRewardService battleRoomRewardService;
	
	@Autowired
	private BattleService battleService;
	
	@Autowired
	private BattleRoomHandleService battleRoomHandleService;

	
	
	@RequestMapping(value="battleDekornSign")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object battleDekornSign(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		String dekornId = httpServletRequest.getParameter("dekornId");
		
		if(CommonUtil.isEmpty(dekornId)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			
			resultVo.setErrorMsg("缺少参数");
			
			return resultVo;
		}
		
		BattleDekorn battleDekorn = battleDekornService.findOne(dekornId);
		
		if(battleDekorn==null){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			
			resultVo.setErrorMsg("输入的是无效参数");
			
			return resultVo;
		}
		
		List<BattleDekornUser> battleDekornUsers = battleDekornUserService.findAllByDekornIdAndUserIdAndIsDel(dekornId,userInfo.getId(),0);
		
		BattleDekornUser battleDekornUser = null;
		if(battleDekornUsers.size()>0){
			battleDekornUser = battleDekornUsers.get(0);
			if(battleDekornUsers.size()>1){
				BattleDekornUser battleDekornUser2 = battleDekornUsers.get(1);
				battleDekornUser2.setIsDel(1);
				battleDekornUserService.update(battleDekornUser2);
			}
		}
		
		if(battleDekornUser == null){
			battleDekornUser = new BattleDekornUser();
			battleDekornUser.setBattleId(battleDekorn.getBattleId());
			battleDekornUser.setDekornId(battleDekorn.getId());
			battleDekornUser.setIsDel(0);
			battleDekornUser.setPeriodId(battleDekorn.getPeriodId());
			battleDekornUser.setUserId(userInfo.getId());
			battleDekornUser.setMaxNum(battleDekorn.getMaxNum());
			battleDekornUser.setMinNum(battleDekorn.getMinNum());
			battleDekornUser.setScrollGogal(battleDekorn.getScoreGogal());
			battleDekornUser.setPlaces(battleDekorn.getPlaces());
			battleDekornUserService.add(battleDekornUser);
		}
		
		List<Integer> statuses = new ArrayList<>();
		statuses.add(BattleRoom.STATUS_FREE);
		statuses.add(BattleRoom.STATUS_IN);
		
		Sort sort = new Sort(Direction.DESC,"createAt");
		Pageable pageable = new PageRequest(0,1,sort);
		BattleRoom battleRoom = null;
		if(!CommonUtil.isEmpty(battleDekornUser.getRoomId())){
			battleRoom = battleRoomService.findOne(battleDekornUser.getRoomId());
		}
		
		if(battleRoom==null){
			List<BattleRoom> battleRooms = battleRoomService.findAllByDekornIdAndStatusIn(battleDekornUser.getDekornId(),statuses,pageable);
			
			if(battleRooms!=null&&battleRooms.size()>0){
				battleRoom = battleRooms.get(0);
			}else{
				Battle battle = battleService.findOne(battleDekornUser.getBattleId());
				battleRoom = battleRoomHandleService.initRoom(battle);
				battleRoom.setIsPk(1);
				battleRoom.setPeriodId(battleDekornUser.getPeriodId());
				battleRoom.setMaxinum(battleDekornUser.getMaxNum());
				battleRoom.setMininum(battleDekornUser.getMinNum());
				battleRoom.setSmallImgUrl(userInfo.getHeadimgurl());
				battleRoom.setIsSearchAble(0);
				battleRoom.setScrollGogal(battleDekornUser.getScrollGogal());
				battleRoom.setPlaces(battleDekornUser.getPlaces());
				battleRoom.setIsDanRoom(0);
				battleRoom.setIsDekorn(1);
				battleRoom.setDekornId(battleDekorn.getId());
				
				if(CommonUtil.isNotEmpty(battleDekorn.getCostBean())){
					battleRoom.setCostBean(battleDekorn.getCostBean());
				}
				
				battleRoomService.add(battleRoom);
				
				if(!CommonUtil.isEmpty(battleDekorn.getRewardBeanNo1())){
					BattleRoomReward battleRoomReward = new BattleRoomReward();
					battleRoomReward.setIsReceive(0);
					battleRoomReward.setRank(1);
					battleRoomReward.setRewardBean(battleDekorn.getRewardBeanNo1());
					battleRoomReward.setRoomId(battleRoom.getId());
					battleRoomRewardService.add(battleRoomReward);
				}
				if(!CommonUtil.isEmpty(battleDekorn.getRewardBeanNo2())){
					BattleRoomReward battleRoomReward = new BattleRoomReward();
					battleRoomReward.setIsReceive(0);
					battleRoomReward.setRank(2);
					battleRoomReward.setRewardBean(battleDekorn.getRewardBeanNo2());
					battleRoomReward.setRoomId(battleRoom.getId());
					battleRoomRewardService.add(battleRoomReward);
				}
				
				if(!CommonUtil.isEmpty(battleDekorn.getRewardBeanNo3())){
					BattleRoomReward battleRoomReward = new BattleRoomReward();
					battleRoomReward.setIsReceive(0);
					battleRoomReward.setRank(3);
					battleRoomReward.setRewardBean(battleDekorn.getRewardBeanNo3());
					battleRoomReward.setRoomId(battleRoom.getId());
					battleRoomRewardService.add(battleRoomReward);
				}
				
				if(!CommonUtil.isEmpty(battleDekorn.getRewardBeanNo4())){
					BattleRoomReward battleRoomReward = new BattleRoomReward();
					battleRoomReward.setIsReceive(0);
					battleRoomReward.setRank(4);
					battleRoomReward.setRewardBean(battleDekorn.getRewardBeanNo4());
					battleRoomReward.setRoomId(battleRoom.getId());
					battleRoomRewardService.add(battleRoomReward);
				}
				
				if(!CommonUtil.isEmpty(battleDekorn.getRewardBeanNo5())){
					BattleRoomReward battleRoomReward = new BattleRoomReward();
					battleRoomReward.setIsReceive(0);
					battleRoomReward.setRank(5);
					battleRoomReward.setRewardBean(battleDekorn.getRewardBeanNo5());
					battleRoomReward.setRoomId(battleRoom.getId());
					battleRoomRewardService.add(battleRoomReward);
				}
				
				if(!CommonUtil.isEmpty(battleDekorn.getRewardBeanNo6())){
					BattleRoomReward battleRoomReward = new BattleRoomReward();
					battleRoomReward.setIsReceive(0);
					battleRoomReward.setRank(6);
					battleRoomReward.setRewardBean(battleDekorn.getRewardBeanNo6());
					battleRoomReward.setRoomId(battleRoom.getId());
					battleRoomRewardService.add(battleRoomReward);
				}
				
				if(!CommonUtil.isEmpty(battleDekorn.getRewardBeanNo7())){
					BattleRoomReward battleRoomReward = new BattleRoomReward();
					battleRoomReward.setIsReceive(0);
					battleRoomReward.setRank(7);
					battleRoomReward.setRewardBean(battleDekorn.getRewardBeanNo7());
					battleRoomReward.setRoomId(battleRoom.getId());
					battleRoomRewardService.add(battleRoomReward);
				}
				
				if(!CommonUtil.isEmpty(battleDekorn.getRewardBeanNo8())){
					BattleRoomReward battleRoomReward = new BattleRoomReward();
					battleRoomReward.setIsReceive(0);
					battleRoomReward.setRank(8);
					battleRoomReward.setRewardBean(battleDekorn.getRewardBeanNo8());
					battleRoomReward.setRoomId(battleRoom.getId());
					battleRoomRewardService.add(battleRoomReward);
				}
				
				if(!CommonUtil.isEmpty(battleDekorn.getRewardBeanNo9())){
					BattleRoomReward battleRoomReward = new BattleRoomReward();
					battleRoomReward.setIsReceive(0);
					battleRoomReward.setRank(9);
					battleRoomReward.setRewardBean(battleDekorn.getRewardBeanNo9());
					battleRoomReward.setRoomId(battleRoom.getId());
					battleRoomRewardService.add(battleRoomReward);
				}
				
				if(!CommonUtil.isEmpty(battleDekorn.getRewardBeanNo10())){
					BattleRoomReward battleRoomReward = new BattleRoomReward();
					battleRoomReward.setIsReceive(0);
					battleRoomReward.setRank(10);
					battleRoomReward.setRewardBean(battleDekorn.getRewardBeanNo10());
					battleRoomReward.setRoomId(battleRoom.getId());
					battleRoomRewardService.add(battleRoomReward);
				}
			}
		}
		
		battleDekornUser.setRoomId(battleRoom.getId());
		
		battleDekornUserService.update(battleDekornUser);
		
		ResultVo resultVo = new ResultVo();
		
		Map<String, Object> data = new HashMap<>();
		data.put("roomId", battleRoom.getId());
		data.put("periodId", battleRoom.getPeriodId());
		data.put("battleId", battleRoom.getBattleId());
		
		resultVo.setData(data);
		
		resultVo.setSuccess(true);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="dekornRoomInfo")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=BattleMemberInfoApiFilter.class)
	public Object dekornRoomInfo(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		BattleRoom battleRoom = sessionManager.getObject(BattleRoom.class);
		
		
		
		List<BattleRoomReward> battleRoomRewards = battleRoomRewardService.findAllByRoomIdOrderByRankAsc(battleRoom.getId()); 
	
		
		ResultVo resultVo = sessionManager.getObject(ResultVo.class);
		
		Map<String, Object> data = (Map<String, Object>) resultVo.getData();
		
		
		Map<String, Object> roomData = new HashMap<>();
		roomData.put("id", battleRoom.getId());
		roomData.put("achievementType", battleRoom.getAchievementType());
		roomData.put("battleId", battleRoom.getBattleId());
		roomData.put("costBean", battleRoom.getCostBean());
		roomData.put("costMasonry", battleRoom.getCostMasonry());
		roomData.put("danId", battleRoom.getDanId());
		roomData.put("dekornId", battleRoom.getDekornId());
		roomData.put("endEnable", battleRoom.getEndEnable());
		roomData.put("status", battleRoom.getStatus());
		roomData.put("num", battleRoom.getNum());
		roomData.put("maxinum", battleRoom.getMaxinum());
		roomData.put("mininum", battleRoom.getMininum());
		data.put("room", roomData);
		data.put("rewards", battleRoomRewards);
		
		resultVo.setSuccess(true);
		
		resultVo.setData(data);
		
		return resultVo;
	}
	
	
	
	
	@RequestMapping(value="dekornList")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object dekornList(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		List<BattleDekorn> battleDekorns = battleDekornService.findAllByIsDel(0);
		
		List<BattleDekornUser> battleDekornUsers = battleDekornUserService.findAllByUserIdAndIsDel(userInfo.getId(), 0);
		Map<String, BattleDekornUser> battleDekornUserMap = new HashMap<>();
		
		for(BattleDekornUser battleDekornUser:battleDekornUsers){
			battleDekornUserMap.put(battleDekornUser.getDekornId(), battleDekornUser);
		}
		
		List<Map<String, Object>> responseDekorns = new ArrayList<>();
		
		for(BattleDekorn battleDekorn:battleDekorns){
			
			BattleDekornUser battleDekornUser  = battleDekornUserMap.get(battleDekorn.getId());
			Map<String, Object> responseDekorn = new HashMap<>();
			responseDekorn.put("id", battleDekorn.getId());
			responseDekorn.put("battleId", battleDekorn.getBattleId());
			responseDekorn.put("name", battleDekorn.getName());
			responseDekorn.put("level", battleDekorn.getLevel());
			responseDekorn.put("periodId", battleDekorn.getPeriodId());
			responseDekorn.put("rewardBeanNo1", battleDekorn.getRewardBeanNo1());
			responseDekorn.put("rewardBeanNo2", battleDekorn.getRewardBeanNo2());
			responseDekorn.put("rewardBeanNo3", battleDekorn.getRewardBeanNo3());
			responseDekorn.put("rewardBeanNo4", battleDekorn.getRewardBeanNo4());
			responseDekorn.put("rewardBeanNo5", battleDekorn.getRewardBeanNo5());
			responseDekorn.put("rewardBeanNo6", battleDekorn.getRewardBeanNo6());
			responseDekorn.put("rewardBeanNo7", battleDekorn.getRewardBeanNo7());
			responseDekorn.put("rewardBeanNo8", battleDekorn.getRewardBeanNo8());
			responseDekorn.put("rewardBeanNo9", battleDekorn.getRewardBeanNo9());
			responseDekorn.put("rewardBeanNo10", battleDekorn.getRewardBeanNo10());
			
			if(battleDekornUser==null){
				responseDekorn.put("status", BattleDekornUser.FREE_STATUS);
			}else{
				responseDekorn.put("status", battleDekornUser.getStatus());
			}
			
			responseDekorns.add(responseDekorn);
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(responseDekorns);
		
		return resultVo;
	}
}
