package com.battle.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.domain.BattleDrawItem;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.BattleDrawItemService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.vo.ResultVo;

@Controller
@RequestMapping(value="/api/battle/battleDraw")
public class BattleDrawApi {

	@Autowired
	private BattleDrawItemService battleDrawItemService;
	
	
	@RequestMapping(value="list")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public ResultVo list(HttpServletRequest httpServletRequest){
		List<BattleDrawItem> battleDrawItems = battleDrawItemService.findAllOrderByLevel();
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(battleDrawItems);
		
		return resultVo;
	}

}
