package com.battle.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.battle.service.task.rankBattleTask;

@Controller
@RequestMapping(value="/api/battle/task")
public class TaskApi {
	@Autowired
	private rankBattleTask rankBattleTask;
	public void rankBattleTask(HttpServletRequest httpServletRequest){
		rankBattleTask.battleRoomInit();
	}
}
