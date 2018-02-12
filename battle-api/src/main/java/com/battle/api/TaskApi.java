package com.battle.api;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.task.rankBattleTask;
import com.wyc.annotation.HandlerAnnotation;

@Controller
@RequestMapping(value="/api/battle/task")
public class TaskApi {
	@Autowired
	private rankBattleTask rankBattleTask;
	@RequestMapping(value="rankBattleTask")
	@ResponseBody
	public void rankBattleTask(HttpServletRequest httpServletRequest){
		rankBattleTask.battleRoomInit();
	}
}
