package com.battle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleQuestionReviewDao;

@Service
public class BattleQuestionReviewService {

	@Autowired
	private BattleQuestionReviewDao battleQuestionReviewDao;
}
