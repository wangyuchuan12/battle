package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleQuestionReview;

public interface BattleQuestionReviewDao extends CrudRepository<BattleQuestionReview, String>{

	List<BattleQuestionReview> findAllByBattleIdAndBattleSubjectId(String battleId, String battleSubjectId);

}
