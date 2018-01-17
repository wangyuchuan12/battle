package com.battle.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.Battle;
import com.battle.domain.BattleCreateDetail;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattlePk;
import com.battle.domain.BattleRoom;
import com.battle.domain.BattleUser;
import com.battle.filter.api.BattleTakepartApiFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleCreateDetailService;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattlePkService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleService;
import com.battle.service.BattleUserService;
import com.battle.service.other.BattlePkImmediateService;
import com.battle.service.other.BattleRoomHandleService;
import com.battle.service.redis.BattlePkRedisService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.config.AppConfig;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/battlePk/")
public class BattlePkApi {

	@Autowired
	private BattlePkService battlePkService;
	
	@Autowired
	private BattleRoomHandleService battleRoomHandleService;
	
	@Autowired
	private BattleCreateDetailService battleCreateDetailServie;
	
	@Autowired
	private BattleService battleService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private BattlePkImmediateService battlePkImmediateService;
	
	@Autowired
	private BattlePkRedisService battlePkRedisService;
	
	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Autowired
	private BattleUserService battleUserService;
	
	final static Logger logger = LoggerFactory.getLogger(BattlePkApi.class);
	
	//主场方进入
	@RequestMapping(value="homeInto")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo homeInto(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		BattlePk battlePk = battlePkService.findOneByHomeUserId(userInfo.getId());
		
		if(battlePk==null){
			battlePk = new BattlePk();
			battlePk.setHomeUserId(userInfo.getId());
			
			battlePk.setHomeUserImgurl(userInfo.getHeadimgurl());
			
			battlePk.setHomeUsername(userInfo.getNickname());
			
			battlePk.setHomeStatus(BattlePk.STATUS_INSIDE);
			
			battlePk.setBattleCount(0);
			
			battlePk.setBeatStatus(BattlePk.STATUS_LEAVE);
			
			battlePk.setRoomStatus(BattlePk.ROOM_STATUS_FREE);
			
			battlePk.setHomeActiveTime(new DateTime());
			
			battlePk.setBeatActiveTime(new DateTime());
			
			battlePkService.add(battlePk);
			
		}
		
		if(battlePk.getRoomStatus()==BattlePk.ROOM_STATUS_FREE||battlePk.getRoomStatus()==BattlePk.ROOM_STATUS_END){
			List<BattleCreateDetail> battleCreateDetails = battleCreateDetailServie.findAllByIsDefault(1);
			
			if(battleCreateDetails==null||battleCreateDetails.size()==0){
				ResultVo resultVo = new ResultVo();
				
				resultVo.setSuccess(false);
				
				resultVo.setErrorMsg("createDetail为空");
				
				return resultVo;
			}
			
			BattleCreateDetail battleCreateDetail = battleCreateDetails.get(0);
			Battle battle = battleService.findOne(battleCreateDetail.getBattleId());
			
			BattleRoom battleRoom = battleRoomHandleService.initRoom(battle);
			battleRoom.setIsPk(1);
			battleRoom.setPeriodId(battleCreateDetail.getPeriodId());
			battleRoom.setMaxinum(2);
			battleRoom.setMininum(2);
			battleRoom.setIsSearchAble(0);
			battleRoom.setScrollGogal(battleCreateDetail.getScrollGogal());
			battleRoom.setPlaces(1);
			battleRoom.setIsDanRoom(0);
			
			battleRoom.setIsIncrease(1);
			
			battleRoomService.add(battleRoom);
			
			battlePk.setRoomId(battleRoom.getId());
			battlePk.setBattleId(battle.getId());
			
			battlePk.setPeriodId(battleCreateDetail.getPeriodId());
			
			battlePk.setHomeActiveTime(new DateTime());
			
			battlePk.setRoomStatus(BattlePk.ROOM_STATUS_CALL);
			
			battlePkService.update(battlePk);
		}
		
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("id", battlePk.getId());
		data.put("homeUserId", battlePk.getHomeUserId());
		data.put("homeUserImgurl", battlePk.getHomeUserImgurl());
		data.put("homeUsername", battlePk.getHomeUsername());
		data.put("homeStatus", battlePk.getHomeStatus());
		
		data.put("beatUserId", battlePk.getBeatUserId());
		data.put("beatUserImgurl", battlePk.getBeatUserImgurl());
		data.put("beatUsername", battlePk.getBeatUsername());
		data.put("beatStatus", battlePk.getBeatStatus());
		data.put("battleCount", battlePk.getBattleCount());
		data.put("roomStatus", battlePk.getRoomStatus());
		
		data.put("roomId", battlePk.getRoomId());
		
		data.put("battleId", battlePk.getBattleId());
		
		data.put("periodId", battlePk.getPeriodId());
		
		data.put("role", 0);
		
		if(battlePk.getHomeUserId().equals(userInfo.getId())){
			data.put("isObtain",1);
		}else{
			data.put("isObtain",0);
		}
		
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		resultVo.setData(data);
		
		return resultVo;
		
	}
	
	
	//客场方进入
	@RequestMapping(value="beatInto")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo beatInto(HttpServletRequest httpServletRequest)throws Exception{
		
		
		System.out.println("...........beatInto");
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		String id = httpServletRequest.getParameter("id");
		
		BattlePk battlePk = battlePkService.findOne(id);
		
		if(battlePk.getHomeUserId().equals(userInfo.getId())){
			System.out.println(".......homeIntoCall");
			return homeInto(httpServletRequest);
		}
		
		if(battlePk.getRoomStatus()==BattlePk.ROOM_STATUS_CALL||battlePk.getRoomStatus()==BattlePk.ROOM_STATUS_FREE){
			
			System.out.println(".......status");
			Integer beatStatus = battlePk.getBeatStatus();
			
			DateTime beatActiveTime = battlePk.getBeatActiveTime();
			
			DateTime nowDatetime = new DateTime();
			
			Long differ = nowDatetime.getMillis()-beatActiveTime.getMillis();
			
			System.out.println("beatStatus:"+beatStatus+",differ:"+differ);
			
			if(beatStatus==BattlePk.STATUS_LEAVE||(beatStatus==BattlePk.STATUS_INSIDE&&differ<2000)||(beatStatus==BattlePk.STATUS_READY&&differ<20000)){
		
				System.out.println(".......STATUS_LEAVE");
				battlePk.setBeatUserId(userInfo.getId());
				
				battlePk.setBeatUserImgurl(userInfo.getHeadimgurl());
				
				battlePk.setBeatUsername(userInfo.getNickname());
				
				battlePk.setBeatStatus(BattlePk.STATUS_INSIDE);
				
				battlePk.setBeatActiveTime(new DateTime());
				
				
				battlePkService.update(battlePk);
			}
		}
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("id", battlePk.getId());
		data.put("homeUserId", battlePk.getHomeUserId());
		data.put("homeUserImgurl", battlePk.getHomeUserImgurl());
		data.put("homeUsername", battlePk.getHomeUsername());
		data.put("homeStatus", battlePk.getHomeStatus());
		
		data.put("beatUserId", battlePk.getBeatUserId());
		data.put("beatUserImgurl", battlePk.getBeatUserImgurl());
		data.put("beatUsername", battlePk.getBeatUsername());
		data.put("beatStatus", battlePk.getBeatStatus());
		data.put("battleCount", battlePk.getBattleCount());
		data.put("roomStatus", battlePk.getRoomStatus());
		data.put("battleId", battlePk.getBattleId());
		data.put("periodId", battlePk.getPeriodId());
		data.put("roomId", battlePk.getRoomId());
		
		data.put("role", 1);
		
		System.out.println(".........battlePk.getBeatUsername():"+battlePk.getBeatUsername());
		
		if(battlePk.getBeatUserId().equals(userInfo.getId())){
			data.put("isObtain",1);
		}else{
			data.put("isObtain",0);
		}
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		resultVo.setData(data);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="restart")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo restart(HttpServletRequest httpServletRequest)throws Exception{

		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		BattlePk battlePk = battlePkService.findOneByHomeUserId(userInfo.getId());
		
		
		battlePk.setHomeStatus(BattlePk.STATUS_INSIDE);
		
		
		battlePk.setBeatStatus(BattlePk.STATUS_LEAVE);
		
		battlePk.setBeatUserImgurl("");
		battlePk.setBeatUsername("");
		
		battlePk.setRoomStatus(BattlePk.ROOM_STATUS_FREE);
		
		List<BattleCreateDetail> battleCreateDetails = battleCreateDetailServie.findAllByIsDefault(1);
		
		if(battleCreateDetails==null||battleCreateDetails.size()==0){
			ResultVo resultVo = new ResultVo();
			
			resultVo.setSuccess(false);
			
			resultVo.setErrorMsg("createDetail为空");
			
			return resultVo;
		}
		
		BattleCreateDetail battleCreateDetail = battleCreateDetails.get(0);
		Battle battle = battleService.findOne(battleCreateDetail.getBattleId());
		
		BattleRoom battleRoom = battleRoomHandleService.initRoom(battle);
		battleRoom.setIsPk(1);
		battleRoom.setPeriodId(battleCreateDetail.getPeriodId());
		battleRoom.setMaxinum(2);
		battleRoom.setMininum(2);
		battleRoom.setIsSearchAble(0);
		battleRoom.setScrollGogal(battleCreateDetail.getScrollGogal());
		battleRoom.setPlaces(1);
		battleRoom.setIsDanRoom(0);
		
		battleRoomService.add(battleRoom);
		
		battlePk.setRoomId(battleRoom.getId());
		battlePk.setBattleId(battle.getId());
		
		battlePk.setPeriodId(battleCreateDetail.getPeriodId());
		
		battlePkService.update(battlePk);

		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		return resultVo;
	}
	
	
	@RequestMapping(value="immediateData")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo immediateData(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		String id = httpServletRequest.getParameter("id");
		
		if(CommonUtil.isEmpty(id)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("调用参数id不能为空");
			logger.error("调用immediateData方法的时候参数传入的参数id为空");
			return resultVo;
		}
		
		BattlePk battlePk = battlePkService.findOne(id);
		
		if(battlePk==null){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("返回battlePk对象为空");
			logger.error("调用immediateData方法的时候返回battlePk对象为空");
			return resultVo;
		}
		
		String homeUserId = battlePk.getHomeUserId();
		
		String beatUserId = battlePk.getBeatUserId();
		
		if(!CommonUtil.isEmpty(homeUserId)){
			if(homeUserId.equals(userInfo.getId())){
				battlePk.setHomeActiveTime(new DateTime());
			}
		}else if(!CommonUtil.isEmpty(beatUserId)){
			if(beatUserId.equals(userInfo.getId())){
				battlePk.setBeatActiveTime(new DateTime());
			}
		}
		
		
		battlePkService.update(battlePk);
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(battlePk);
		
		return resultVo;
		
	}
	
	
	@RequestMapping(value="ready")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=BattleTakepartApiFilter.class)
	public ResultVo ready(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		
		BattlePeriodMember battlePeriodMember2 = sessionManager.getObject(BattlePeriodMember.class);
		
		System.out.println(".................battlePeriodMember2:"+battlePeriodMember2.getId()+",battleRoom.id:"+battlePeriodMember2.getRoomId());
		
		ResultVo resultVo2 = (ResultVo)sessionManager.getReturnValue();
		
		if(resultVo2==null){
			resultVo2 = sessionManager.getObject(ResultVo.class);
		}
		
		if(resultVo2!=null){
			System.out.println("....................................success:"+resultVo2.isSuccess()+",errorMsg:"+resultVo2.getErrorMsg());
		}
		
		String id = httpServletRequest.getParameter("id");
		
		String role = httpServletRequest.getParameter("role");
		
		if(CommonUtil.isEmpty(role)){
			role = "0";
		}
		
		Integer roleInt = Integer.parseInt(role);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);

		BattlePk battlePk = null;

		try{
			battlePk = battlePkRedisService.get(id);
		}catch(Exception e){
			
		}
		
		
		if(battlePk==null){
			battlePk  = battlePkService.findOne(id);
		}
		
		
		if(battlePk.getRoomStatus()==BattlePk.ROOM_STATUS_BATTLE||battlePk.getRoomStatus()==BattlePk.ROOM_STATUS_END){
			ResultVo resultVo = new ResultVo();
			
			resultVo.setSuccess(false);
			
			resultVo.setErrorMsg("状态不对");
			
			return resultVo;
		}
		
		
		System.out.println("............roleInt:"+roleInt+",userInfo.getId:"+userInfo.getId()+",battlePk.getHomeUserId:"+battlePk.getHomeUserId());
		
		if(roleInt==0&&userInfo.getId().equals(battlePk.getHomeUserId())){
			
			System.out.println("battlePk.getHomeStatus():"+battlePk.getHomeStatus());
			System.out.println("battlePk.getHomeStatus()==BattlePk.STATUS_READY:"+(battlePk.getHomeStatus()==BattlePk.STATUS_READY));
			System.out.println("battlePk.getHomeStatus()==BattlePk.STATUS_INSIDE:"+(battlePk.getHomeStatus()==BattlePk.STATUS_INSIDE));
			if(battlePk.getHomeStatus().equals(BattlePk.STATUS_READY)){
				System.out.println("...............................1");
				battlePk.setHomeStatus(BattlePk.STATUS_INSIDE);
				battlePk.setRoomStatus(BattlePk.ROOM_STATUS_CALL);
				BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
				battlePeriodMember.setIsDel(1);
				BattleRoom battleRoom = sessionManager.getObject(BattleRoom.class);
				Integer num = battleRoom.getNum();
				num--;
				battleRoom.setNum(num);
				battleRoom.setStatus(BattleRoom.STATUS_IN);
				sessionManager.update(battlePeriodMember);
				sessionManager.update(battleRoom);
			}else if(battlePk.getHomeStatus().equals(BattlePk.STATUS_INSIDE)){
				System.out.println(".............................2");
				battlePk.setHomeStatus(BattlePk.STATUS_READY);
				if(battlePk.getBeatStatus()==BattlePk.STATUS_READY){
					battlePk.setRoomStatus(BattlePk.ROOM_STATUS_BATTLE);
				}else{
					battlePk.setRoomStatus(BattlePk.ROOM_STATUS_CALL);
				}
			}
		}else if(roleInt==1&&!userInfo.getId().equals(battlePk.getHomeUserId())){
			if(battlePk.getBeatStatus().equals(BattlePk.STATUS_INSIDE)){
				System.out.println("..............................3");
				battlePk.setBeatStatus(BattlePk.STATUS_READY);
				if(battlePk.getHomeStatus()==BattlePk.STATUS_READY){
					battlePk.setRoomStatus(BattlePk.ROOM_STATUS_BATTLE);
				}else{
					battlePk.setRoomStatus(BattlePk.ROOM_STATUS_CALL);
				}
			}else if(battlePk.getBeatStatus().equals(BattlePk.STATUS_READY)){
				
				System.out.println(".................................4");
				battlePk.setBeatStatus(BattlePk.STATUS_INSIDE);
				battlePk.setRoomStatus(BattlePk.ROOM_STATUS_CALL);
				
				BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
				battlePeriodMember.setIsDel(1);
				BattleRoom battleRoom = sessionManager.getObject(BattleRoom.class);
				Integer num = battleRoom.getNum();
				num--;
				battleRoom.setNum(num);
				battleRoom.setStatus(BattleRoom.STATUS_IN);
				sessionManager.update(battlePeriodMember);
				sessionManager.update(battleRoom);
				
			}
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("数据错误");
			
			return resultVo;
		}
		
		
		battlePkService.update(battlePk);
		
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(battlePk);
		
		return resultVo;
		
	}
	
}
