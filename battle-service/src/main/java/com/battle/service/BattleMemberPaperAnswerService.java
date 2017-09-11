package com.battle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleMemberPaperAnswerDao;

@Service
public class BattleMemberPaperAnswerService {

	@Autowired
	private BattleMemberPaperAnswerDao battleMemberPaperAnswerDao;
}
