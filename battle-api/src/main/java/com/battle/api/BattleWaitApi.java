package com.battle.api;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattleWait;
import com.battle.domain.BattleWaitUser;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleWaitService;
import com.battle.service.BattleWaitUserService;
import com.battle.socket.service.BattleWaitSocketService;
import com.battle.socket.service.InitRoomService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;


@Controller
@RequestMapping(value="/api/battle/battleWait")
public class BattleWaitApi {
	@Autowired
	private BattleWaitUserService battleWaitUserService;
	
	@Autowired
	private BattleWaitSocketService battleWaitSocketService;
	
	@Autowired
	private BattleWaitService battleWaitService;
	
	@Autowired
	private InitRoomService initRoomService;
	
	@RequestMapping(value="waitUsers")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object waitUsers(HttpServletRequest httpServletRequest)throws Exception{
		
		String waitId = httpServletRequest.getParameter("waitId");
		List<BattleWaitUser> battleWaitUsers = battleWaitUserService.findAllByWaitId(waitId);
		
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleWaitUsers);
		return resultVo;
	}
	
	@RequestMapping(value="into")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object into(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		String waitId = httpServletRequest.getParameter("waitId");
		String danUserId = httpServletRequest.getParameter("danUserId");
		BattleWaitUser battleWaitUser = battleWaitUserService.findOneByWaitIdAndUserId(waitId,userInfo.getId());
		if(battleWaitUser==null){
			BattleWait battleWait = battleWaitService.findOne(waitId);
			Integer num = battleWait.getNum();
			if(num==null){
				num = 0;
			}
			num++;
			battleWait.setNum(num);
			if(num>=battleWait.getMininum()&&battleWait.getIsPrepareStart()==0){
				battleWait.setIsPrepareStart(1);
				battleWait.setStatus(BattleWait.START_STATUS);
				initRoomService.addDanRoom(waitId);
			}
			battleWaitService.update(battleWait);
			
			battleWaitUser = new BattleWaitUser();
			battleWaitUser.setStatus(BattleWaitUser.INTO_STATUS);
			battleWaitUser.setUserId(userInfo.getId());
			battleWaitUser.setWaitId(waitId);
			battleWaitUser.setNickname(userInfo.getNickname());
			battleWaitUser.setImgUrl(userInfo.getHeadimgurl());
			if(CommonUtil.isNotEmpty(danUserId)){
				battleWaitUser.setDanUserId(danUserId);
			}
			battleWaitUserService.add(battleWaitUser);
			battleWaitSocketService.waitPublish(battleWaitUser);
		}else{
			battleWaitUser.setStatus(BattleWaitUser.INTO_STATUS);
			battleWaitUserService.update(battleWaitUser);
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleWaitUser);
		return resultVo;
	}
	
	
	
	@RequestMapping(value="ready")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object ready(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		String waitId = httpServletRequest.getParameter("waitId");
		
		BattleWaitUser battleWaitUser = battleWaitUserService.findOneByWaitIdAndUserId(waitId,userInfo.getId());
		
		battleWaitUser.setStatus(BattleWaitUser.READY_STATUS);
		
		battleWaitUserService.update(battleWaitUser);
		
		battleWaitSocketService.waitPublish(battleWaitUser);
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleWaitUser);
		return resultVo;
	}
	
	
	@RequestMapping(value="out")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object out(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		String waitId = httpServletRequest.getParameter("waitId");
		
		BattleWaitUser battleWaitUser = battleWaitUserService.findOneByWaitIdAndUserId(waitId,userInfo.getId());
		
		battleWaitUser.setStatus(BattleWaitUser.OUT_STATUS);
		
		battleWaitUserService.update(battleWaitUser);
		
		battleWaitSocketService.waitPublish(battleWaitUser);
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleWaitUser);
		return resultVo;
	}
	
}
