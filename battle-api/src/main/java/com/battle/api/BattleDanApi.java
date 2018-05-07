package com.battle.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.Battle;
import com.battle.domain.BattleAccountResult;
import com.battle.domain.BattleDan;
import com.battle.domain.BattleDanPoint;
import com.battle.domain.BattleDanProject;
import com.battle.domain.BattleDanReward;
import com.battle.domain.BattleDanUser;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleRoomReward;
import com.battle.domain.BattleWait;
import com.battle.filter.api.BattleTakepartApiFilter;
import com.battle.filter.element.CurrentAccountResultFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleAccountResultService;
import com.battle.service.BattleDanPointService;
import com.battle.service.BattleDanProjectService;
import com.battle.service.BattleDanRewardService;
import com.battle.service.BattleDanService;
import com.battle.service.BattleDanTaskService;
import com.battle.service.BattleDanTaskUserService;
import com.battle.service.BattleDanUserPassThroughService;
import com.battle.service.BattleDanUserService;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattleRoomRewardService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleService;
import com.battle.service.BattleUserService;
import com.battle.service.BattleWaitService;
import com.battle.service.other.BattleRoomHandleService;
import com.battle.socket.service.InitRoomService;
import com.wyc.AttrEnum;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.Account;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.service.AccountService;
import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/battle/dan")
public class BattleDanApi {

	@Autowired
	private BattleDanPointService battleDanPointService;
	
	@Autowired
	private BattleDanService battleDanService;
	
	@Autowired
	private BattleDanUserService battleDanUserService;
	
	@Autowired
	private BattleDanProjectService battleDanProjectService;
	
	@Autowired
	private BattleDanTaskService battleDanTaskService;
	
	@Autowired
	private BattleDanTaskUserService battleDanTaskUserService;
	
	@Autowired
	private BattleDanUserPassThroughService battleDanUserPassThroughService;

	@Autowired
	private BattleRoomHandleService battleRoomHandleService;
	
	@Autowired
	private BattleService battleService;
	
	@Autowired
	private BattleUserService battleUserService;

	
	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BattleAccountResultService battleAccountResultService;
	
	@Autowired
	private BattleRoomRewardService battleRoomRewardService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private BattleDanRewardService battleDanRewardService;
	
	@Autowired
	private WxUserInfoService wxUserInfoService;
	
	@Autowired
	private InitRoomService initRoomService;
	
	@Autowired
	private BattleWaitService battleWaitService;
	
	final static Logger logger = LoggerFactory.getLogger(BattleDanApi.class);
	
	@RequestMapping(value="tasks")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object tasks(HttpServletRequest httpServletRequest)throws Exception{
		
		/*
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		String danId = httpServletRequest.getParameter("danId");
		List<BattleDanTask> battleDanTasks = battleDanTaskService.findAllByDanIdOrderByIndexAsc(danId);
		
		List<BattleDanTaskUser> battleDanTaskUsers = battleDanTaskUserService.findAllByDanIdAndUserIdOrderByIndexAsc(danId,userInfo.getId());
		
		if(battleDanTaskUsers==null||battleDanTaskUsers.size()==0){
			battleDanTaskUsers = new ArrayList<>();
			
			for(BattleDanTask battleDanTask:battleDanTasks){
				BattleDanTaskUser battleDanTaskUser = new BattleDanTaskUser();
				battleDanTaskUser.setBattleId(battleDanTask.getBattleId());
				battleDanTaskUser.setDanId(battleDanTask.getDanId());
				battleDanTaskUser.setGoalScore(battleDanTask.getGoalScore());
				battleDanTaskUser.setIndex(battleDanTask.getIndex());
				battleDanTaskUser.setPeriodId(battleDanTask.getPeriodId());
				battleDanTaskUser.setType(battleDanTask.getType());
				battleDanTaskUser.setStatus(BattleDanTaskUser.STATGUS_FREE);
				battleDanTaskUser.setRewardBean(battleDanTask.getRewardBean());
				battleDanTaskUser.setRewardExp(battleDanTask.getRewardExp());
				battleDanTaskUser.setName(battleDanTask.getName());
				battleDanTaskUser.setInstruction(battleDanTask.getInstruction());
				battleDanTaskUser.setButtonName(battleDanTask.getButtonName());
				BattleDanUser battleDanUser = battleDanUserService.findAllByDanIdAndUserId(danId, userInfo.getId());
				battleDanTaskUser.setDanUserId(battleDanUser.getId());
				battleDanTaskUser.setUserId(userInfo.getId());

				battleDanTaskUser.setProjectId(battleDanTask.getProjectId());
				battleDanTaskUserService.add(battleDanTaskUser);
				battleDanTaskUsers.add(battleDanTaskUser);
			}
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		resultVo.setData(battleDanTaskUsers);
		
		return resultVo;*/
		return null;
	}
	
	@RequestMapping(value="passThroughTakepartRoom")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=BattleTakepartApiFilter.class)
	public Object passThroughTakepartRoom(HttpServletRequest httpServletRequest)throws Exception{
		/*
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		BattleRoom battleRoom = sessionManager.getObject(BattleRoom.class);
		
		BattleUser battleUser = sessionManager.getObject(BattleUser.class);
		String passThroughId = httpServletRequest.getParameter("passThroughId");
		
		BattleDanUserPassThrough battleDanUserPassThrough = battleDanUserPassThroughService.fineOne(passThroughId);
		
		battleDanUserPassThrough.setIsRoomTakepart(1);
		
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		if(battleRoom.getStatus()==BattleRoom.STATUS_END){
			battlePeriodMember = battlePeriodMemberService.findOneByRoomIdAndBattleUserIdAndIsDel(battleRoom.getId(), battleUser.getId(), 0); 
		}
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("battleId", battlePeriodMember.getBattleId());
		data.put("battleUserId", battlePeriodMember.getBattleUserId());
		data.put("headImg", battlePeriodMember.getHeadImg());
		data.put("id", battlePeriodMember.getId());
		data.put("loveCount", battlePeriodMember.getLoveCount());
		data.put("loveResidule", battlePeriodMember.getLoveResidule());
		data.put("nickname", battlePeriodMember.getNickname());
		data.put("periodId", battlePeriodMember.getPeriodId());
		data.put("process", battlePeriodMember.getProcess());
		data.put("stageCount", battlePeriodMember.getStageCount());
		data.put("stageIndex", battlePeriodMember.getStageIndex());
		
		data.put("status", battlePeriodMember.getStatus());
		
		data.put("isCreater", battleUser.getIsCreater());
		data.put("isManager", battleUser.getIsManager());
		data.put("openId", battleUser.getOpenId());
		data.put("userId", battleUser.getUserId());
		
		data.put("roomId", battlePeriodMember.getRoomId());
		
		data.put("speedCoolBean", battleRoom.getSpeedCoolBean());
		data.put("speedCoolSecond", battleRoom.getSpeedCoolSecond());
		
		data.put("score", battlePeriodMember.getScore());
		
		data.put("roomProcess", battleRoom.getRoomProcess());
		data.put("roomScore", battleRoom.getRoomScore());
		
		data.put("num", battleRoom.getNum());
		
		data.put("maxinum", battleRoom.getMaxinum());
		
		data.put("mininum", battleRoom.getMininum());
		
		data.put("shareTime", battlePeriodMember.getShareTime());
		
		data.put("roomStatus", battleRoom.getStatus());
		
		data.put("endType", battleRoom.getEndType());
		
		data.put("scrollGogal", battleRoom.getScrollGogal());
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(data);
		
		return resultVo;*/
		return null;
	}
	
	@RequestMapping(value="startPassThrough")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object startPassThrough(HttpServletRequest httpServletRequest)throws Exception{
		/*
		String projectId = httpServletRequest.getParameter("projectId");
		String danId = httpServletRequest.getParameter("danId");
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		BattleDanUser battleDanUser = battleDanUserService.findOneByDanIdAndUserId(danId, userInfo.getId());
		
		BattleDanProject battleDanProject = battleDanProjectService.findOne(projectId);
	
		
		BattleDanUserPassThrough battleDanUserPassThrough = battleDanUserPassThroughService.
				findOneByBattleDanUserIdAndProjectId(battleDanUser.getId(),battleDanProject.getId());
		
		if(battleDanUserPassThrough==null){
			battleDanUserPassThrough = new BattleDanUserPassThrough();
			battleDanUserPassThrough.setBattleDanUserId(battleDanUser.getId());
			battleDanUserPassThrough.setGoalScore(battleDanProject.getPassThroughScore());
			battleDanUserPassThrough.setScore(0);
			battleDanUserPassThrough.setProjectId(battleDanProject.getId());
			battleDanUserPassThrough.setUserId(userInfo.getId());
			battleDanUserPassThrough.setStatus(BattleDanUserPassThrough.STAGTUS_IN);
			battleDanUserPassThrough.setIsRoomTakepart(0);
			battleDanUserPassThrough.setBattleId(battleDanProject.getBattleId());
			
			Battle battle = battleService.findOne(battleDanProject.getBattleId());
			
			Sort sort = new Sort(Direction.DESC,"createAt");
			Pageable pageable = new PageRequest(0,1,sort);
			
			List<BattleRoom> battleRooms = battleRoomService.findAllByBattleIdAndPeriodIdAndStatusAndIsPassThrough(battle.getId(),battleDanProject.getPeriodId(),BattleRoom.STATUS_IN,1,pageable);
			BattleRoom battleRoom;
			if(battleRooms!=null&&battleRooms.size()>0){
				battleRoom =  battleRooms.get(0);
			}
			battleRoom = battleRoomHandleService.initRoom(battle);
			BattleUser battleUser = battleUserService.findOneByUserIdAndBattleId(userInfo.getId(), battle.getId());
			battleRoom.setIsPk(0);
			battleRoom.setIsPassThrough(1);
			battleRoom.setPeriodId(battleDanProject.getPeriodId());
			battleRoom.setMaxinum(1);
			battleRoom.setMininum(1);
			battleRoom.setOwner(battleUser.getId());
			battleRoom.setSmallImgUrl(userInfo.getHeadimgurl());
			battleRoom.setIsSearchAble(0);
			battleRoom.setScrollGogal(battleDanProject.getPassThroughScore());
			battleRoomService.add(battleRoom);
			battleDanUserPassThrough.setRoomId(battleRoom.getId());
			battleDanUserPassThroughService.add(battleDanUserPassThrough);
			
			BattleDanTaskUser battleDanTaskUser = battleDanTaskUserService.findOneByDanIdAndProjectIdAndUserIdAndType(danId,projectId,userInfo.getId(),BattleDanTask.PASS_POINT_TYPE);
			
			if(battleDanTaskUser!=null){
				
				battleDanTaskUser.setRoomId(battleRoom.getId());
				
				battleDanTaskUser.setGoalScore(battleDanProject.getPassThroughScore());
				
				battleDanTaskUser.setRoomScore(0);
				
				battleDanTaskUserService.update(battleDanTaskUser);
			}
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleDanUserPassThrough);
		return resultVo;*/
		return null;
	}
	
	@RequestMapping(value="receiveTaskReward")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object receiveTaskReward(HttpServletRequest httpServletRequest)throws Exception{
		
		/*
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		String taskId = httpServletRequest.getParameter("taskId");
		BattleDanTaskUser battleDanTaskUser = battleDanTaskUserService.findOne(taskId);
		
		Integer rewardBean = battleDanTaskUser.getRewardBean();
		
		Integer rewardExp = battleDanTaskUser.getRewardExp();
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		Account account = accountService.fineOneSync(userInfo.getAccountId());
		
		BattleAccountResult battleAccountResult = battleAccountResultService.findOneByUserId(userInfo.getId());
		
		
		Long wisdomCount = account.getWisdomCount();
		Long exp = battleAccountResult.getExp();
		
		if(wisdomCount==null){
			wisdomCount = 0L;
		}
		if(exp==null){
			exp = 0L;
		}
		
		if(rewardExp==null){
			rewardExp = 0;
		}
		
		if(rewardBean==null){
			rewardBean = 0;
		}
		
		wisdomCount = wisdomCount+rewardBean;
		exp = exp+rewardExp;
		account.setWisdomCount(wisdomCount);
		battleAccountResult.setExp(exp);
		
		accountService.update(account);
		battleAccountResultService.update(battleAccountResult);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		return resultVo;*/
		return null;
	
	}
	
	@RequestMapping(value="accountResultInfo")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentAccountResultFilter.class)
	public Object accountResultInfo(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		BattleAccountResult battleAccountResult = sessionManager.getObject(BattleAccountResult.class);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("nickname", userInfo.getNickname());
		data.put("headimgurl", userInfo.getHeadimgurl());
		data.put("exp", battleAccountResult.getExp());
		data.put("level", battleAccountResult.getLevel());
		
		data.put("danName", battleAccountResult.getDanName());
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		resultVo.setData(data);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="danInfo")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object danInfo(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		String danId = httpServletRequest.getParameter("danId");
		
		List<BattleDanUser> battleDanUsers = battleDanUserService.findAllByDanIdAndUserId(danId,userInfo.getId());
		
		BattleDanUser battleDanUser = null;
		if(battleDanUsers!=null&&battleDanUsers.size()>0){
			battleDanUser = battleDanUsers.get(0);
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("没有battleDanUser对象");
			logger.error("调用danInfo方法的时候，没有battleDanUser对象");
			return resultVo;
		}
		
		List<BattleDanProject> battleDanProjects = battleDanProjectService.findAllByDanIdOrderByIndexAsc(battleDanUser.getDanId());
		
		List<Map<String, Object>> responseProjects = new ArrayList<>();
		
		for(BattleDanProject battleDanProject:battleDanProjects){
			Map<String, Object> responseProject = new HashMap<>();
			responseProject.put("id", battleDanProject.getId());
			responseProject.put("battleName", battleDanProject.getBattleName());
			responseProject.put("battleImg", battleDanProject.getBattleImg());
			
			responseProjects.add(responseProject);
		}
		
		
		
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("battleDanUser", battleDanUser);
		responseData.put("projects", responseProjects);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(responseData);
		return resultVo;
	}
	
	@RequestMapping(value="danRoomInfo")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object danRoomInfo(HttpServletRequest httpServletRequest)throws Exception{
		String danId = httpServletRequest.getParameter("danId");
	
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		List<BattleDanUser> battleDanUsers = battleDanUserService.findAllByDanIdAndUserId(danId, userInfo.getId());
		
		BattleDanUser battleDanUser = null;
		
		if(battleDanUsers!=null&&battleDanUsers.size()>0){
			battleDanUser = battleDanUsers.get(0);
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("没有battleDanUser对象");
			logger.error("调用danRoomInfo方法的时候，没有battleDanUser对象");
			return resultVo;
		}
		
		BattleRoom battleRoom=null;
		if(!CommonUtil.isEmpty(battleDanUser.getRoomId())){
			battleRoom = battleRoomService.findOne(battleDanUser.getRoomId());
		}
		List<BattleRoomReward> battleRoomRewards = new ArrayList<>();
		if(battleRoom==null){
			Sort sort = new Sort(Direction.DESC,"createAt");
			Pageable pageable = new PageRequest(0,1,sort);
			List<Integer> statuses = new ArrayList<>();
			statuses.add(BattleRoom.STATUS_FREE);
			statuses.add(BattleRoom.STATUS_IN);
			List<BattleRoom> oldBattleRooms = battleRoomService.findAllByIsDanRoomAndBattleIdAndPeriodIdAndStatusIn(1,battleDanUser.getBattleId(),battleDanUser.getPeriodId(),statuses,pageable);
			
			List<BattleRoom> battleRooms = new ArrayList<>();
			
			for(BattleRoom oldBattleRoom:oldBattleRooms){
				if(oldBattleRoom.getStartTime().isBeforeNow()){
					if(oldBattleRoom.getMininum()>oldBattleRoom.getNum()){
						battleRooms.add(oldBattleRoom);
					}
				}else{
					battleRooms.add(oldBattleRoom);
				}
			}
			
			if(battleRooms!=null&&battleRooms.size()>0){
				
				battleRoom = battleRooms.get(0);
				battleDanUser.setRoomId(battleRoom.getId());
				
				battleDanUserService.update(battleDanUser);
				
				battleRoomRewards = battleRoomRewardService.findAllByRoomIdOrderByRankAsc(battleRoom.getId());
				
			}else{
				Battle battle = battleService.findOne(battleDanUser.getBattleId());
				battleRoom = battleRoomHandleService.initRoom(battle);
				battleRoom.setIsPk(1);
				battleRoom.setPeriodId(battleDanUser.getPeriodId());
				battleRoom.setMaxinum(battleDanUser.getMaxNum());
				battleRoom.setMininum(battleDanUser.getMinNum());
				battleRoom.setSmallImgUrl(userInfo.getHeadimgurl());
				battleRoom.setIsSearchAble(0);
				battleRoom.setScrollGogal(battleDanUser.getScoreGogal());
				battleRoom.setPlaces(battleDanUser.getPlaces());
				battleRoom.setIsDanRoom(1);
				
				battleRoom.setIsIncrease(0);
				
				battleRoom.setDanId(danId);
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				
				calendar.add(Calendar.SECOND, battleDanUser.getTimeLong());

				battleRoom.setStartTime(new DateTime(calendar.getTime()));
				
				initRoomService.addRoom(battleRoom);
				
				Sort rewardSort = new Sort(Direction.ASC,"rank");
				Pageable rewardPageable = new PageRequest(0,40,rewardSort);
				List<BattleDanReward> battleDanRewards = battleDanRewardService.findAllByDanId(danId,rewardPageable);
				
				for(BattleDanReward battleDanReward:battleDanRewards){
					BattleRoomReward battleRoomReward = new BattleRoomReward();
					
					battleRoomReward.setIsReceive(0);
					battleRoomReward.setRank(battleDanReward.getRank());
					battleRoomReward.setRewardBean(battleDanReward.getRewardBean());
					battleRoomReward.setRoomId(battleRoom.getId());
					
					battleRoomRewardService.add(battleRoomReward);
					
					battleRoomRewards.add(battleRoomReward);
				}
				

				
				battleDanUser.setRoomId(battleRoom.getId());
				battleDanUserService.update(battleDanUser);

			}
		}else{

			battleDanUser.setRoomId(battleRoom.getId());
			
			battleDanUserService.update(battleDanUser);
			
			battleRoomRewards = battleRoomRewardService.findAllByRoomIdOrderByRankAsc(battleRoom.getId());
		}
		
		
		
		List<BattlePeriodMember> battlePeriodMembers = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomId(
				battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId());
		
		BattlePeriodMember myBattlePeriodMember = null;
		for(BattlePeriodMember battlePeriodMember:battlePeriodMembers){
			if(battlePeriodMember.getUserId().equals(userInfo.getId())){
				myBattlePeriodMember = battlePeriodMember;
			}
		}
		Map<String, Object> data = new HashMap<>();
		data.put("name", battleRoom.getName());
		data.put("places",battleRoom.getPlaces());
		data.put("roomId", battleRoom.getId());
		data.put("battleId", battleRoom.getBattleId());
		data.put("rewards", battleRoomRewards);
		data.put("members", battlePeriodMembers);
		
		data.put("maxinum", battleRoom.getMaxinum());
		
		data.put("mininum", battleRoom.getMininum());
		
		data.put("roomStatus", battleRoom.getStatus());
		
		
		
		Long differ =(battleRoom.getStartTime().getMillis()-new DateTime().getMillis())/1000;
		
		data.put("timeDiffer",differ);
		
		if(!CommonUtil.isEmpty(myBattlePeriodMember)){
			data.put("status", myBattlePeriodMember.getStatus());
		}else{
			data.put("status", BattlePeriodMember.STATUS_FREE);
		}
		
		
		data.put("num", battleRoom.getNum());
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(data);
		
		return resultVo;
	}
	
	@RequestMapping(value="battleDanSign")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object battleDanSign(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		String danId = httpServletRequest.getParameter("danId");
		
		if(CommonUtil.isEmpty(danId)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			
			resultVo.setErrorMsg("缺少参数");
			
			return resultVo;
		}
		
		BattleDan battleDan = battleDanService.findOne(danId);
		
		if(battleDan==null){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			
			resultVo.setErrorMsg("输入的是无效参数");
			
			return resultVo;
		}
		
		
		List<BattleDanUser> battleDanUsers = battleDanUserService.findAllByDanIdAndUserId(danId, userInfo.getId());
		
		BattleDanUser battleDanUser = null;
		if(battleDanUsers.size()>0){
			battleDanUser = battleDanUsers.get(0);
			if(battleDanUsers.size()>1){
				BattleDanUser battleDanUser2 = battleDanUsers.get(1);
				battleDanUser2.setIsDel(1);
				battleDanUserService.update(battleDanUser2);
			}
		}
		
		
		if(battleDanUser==null){
			battleDanUser = new BattleDanUser();
			battleDanUser.setStatus(BattleDanUser.STATUS_FREE);
			battleDanUser.setDanId(battleDan.getId());
			battleDanUser.setDanName(battleDan.getName());
			battleDanUser.setImgUrl(battleDan.getImgUrl());
			battleDanUser.setLevel(battleDan.getLevel());
			battleDanUser.setPointId(battleDan.getPointId());
			
			battleDanUser.setBattleId(battleDan.getBattleId());
			
			battleDanUser.setPeriodId(battleDan.getPeriodId());
			
			battleDanUser.setPlaces(battleDan.getPlaces());
			
			battleDanUser.setUserId(userInfo.getId());
			
			battleDanUser.setSign1BeanCost(battleDan.getSign1BeanCost());
			
			battleDanUser.setSign2BeanCost(battleDan.getSign2BeanCost());
			
			battleDanUser.setSign3BeanCost(battleDan.getSign3BeanCost());
			
			battleDanUser.setSign4BeanCost(battleDan.getSign4BeanCost());
			
			battleDanUser.setIsSign(0);
			
			battleDanUser.setSignCount(0);
			
			battleDanUser.setScoreGogal(battleDan.getScoreGogal());
			
			battleDanUser.setMaxNum(battleDan.getMaxNum());
			
			battleDanUser.setTimeLong(battleDan.getTimeLong());
			
			battleDanUser.setMinNum(battleDan.getMinNum());
			
			battleDanUser.setLoveCount(battleDan.getLoveCount());
			
			battleDanUser.setIsDel(0);
			
			battleDanUserService.add(battleDanUser);
			
			battleDanUsers.add(battleDanUser);
		}
		
		Integer signCount = battleDanUser.getSignCount();
		
		Integer beanCost = 0;
		if(signCount == 0){
			beanCost = battleDanUser.getSign1BeanCost();
		}else if(signCount == 1){
			beanCost = battleDanUser.getSign2BeanCost();
		}else if(signCount == 2){
			beanCost = battleDanUser.getSign3BeanCost();
		}else if(signCount>=3){
			beanCost = battleDanUser.getSign4BeanCost();
		}
		
		Account account = accountService.fineOneSync(userInfo.getAccountId());
		
		Long wisdomCount = account.getWisdomCount();
		if(wisdomCount==null){
			wisdomCount = 0L;
		}
		if(wisdomCount<beanCost){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("智慧豆不够");
			resultVo.setErrorCode(0);
			
			return resultVo;
		}else{
			/*
			wisdomCount = wisdomCount - beanCost;
			if(wisdomCount<0){
				wisdomCount = 0L;
			}
			account.setWisdomCount(wisdomCount);*/
		}
		
		List<Integer> statuses = new ArrayList<>();
		statuses.add(BattleRoom.STATUS_FREE);
		statuses.add(BattleRoom.STATUS_IN);
		
		
		/*
		Sort sort = new Sort(Direction.DESC,"createAt");
		Pageable pageable = new PageRequest(0,1,sort);
		BattleRoom battleRoom = null;
		List<BattleRoom> oldBattleRooms = battleRoomService.findAllByIsDanRoomAndBattleIdAndPeriodIdAndStatusIn(1,battleDanUser.getBattleId(),battleDanUser.getPeriodId(),statuses,pageable);
		
		List<BattleRoom> battleRooms = new ArrayList<>();
		
		for(BattleRoom oldBattleRoom:oldBattleRooms){
			if(oldBattleRoom.getStartTime().isBeforeNow()){
				if(oldBattleRoom.getMininum()>oldBattleRoom.getNum()){
					battleRooms.add(oldBattleRoom);
				}
			}else{
				battleRooms.add(oldBattleRoom);
			}
		}
		
		if(battleRooms!=null&&battleRooms.size()>0){
			
			battleRoom = battleRooms.get(0);
			battleDanUser.setRoomId(battleRoom.getId());
			
			battleDanUserService.update(battleDanUser);
			
		}else{
			Battle battle = battleService.findOne(battleDanUser.getBattleId());
			battleRoom = battleRoomHandleService.initRoom(battle);
			battleRoom.setIsPk(1);
			battleRoom.setPeriodId(battleDanUser.getPeriodId());
			battleRoom.setMaxinum(battleDanUser.getMaxNum());
			battleRoom.setMininum(battleDanUser.getMinNum());
			battleRoom.setSmallImgUrl(userInfo.getHeadimgurl());
			battleRoom.setIsSearchAble(0);
			battleRoom.setScrollGogal(battleDanUser.getScoreGogal());
			battleRoom.setPlaces(battleDanUser.getPlaces());
			battleRoom.setIsDanRoom(1);
			
			battleRoom.setDanId(danId);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			
			calendar.add(Calendar.SECOND, battleDanUser.getTimeLong());

			battleRoom.setStartTime(new DateTime(calendar.getTime()));
			
			battleRoom.setLoveCount(battleDanUser.getLoveCount());
			
			initRoomService.addRoom(battleRoom);
			
			Sort rewardSort = new Sort(Direction.ASC,"rank");
			Pageable rewardPageable = new PageRequest(0,40,rewardSort);
			List<BattleDanReward> battleDanRewards = battleDanRewardService.findAllByDanId(danId,rewardPageable);
			
			for(BattleDanReward battleDanReward:battleDanRewards){
				BattleRoomReward battleRoomReward = new BattleRoomReward();
				
				battleRoomReward.setIsReceive(0);
				battleRoomReward.setRank(battleDanReward.getRank());
				battleRoomReward.setRewardBean(battleDanReward.getRewardBean());
				battleRoomReward.setRewardLove(battleDanReward.getRewardLove());
				battleRoomReward.setRoomId(battleRoom.getId());
				
				battleRoomRewardService.add(battleRoomReward);
			}
			
			battleDanUser.setRoomId(battleRoom.getId());
			battleDanUserService.update(battleDanUser);

		}*/
		
		
		List<BattleWait> battleWaits = battleWaitService.findAllByBattleIdAndStatus(battleDan.getBattleId(),BattleWait.CALL_STATUS);
		
		BattleWait battleWait;
		if(battleWaits==null||battleWaits.size()==0){
			
			battleWait = new BattleWait();
			battleWait.setActDelay(2000);
			battleWait.setBattleId(battleDan.getBattleId());
			battleWait.setIsPrepareStart(0);
			battleWait.setMaxinum(battleDan.getMaxNum());
			battleWait.setMininum(battleDan.getMinNum());
			battleWait.setNum(0);
			battleWait.setPeriodId(battleDan.getPeriodId());
			battleWait.setStatus(BattleWait.CALL_STATUS);
			battleWait.setDanId(battleDan.getId());
			battleWaitService.add(battleWait);
		}else{
			battleWait = battleWaits.get(0);
		}
		
		
		battleDanUser.setSignCount(battleDanUser.getSignCount()+1);
		battleDanUser.setIsSign(1);
		
		battleDanUserService.update(battleDanUser);
		
		//accountService.update(account);
		
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		Map<String, Object> data = new HashMap<>();
		data.put("waitId", battleWait.getId());
		data.put("danUserId", battleDanUser.getId());
		
		resultVo.setData(data);
		
		return resultVo;
	}
	
	@RequestMapping(value="list")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentAccountResultFilter.class)
	public Object list(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		List<BattleDanPoint> battleDanPoints = battleDanPointService.findAllByIsRun(1);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		BattleDanPoint battleDanPoint = null;
		if(battleDanPoints!=null&&battleDanPoints.size()==1){
			battleDanPoint = battleDanPoints.get(0);
		}else if(battleDanPoints.size()>0){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("关卡有多条记录并发");
			return resultVo;
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("关卡没卡记录");
			return resultVo;
		}
	
		
		List<BattleDan> battleDans = battleDanService.findAllByPointIdOrderByLevelAsc(battleDanPoint.getId());
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		BattleAccountResult battleAccountResult = sessionManager.getObject(BattleAccountResult.class);
		for(BattleDan battleDan:battleDans){
			Map<String, Object> map = new HashMap<>();
			map.put("id", battleDan.getId());
			map.put("danId", battleDan.getId());
			map.put("battleId", battleDan.getBattleId());
			map.put("imgUrl", battleDan.getImgUrl());
			map.put("level", battleDan.getLevel());
			map.put("maxNum", battleDan.getMaxNum());
			map.put("minNum", battleDan.getMinNum());
			map.put("name", battleDan.getName());
			map.put("danName", battleDan.getName());
			map.put("periodId", battleDan.getPeriodId());
			map.put("places", battleDan.getPlaces());
			map.put("pointId", battleDan.getPointId());
			map.put("scoreGogal", battleDan.getScoreGogal());
			map.put("sign1BeanCost", battleDan.getSign1BeanCost());
			map.put("sign2BeanCost", battleDan.getSign2BeanCost());
			map.put("sign3BeanCost", battleDan.getSign3BeanCost());
			map.put("sign4BeanCost", battleDan.getSign4BeanCost());
			map.put("timeLong", battleDan.getTimeLong());
			if(battleDan.getLevel()<battleAccountResult.getLevel()){
				map.put("status", BattleDan.STATUS_SUCCESS);
			}else if(battleDan.getLevel()==battleAccountResult.getLevel()){
				map.put("status", BattleDan.STATUS_IN);
			}else{
				map.put("status", BattleDan.STATUS_FREE);
			}
			
			List<BattleDanUser> battleDanUsers = battleDanUserService.findAllByDanIdAndUserId(battleDan.getId(), userInfo.getId());
			
			BattleDanUser battleDanUser = null;
			if(battleDanUsers.size()>0){
				battleDanUser = battleDanUsers.get(0);
				if(battleDanUsers.size()>1){
					BattleDanUser battleDanUser2 = battleDanUsers.get(1);
					battleDanUser2.setIsDel(1);
					battleDanUserService.update(battleDanUser2);
				}
			}
			
			if(battleDanUser==null){
				map.put("costBean", battleDan.getSign1BeanCost());
				map.put("signCount", 0);
				map.put("isSign", 0);
			}else if(battleDanUser.getSignCount()==0){
				map.put("costBean", battleDan.getSign1BeanCost());
				/*map.put("signCount", battleDanUser.getSignCount());
				map.put("isSign", 0);*/
				map.put("signCount", 0);
				map.put("isSign", 0);
			}else if(battleDanUser.getSignCount()==1){
				map.put("costBean", battleDan.getSign2BeanCost());
				/*map.put("signCount", battleDanUser.getSignCount());
				map.put("isSign", 1);*/
				map.put("signCount", 0);
				map.put("isSign", 0);
			}else if(battleDanUser.getSignCount()==2){
				map.put("costBean", battleDan.getSign3BeanCost());
				/*map.put("signCount", battleDanUser.getSignCount());
				map.put("isSign", 1);*/
				map.put("signCount", 0);
				map.put("isSign", 0);
			}else if(battleDanUser.getSignCount()>=3){
				map.put("costBean", battleDan.getSign4BeanCost());
				/*map.put("signCount", battleDanUser.getSignCount());
				map.put("isSign", 1);*/
				map.put("signCount", 0);
				map.put("isSign", 0);
			}
			
			list.add(map);
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(list);
		
		return resultVo;
		
		/*
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		synchronized(userInfo.getId().intern()){
			
			List<BattleDanUser> battleDanUsers = battleDanUserService.findAllByUserIdAndPointIdOrderByLevelAsc(userInfo.getId(),battleDanPoint.getId());
			
			if(battleDanUsers==null||battleDanUsers.size()==0){
				userInfo.setIsSyncDan(1);
				wxUserInfoService.update(userInfo);
				List<BattleDan> battleDans = battleDanService.findAllByPointIdOrderByLevelAsc(battleDanPoint.getId());
				battleDanUsers = new ArrayList<>();
				Integer index = 0;
				for(BattleDan battleDan:battleDans){
					BattleDanUser battleDanUser = new BattleDanUser();
					if(index==0){
						battleDanUser.setStatus(BattleDanUser.STATUS_IN);
					}else{
						battleDanUser.setStatus(BattleDanUser.STATUS_FREE);
					}
					index++;
					battleDanUser.setDanId(battleDan.getId());
					battleDanUser.setDanName(battleDan.getName());
					battleDanUser.setImgUrl(battleDan.getImgUrl());
					battleDanUser.setLevel(battleDan.getLevel());
					battleDanUser.setPointId(battleDan.getPointId());
					
					battleDanUser.setBattleId(battleDan.getBattleId());
					
					battleDanUser.setPeriodId(battleDan.getPeriodId());
					
					battleDanUser.setPlaces(battleDan.getPlaces());
					
					battleDanUser.setUserId(userInfo.getId());
					
					battleDanUser.setSign1BeanCost(battleDan.getSign1BeanCost());
					
					battleDanUser.setSign2BeanCost(battleDan.getSign2BeanCost());
					
					battleDanUser.setSign3BeanCost(battleDan.getSign3BeanCost());
					
					battleDanUser.setSign4BeanCost(battleDan.getSign4BeanCost());
					
					battleDanUser.setIsSign(0);
					
					battleDanUser.setSignCount(0);
					
					battleDanUser.setScoreGogal(battleDan.getScoreGogal());
					
					battleDanUser.setMaxNum(battleDan.getMaxNum());
					
					battleDanUser.setTimeLong(battleDan.getTimeLong());
					
					battleDanUser.setMinNum(battleDan.getMinNum());
					
					battleDanUser.setIsDel(0);
					
					battleDanUserService.add(battleDanUser);
					
					battleDanUsers.add(battleDanUser);
				}
			}else{
				userInfo.setIsSyncDan(1);
				wxUserInfoService.update(userInfo);
				BattleAccountResult battleAccountResult = battleAccountResultService.findOneByUserId(userInfo.getId());
				for(Integer i = 0;i<battleDanUsers.size();i++){
					BattleDanUser battleDanUser = battleDanUsers.get(i);
					if(battleDanUser.getStatus()==BattleDanUser.STATUS_SUCCESS){
						if(i<battleDanUsers.size()-1){
							BattleDanUser battleDanUser2 = battleDanUsers.get(i+1);
							battleAccountResult.setLevel(battleDanUser2.getLevel());
							battleAccountResult.setDanName(battleDanUser2.getDanName());
							battleAccountResult.setDanId(battleDanUser2.getId());
						}
					}
				}
				
				battleAccountResultService.update(battleAccountResult);
			}
			
			
			
			List<Map<String, Object>> usersResponse = new ArrayList<>();
			
			for(BattleDanUser battleDanUser:battleDanUsers){
				Map<String, Object> battleDanUserMap = new HashMap<>();
				
				battleDanUserMap.put("id", battleDanUser.getId());
				battleDanUserMap.put("danId", battleDanUser.getDanId());
				battleDanUserMap.put("danName", battleDanUser.getDanName());
				battleDanUserMap.put("imgUrl", battleDanUser.getImgUrl());
				battleDanUserMap.put("level", battleDanUser.getLevel());
			
				
				Integer signCount = battleDanUser.getSignCount();
				Integer sign1BeanCount = battleDanUser.getSign1BeanCost();
				Integer sign2BeanCount = battleDanUser.getSign2BeanCost();
				Integer sign3BeanCount = battleDanUser.getSign3BeanCost();
				Integer sign4BeanCount = battleDanUser.getSign4BeanCost();
				Integer costBean = 0;
				
				if(signCount==0){
					costBean = sign1BeanCount;
				}else if(signCount==1){
					costBean = sign2BeanCount;
				}else if(signCount==2){
					costBean = sign3BeanCount;
				}else if(signCount==3){
					costBean = sign3BeanCount;
				}else{
					costBean = sign4BeanCount;
				}
				
				battleDanUserMap.put("costBean", costBean);
				
				battleDanUserMap.put("signCount", signCount);
				
				battleDanUserMap.put("status", battleDanUser.getStatus());
				
				battleDanUserMap.put("isSign", battleDanUser.getIsSign());
				
				usersResponse.add(battleDanUserMap);
			}
			
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			
			resultVo.setData(usersResponse);
			
			return resultVo;
		}*/
		
	}
}
