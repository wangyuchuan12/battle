package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleMemberQuestionAnswer;

public interface BattleMemberQuestionAnswerDao extends CrudRepository<BattleMemberQuestionAnswer, String>{

	List<BattleMemberQuestionAnswer> findAllByBattleMemberPaperAnswerId(String battleMemberPaperAnswerId);

}
