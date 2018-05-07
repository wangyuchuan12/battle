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
import com.battle.domain.BattlePeriodMember;
import com.battle.domain.BattlePk;
import com.battle.domain.BattleRoom;
import com.battle.filter.api.BattleTakepartApiFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattlePeriodMemberService;
import com.battle.service.BattlePkService;
import com.battle.service.BattleRoomService;
import com.battle.socket.service.BattleEndSocketService;
import com.battle.socket.service.InitRoomService;
import com.battle.socket.service.PkSocketService;
import com.battle.socket.service.ProgressStatusSocketService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping(value="/api/battleSyncPk/")
public class BattlePkSyncApi {

	@Autowired
	private PkSocketService pkSocketService;
	@Autowired
	private BattlePkService battlePkService;
	
	@Autowired
	private InitRoomService initRoomService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private BattleEndSocketService battleEndSocketService;
	
	@Autowired
	private BattlePeriodMemberService battlePeriodMemberService;
	
	@Autowired
	private ProgressStatusSocketService progressStatusSocketService;
	
	
	final static Logger logger = LoggerFactory.getLogger(BattlePkSyncApi.class);
	
	
	@RequestMapping(value="ready")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo ready(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);

		String id = httpServletRequest.getParameter("id");
		
		//0表示主场 1表示客场
		String role = httpServletRequest.getParameter("role");
		
		
		if(CommonUtil.isEmpty(role)){
			role = "0";
		}
		
		Integer roleInt = Integer.parseInt(role);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);

		BattlePk battlePk = battlePkService.findOne(id);;


		if(roleInt==0&&userInfo.getId().equals(battlePk.getHomeUserId())){
			
			if(battlePk.getHomeStatus().equals(BattlePk.STATUS_READY)){
				battlePk.setHomeStatus(BattlePk.STATUS_INSIDE);
				
			}else if(battlePk.getHomeStatus().equals(BattlePk.STATUS_INSIDE)){
				battlePk.setHomeStatus(BattlePk.STATUS_READY);
			}
		}else if(roleInt==1&&!userInfo.getId().equals(battlePk.getHomeUserId())&&userInfo.getId().equals(battlePk.getBeatUserId())){
			if(battlePk.getBeatStatus().equals(BattlePk.STATUS_INSIDE)){
				battlePk.setBeatStatus(BattlePk.STATUS_READY);
			
			}else if(battlePk.getBeatStatus().equals(BattlePk.STATUS_READY)){
				battlePk.setBeatStatus(BattlePk.STATUS_INSIDE);				
			}
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("数据错误");
			
			return resultVo;
		}
		
		if(battlePk.getHomeStatus().intValue()==BattlePk.STATUS_READY&&
				battlePk.getBeatStatus().intValue()==BattlePk.STATUS_READY&&
				battlePk.getRoomStatus().intValue()==BattlePk.ROOM_STATUS_FREE){
			BattleRoom battleRoom = initRoomService.addPkRoom(battlePk.getHomeUserId(),battlePk.getBeatUserId());
			battlePk.setHomeStatus(BattlePk.STATUS_BATTLE);
			battlePk.setBeatStatus(BattlePk.STATUS_BATTLE);
			battlePk.setRoomStatus(BattlePk.ROOM_STATUS_BATTLE);
			battlePk.setRoomId(battleRoom.getId());
			battlePk.setBattleId(battleRoom.getBattleId());
			battlePk.setPeriodId(battleRoom.getPeriodId());
		}
		
		battlePkService.update(battlePk);
		pkSocketService.statusPublish(battlePk);
		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		resultVo.setData(battlePk);
		
		return resultVo;
		
	}
	
	
	@RequestMapping(value="restart")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo restart(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		final BattlePk battlePk = battlePkService.findOneByHomeUserId(userInfo.getId());
		
		
		battlePk.setHomeStatus(BattlePk.STATUS_INSIDE);
		
		
		battlePk.setBeatStatus(BattlePk.STATUS_INSIDE);

		
		battlePk.setRoomStatus(BattlePk.ROOM_STATUS_FREE);
		
		
		final BattleRoom oldBattleRoom = battleRoomService.findOne(battlePk.getRoomId());
		
		oldBattleRoom.setStatus(BattleRoom.STATUS_END);
		
		oldBattleRoom.setEndType(BattleRoom.FORCE_END_TYPE);
		
		battleRoomService.update(oldBattleRoom);
		
		battlePk.setRoomId("");
		battlePk.setBattleId("");
		
		battlePk.setPeriodId("");
		
		battlePkService.update(battlePk);
		
		final BattlePeriodMember battlePeriodMember = battlePeriodMemberService.findOneByRoomIdAndUserIdAndIsDel(oldBattleRoom.getId(), userInfo.getId(), 0);
		battlePeriodMember.setLoveResidule(0);
		
		battlePeriodMemberService.update(battlePeriodMember);
		
		new Thread(){
			public void run() {
				try{
					battleEndSocketService.endPublish(oldBattleRoom.getId());
					pkSocketService.statusPublish(battlePk);
					progressStatusSocketService.statusPublish(oldBattleRoom.getId(), battlePeriodMember, battlePeriodMember.getUserId());
				}catch(Exception e){
					logger.error("{}",e);
				}
			}
		}.start();
		

		ResultVo resultVo = new ResultVo();
		
		resultVo.setSuccess(true);
		
		return resultVo;
	}
	
	//客场方进入
	@RequestMapping(value="beatInto")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo beatInto(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		String id = httpServletRequest.getParameter("id");
		
		BattlePk battlePk = battlePkService.findOne(id);
		
		if(battlePk.getHomeUserId().equals(userInfo.getId())){
			return homeInto(httpServletRequest);
		}
		
		Integer beatStatus = battlePk.getBeatStatus();
		
		DateTime beatActiveTime = battlePk.getBeatActiveTime();
		
		DateTime nowDatetime = new DateTime();
		
		Long differ = nowDatetime.getMillis()-beatActiveTime.getMillis();
		
		ResultVo resultVo  = new ResultVo();
		
		if(beatStatus==BattlePk.STATUS_LEAVE||(beatStatus==BattlePk.STATUS_INSIDE&&differ>50000)||(beatStatus==BattlePk.STATUS_READY&&differ>5000000)){
	
			battlePk.setBeatUserId(userInfo.getId());
			
			battlePk.setBeatUserImgurl(userInfo.getHeadimgurl());
			
			battlePk.setBeatUsername(userInfo.getNickname());
			
			battlePk.setBeatStatus(BattlePk.STATUS_INSIDE);
			
			battlePk.setBeatActiveTime(new DateTime());

			battlePkService.update(battlePk);
			
			pkSocketService.statusPublish(battlePk);
			
			
		}else{
		}
		
		
		if(battlePk.getRoomStatus().intValue()==BattlePk.ROOM_STATUS_BATTLE){
			BattleRoom battleRoom = battleRoomService.findOne(battlePk.getRoomId());
			if(battleRoom.getStatus().intValue()==BattleRoom.STATUS_END){
				battlePk.setRoomStatus(BattlePk.ROOM_STATUS_FREE);
				battlePk.setBeatStatus(BattlePk.STATUS_INSIDE);
				battlePk.setHomeStatus(BattlePk.STATUS_INSIDE);
				battlePk.setRoomId("");
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
		
		if(battlePk.getBeatUserId().equals(userInfo.getId())){
			data.put("isObtain",1);
		}else{
			data.put("isObtain",0);
		}
		resultVo.setSuccess(true);
		resultVo.setData(data);
		return resultVo;
		
	}
	
	
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
			
		}else{
			battlePk.setHomeUserImgurl(userInfo.getHeadimgurl());
			
			battlePk.setHomeUsername(userInfo.getNickname());
			
			battlePk.setHomeActiveTime(new DateTime());
		}
		
		
		if(battlePk.getRoomStatus().intValue()==BattlePk.ROOM_STATUS_BATTLE){
			BattleRoom battleRoom = battleRoomService.findOne(battlePk.getRoomId());
			if(battleRoom.getStatus().intValue()==BattleRoom.STATUS_END){
				battlePk.setRoomStatus(BattlePk.ROOM_STATUS_FREE);
				battlePk.setBeatStatus(BattlePk.STATUS_LEAVE);
				battlePk.setHomeStatus(BattlePk.STATUS_INSIDE);
				battlePk.setRoomId("");
				battlePkService.update(battlePk);
			}
		}
		
		pkSocketService.statusPublish(battlePk);
		
		
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
	
}
