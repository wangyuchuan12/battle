package com.battle.api;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.battle.domain.Battle;
import com.battle.domain.BattleDanTaskUser;
import com.battle.domain.BattleMemberLoveCooling;
import com.battle.domain.BattleMemberPaperAnswer;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattlePeriodStage;
import com.battle.domain.BattleQuestion;
import com.battle.domain.BattleRedPacketAmountDistribution;
import com.battle.domain.BattleRedpacket;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleRoomEntry;
import com.battle.domain.BattleRoomRecord;
import com.battle.domain.BattleSubject;
import com.battle.domain.BattleUser;
import com.battle.filter.api.BattleMemberInfoApiFilter;
import com.battle.filter.api.BattleMembersApiFilter;
import com.battle.filter.api.BattleSubjectApiFilter;
import com.battle.filter.api.BattleTakepartApiFilter;
import com.battle.filter.api.CurrentLoveCoolingApiFilter;
import com.battle.filter.element.CurrentBattleUserFilter;
import com.battle.filter.element.CurrentMemberInfoFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleDanTaskUserService;
import com.battle.service.BattleMemberPaperAnswerService;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattlePeriodService;
import com.battle.service.BattlePeriodStageService;
import com.battle.service.BattleQuestionService;
import com.battle.service.BattleRedPacketAmountDistributionService;
import com.battle.service.BattleRedpacketService;
import com.battle.service.BattleRoomEntryService;
import com.battle.service.BattleRoomRecordService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleService;
import com.battle.service.BattleSubjectService;
import com.battle.service.BattleUserService;
import com.battle.service.listener.BattleRoomEndListener;
import com.battle.service.other.BattleDanHandleService;
import com.battle.service.other.BattleRoomHandleService;
import com.battle.socket.service.ProgressStatusSocketService;
import com.battle.socket.task.RoomStartTask;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.Account;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.service.AccountService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.util.MySimpleDateFormat;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/battle/")
public class BattleApi {
	
	@Autowired
	private BattlePeriodStageService battlePeriodStageService;
	
	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Autowired
	private BattleQuestionService battleQuestionService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private BattlePeriodService battlePeriodService;
	
	@Autowired
	private BattleService battleService;
	
	@Autowired
	private BattleUserService battleUserService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BattleRedpacketService battleRedpacketService;
	
	@Autowired
	private BattleRedPacketAmountDistributionService battleRedPacketAmountDistributionService;
	
	@Autowired
	private BattleMemberPaperAnswerService battleMemberPaperAnswerService;
	
	@Autowired
	private BattleRoomRecordService battleRoomRecordService;
	
	@Autowired
	private MySimpleDateFormat mySimpleDateFormat;
	
	@Autowired
	private BattleRoomEntryService battleRoomEntryService;
	
	@Autowired
	private BattleSubjectService battleSubjectService;
	
	@Autowired
	private BattleRoomHandleService battleRoomHandleService;
	
	@Autowired
	private BattleDanTaskUserService battleDanTaskUserService;
	
	
	@Autowired
	private BattleDanHandleService battleDanHandleService;
	
	@Autowired
	private BattleRoomEndListener battleRoomEndListener;
	
	@Autowired
	private ProgressStatusSocketService progressStatusSocketService;
	
	@Autowired
	private RoomStartTask roomStartTask;
	
	
	final static Logger logger = LoggerFactory.getLogger(BattleApi.class);
	@RequestMapping(value="roomInfo")
	@ResponseBody
	@Transactional
	public Object roomInfo(HttpServletRequest httpServletRequest){
		String id = httpServletRequest.getParameter("id");
		
		BattleRoom battleRoom = battleRoomService.findOne(id);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleRoom);
		
		return resultVo;
	}

	@RequestMapping(value="info")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	public Object info(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		BattleUser battleUser = sessionManager.getObject(BattleUser.class);
		String battleId = httpServletRequest.getParameter("battleId");
		String roomId = httpServletRequest.getParameter("roomId");
		BattleRoom battleRoom = battleRoomService.findOne(roomId);
		
		Map<String, Object> data = new HashMap<>();
		Battle battle = sessionManager.findOne(Battle.class, battleId);
		
		data.put("id", battle.getId());
		//data.put("currentPeriodId", battle.getCurrentPeriodId());
		//data.put("distance", battle.getDistance());
		data.put("headImg", battle.getHeadImg());
		data.put("instruction", battle.getInstruction());
		data.put("isActivation", battle.getIsActivation());
		data.put("name", battle.getName());
		data.put("maxinum", battleRoom.getMaxinum());
		data.put("mininum", battleRoom.getMininum());
		data.put("owner", battleRoom.getOwner());
		data.put("speedCoolBean", battleRoom.getSpeedCoolBean());
		data.put("speedCoolSecond", battleRoom.getSpeedCoolSecond());
		data.put("places", battleRoom.getPlaces());
		if(battleRoom.getOwner().equals(battleUser.getId())){
			data.put("isOwner", 1);
		}else{
			data.put("isOwner", 0);
		}
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(data);
		return resultVo;
	}
	
	@RequestMapping(value="randomRoom")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object randomRoom(HttpServletRequest httpServletRequest)throws Exception{
		String battleId = httpServletRequest.getParameter("battleId");
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		Pageable pageable = new PageRequest(0, 2);
		
		
		Page<BattleRoom> battleRoomPage = battleRoomService.findAllByBattleIdAndUserId(battleId,userInfo.getId(), pageable);
		
		List<BattleRoom> battleRooms = battleRoomPage.getContent();
		
		if(battleRooms!=null&&battleRooms.size()>0){
			
			BattleRoom battleRoom = battleRooms.get(0);
		
			BattleUser battleUser = battleUserService.findOneByUserIdAndBattleId(userInfo.getId(), battleId);
			if(battleRoom!=null&&battleUser!=null&&!battleRoom.getOwner().equals(battleUser.getId())){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(true);
				resultVo.setData(battleRoom);
				return resultVo;
			}else{
				if(battleRooms.size()>=2){
					battleRoom = battleRooms.get(1);
					if(battleRoom!=null){
						ResultVo resultVo = new ResultVo();
						resultVo.setSuccess(true);
						resultVo.setData(battleRoom);
						return resultVo;
					}
				}
			}
		}
		
		
		battleRoomPage = battleRoomService.findAllByBattleIdAndStatusAndIsSearchAble(battleId, BattleRoom.STATUS_IN , 1, pageable);
		
		battleRooms = battleRoomPage.getContent();
		
		if(battleRooms!=null&&battleRooms.size()>0){
			BattleRoom battleRoom = battleRooms.get(0);
			
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			resultVo.setData(battleRoom);
			
			return resultVo;
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(false);
		resultVo.setErrorCode(0);
		resultVo.setErrorMsg("返回为空");
		return resultVo;
	}
	
	@RequestMapping(value="myRooms")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object myRooms(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		Pageable pageable = new PageRequest(0, 20);
		Page<BattleRoom> battleRoomPage = battleRoomService.findAllByUserId(userInfo.getId(),pageable);
		
		List<BattleRoom> battleRooms = battleRoomPage.getContent();
		
		if(battleRooms!=null&&battleRooms.size()>0){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			resultVo.setData(battleRooms);
			
			return resultVo;
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(false);
		resultVo.setErrorCode(0);
		resultVo.setErrorMsg("返回为空");
		return resultVo;
	}
	
	@RequestMapping(value="memberInfo")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=BattleMemberInfoApiFilter.class)
	public Object memberInfo(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		if(sessionManager.isReturn()){
			ResultVo resultVo = (ResultVo)sessionManager.getReturnValue();
			return resultVo;
		}else{
			ResultVo resultVo = (ResultVo)sessionManager.getObject(ResultVo.class);
			return resultVo;
		}
	}
	
	@RequestMapping(value="members")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=BattleMembersApiFilter.class)
	public Object members(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		if(sessionManager.isReturn()){
			ResultVo resultVo = (ResultVo)sessionManager.getReturnValue();
			return resultVo;
		}else{
			ResultVo resultVo = (ResultVo)sessionManager.getObject(ResultVo.class);
			return resultVo;
		}
	}
	
	@RequestMapping(value="currentLoveCooling")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentLoveCoolingApiFilter.class)
	public Object currentLoveCooling(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		if(sessionManager.isReturn()){
			ResultVo resultVo = (ResultVo)sessionManager.getReturnValue();
			return resultVo;
		}else{
			ResultVo resultVo = (ResultVo)sessionManager.getObject(ResultVo.class);
			return resultVo;
		}
	}
	
	@RequestMapping(value="startCoolingLove")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentLoveCoolingApiFilter.class)
	public Object startCoolingLove(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		if(sessionManager.isReturn()){
			ResultVo resultVo = (ResultVo)sessionManager.getReturnValue();
			return resultVo;
		}else{
			ResultVo resultVo = (ResultVo)sessionManager.getObject(ResultVo.class);
			BattleMemberLoveCooling battleMemberLoveCooling = (BattleMemberLoveCooling)resultVo.getData();
			BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
			
			Integer loveResdule = battlePeriodMember.getLoveResidule();
			Integer loveCount = battlePeriodMember.getLoveCount();
			
			if(loveResdule<loveCount){
				battleMemberLoveCooling.setStartDatetime(new DateTime());
				battleMemberLoveCooling.setStatus(BattleMemberLoveCooling.STATUS_IN);
				battleMemberLoveCooling.setSchedule(0L);
				resultVo.setSuccess(true);
			}else{
				battleMemberLoveCooling.setStatus(BattleMemberLoveCooling.STATUS_COMPLETE);
				battleMemberLoveCooling.setSchedule(0L);
				resultVo.setSuccess(false);
				resultVo.setData(null);
			}
			
			sessionManager.update(battleMemberLoveCooling);
			
			return resultVo;
		}
	}
	
	
	@RequestMapping(value="battleSubjects")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=BattleSubjectApiFilter.class)
	public Object battleSubjects(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		if(sessionManager.isReturn()){
			ResultVo resultVo = (ResultVo)sessionManager.getReturnValue();
			return resultVo;
		}else{
			ResultVo resultVo = (ResultVo)sessionManager.getObject(ResultVo.class);
			return resultVo;
		}
	}
	
	@RequestMapping(value="battleSubjectByBattleId")
	@ResponseBody
	@Transactional
	public Object battleSubjectsByBattleId(HttpServletRequest httpServletRequest)throws Exception{
		String battleId = httpServletRequest.getParameter("battleId");
		
		List<BattleSubject> battleSubjects = battleSubjectService.findAllByBattleIdAndIsDelOrderBySeqAsc(battleId, 0);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		resultVo.setData(battleSubjects);
		
		return resultVo;
	}
	
	
	
	//参加关卡
	@RequestMapping(value="stageTakepart")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	public Object stageTakepart(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		String subjectIdStr = httpServletRequest.getParameter("subjectIds");
		
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		Integer stageIndex = battlePeriodMember.getStageIndex();
		
		Integer stageCount = battlePeriodMember.getStageCount();
		
		Integer isLast = 0;
		if(stageIndex==stageCount){
			isLast = 1;
		}else if(stageIndex>stageCount){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("已经超出了");
			return resultVo;
		}
		
		if(battlePeriodMember.getStatus()==BattlePeriodMember.STATUS_IN){
			
			UserInfo userInfo = sessionManager.getObject(UserInfo.class);
			
			String[] subjectIds = subjectIdStr.split(",");
			
			BattlePeriodStage battlePeriodStage = battlePeriodStageService.findOneByBattleIdAndPeriodIdAndIndex(battlePeriodMember.getBattleId(), battlePeriodMember.getPeriodId(), stageIndex);
			
			Account account = accountService.fineOneSync(userInfo.getAccountId());
			
			Long beanNum = account.getWisdomCount();
			if(beanNum==null){
				beanNum = 0l;
			}
			
			Integer costBeanNum = battlePeriodStage.getCostBean();
			if(costBeanNum==null){
				costBeanNum = 0;
			}
			
			if(beanNum<costBeanNum){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorCode(0);
				resultVo.setErrorMsg("智慧豆不足");
				return resultVo;
			}
			
			
			if(beanNum>=costBeanNum){
				beanNum = beanNum - costBeanNum;
				account.setWisdomCount(beanNum);
				
				accountService.update(account);
			}
			Integer questionCount = battlePeriodStage.getQuestionCount();
			
			
			
			List<BattleQuestion> battleQuestions = battleQuestionService.findAllByBattleIdAndPeriodStageIdAndBattleSubjectIdInAndIsDel(battlePeriodStage.getBattleId(), battlePeriodStage.getId(),subjectIds,0);
			
			
			
			List<String> battleQuestionIdArray = new ArrayList<>();
			for(BattleQuestion battleQuestion:battleQuestions){
				battleQuestionIdArray.add(battleQuestion.getQuestionId());
			}
			
			Collections.shuffle(battleQuestionIdArray);
			
			Integer length = battleQuestionIdArray.size();
			
			if(questionCount==null){
				questionCount = 0;
			}
			
			if(length>questionCount){
				length = questionCount;
			}
			
			battleQuestionIdArray = battleQuestionIdArray.subList(0, length);
			
			if(stageIndex<=stageCount){
				
				battlePeriodMemberService.update(battlePeriodMember);
				
			}else{
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("不是正在进行中状态");
				return resultVo;
				
			}
			
			Map<String, Object> data = new HashMap<>();
			
			data.put("isLast", isLast);
			
			data.put("questionIds", battleQuestionIdArray);
			
			data.put("costBean", costBeanNum);
			
			
			
			ResultVo resultVo = new ResultVo();
			
			resultVo.setSuccess(true);
			
			resultVo.setData(data);
			
			return resultVo;
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("不是正在进行中状态");
			return resultVo;
		}
	}
	
	
	@RequestMapping(value="periodsByBattleId")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentBattleUserFilter.class)
	public Object periodsByBattleId(HttpServletRequest httpServletRequest)throws Exception{
		String battleId = httpServletRequest.getParameter("battleId");
		if(CommonUtil.isEmpty(battleId)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("参数battleId不能为空");
			return resultVo;
		}
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		BattleUser battleUser = sessionManager.getObject(BattleUser.class);
		
		List<BattlePeriod> battlePeriods = battlePeriodService.findAllByBattleIdAndStatusAndAuthorBattleUserIdAndIsPublicOrderByIndexAsc(battleId,BattlePeriod.IN_STATUS,battleUser.getId(),0);
		
		List<BattlePeriod> battlePeriods2 = battlePeriodService.findAllByBattleIdAndStatusAndIsPublicOrderByIndexAsc(battleId,BattlePeriod.IN_STATUS,1);
		
		battlePeriods.addAll(battlePeriods2);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battlePeriods);
		resultVo.setMsg("返回数据成功");
		if(battlePeriods==null||battlePeriods.size()==0){
			resultVo.setMsg("返回的数据为空");
		}
		return resultVo;
	}
	
	@RequestMapping(value="battles")
	@ResponseBody
	@Transactional
	public Object battles(HttpServletRequest httpServletRequest){
		List<Battle> battles = battleService.findAllByStatusOrderByIndexAsc(Battle.IN_STATUS);
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battles);
		
		if(battles==null||battles.size()==0){
			resultVo.setMsg("返回数据为空");
		}else{
			resultVo.setMsg("返回数据成功");
		}
		
		return resultVo;
	}
	
	
	@RequestMapping(value="roomEntryInfoByKey")
	@ResponseBody
	@Transactional
	public Object roomEntryInfoByKey(HttpServletRequest httpServletRequest)throws Exception{
		String key = httpServletRequest.getParameter("key");
		
		BattleRoomEntry battleRoomEntry = battleRoomEntryService.findOneByKey(key);
		
		Map<String, Object> data = new HashMap<>();
		data.put("battleId", battleRoomEntry.getBattleId());
		data.put("roomId", battleRoomEntry.getRoomId());
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(data);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="addRoomWidthShare")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object addRoomWidthShare(HttpServletRequest httpServletRequest)throws Exception{

		String roomId = httpServletRequest.getParameter("roomId");
		
		String maxinum = httpServletRequest.getParameter("maxinum");
		String mininum = httpServletRequest.getParameter("mininum");
		Integer maxinumInt = 2;
		if(!CommonUtil.isEmpty(maxinum)){
			maxinumInt = Integer.parseInt(maxinum);
		}
		
		Integer mininumInt = 2;
		
		if(!CommonUtil.isEmpty(mininum)){
			mininumInt = Integer.parseInt(mininum);
		}
		
		if(maxinumInt==2){
			httpServletRequest.setAttribute("isPk", 1);
		}
		
		String key = httpServletRequest.getParameter("key");
		BattleRoom battleRoom = battleRoomService.findOne(roomId);
		httpServletRequest.setAttribute("battleId", battleRoom.getBattleId());
		httpServletRequest.setAttribute("periodId", battleRoom.getPeriodId());
		httpServletRequest.setAttribute("maxinum", maxinumInt);
		httpServletRequest.setAttribute("mininum", mininumInt);
		
		
		ResultVo resultVo = addRoom(httpServletRequest);
		if(resultVo.isSuccess()){
			BattleRoom createBattleRoom = (BattleRoom)resultVo.getData();
			/*Account account = accountService.fineOneSync(userInfo.getAccountId());
			Long wisdomCount = account.getWisdomCount();
			wisdomCount = wisdomCount+200;
			account.setWisdomCount(wisdomCount);
			accountService.update(account);*/
			
			BattleRoomEntry battleRoomEntry = new BattleRoomEntry();
			
			battleRoomEntry.setKey(key);
			
			battleRoomEntry.setRoomId(createBattleRoom.getId());
			
			battleRoomEntry.setBattleId(battleRoom.getBattleId());
		
			
			battleRoomEntryService.add(battleRoomEntry);
			
			/*
			BattleUser battleUser = battleUserService.findOneByUserIdAndBattleId(userInfo.getId(), createBattleRoom.getBattleId());
			
			BattlePeriod battlePeriod = battlePeriodService.findOne(createBattleRoom.getPeriodId());
			
			
		
			BattlePeriodMember battlePeriodMember = new BattlePeriodMember();
			battlePeriodMember = new BattlePeriodMember();
			battlePeriodMember.setBattleId(createBattleRoom.getBattleId());
			battlePeriodMember.setBattleUserId(battleUser.getId());
			battlePeriodMember.setPeriodId(createBattleRoom.getPeriodId());
			battlePeriodMember.setProcess(0);
			battlePeriodMember.setNickname(userInfo.getNickname());
			battlePeriodMember.setHeadImg(userInfo.getHeadimgurl());
			battlePeriodMember.setStatus(BattlePeriodMember.STATUS_FREE);
			battlePeriodMember.setLoveCount(5);
			battlePeriodMember.setLoveResidule(5);
			battlePeriodMember.setStageIndex(1);
			battlePeriodMember.setStageCount(battlePeriod.getStageCount());
			battlePeriodMember.setIsDel(0);
			battlePeriodMember.setRoomId(roomId);
			battlePeriodMember.setScore(0);
			
			battlePeriodMember.setUserId(userInfo.getId());
			
			battlePeriodMember.setShareTime(0);
			
			battlePeriodMemberService.add(battlePeriodMember);
			*/
			
		}else{
			
			if(resultVo.getErrorCode()==0){
				
				BattleRoomEntry battleRoomEntry = new BattleRoomEntry();
				
				battleRoomEntry.setRoomId(battleRoom.getId());
				
				battleRoomEntry.setKey(key);
				
				battleRoomEntry.setBattleId(battleRoom.getBattleId());
				
				battleRoomEntryService.add(battleRoomEntry);
				
				resultVo.setSuccess(true);
				
				resultVo.setCode(0);
			}
			
		}
		
		return resultVo;
	}
	
	
	@RequestMapping(value="addRoom")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo addRoom(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		String battleId = httpServletRequest.getParameter("battleId");
		
		String isPk = httpServletRequest.getParameter("isPk");
		
		if(CommonUtil.isEmpty(isPk)){
			Object isPkTarget = httpServletRequest.getAttribute("isPk");
			if(!CommonUtil.isEmpty(isPkTarget)){
				isPk = isPkTarget.toString();
			}
		}
		
		Integer isPkInt = 0;
		
		if(!CommonUtil.isEmpty(isPk)){
			isPkInt = Integer.parseInt(isPk);
		}
		if(CommonUtil.isEmpty(battleId)){
			battleId = httpServletRequest.getAttribute("battleId").toString();
		}
		String periodId = httpServletRequest.getParameter("periodId");
		if(CommonUtil.isEmpty(periodId)){
			periodId = httpServletRequest.getAttribute("periodId").toString();
		}
		String maxinum = httpServletRequest.getParameter("maxinum");
		if(CommonUtil.isEmpty(maxinum)){
			maxinum = httpServletRequest.getAttribute("maxinum").toString();
		}
		String mininum = httpServletRequest.getParameter("mininum");
		
		if(CommonUtil.isEmpty(mininum)){
			mininum = httpServletRequest.getAttribute("mininum").toString();
		}
		
		String isPublic = "0";
		
		
		
		if(CommonUtil.isEmpty(isPublic)){
			ResultVo resultVo = new ResultVo();
			resultVo.setErrorMsg("参数isPublic不能为空");
			resultVo.setSuccess(false);
			return resultVo;
		}
		
		
		if(CommonUtil.isEmpty(battleId)){
			ResultVo resultVo = new ResultVo();
			resultVo.setErrorMsg("参数battleId不能为空");
			resultVo.setSuccess(false);
			return resultVo;
		}
		
		if(CommonUtil.isEmpty(periodId)){
			ResultVo resultVo = new ResultVo();
			resultVo.setErrorMsg("参数periodId不能为空");
			resultVo.setSuccess(false);
			return resultVo;
		}
		
		if(CommonUtil.isEmpty(maxinum)){
			ResultVo resultVo = new ResultVo();
			resultVo.setErrorMsg("参数maxinum不能为空");
			resultVo.setSuccess(false);
			return resultVo;
		}
		
		if(CommonUtil.isEmpty(mininum)){
			ResultVo resultVo = new ResultVo();
			resultVo.setErrorMsg("参数mininum不能为空");
			resultVo.setSuccess(false);
			return resultVo;
		}
		
		int isPublicInt = Integer.parseInt(isPublic);
		if(isPublicInt!=0&&isPublicInt!=1){
			ResultVo resultVo = new ResultVo();
			resultVo.setErrorMsg("isPublic的值必须是0或者1");
			resultVo.setSuccess(false);
			return resultVo;
		}
		
		
		Integer maxinumInt = Integer.parseInt(maxinum);
		Integer mininumInt = Integer.parseInt(mininum);
		
		if(maxinumInt<2){
			ResultVo resultVo = new ResultVo();
			resultVo.setErrorMsg("maxinumInt参数不能小于2");
			resultVo.setSuccess(false);
			return resultVo;
		}
		
		if(mininumInt<1){
			ResultVo resultVo = new ResultVo();
			resultVo.setErrorMsg("mininumInt参数不能小于1");
			resultVo.setSuccess(false);
			return resultVo;
		}
		
		if(maxinumInt>500){
			ResultVo resultVo = new ResultVo();
			resultVo.setErrorMsg("maxinumInt参数不能大于50");
			resultVo.setSuccess(false);
			return resultVo;
		}
		
		if(maxinumInt<mininumInt){
			ResultVo resultVo = new ResultVo();
			resultVo.setErrorMsg("maxinumInt参数不能小于mininumInt参数");
			resultVo.setSuccess(false);
			return resultVo;
		}
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		
		BattlePeriod battlePeriod = battlePeriodService.findOne(periodId);
		if(CommonUtil.isEmpty(battlePeriod)){
			ResultVo resultVo = new ResultVo();
			resultVo.setErrorMsg("返回的battlPeriod为空");
			resultVo.setSuccess(false);
			return resultVo;
		}
		
		BattleUser battleUser = battleUserService.findOneByUserIdAndBattleId(userInfo.getId(), battleId);
		if(battleUser==null){
			battleUser = new BattleUser();
			battleUser.setBattleId(battleId);
			battleUser.setUserId(userInfo.getId());
			battleUser.setIsManager(0);
			battleUser.setOpenId(userInfo.getOpenid());
			battleUserService.add(battleUser);
		}
		
		
		Battle battle = battleService.findOne(battleId);
		
		if(battle==null){
			ResultVo resultVo = new ResultVo();
			resultVo.setErrorMsg("battle为空");
			resultVo.setSuccess(false);
			return resultVo;
		}
		
		
		List<BattleRoom> battleRooms = battleRoomService.findAllByBattleIdAndPeriodIdAndOwnerAndIsPk(battleId,periodId,battleUser.getId(),0);
		if(isPkInt!=1&&battleRooms!=null&&battleRooms.size()>0&&battleUser.getIsManager()!=1){
			BattleRoom battleRoom = battleRooms.get(0);
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorCode(0);
			resultVo.setErrorMsg("您已经创建过该主题的房间，不能重复创建");
			resultVo.setData(battleRoom);
			return resultVo;
		}
		
		BattleRoom battleRoom = battleRoomHandleService.initRoom(battle);
		
		battleRoom.setIsPk(isPkInt);
		battleRoom.setPeriodId(periodId);
		battleRoom.setMaxinum(maxinumInt);
		battleRoom.setMininum(mininumInt);
		battleRoom.setOwner(battleUser.getId());
		battleRoom.setSmallImgUrl(userInfo.getHeadimgurl());
		battleRoom.setIsSearchAble(isPublicInt);
		battleRoom.setScrollGogal(500);
		
		battleRoomService.add(battleRoom);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setData(battleRoom);
		resultVo.setMsg("添加成功");
		resultVo.setSuccess(true);
		
		return resultVo;
		
	}
	
	
	@RequestMapping(value="supperLove")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	public Object supperLove(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		Integer loveResidule = battlePeriodMember.getLoveResidule();
		Integer loveCount = battlePeriodMember.getLoveCount();
		
		if(loveResidule==null||loveResidule<0){
			loveResidule = 0;
		}
		
		if(loveResidule>=loveCount){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorCode(1);
			resultVo.setErrorMsg("爱心已充满");
			return resultVo;
		}

		loveResidule++;
		
		battlePeriodMember.setStatus(BattlePeriodMember.STATUS_IN);
		
		battlePeriodMember.setLoveResidule(loveResidule);
		
		battlePeriodMemberService.update(battlePeriodMember);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		Account account = accountService.fineOneSync(userInfo.getAccountId());
		
		Integer loveLife = account.getLoveLife();
		
		if(loveLife==null||loveLife==0){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorCode(0);
			resultVo.setErrorMsg("爱心不足");
			return resultVo;
		}
		
		loveLife--;
		
		account.setLoveLife(loveLife);
		
		accountService.update(account);
		
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battlePeriodMember);
		
		resultVo.setMsg("补充成功");
		return resultVo;
	}
	
	
	@RequestMapping(value="speed_cool_bean")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentLoveCoolingApiFilter.class)
	public Object speedCoolBean(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		BattleMemberLoveCooling battleMemberLoveCooling = sessionManager.getObject(BattleMemberLoveCooling.class);
		
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		
		Integer loveResidule = battlePeriodMember.getLoveResidule();
		Integer loveCount = battlePeriodMember.getLoveCount();
		
		BattleRoom battleRoom = battleRoomService.findOne(battlePeriodMember.getRoomId());
		
		Integer speedCoolBean = battleRoom.getSpeedCoolBean();
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		Account account  = accountService.fineOneSync(userInfo.getAccountId());
		
		Long wisdomCount = account.getWisdomCount();
		if(wisdomCount==null){
			wisdomCount = 0L;
		}
		
		if(wisdomCount<speedCoolBean){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("智慧豆不足");
			return resultVo;
		}
		account.setWisdomCount(wisdomCount-speedCoolBean);
		
		accountService.update(account);
		Integer second = battleRoom.getSpeedCoolSecond();
		
		Integer unit = battleMemberLoveCooling.getUnit();
		Long upperLimit = battleMemberLoveCooling.getUpperLimit();
		
		Long schedule = battleMemberLoveCooling.getSchedule();
		
		schedule = schedule+second*unit;
		
		long time = schedule/upperLimit;
		
		schedule = schedule - upperLimit*time;
		
		battleMemberLoveCooling.setStartDatetime(new DateTime());
		
		Long loveResidule2 = time+loveResidule;
		
		if(loveResidule2<loveCount){
			battleMemberLoveCooling.setStatus(BattlePeriodMember.STATUS_IN);
			battleMemberLoveCooling.setSchedule(schedule);
			battlePeriodMember.setLoveResidule(loveResidule2.intValue());
			battleMemberLoveCooling.setCoolLoveSeq(loveResidule2.intValue()+1);
			battleMemberLoveCooling.setStartDatetime(new DateTime());
		}else{
			battleMemberLoveCooling.setStatus(BattlePeriodMember.STATUS_COMPLETE);
			battleMemberLoveCooling.setSchedule(upperLimit);
			battleMemberLoveCooling.setSchedule(0L);
			battleMemberLoveCooling.setCoolLoveSeq(0);
			battlePeriodMember.setLoveResidule(loveCount);
		}
		
		
		sessionManager.update(battleMemberLoveCooling);
		sessionManager.update(battlePeriodMember);
		
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("status", battleMemberLoveCooling.getStatus());
		responseData.put("coolLoveSeq", battleMemberLoveCooling.getCoolLoveSeq());
		responseData.put("millisec", battleMemberLoveCooling.getMillisec());
		responseData.put("schedule", battleMemberLoveCooling.getSchedule());
		responseData.put("startDatetime", battleMemberLoveCooling.getStartDatetime());
		responseData.put("unit", battleMemberLoveCooling.getUnit());
		responseData.put("upperLimit", battleMemberLoveCooling.getUpperLimit());
		responseData.put("loveCount", battlePeriodMember.getLoveCount());
		responseData.put("loveResidule", battlePeriodMember.getLoveResidule());
		responseData.put("speedCoolBean", speedCoolBean);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(responseData);
		return resultVo;
	}
	
	
	@RequestMapping(value="rooms")
	@ResponseBody
	@Transactional
	public Object rooms(HttpServletRequest httpServletRequest)throws Exception{
		String page = httpServletRequest.getParameter("page");
		String size = httpServletRequest.getParameter("size");
		String status = httpServletRequest.getParameter("status");
		
		if(CommonUtil.isEmpty(page)){
			page = "0";
		}
		
		if(CommonUtil.isEmpty(size)){
			size = "10";
		}
		
		if(CommonUtil.isEmpty(status)){
			status = "1";
		}
		
		Integer pageInt = Integer.parseInt(page);
		
		Integer sizeInt = Integer.parseInt(size);
		
		
		
		if(sizeInt>20){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("条数不能超过50");
			return resultVo;
		}
		
		Sort sort = new Sort(Direction.DESC,"hot");
		Pageable pageable = new PageRequest(pageInt,sizeInt,sort);
		List<Integer> statues = new ArrayList<>();
		statues.add(BattleRoom.STATUS_FREE);
		statues.add(BattleRoom.STATUS_IN);
		statues.add(BattleRoom.STATUS_FULL);
		Page<BattleRoom> battleRooms = battleRoomService.findAllByIsDisplayAndStatusInAndIsDel(1,statues,0,pageable);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleRooms.getContent());
		
		return resultVo;
		
		
	}
	
	@RequestMapping(value="roomSignout")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	@Transactional
	public Object roomSignOut(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		
		if(battlePeriodMember.getStatus()!=BattlePeriodMember.STATUS_IN){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("不是正在进行中，无法退出");
			return resultVo;
		}
		
		BattleRoom battleRoom = sessionManager.getObject(BattleRoom.class);
		
		Integer num = battleRoom.getNum();
		
		int status = battleRoom.getStatus();
		
		if(status==BattleRoom.STATUS_FULL){
			battleRoom.setStatus(BattleRoom.STATUS_IN);
		}

		battleRoom.setNum(num-1);
		
		
		battleRoomService.update(battleRoom);
		
		battlePeriodMember.setLoveResidule(0);
		
		battlePeriodMember.setStatus(BattlePeriodMember.STATUS_OUT);
		
		battlePeriodMemberService.update(battlePeriodMember);
	
		
		System.out.println(".......................roomSignOut");
		
		progressStatusSocketService.statusPublish(battlePeriodMember.getRoomId(),battlePeriodMember.getUserId());
		
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		return resultVo;
		
		
	}
	
	
	@RequestMapping(value="takepart")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=BattleTakepartApiFilter.class)
	public Object takepart(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		if(sessionManager.isReturn()){
			ResultVo resultVo = (ResultVo)sessionManager.getReturnValue();
			return resultVo;
		}else{
			ResultVo resultVo = (ResultVo)sessionManager.getObject(ResultVo.class);
			return resultVo;
		}
	}
	
	@RequestMapping(value="redPacks")
	@ResponseBody
	@Transactional
	public Object redPacks(HttpServletRequest httpServletRequest)throws Exception{
		String roomId = httpServletRequest.getParameter("roomId");
		List<BattleRedpacket> battleRedpackets = battleRedpacketService.findAllByIsRoomAndRoomIdOrderByHandTimeDesc(1, roomId);

		Map<Integer,List<Map<String, Object>>> redpackMap = new HashMap<>();
		for(BattleRedpacket battleRedpacket:battleRedpackets){
			List<Map<String, Object>> list = redpackMap.get(battleRedpacket.getStageIndex());
			if(list==null){
				list = new ArrayList<>();
				redpackMap.put(battleRedpacket.getStageIndex(), list);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("amount", battleRedpacket.getAmount());
			map.put("beanNum", battleRedpacket.getBeanNum());
			map.put("costAmount", battleRedpacket.getCostAmount());
			map.put("costBean", battleRedpacket.getCostBean());
			map.put("costMasonry", battleRedpacket.getCostMasonry());
			map.put("costType", battleRedpacket.getCostType());
			map.put("id", battleRedpacket.getId());
			map.put("isPersonalProcessMeet", battleRedpacket.getIsPersonalProcessMeet());
			map.put("isPersonalScoreMeet", battleRedpacket.getIsPersonalScoreMeet());
			map.put("isRoom", battleRedpacket.getIsRoom());
			map.put("isRoomMeet", battleRedpacket.getIsRoomMeet());
			map.put("isRoomProcessMeet", battleRedpacket.getIsRoomProcessMeet());
			map.put("isRoomScoreMeet", battleRedpacket.getIsRoomScoreMeet());
			map.put("masonryNum", battleRedpacket.getMasonryNum());
			map.put("name", battleRedpacket.getName());
			map.put("num", battleRedpacket.getNum());
			map.put("personalProcessMeet", battleRedpacket.getPersonalProcessMeet());
			map.put("personalScoreMeet", battleRedpacket.getPersonalScoreMeet());
			map.put("receiveAmount", battleRedpacket.getReceiveAmount());
			map.put("receiveNum", battleRedpacket.getReceiveNum());
			map.put("roomId", battleRedpacket.getRoomId());
			map.put("roomMeetNum", battleRedpacket.getRoomMeetNum());
			map.put("roomProcessMeet", battleRedpacket.getRoomProcessMeet());
			map.put("roomScoreMeet", battleRedpacket.getRoomScoreMeet());
			map.put("senderImg", battleRedpacket.getSenderImg());
			map.put("senderName", battleRedpacket.getSenderName());
			map.put("stageIndex", battleRedpacket.getStageIndex());
			map.put("status", battleRedpacket.getStatus());
			map.put("takepartCostBean", battleRedpacket.getTakepartCostBean());
			map.put("takepartCostMasonry", battleRedpacket.getTakepartCostMasonry());
			map.put("timeLong", battleRedpacket.getTimeLong());
			map.put("typeId", battleRedpacket.getTypeId());
			list.add(map);
		}
		
		List<Map<String, Object>> responseDatas = new ArrayList<>();
		
		for(Entry<Integer, List<Map<String, Object>>> entry:redpackMap.entrySet()){
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("stageIndex", entry.getKey());
			responseData.put("redpacks", entry.getValue());
			responseDatas.add(responseData);
		}
		
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(responseDatas);
		return resultVo;
	}
	
	@RequestMapping(value="redPacketAmountDistributions")
	@ResponseBody
	@Transactional
	public Object redPacketAmountDistributions(HttpServletRequest httpServletRequest){
		return null;
	}
	
	@RequestMapping(value="roomRecords")
	@ResponseBody
	@Transactional
	public Object roomRecords(HttpServletRequest httpServletRequest){
		String roomId = httpServletRequest.getParameter("roomId");
		
		Sort sort = new Sort(Direction.DESC,"happenTime");
		Pageable pageable = new PageRequest(0, 10,sort);
		
		List<BattleRoomRecord> battleRoomRecords = battleRoomRecordService.findAllByRoomId(roomId,pageable);
		
		List<Map<String, Object>> responseDatas = new ArrayList<>();
		
		for(BattleRoomRecord battleRoomRecord:battleRoomRecords){
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("happenTime", mySimpleDateFormat.format(battleRoomRecord.getHappenTime().toDate()));
			responseData.put("imgUrl", battleRoomRecord.getImgUrl());
			responseData.put("log", battleRoomRecord.getLog());
			responseDatas.add(responseData);
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(responseDatas);
		
		return resultVo;
	}
	
	@RequestMapping(value="receiveRedpack")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=BattleMemberInfoApiFilter.class)
	public Object receiveRedpack(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		String id = httpServletRequest.getParameter("id");
		
		BattleRedpacket battleRedpacket = battleRedpacketService.findOne(id);
		
		Integer isRoom = battleRedpacket.getIsRoom();
		
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		BattleRedPacketAmountDistribution battleRedPacketAmountDistribution = battleRedPacketAmountDistributionService.
				findOneByRedPacketIdAndStatusAndMemberId(id,BattleRedPacketAmountDistribution.STATUS_DISTRIBUTION,battlePeriodMember.getId());
		
		if(isRoom==1){
			String roomId = battleRedpacket.getRoomId();
			BattleRoom battleRoom = battleRoomService.findOne(roomId);
			Integer roomNum = battleRoom.getNum();
			if(roomNum==null){
				roomNum = 0;
			}
			Integer isRoomMeet = battleRedpacket.getIsRoomMeet();
			Integer isRoomProcessMeet = battleRedpacket.getIsRoomProcessMeet();
			Integer isRoomScoreMeet = battleRedpacket.getIsRoomScoreMeet();
			if(isRoomProcessMeet==1){
				Integer roomProcess = battleRoom.getRoomProcess();
				if(roomProcess==null){
					roomProcess = 0;
				}
				Integer roomProcessMeet = battleRedpacket.getRoomProcessMeet();
				if(roomProcessMeet==null){
					roomProcessMeet = 0;
				}
				if(roomProcessMeet>roomProcess){
					ResultVo resultVo = new ResultVo();
					resultVo.setSuccess(false);
					resultVo.setErrorCode(2);
					return resultVo;
				}
			}
			
			if(isRoomScoreMeet==1){
				Integer roomScore = battleRoom.getRoomScore();
				if(roomScore==null){
					roomScore = 0;
				}
				
				Integer roomScoreMeet = battleRedpacket.getRoomScoreMeet();
				if(roomScoreMeet==null){
					roomScoreMeet = 0;
				}
				if(roomScoreMeet>roomScore){
					ResultVo resultVo = new ResultVo();
					resultVo.setSuccess(false);
					resultVo.setErrorCode(3);
					return resultVo;
				}
			}
			
			if(isRoomMeet==1){
				Integer roomMeetNum = battleRedpacket.getRoomMeetNum();
				if(roomMeetNum==null){
					roomMeetNum = 0;
				}
				
				if(roomMeetNum>roomNum){
					ResultVo resultVo = new ResultVo();
					resultVo.setSuccess(false);
					resultVo.setErrorCode(4);
					return resultVo;
				}
			}
		}
		
		Integer isPersonalProcessMeet = battleRedpacket.getIsPersonalProcessMeet();
		if(isPersonalProcessMeet==1){
			Integer personalProcessMeet = battleRedpacket.getPersonalProcessMeet();
			Integer process = battlePeriodMember.getProcess();
			if(process==null){
				process = 0;
			}
			
			if(personalProcessMeet==null){
				personalProcessMeet = 0;
			}
			if(personalProcessMeet>process){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorCode(5);
				return resultVo;
			}
		}
		
		Integer isPersonalScoreMeet = battleRedpacket.getIsPersonalScoreMeet();
		
		if(isPersonalScoreMeet==1){
			Integer personalScoreMeet = battleRedpacket.getPersonalScoreMeet();
			Integer score = battlePeriodMember.getScore();
			if(personalScoreMeet==null){
				personalScoreMeet = 0;
			}
			
			if(score==null){
				score = 0;
			}
			
			if(personalScoreMeet>score){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorCode(6);
				return resultVo;
			}
		}
		
		if(battleRedPacketAmountDistribution!=null){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorCode(0);
			resultVo.setErrorMsg("您已经领取，不能再领取了");
			return resultVo;
		}
		
		Pageable pageable = new PageRequest(0, 1);
		
		List<BattleRedPacketAmountDistribution> battleRedPacketAmountDistributions = battleRedPacketAmountDistributionService.
				findAllByRedPacketIdAndStatus(id,BattleRedPacketAmountDistribution.STATUS_FREE,pageable);
		
		if(battleRedPacketAmountDistributions!=null&&battleRedPacketAmountDistributions.size()>0){
			battleRedPacketAmountDistribution = battleRedPacketAmountDistributions.get(0);
			
			UserInfo userInfo = sessionManager.getObject(UserInfo.class);
			Account account = accountService.fineOneSync(userInfo.getAccountId());
			
			BigDecimal distributionAmount = battleRedPacketAmountDistribution.getAmount();
			Long distributionBeanNum = battleRedPacketAmountDistribution.getBeanNum();
			Long distributionMastonryNum = battleRedPacketAmountDistribution.getMastonryNum();
			
			if(distributionAmount==null){
				distributionAmount = new BigDecimal(0);
			}
			
			if(distributionBeanNum==null){
				distributionBeanNum = 0L;
			}
			
			if(distributionMastonryNum==null){
				distributionMastonryNum = 0L;
			}
			
			
			BigDecimal amount = account.getAmountBalance();
			Long beanNum = account.getWisdomCount();
			Integer mastonryNum = account.getMasonry();
			
			if(amount==null){
				amount = new BigDecimal(0);
			}
			
			if(beanNum==null){
				beanNum = 0L;
			}
			
			if(mastonryNum==null){
				mastonryNum = 0;
			}
			
			amount = amount.add(distributionAmount);
			beanNum = beanNum+distributionBeanNum;
			mastonryNum = mastonryNum+distributionMastonryNum.intValue();
			
			account.setAmountBalance(amount);
			account.setWisdomCount(beanNum);
			account.setMasonry(mastonryNum);
			
			
			accountService.update(account);
			
			battleRedPacketAmountDistribution.setStatus(BattleRedPacketAmountDistribution.STATUS_DISTRIBUTION);
			
			battleRedPacketAmountDistribution.setMemberId(battlePeriodMember.getId());
			battleRedPacketAmountDistribution.setReceiveTime(new DateTime());
			battleRedPacketAmountDistribution.setImgUrl(battlePeriodMember.getHeadImg());
			battleRedPacketAmountDistribution.setNickname(battlePeriodMember.getNickname());
			
			battleRedPacketAmountDistributionService.update(battleRedPacketAmountDistribution);
			
			
			Integer receiveNum = battleRedpacket.getReceiveNum();
			if(receiveNum==null){
				receiveNum = 0;
			}
			
			receiveNum++;
			
			if(receiveNum>=battleRedpacket.getNum()){
				battleRedpacket.setStatus(BattleRedpacket.STATUS_END);
			}
			
			battleRedpacket.setReceiveNum(receiveNum);
			
			battleRedpacketService.update(battleRedpacket);
			
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			
			return resultVo;
		}else{
			battleRedpacket.setStatus(BattleRedpacket.STATUS_END);
			battleRedpacketService.update(battleRedpacket);
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorCode(1);
			resultVo.setErrorMsg("已经领取完了");
			return resultVo;
		}
	}
	
	
	@RequestMapping(value="redPackDistributions")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	public ResultVo redPackDistributions(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		String redPackId = httpServletRequest.getParameter("redPackId");
		
		Sort sort = new Sort(Direction.DESC,"receiveTime");
		Pageable pageable = new PageRequest(0, 20, sort);
		
		List<BattleRedPacketAmountDistribution> battleRedPacketAmountDistributions = battleRedPacketAmountDistributionService.
				findAllByRedPacketIdAndStatus(redPackId, BattleRedPacketAmountDistribution.STATUS_DISTRIBUTION, pageable);
		
		List<Map<String, Object>> responseDistributions = new ArrayList<>();
		
		for(BattleRedPacketAmountDistribution battleRedPacketAmountDistribution:battleRedPacketAmountDistributions){
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("id", battleRedPacketAmountDistribution.getId());
			responseData.put("amount", battleRedPacketAmountDistribution.getAmount());
			responseData.put("beanNum", battleRedPacketAmountDistribution.getBeanNum());
			responseData.put("imgUrl", battleRedPacketAmountDistribution.getImgUrl());
			responseData.put("mastonryNum", battleRedPacketAmountDistribution.getMastonryNum());
			responseData.put("memberId", battleRedPacketAmountDistribution.getMemberId());
			responseData.put("nickname", battleRedPacketAmountDistribution.getNickname());
			responseData.put("receiveTime",mySimpleDateFormat.format(battleRedPacketAmountDistribution.getReceiveTime().toDate()));
			responseData.put("redPacketId", battleRedPacketAmountDistribution.getRedPacketId());
			responseData.put("seq", battleRedPacketAmountDistribution.getSeq());
			responseData.put("status", battleRedPacketAmountDistribution.getStatus());
			responseData.put("userId", battleRedPacketAmountDistribution.getUserId());
			
			if(battleRedPacketAmountDistribution.getMemberId().equals(battlePeriodMember.getId())){
				responseData.put("isMy", 1);
			}else{
				responseData.put("isMy", 0);
			}
			
			responseDistributions.add(responseData);
		}
		
		BattleRedpacket battleRedpacket = battleRedpacketService.findOne(redPackId);
		
		Map<String, Object> responseData = new HashMap<>();
		
		responseData.put("id", battleRedpacket.getId());
		responseData.put("senderImg", battleRedpacket.getSenderImg());
		responseData.put("senderName", battleRedpacket.getSenderName());
		responseData.put("num", battleRedpacket.getNum());
		responseData.put("receiveNum", battleRedpacket.getReceiveNum());
		
		responseData.put("distributions", responseDistributions);
		
	
		
		ResultVo resultVo = new ResultVo();
		resultVo.setData(responseData);
		resultVo.setSuccess(true);
		
		return resultVo;
	}
	
	@RequestMapping(value="myPersonalRoom")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo myPersonalRoom(HttpServletRequest httpServletRequest)throws Exception{
		/*SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		String battleId = httpServletRequest.getParameter("battleId");
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		BattleUser battleUser = battleUserService.findOneByUserIdAndBattleId(userInfo.getId(), battleId);
		
		
		Battle battle = battleService.findOne(battleId);
		
		List<BattleRoom> battleRooms = battleRoomService.findAllByBattleIdAndPeriodIdAndOwner(battleId,battle.getCurrentPeriodId(),battleUser.getId());
	
		if(battleRooms!=null&&battleRooms.size()>0){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			resultVo.setData(battleRooms.get(0));
			return resultVo;
		}else{
			httpServletRequest.setAttribute("battleId", battleId);
			//httpServletRequest.setAttribute("periodId",battle.getCurrentPeriodId());
			httpServletRequest.setAttribute("maxinum", 40);
			httpServletRequest.setAttribute("mininum", 2);
			ResultVo resultVo = addRoom(httpServletRequest);
			return resultVo;
		}*/
		return null;
		
	}
	
	
	
	@RequestMapping(value="syncPapersData")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	public ResultVo syncPapersData(HttpServletRequest httpServletRequest)throws Exception{
		
		String groupId = httpServletRequest.getParameter("groupId");
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		BattleRoom battleRoom = null;
		
		try{
			battleRoom = battleRoomService.findOne(battlePeriodMember.getRoomId());
			System.out.println("............222");
		}catch(Exception e){
			logger.error("获取battleRoom错误第一次");
			try{
				battleRoom = battleRoomService.findOne(battlePeriodMember.getRoomId());
			}catch(Exception e2){
				logger.error("获取battleRoom错误第二次,返回结果");
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("获取battleRoom错误");
				return resultVo;
			}
			
		}
		
		System.out.println("............333");
		
		if(battleRoom.getStatus()==BattleRoom.STATUS_END){
			System.out.println("............444");
			Map<String, Object> data = new HashMap<>();
			
			Sort sort = new Sort(Direction.DESC,"score");
			Pageable pageable = new PageRequest(0, 100,sort);
			List<Integer> statuses = new ArrayList<>();
			statuses.add(BattlePeriodMember.STATUS_COMPLETE);
			statuses.add(BattlePeriodMember.STATUS_IN);
			List<BattlePeriodMember> battlePeriodMembers =  new ArrayList<>();
			
			if(CommonUtil.isEmpty(groupId)){
					
				battlePeriodMembers = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDel(battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId(), statuses, 0,pageable);
			}else{
				battlePeriodMembers = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndGroupIdAndIsDel(battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId(), statuses, groupId, 0, pageable);
			}
			data.put("status", battleRoom.getStatus());
			data.put("endType", battleRoom.getEndType());
			
			data.put("roomProcess", battleRoom.getRoomProcess());
			data.put("roomScore", battleRoom.getRoomScore());
			
			data.put("process", battlePeriodMember.getProcess());
			
			data.put("score", battlePeriodMember.getScore());
			
			data.put("scoreGogal", battlePeriodMember.getScrollGogal());
			
			data.put("rewardBean", battlePeriodMember.getRewardBean());
			
			data.put("members", battlePeriodMembers);
			
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			
			resultVo.setData(data);
			
			resultVo.setErrorMsg("同步成功");
					
			return resultVo;
			
		}
		
		
		
		
		List<BattleMemberPaperAnswer> battleMemberPaperAnswers = battleMemberPaperAnswerService.
				findAllByBattlePeriodMemberIdAndIsSyncData(battlePeriodMember.getId(),0);
		
		Integer memberScore = battlePeriodMember.getScore();
	
		
		Integer memberScoreGogal = battlePeriodMember.getScrollGogal();
		
		if(memberScore==null){
			memberScore = 0;
		}
		
		if(memberScoreGogal==null){
			memberScoreGogal = 0;
		}
		
		Integer thisScore = 0;
		
		for(BattleMemberPaperAnswer battleMemberPaperAnswer:battleMemberPaperAnswers){
			
			Integer isPass = battleMemberPaperAnswer.getIsPass();
			BattleRoomRecord battleRoomRecord = new BattleRoomRecord();
			battleRoomRecord.setHappenTime(new DateTime());
			battleRoomRecord.setImgUrl(battlePeriodMember.getHeadImg());
			battleRoomRecord.setNickname(battlePeriodMember.getNickname());
			battleRoomRecord.setMemberId(battlePeriodMember.getId());
			battleRoomRecord.setNickname(battlePeriodMember.getNickname());
			battleRoomRecord.setRoomId(battlePeriodMember.getRoomId());
			battleMemberPaperAnswer.setIsSyncData(1);
			Integer process = battleMemberPaperAnswer.getProcess();
			
			Integer score = battleMemberPaperAnswer.getScore();
			
			
			
			Integer startIndex = battleMemberPaperAnswer.getStartIndex();
			Integer endIndex = battleMemberPaperAnswer.getEndIndex();
			
			if(startIndex==null){
				startIndex = 0;
			}
			
			
			if(process==null){
				process = 0;
			}
			
			endIndex = startIndex+process;
			battleMemberPaperAnswer.setEndIndex(endIndex);
			
			battleMemberPaperAnswerService.update(battleMemberPaperAnswer);
			
			Integer roomProcess = battleRoom.getRoomProcess();
			
			Integer roomScore = battleRoom.getRoomScore();
			
			
			
			if(roomProcess==null){
				roomProcess = 0;
			}
			
			if(roomScore==null){
				roomScore = 0;
			}
			
			if(score==null){
				score = 0;
			}
			
			if(memberScore==null){
				memberScore = 0;
			}
			
			thisScore = thisScore+score;
			
			roomScore = roomScore+score;
			
			memberScore = memberScore+score;
			
			roomProcess = roomProcess + process;
			
			battleRoom.setRoomProcess(roomProcess);
			
			battleRoom.setRoomScore(roomScore);
			
			if(isPass==1){
				
				roomScore = roomScore+battleRoom.getFullRightAddScore();
				
				memberScore = memberScore+battleRoom.getFullRightAddScore();
				
				battleRoom.setRoomScore(roomScore);
				

				StringBuffer sb = new StringBuffer();
				sb.append("["+battlePeriodMember.getNickname()+"]"+"挑战第"+battleMemberPaperAnswer.getStageIndex()+"关成功");
				sb.append(",答对"+battleMemberPaperAnswer.getRightSum()+"题");
				sb.append(",答错"+battleMemberPaperAnswer.getWrongSum()+"题");
				sb.append(",贡献房间分数"+score+"+"+battleMemberPaperAnswer.getFullRightAddScore()+"(通关加分)分");
				sb.append(",贡献距离:"+(process*10)+"米");
				battleRoomRecord.setLog(sb.toString());
				
				battleRoomRecordService.add(battleRoomRecord);
				
				battleRoomEndListener.answerPass(battleRoom,battleMemberPaperAnswer,battlePeriodMember);
				
			}else{
				
				StringBuffer sb = new StringBuffer();
				sb.append("["+battlePeriodMember.getNickname()+"]"+"挑战第"+battleMemberPaperAnswer.getStageIndex()+"关失败");
				sb.append(",答对"+battleMemberPaperAnswer.getRightSum()+"题");
				sb.append(",答错"+battleMemberPaperAnswer.getWrongSum()+"题");
				if(score>0){
					sb.append(",贡献房间分数:"+battleMemberPaperAnswer.getScore()+"分");
				}else if(score<0){
					sb.append(",扣除房间分数:"+(-battleMemberPaperAnswer.getScore())+"分");
				}
				sb.append(",贡献距离："+(process*10)+"米");
				battleRoomRecord.setLog(sb.toString());
				
				battleRoomRecordService.add(battleRoomRecord);
				
				battleRoomEndListener.answerUnPass(battleRoom,battleMemberPaperAnswer,battlePeriodMember);
			}
			
		}
		
		Integer scrollGogal = battleRoom.getScrollGogal();
		
		if(scrollGogal==null){
			scrollGogal = 0;
		}
		
		battlePeriodMember.setScore(memberScore);
		
		sessionManager.update(battlePeriodMember);
		
		Integer roomScore = battleRoom.getRoomScore();
		
		if(memberScore>=memberScoreGogal){
			
			if(battleRoom.getEndEnable()==1){
				battleRoom.setStatus(BattleRoom.STATUS_END);
				battleRoom.setEndType(BattleRoom.SCROLL_GOGAL_END_TYPE);
			}
			
			
			
			List<BattlePeriodMember> battlePeriodMembers = battleDanHandleService.rewardReceive(battleRoom);
			
			for(BattlePeriodMember battlePeriodMember2:battlePeriodMembers){
				if(battlePeriodMember2.getId().equals(battlePeriodMember.getId())){
					battlePeriodMember = battlePeriodMember2;
					break;
				}
			}
			battlePeriodMember.setStatus(BattlePeriodMember.STATUS_COMPLETE);
			
			sessionManager.update(battlePeriodMember);
		
			battleRoomEndListener.roomEnd(battleRoom, battlePeriodMembers);
		}else if(battlePeriodMember.getStageIndex()>battlePeriodMember.getStageCount()){
			
			if(battleRoom.getEndEnable()!=null&&battleRoom.getEndEnable()==1){
				battleRoom.setStatus(BattleRoom.STATUS_END);
			}
			
			
			
			List<BattlePeriodMember> battlePeriodMembers = battleDanHandleService.rewardReceive(battleRoom);
			
			for(BattlePeriodMember battlePeriodMember2:battlePeriodMembers){
				if(battlePeriodMember2.getId().equals(battlePeriodMember.getId())){
					battlePeriodMember = battlePeriodMember2;
					break;
				}
			}
			battlePeriodMember.setStatus(BattlePeriodMember.STATUS_COMPLETE);
			
			sessionManager.update(battlePeriodMember);
			
			battleRoomEndListener.roomEnd(battleRoom, battlePeriodMembers);
			
		}
		
		
		
		
		
		battleRoomService.update(battleRoom);
		
		Map<String, Object> data = new HashMap<>();
		
		
		Integer isPassThrough = battleRoom.getIsPassThrough();
		if(CommonUtil.isEmpty(isPassThrough)){
			isPassThrough = 0; 
		}
		
		if(isPassThrough==1){
			UserInfo userInfo = sessionManager.getObject(UserInfo.class);
			BattleDanTaskUser battleDanTaskUser = battleDanTaskUserService.findOneByRoomIdAndUserId(battleRoom.getId(),userInfo.getId());
			
			if(battleDanTaskUser!=null){
				Integer goalScore = battleDanTaskUser.getGoalScore();
				if(goalScore==null){
					goalScore = 0;
				}
				battleDanTaskUser.setRoomScore(roomScore);
				
				if(battleRoom.getStatus()==BattleRoom.STATUS_END){
					battleDanTaskUser.setStatus(BattleDanTaskUser.STATGUS_COMPLETE);
				}
				
				battleDanTaskUserService.update(battleDanTaskUser);
			}

			
		}else{

		}
		
		Sort sort = new Sort(Direction.DESC,"score");
		Pageable pageable = new PageRequest(0, 100,sort);
		
		List<Integer> statuses = new ArrayList<>();
		statuses.add(BattlePeriodMember.STATUS_IN);
		statuses.add(BattlePeriodMember.STATUS_COMPLETE);
		
		
		
		List<BattlePeriodMember> battlePeriodMembers = new ArrayList<>();
		
		if(CommonUtil.isEmpty(groupId)){
			
			battlePeriodMembers = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDel(battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId(), statuses, 0,pageable);
		}else{
			battlePeriodMembers = battlePeriodMemberService.findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndGroupIdAndIsDel(battleRoom.getBattleId(), battleRoom.getPeriodId(), battleRoom.getId(), statuses, groupId, 0, pageable);
		}
		
		data.put("status", battleRoom.getStatus());
		
		
		System.out.println("............555");
		
		if(battlePeriodMember.getStatus()==BattlePeriodMember.STATUS_COMPLETE){
			data.put("status", BattleRoom.STATUS_END);
		}
		data.put("endType", battleRoom.getEndType());
		
		data.put("roomProcess", battleRoom.getRoomProcess());
		data.put("roomScore", battleRoom.getRoomScore());
		
		data.put("process", battlePeriodMember.getProcess());
		
		data.put("score", battlePeriodMember.getScore());
		
		data.put("scoreGogal", battlePeriodMember.getScrollGogal());
		
		data.put("rewardBean", battlePeriodMember.getRewardBean());
		
		data.put("members", battlePeriodMembers);
		
		data.put("thisScore", thisScore);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		resultVo.setData(data);
		
		resultVo.setErrorMsg("同步成功");
		
	
		return resultVo;
	}
}