package com.battle.api;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.Battle;
import com.battle.domain.BattleDan;
import com.battle.domain.BattleDanPoint;
import com.battle.domain.BattleDanUser;
import com.battle.domain.BattleRoom;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleDanPointService;
import com.battle.service.BattleDanService;
import com.battle.service.BattleDanUserService;
import com.battle.service.BattleRoomService;
import com.battle.service.BattleService;
import com.battle.service.other.BattleRoomHandleService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
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
	private BattleRoomHandleService battleRoomHandleService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	
	@Autowired
	private BattleService battleService;
	@RequestMapping(value="list")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object list(HttpServletRequest httpServletRequest)throws Exception{
		
		List<BattleDanPoint> battleDanPoints = battleDanPointService.findAllByIsRun(1);
		
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
		
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		List<BattleDanUser> battleDanUsers = battleDanUserService.findAllByUserIdAndPointIdOrderByLevelAsc(userInfo.getId(),battleDanPoint.getId());
		
		if(battleDanUsers==null||battleDanUsers.size()==0){
			List<BattleDan> battleDans = battleDanService.findAllByPointIdOrderByLevelAsc(battleDanPoint.getId());
			battleDanUsers = new ArrayList<>();
			boolean flag = true;
			for(BattleDan battleDan:battleDans){
				BattleDanUser battleDanUser = new BattleDanUser();
				battleDanUser.setDanId(battleDan.getId());
				battleDanUser.setDanName(battleDan.getName());
				battleDanUser.setImgUrl(battleDan.getImgUrl());
				battleDanUser.setLevel(battleDan.getLevel());
				battleDanUser.setPointId(battleDan.getPointId());
				if(flag){
					battleDanUser.setStatus(BattleDanUser.STATUS_IN);
				}else{
					flag = false;
					battleDanUser.setStatus(BattleDanUser.STATUS_FREE);
				}
				
				battleDanUser.setUserId(userInfo.getId());
				
				battleDanUserService.add(battleDanUser);
				
				battleDanUsers.add(battleDanUser);
			}
		}else{
			for(Integer i = 0;i<battleDanUsers.size();i++){
				BattleDanUser battleDanUser = battleDanUsers.get(i);
				if(battleDanUser.getStatus()==BattleDanUser.STATUS_SUCCESS){
					if(i<battleDanUsers.size()-1){
						BattleDanUser battleDanUser2 = battleDanUsers.get(i+1);
						if(battleDanUser2.getStatus()==BattleDanUser.STATUS_FREE){
							battleDanUser2.setStatus(BattleDanUser.STATUS_IN);
							battleDanUserService.update(battleDanUser2);
						}
					}
				}
			}
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		resultVo.setData(battleDanUsers);
		
		return resultVo;
		
	}
	
	@RequestMapping(value="startDan")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object startDan(HttpServletRequest httpServletRequest)throws Exception{
		String battleDanUserId = httpServletRequest.getParameter("battleDanUserId");
		BattleDanUser battleDanUser = battleDanUserService.findOne(battleDanUserId);
		
		
		Battle battle = battleService.findOne(battleDanUser.getBattleId());
		
		String roomId = battleDanUser.getRoomId();
		
		BattleRoom battleRoom = null;
		
		if(!CommonUtil.isEmpty(roomId)){
			battleRoom = battleRoomService.findOne(roomId);
		}
		
		if(battleRoom==null){
			battleRoom = battleRoomHandleService.initRoom(battle);
			battleRoom.setMaxinum(0);
			battleRoom.setMininum(0);
			battleRoom.setPeriodId(battleDanUser.getPeriodId());
			battleRoom.setIsSearchAble(0);
			battleRoomService.add(battleRoom);
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleRoom);
		return resultVo;
	
	}
}
