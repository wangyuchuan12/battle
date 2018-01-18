package com.battle.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.Battle;
import com.battle.domain.BattleExpert;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattleUser;
import com.battle.filter.element.CurrentBattleUserFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleExpertService;
import com.battle.service.BattlePeriodService;
import com.battle.service.BattleService;
import com.battle.service.BattleUserService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;
import com.wyc.common.wx.domain.WxContext;

@Controller
@RequestMapping(value="/api/battleExpert/")
public class BattleExpertApi {
	
	@Autowired
	private BattleExpertService battleExpertService;
	
	@Autowired
	private BattleUserService battleUserService;
	
	@Autowired
	private BattleService battleService;
	
	@Autowired
	private BattlePeriodService battlePeriodService;
	
	@Autowired
	private WxContext wxContext;

	@RequestMapping(value="apply")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object apply(HttpServletRequest httpServletRequest)throws Exception{
		
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		String phonenum = httpServletRequest.getParameter("phonenum");
		
		String code = httpServletRequest.getParameter("code");
		
		Object vCode = sessionManager.rawGetBySession("auth_msg_code_"+phonenum+"_"+wxContext.getApplyExpertProjectCode());
		
		String wechat = httpServletRequest.getParameter("wechat");
		
		String battleId = httpServletRequest.getParameter("battleId");
		
		String introduce = httpServletRequest.getParameter("introduce");

		if(CommonUtil.isEmpty(code)||CommonUtil.isEmpty(vCode)||!code.equals(vCode)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("验证不通过");
			return resultVo;
		}
		
		if(CommonUtil.isEmpty(phonenum)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setMsg("手机号码不能为空");
			return resultVo;
		}
		
		if(CommonUtil.isEmpty(wechat)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setMsg("微信号不能为空");
			return resultVo;
		}
		
		if(CommonUtil.isEmpty(battleId)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setMsg("battleId参数不能为空");
			return resultVo;
		}
		
		Battle battle = battleService.findOne(battleId);
		
		if(battle==null){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setMsg("找不到比赛");
			return resultVo;
		}
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		BattleUser battleUser = battleUserService.findOneByUserIdAndBattleId(userInfo.getId(), battleId);
		if(battleUser==null){
			battleUser = new BattleUser();
			battleUser.setBattleId(battleId);
			battleUser.setUserId(userInfo.getId());
			battleUser.setIsManager(0);
			battleUser.setOpenId(userInfo.getOpenid());
			battleUserService.add(battleUser);
		}
		
		BattleExpert battleExpert = battleExpertService.findOneByBattleIdAndBattleUserId(battleId,battleUser.getId());
		if(battleExpert==null){
			battleExpert = new BattleExpert();
			battleExpert.setApplyDateTime(new DateTime());
			battleExpert.setBattleId(battleId);
			battleExpert.setBattleUserId(battleUser.getId());
			battleExpert.setHandNum(0);
			battleExpert.setIsHand(0);
			battleExpert.setPhonenum(phonenum);
			battleExpert.setWechat(wechat);
			battleExpert.setIntroduce(introduce);
			battleExpert.setUserImg(userInfo.getHeadimgurl());
			battleExpert.setNickname(userInfo.getNickname());
			battleExpert.setOpenid(userInfo.getOpenid());
			
			battleExpert.setStatus(BattleExpert.AUDIT_STATUS);
			battleExpertService.add(battleExpert);
			
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			resultVo.setMsg("提交成功");
			resultVo.setData(battleExpert);
			return resultVo;
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setMsg("用户已存在");
			return resultVo;
		}
	}
	
	@RequestMapping(value="pass_audit")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object audit(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		String battleExpertId = httpServletRequest.getParameter("battleExpertId");
		
		//审核类型 0拒绝 1接受 2撤回权限
		String type = httpServletRequest.getParameter("type");
		
		String reason = httpServletRequest.getParameter("reason");
		
		String remark = httpServletRequest.getParameter("remark");
		
		Integer typeInt = Integer.parseInt(type);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		BattleExpert battleExpert = battleExpertService.findOne(battleExpertId);
		
		
		BattleUser battleUser = battleUserService.findOneByUserIdAndBattleId(userInfo.getId(), battleExpert.getBattleId());
		
		if(battleUser==null||battleUser.getIsManager()==0){
			ResultVo resultVo = new ResultVo();
			resultVo.setErrorMsg("该用户没有审核权限");
			resultVo.setSuccess(false);
			return resultVo;
		}
		
		if(typeInt==0){
			if(battleExpert.getStatus()==BattleExpert.APPLY_STATUS){
				battleExpert.setStatus(BattleExpert.RJECT_STATUS);
				battleExpert.setRejectBattleUserId(battleUser.getId());
				battleExpert.setRejectDateTime(new DateTime());
				battleExpert.setRejectReason(reason);
				battleExpertService.update(battleExpert);
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(true);
				return resultVo;
			}else{
				ResultVo resultVo = new ResultVo();
				resultVo.setErrorMsg("用户不是审核中状态");
				resultVo.setSuccess(false);
				return resultVo;
			}
		}
		
		if(typeInt==1){
			if(battleExpert.getStatus()==BattleExpert.APPLY_STATUS){
				battleExpert.setStatus(BattleExpert.AUDIT_STATUS);
				battleExpert.setAuditDateTime(new DateTime());
				battleExpert.setAuditBattleUserId(battleUser.getId());
				battleExpert.setRemark(remark);
				battleExpertService.update(battleExpert);
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(true);
				return resultVo;
			}else{
				ResultVo resultVo = new ResultVo();
				resultVo.setErrorMsg("用户不是审核中状态");
				resultVo.setSuccess(false);
				return resultVo;
			}
		}
		
		if(typeInt==2){
			if(battleExpert.getStatus()==BattleExpert.AUDIT_STATUS){
				battleExpert.setStatus(BattleExpert.REVOKE_STATUS);
				battleExpert.setRevokeBattleUserId(battleUser.getId());
				battleExpert.setRevokeDateTime(new DateTime());
				battleExpert.setRevokeReason(reason);
				battleExpertService.update(battleExpert);
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(true);
				return resultVo;
			}else{
				ResultVo resultVo = new ResultVo();
				resultVo.setErrorMsg("用户不是已审核状态");
				resultVo.setSuccess(false);
				return resultVo;
			}
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(false);
		resultVo.setErrorMsg("操作错误，请检查");
		return resultVo;
		
	}
	
	@RequestMapping(value="qualified")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=CurrentBattleUserFilter.class)
	public Object qualified(HttpServletRequest httpServletRequest)throws Exception{
		
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		if(sessionManager.isReturn()){
			ResultVo resultVo = (ResultVo)sessionManager.getReturnValue();
			return resultVo;
		}else{
			
		}
		
		String battleId = httpServletRequest.getParameter("battleId");
		
		BattleUser battleUser = sessionManager.getObject(BattleUser.class);
		
		BattleExpert battleExpert = battleExpertService.findOneByBattleIdAndBattleUserId(battleId, battleUser.getId());
		
		if(battleExpert!=null&&battleExpert.getStatus()==BattleExpert.AUDIT_STATUS){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			Map<String, Object> data = new HashMap<>();
			data.put("qualified", true);
			data.put("expertId", battleExpert.getId());
			resultVo.setData(data);
			return resultVo;
		}else if(battleExpert==null){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			Map<String, Object> data = new HashMap<>();
			data.put("qualified", false);
			data.put("status", 0);
			resultVo.setData(data);
			return resultVo;
		}else if(battleExpert.getStatus()==BattleExpert.APPLY_STATUS){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			Map<String, Object> data = new HashMap<>();
			data.put("qualified", false);
			data.put("status", 1);
			data.put("expertId", battleExpert.getId());
			resultVo.setData(data);
			return resultVo;
		}else if(battleExpert.getStatus()==BattleExpert.RJECT_STATUS){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			Map<String, Object> data = new HashMap<>();
			data.put("qualified", false);
			data.put("status", 2);
			data.put("expertId", battleExpert.getId());
			resultVo.setData(data);
			return resultVo;
		}else if(battleExpert.getStatus()==BattleExpert.REVOKE_STATUS){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			Map<String, Object> data = new HashMap<>();
			data.put("qualified", false);
			data.put("status", 3);
			resultVo.setData(data);
			data.put("expertId", battleExpert.getId());
			return resultVo;
		}
		return null;
	}
	
	@RequestMapping(value="confirmPeriods")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=CurrentBattleUserFilter.class)
	public Object confirmPeriods(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		BattleUser battleUser = sessionManager.getObject(BattleUser.class);
		
		String battleId = httpServletRequest.getParameter("battleId");
		
		List<BattlePeriod> battlePeriods = battlePeriodService.findAllByBattleIdAndAuthorBattleUserIdAndStatus(battleId,battleUser.getId(),BattlePeriod.IN_STATUS);
	
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battlePeriods);
		
		return resultVo;
	
	}
	
	@RequestMapping(value="info")
	@ResponseBody
	@HandlerAnnotation(hanlerFilter=CurrentBattleUserFilter.class)
	public Object info(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		String id = httpServletRequest.getParameter("id");
		
		String battleId = httpServletRequest.getParameter("battleId");
		
		BattleUser battleUser = sessionManager.getObject(BattleUser.class);
		
		BattleExpert battleExpert = battleExpertService.findOne(id);
		
		Battle battle = battleService.findOne(battleId);
		
		Map<String, Object> data = new HashMap<>();
		data.put("id", battleExpert.getId());
	//	data.put("applyDateTime", battleExpert.getApplyDateTime());
		data.put("auditBattleUserId", battleExpert.getAuditBattleUserId());
		data.put("auditDateTime", battleExpert.getAuditDateTime());
		data.put("battleId", battleExpert.getBattleId());
		data.put("battleUserId", battleExpert.getBattleUserId());
		data.put("handNum", battleExpert.getHandNum());
		data.put("introduce", battleExpert.getIntroduce());
		data.put("isHand", battleExpert.getIsHand());
		data.put("phonenum", battleExpert.getPhonenum());
		data.put("rejectBattleUserId", battleExpert.getRejectBattleUserId());
		data.put("rejectDateTime", battleExpert.getRejectDateTime());
		data.put("rejectReason", battleExpert.getRejectReason());
		data.put("remark", battleExpert.getRemark());
		data.put("status", battleExpert.getStatus());
		data.put("wechat", battleExpert.getWechat());
		
		data.put("nickname", battleExpert.getNickname());
		
		data.put("userImg", battleExpert.getUserImg());
		
		data.put("headImg", battle.getHeadImg());
		data.put("name", battle.getName());
		
		if(battleUser.getIsManager()==1){
			
			//管理员
			data.put("identity", 0);
		}else if(battleExpert.getBattleUserId().equals(battleUser.getId())){
			
			//申请人
			data.put("identity", 1);
		}else{
			
			//游客
			data.put("identity", 2);
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(data);
		
		return resultVo;
	}
}
