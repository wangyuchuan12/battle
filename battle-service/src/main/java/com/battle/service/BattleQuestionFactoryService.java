package com.battle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleQuestionFactoryDao;

@Service
public class BattleQuestionFactoryService {

	@Autowired
	private BattleQuestionFactoryDao battleQuestionFactoryDao;
}
