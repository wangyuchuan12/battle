package com.battle.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.battle.domain.BattleDan;
import com.battle.domain.BattleDanPoint;
import com.battle.domain.BattleDanProject;
import com.battle.domain.BattleDanTask;
import com.battle.domain.BattleDanTaskUser;
import com.battle.domain.BattleDanUser;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleDanPointService;
import com.battle.service.BattleDanProjectService;
import com.battle.service.BattleDanService;
import com.battle.service.BattleDanTaskService;
import com.battle.service.BattleDanTaskUserService;
import com.battle.service.BattleDanUserService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;
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

	
	
	@RequestMapping(value="tasks")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object tasks(HttpServletRequest httpServletRequest)throws Exception{
		
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
				battleDanTaskUserService.add(battleDanTaskUser);
				battleDanTaskUsers.add(battleDanTaskUser);
			}
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		resultVo.setData(battleDanTaskUsers);
		
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
		
		BattleDanUser battleDanUser = battleDanUserService.findOneByDanIdAndUserId(danId,userInfo.getId());
		
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
}
