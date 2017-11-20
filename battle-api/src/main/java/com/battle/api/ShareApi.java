package com.battle.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattlePeriodMember;
import com.battle.filter.element.CurrentMemberInfoFilter;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;

@Controller
@RequestMapping(value="/api/battle/share")
public class ShareApi {

	@RequestMapping(value="shareInProgress")
	@HandlerAnnotation(hanlerFilter=CurrentMemberInfoFilter.class)
	@ResponseBody
	@Transactional
	public ResultVo shareInProgress(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		
		BattlePeriodMember battlePeriodMember = sessionManager.getObject(BattlePeriodMember.class);
		
		Integer shareTime = battlePeriodMember.getShareTime();
		if(shareTime==null){
			shareTime = 0;
		}
		
		shareTime++;
		
		battlePeriodMember.setShareTime(shareTime);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		Map<String, Object> data = new HashMap<>();
		data.put("shareTime", shareTime);
		resultVo.setData(data);
		return resultVo;
	}
}
