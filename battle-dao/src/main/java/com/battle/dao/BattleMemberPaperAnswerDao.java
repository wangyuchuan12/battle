package com.battle.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleMemberPaperAnswer;

public interface BattleMemberPaperAnswerDao extends CrudRepository<BattleMemberPaperAnswer, String>{

	@Cacheable(value="userCache")
	BattleMemberPaperAnswer findOneByBattlePeriodMemberId(String memberId);

	@Cacheable(value="userCache")
	BattleMemberPaperAnswer findOneByQuestionAnswerId(String id);

	List<BattleMemberPaperAnswer> findAllByBattlePeriodMemberIdAndIsSyncData(String memberId, int isSyncData);
	
	
	@Cacheable(value="userCache") 
	public BattleMemberPaperAnswer findOne(String id);

}
