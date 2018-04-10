package com.battle.manager.api;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.battle.domain.Battle;
import com.battle.domain.BattlePeriod;
import com.battle.domain.BattlePeriodStage;
import com.battle.domain.BattleUser;
import com.battle.service.BattlePeriodService;
import com.battle.service.BattlePeriodStageService;
import com.battle.service.BattleService;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.wx.domain.UserInfo;

@Controller
@RequestMapping("/api/battle")
public class BattleApi {
	
	@Autowired
	private BattleService battleService;
	
	@Autowired
	private BattlePeriodService battlePeriodService;
	
	@Autowired
	private BattlePeriodStageService battlePeriodStageService;
	
	@RequestMapping("/info")
	@ResponseBody
	public Object info(HttpServletRequest httpServletRequest){
		
		String id = httpServletRequest.getParameter("id");
		
		Battle battle = battleService.findOne(id);
		
		ResultVo resultVo  = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battle);
		
		return resultVo;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/add")
	public Object add(HttpServletRequest httpServletRequest){
		String name = httpServletRequest.getParameter("name");
		String instruction = httpServletRequest.getParameter("instruction");
		String isActivation = httpServletRequest.getParameter("isActivation");
		String headImg = httpServletRequest.getParameter("headImg");;
		Battle battle = new Battle();
		battle.setName(name);
		battle.setInstruction(instruction);
		battle.setIsActivation(Integer.parseInt(isActivation));
		battle.setHeadImg(headImg);
		battle.setStatus(Battle.IN_STATUS);
		battle.setMaxPeriodIndex(0);
		
		battleService.add(battle);
		
		

		Integer maxPeriodIndex = battle.getMaxPeriodIndex();
		if(maxPeriodIndex==null){
			maxPeriodIndex = 0;
		}
		maxPeriodIndex++;
		
		
		BattlePeriod battlePeriod = new BattlePeriod();
		battlePeriod.setBattleId(battle.getId());
		battlePeriod.setIndex(maxPeriodIndex);
		battlePeriod.setAuthorBattleUserId("");
		battlePeriod.setStatus(BattlePeriod.FREE_STATUS);
		battlePeriod.setTakepartCount(0);
		
		battlePeriod.setIsDefault(0);
		
		battlePeriod.setOwnerImg("");
		
		battlePeriod.setOwnerNickname("");
		
		battlePeriod.setStageCount(1);
		
		battlePeriod.setUnit(1);
		
		battlePeriod.setRightCount(0);
		
		battlePeriod.setWrongCount(0);
		
		battlePeriod.setIsPublic(1);
		
		battle.setMaxPeriodIndex(maxPeriodIndex);
		
		battlePeriodService.add(battlePeriod);
		battleService.update(battle);
		
		
		for(Integer i=1;i<31;i++){
			BattlePeriodStage battlePeriodStage = new BattlePeriodStage();
			battlePeriodStage.setBattleId(battle.getId());
			battlePeriodStage.setIndex(i);
			battlePeriodStage.setPeriodId(battlePeriod.getId());
			battlePeriodStage.setQuestionCount(4);
			battlePeriodStage.setPassRewardBean(10);
			battlePeriodStage.setPassCount(4);
			battlePeriodStageService.add(battlePeriodStage);
		}
		
		ResultVo resultVo = new ResultVo();
	    resultVo.setSuccess(true);
	    resultVo.setData(battle);;
	    return resultVo;

	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object update(HttpServletRequest httpServletRequest){
		String id = httpServletRequest.getParameter("id");
		String name = httpServletRequest.getParameter("name");
		String instruction = httpServletRequest.getParameter("instruction");
		String isActivation = httpServletRequest.getParameter("isActivation");
		String headImg = httpServletRequest.getParameter("headImg");;
		String status = httpServletRequest.getParameter("status");
		Battle battle = battleService.findOne(id);
		battle.setName(name);
		battle.setInstruction(instruction);
		battle.setIsActivation(Integer.parseInt(isActivation));
		battle.setHeadImg(headImg);
		battle.setStatus(Integer.parseInt(status));
		battleService.update(battle);
		
		ResultVo resultVo = new ResultVo();
	    resultVo.setSuccess(true);
	    resultVo.setData(battle);;
	    return resultVo;

	}
}
