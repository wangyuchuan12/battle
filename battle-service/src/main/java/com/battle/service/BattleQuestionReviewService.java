package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleQuestionReviewDao;
import com.battle.domain.BattleQuestionReview;

@Service
public class BattleQuestionReviewService {

	@Autowired
	private BattleQuestionReviewDao battleQuestionReviewDao;

	public List<BattleQuestionReview> findAllByBattleIdAndBattleSubjectId(String battleId, String battleSubjectId) {
		
		return battleQuestionReviewDao.findAllByBattleIdAndBattleSubjectId(battleId,battleSubjectId);
	}

	public BattleQuestionReview findOne(String id) {
		
		return battleQuestionReviewDao.findOne(id);
	}
}
