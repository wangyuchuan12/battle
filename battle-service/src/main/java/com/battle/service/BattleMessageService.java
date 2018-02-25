package com.battle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleMessageDao;

@Service
public class BattleMessageService {

	@Autowired
	private BattleMessageDao battleMessageDao;
}
