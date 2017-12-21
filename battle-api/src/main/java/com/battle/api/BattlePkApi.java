package com.battle.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.Battle;
import com.battle.domain.BattleCreateDetail;
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
			
			battleRoomService.add(battleRoom);
			
			battlePk.setRoomId(battleRoom.getId());
			battlePk.setBattleId(battle.getId());
			
			battlePk.setPeriodId(battleCreateDetail.getPeriodId());
			
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
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		String id = httpServletRequest.getParameter("id");
		
		BattlePk battlePk = battlePkService.findOne(id);
		
		if(battlePk.getHomeUserId().equals(userInfo.getId())){
			return homeInto(httpServletRequest);
		}
		
		if(battlePk.getRoomStatus()==BattlePk.ROOM_STATUS_CALL||battlePk.getRoomStatus()==BattlePk.ROOM_STATUS_FREE){
			
			Integer beatStatus = battlePk.getBeatStatus();
			
			if(beatStatus==BattlePk.STATUS_LEAVE){
		
				battlePk.setBeatUserId(userInfo.getId());
				
				battlePk.setBeatUserImgurl(userInfo.getHeadimgurl());
				
				battlePk.setBeatUsername(userInfo.getNickname());
				
				battlePk.setBeatStatus(BattlePk.STATUS_INSIDE);
				
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
	
	
	@RequestMapping(value="immediateData")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo immediateData(HttpServletRequest httpServletRequest)throws Exception{
		
		String id = httpServletRequest.getParameter("id");
		
		BattlePk battlePk = battlePkImmediateService.immediateUntilStatusChange(id);
		
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
		
		if(roleInt==0&&userInfo.getId().equals(battlePk.getHomeUserId())){
			if(battlePk.getHomeStatus()==BattlePk.STATUS_READY){
				battlePk.setHomeStatus(BattlePk.STATUS_INSIDE);
				battlePk.setRoomStatus(BattlePk.ROOM_STATUS_CALL);
				BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
				battlePeriodMember.setIsDel(1);
				BattleRoom battleRoom = sessionManager.getObject(BattleRoom.class);
				Integer num = battleRoom.getNum();
				num--;
				battleRoom.setNum(num);
				battleRoom.setStatus(BattleRoom.STATUS_IN);
				battlePeriodMemberService.update(battlePeriodMember);
				battleRoomService.update(battleRoom);
			}else if(battlePk.getHomeStatus()==BattlePk.STATUS_INSIDE){
				battlePk.setHomeStatus(BattlePk.STATUS_READY);
				
				
				if(battlePk.getBeatStatus()==BattlePk.STATUS_READY){
					battlePk.setRoomStatus(BattlePk.ROOM_STATUS_BATTLE);
				}else{
					battlePk.setRoomStatus(BattlePk.ROOM_STATUS_CALL);
				}
			}
		}else if(roleInt==1&&!userInfo.getId().equals(battlePk.getHomeUserId())){
			if(battlePk.getBeatStatus()==BattlePk.STATUS_INSIDE){
				battlePk.setBeatStatus(BattlePk.STATUS_READY);
				if(battlePk.getHomeStatus()==BattlePk.STATUS_READY){
					battlePk.setRoomStatus(BattlePk.ROOM_STATUS_BATTLE);
				}else{
					battlePk.setRoomStatus(BattlePk.ROOM_STATUS_CALL);
				}
			}else if(battlePk.getBeatStatus()==BattlePk.STATUS_READY){
				
				battlePk.setBeatStatus(BattlePk.STATUS_INSIDE);
				battlePk.setRoomStatus(BattlePk.ROOM_STATUS_CALL);
				
				BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
				battlePeriodMember.setIsDel(1);
				BattleRoom battleRoom = sessionManager.getObject(BattleRoom.class);
				Integer num = battleRoom.getNum();
				num--;
				battleRoom.setNum(num);
				battleRoom.setStatus(BattleRoom.STATUS_IN);
				battlePeriodMemberService.update(battlePeriodMember);
				battleRoomService.update(battleRoom);
				
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
