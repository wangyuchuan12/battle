package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleMemberPaperAnswer;

public interface BattleMemberPaperAnswerDao extends CrudRepository<BattleMemberPaperAnswer, String>{

	BattleMemberPaperAnswer findOneByBattlePeriodMemberId(String memberId);

}
