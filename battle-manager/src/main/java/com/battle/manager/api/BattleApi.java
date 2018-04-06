package com.battle.manager.api;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.battle.domain.Battle;
import com.battle.service.BattleService;
import com.wyc.common.domain.vo.ResultVo;

@Controller
@RequestMapping("/api/battle")
public class BattleApi {
	
	@Autowired
	private BattleService battleService;
	
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
		battleService.add(battle);
		
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
