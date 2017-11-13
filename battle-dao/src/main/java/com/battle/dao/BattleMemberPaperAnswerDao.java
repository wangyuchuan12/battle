package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleMemberPaperAnswer;

public interface BattleMemberPaperAnswerDao extends CrudRepository<BattleMemberPaperAnswer, String>{

	BattleMemberPaperAnswer findOneByBattlePeriodMemberId(String memberId);

	BattleMemberPaperAnswer findOneByQuestionAnswerId(String id);

	List<BattleMemberPaperAnswer> findAllByBattlePeriodMemberIdAndIsSyncData(String memberId, int isSyncData);

}
