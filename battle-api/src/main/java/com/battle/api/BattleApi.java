package com.battle.api;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
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
import com.battle.domain.BattleMemberLoveCooling;
import com.battle.domain.BattleMemberPaperAnswer;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattlePeriodStage;
import com.battle.domain.BattleQuestion;
import com.battle.domain.BattleRedPacketAmountDistribution;
import com.battle.domain.BattleRedpacket;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleRoomRecord;
import com.battle.domain.BattleUser;
import com.battle.filter.api.BattleMemberInfoApiFilter;
import com.battle.filter.api.BattleMembersApiFilter;
import com.battle.filter.api.BattleSubjectApiFilter;
import com.battle.filter.api.BattleTakepartApiFilter;
import com.battle.filter.api.CurrentLoveCoolingApiFilter;
import com.battle.filter.element.CurrentBattleUserFilter;
import com.battle.filter.element.CurrentMemberInfoFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleMemberPaperAnswerService;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattlePeriodService;
import com.battle.service.BattlePeriodStageService;
import com.battle.service.BattleQuestionService;
import com.battle.service.BattleRedPacketAmountDistributionService;
import com.battle.service.BattleRedpacketService;
import com.battle.service.BattleRoomRecordService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleService;
import com.battle.service.BattleUserService;
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
	
	@RequestMapping(value="roomInfo")
	@ResponseBody
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
		data.put("currentPeriodId", battle.getCurrentPeriodId());
		data.put("distance", battle.getDistance());
		data.put("headImg", battle.getHeadImg());
		data.put("instruction", battle.getInstruction());
		data.put("isActivation", battle.getIsActivation());
		data.put("name", battle.getName());
		data.put("maxinum", battleRoom.getMaxinum());
		data.put("mininum", battleRoom.getMininum());
		data.put("owner", battleRoom.getOwner());
		data.put("speedCoolBean", battleRoom.getSpeedCoolBean());
		data.put("speedCoolSecond", battleRoom.getSpeedCoolSecond());
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
	public Object randomRoom(HttpServletRequest httpServletRequest)throws Exception{
		String battleId = httpServletRequest.getParameter("battleId");
		Pageable pageable = new PageRequest(0, 1);
		
		Page<BattleRoom> battleRoomPage = battleRoomService.findAllByBattleIdAndStatusAndIsSearchAble(battleId, BattleRoom.STATUS_IN , 1, pageable);
		
		List<BattleRoom> battleRooms = battleRoomPage.getContent();
		
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
			
			String[] subjectIds = subjectIdStr.split(",");
			
			BattlePeriodStage battlePeriodStage = battlePeriodStageService.findOneByBattleIdAndPeriodIdAndIndex(battlePeriodMember.getBattleId(), battlePeriodMember.getPeriodId(), stageIndex);
			
			Integer questionCount = battlePeriodStage.getQuestionCount();
			
			
			
			List<BattleQuestion> battleQuestions = battleQuestionService.findAllByBattleIdAndPeriodStageIdAndBattleSubjectIdIn(battlePeriodStage.getBattleId(), battlePeriodStage.getId(),subjectIds);
			
			
			
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
	
	
	@RequestMapping(value="addRoom")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object addRoom(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		String battleId = httpServletRequest.getParameter("battleId");
		String periodId = httpServletRequest.getParameter("periodId");
		String maxinum = httpServletRequest.getParameter("maxinum");
		String mininum = httpServletRequest.getParameter("mininum");
		String isPublic = httpServletRequest.getParameter("isPublic");
		
		
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
		
		
		List<BattleRoom> battleRooms = battleRoomService.findAllByBattleIdAndPeriodIdAndOwner(battleId,periodId,battleUser.getId());
		if(battleRooms!=null&&battleRooms.size()>0&&battleUser.getIsManager()!=1){
			BattleRoom battleRoom = battleRooms.get(0);
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorCode(0);
			resultVo.setErrorMsg("您已经创建过该主题的房间，不能重复创建");
			resultVo.setData(battleRoom);
			return resultVo;
		}
		BattleRoom battleRoom = new BattleRoom();
		battleRoom.setBattleId(battleId);
		battleRoom.setPeriodId(periodId);
		battleRoom.setCreationTime(new DateTime());
		battleRoom.setMaxinum(maxinumInt);
		battleRoom.setMininum(mininumInt);
		battleRoom.setPeriodId(periodId);
		battleRoom.setOwner(battleUser.getId());
		battleRoom.setName(battle.getName());
		battleRoom.setInstruction(battle.getInstruction());
		battleRoom.setImgUrl(battle.getHeadImg());
		battleRoom.setStatus(BattleRoom.STATUS_FREE);
		battleRoom.setNum(0);
		
		battleRoom.setSmallImgUrl(userInfo.getHeadimgurl());
		
		battleRoom.setIsSearchAble(isPublicInt);
		
		battleRoom.setIsDisplay(0);
		
		battleRoom.setSpeedCoolBean(10);
		battleRoom.setSpeedCoolSecond(1800);
		
		battleRoom.setRedPackNum(0);
		
		battleRoom.setRoomScore(0);
		
		battleRoom.setFullRightAddScore(10);
		
		battleRoom.setRightAddProcess(1);
		
		battleRoom.setRightAddScore(1);
		
		battleRoom.setWrongSubScore(1);
		
		battleRoom.setIsRedpack(0);
		
		battleRoom.setRedpackAmount(new BigDecimal(0));
		
		battleRoom.setRedpackBean(0);
		
		battleRoom.setRedpackMasonry(0);
		
		battleRoomService.add(battleRoom);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setData(battleRoom);
		resultVo.setMsg("添加成功");
		resultVo.setSuccess(true);
		
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
		
		Integer statusInt = Integer.parseInt(status);
		
		if(sizeInt>20){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("条数不能超过50");
			return resultVo;
		}
		
		
		Pageable pageable = new PageRequest(pageInt,sizeInt);
		Page<BattleRoom> battleRooms = battleRoomService.findAllByIsDisplayAndStatusOrderByCreationTimeAsc(1,statusInt,pageable);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleRooms.getContent());
		
		return resultVo;
		
		
	}
	
	@RequestMapping(value="roomSignout")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
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
		
		battlePeriodMember.setStatus(BattlePeriodMember.STATUS_OUT);
		
		battlePeriodMemberService.update(battlePeriodMember);
		
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
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleRedpackets);
		
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
		
		System.out.println("isPersonalScoreMeet:"+isPersonalScoreMeet);
		
		
		if(isPersonalScoreMeet==1){
			Integer personalScoreMeet = battleRedpacket.getPersonalScoreMeet();
			Integer score = battlePeriodMember.getScore();
			if(personalScoreMeet==null){
				personalScoreMeet = 0;
			}
			
			if(score==null){
				score = 0;
			}
			
			System.out.println("personalScoreMeet:"+personalScoreMeet+",score:"+score);
			
			System.out.println("personalScoreMeet>score:"+(personalScoreMeet>score));
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
	
	@RequestMapping(value="syncPapersData")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	public ResultVo syncPapersData(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		BattleRoom battleRoom = battleRoomService.findOne(battlePeriodMember.getRoomId());
		
		List<BattleMemberPaperAnswer> battleMemberPaperAnswers = battleMemberPaperAnswerService.
				findAllByBattlePeriodMemberIdAndIsSyncData(battlePeriodMember.getId(),0);
		
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
			
			if(score==null){
				score = 0;
			}
			
			battleMemberPaperAnswerService.update(battleMemberPaperAnswer);
			if(process==null){
				process = 0;
			}
			
			Integer roomProcess = battleRoom.getRoomProcess();
			
			Integer roomScore = battleRoom.getRoomScore();
			
			
			
			if(roomProcess==null){
				roomProcess = 0;
			}
			
			if(roomScore==null){
				roomScore = 0;
			}
			
			roomProcess = roomProcess + process;
			
			battleRoom.setRoomProcess(roomProcess);
			
			if(isPass==1){
				
				roomScore = roomScore+battleRoom.getFullRightAddScore();
				
				battleRoom.setRoomScore(roomScore);

				StringBuffer sb = new StringBuffer();
				sb.append("["+battlePeriodMember.getNickname()+"]"+"挑战第"+battleMemberPaperAnswer.getStageIndex()+"关成功");
				sb.append(",答对"+battleMemberPaperAnswer.getRightSum()+"题");
				sb.append(",答错"+battleMemberPaperAnswer.getWrongSum()+"题");
				sb.append(",贡献房间分数"+score+"+"+battleMemberPaperAnswer.getFullRightAddScore()+"(通关加分)分");
				sb.append(",贡献距离:"+(process*10)+"米");
				battleRoomRecord.setLog(sb.toString());
				
				battleRoomRecordService.add(battleRoomRecord);
				
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
			}
			
			battleRoomService.update(battleRoom);
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		resultVo.setErrorMsg("同步成功");
		
		return resultVo;
	}
}