package com.battle.api;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.battle.domain.Battle;
import com.battle.domain.BattleMemberLoveCooling;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattlePeriodStage;
import com.battle.domain.BattleQuestion;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleUser;
import com.battle.filter.api.BattleMemberInfoApiFilter;
import com.battle.filter.api.BattleMembersApiFilter;
import com.battle.filter.api.BattleSubjectApiFilter;
import com.battle.filter.api.BattleTakepartApiFilter;
import com.battle.filter.api.CurrentLoveCoolingApiFilter;
import com.battle.filter.element.CurrentBattleUserFilter;
import com.battle.filter.element.CurrentMemberInfoFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattlePeriodService;
import com.battle.service.BattlePeriodStageService;
import com.battle.service.BattleQuestionService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleService;
import com.battle.service.BattleUserService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.Account;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.service.AccountService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
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
		
		Page<BattleRoom> battleRoomPage = battleRoomService.findAllByBattleIdAndStatusAndIsPublic(battleId, BattleRoom.STATUS_IN , 1, pageable);
		
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
		
		System.out.println("userInfo.id:"+userInfo.getId()+",battleRooms:"+battleRooms);
		
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
		battleRoom.setIsPublic(0);
		battleRoom.setIsPublic(isPublicInt);
		
		battleRoom.setSpeedCoolBean(10);
		battleRoom.setSpeedCoolSecond(1800);
		
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
		Page<BattleRoom> battleRooms = battleRoomService.findAllByIsPublicAndStatusOrderByCreationTimeAsc(1,statusInt,pageable);
		
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
	
}