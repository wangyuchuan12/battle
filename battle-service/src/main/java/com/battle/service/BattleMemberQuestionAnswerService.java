package com.battle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleMemberQuestionAnswerDao;

@Service
public class BattleMemberQuestionAnswerService {

	@Autowired
	private BattleMemberQuestionAnswerDao memberQuestionAnswerDao;
}
