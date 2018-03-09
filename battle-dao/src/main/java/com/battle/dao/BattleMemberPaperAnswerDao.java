package com.battle.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleMemberPaperAnswer;

public interface BattleMemberPaperAnswerDao extends CrudRepository<BattleMemberPaperAnswer, String>{


	//@Cacheable(value="userCache",keyGenerator="sessionKeyGenerator")
	BattleMemberPaperAnswer findOneByQuestionAnswerId(String id);

	List<BattleMemberPaperAnswer> findAllByBattlePeriodMemberIdAndIsSyncData(String memberId, int isSyncData);
	
	
	//@Cacheable(value="userCache",keyGenerator="sessionKeyGenerator") 
	public BattleMemberPaperAnswer findOne(String id);

	BattleMemberPaperAnswer findOneByBattlePeriodMemberIdAndStageIndex(String memberId, Integer stageIndex);

	List<BattleMemberPaperAnswer> findAllByBattlePeriodMemberIdAndIsReceive(String memberId, int isReceive);

}
