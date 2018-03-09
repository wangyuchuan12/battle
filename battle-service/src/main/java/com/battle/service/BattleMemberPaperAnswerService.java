package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleMemberPaperAnswerDao;
import com.battle.domain.BattleMemberPaperAnswer;

@Service
public class BattleMemberPaperAnswerService {

	@Autowired
	private BattleMemberPaperAnswerDao battleMemberPaperAnswerDao;

	public void add(BattleMemberPaperAnswer battleMemberPaperAnswer) {
		
		battleMemberPaperAnswer.setId(UUID.randomUUID().toString());
		battleMemberPaperAnswer.setUpdateAt(new DateTime());
		battleMemberPaperAnswer.setCreateAt(new DateTime());
		
		battleMemberPaperAnswerDao.save(battleMemberPaperAnswer);
		
	}

	public void update(BattleMemberPaperAnswer battleMemberPaperAnswer) {
		
		battleMemberPaperAnswer.setUpdateAt(new DateTime());
		
		battleMemberPaperAnswerDao.save(battleMemberPaperAnswer);
		
	}

	public BattleMemberPaperAnswer findOneByQuestionAnswerId(String id) {
		
		return battleMemberPaperAnswerDao.findOneByQuestionAnswerId(id);
	}

	public BattleMemberPaperAnswer findOne(String id) {
		
		return battleMemberPaperAnswerDao.findOne(id);
	}

	public List<BattleMemberPaperAnswer> findAllByBattlePeriodMemberIdAndIsSyncData(String memberId, int isSyncData) {
		
		return battleMemberPaperAnswerDao.findAllByBattlePeriodMemberIdAndIsSyncData(memberId,isSyncData);
	}

	public BattleMemberPaperAnswer findOneByBattlePeriodMemberIdAndStageIndex(String memberId, Integer stageIndex) {
		
		return battleMemberPaperAnswerDao.findOneByBattlePeriodMemberIdAndStageIndex(memberId,stageIndex);
	}

	public List<BattleMemberPaperAnswer> findAllByBattlePeriodMemberIdAndIsReceive(String memberId, int isReceive) {
		
		return battleMemberPaperAnswerDao.findAllByBattlePeriodMemberIdAndIsReceive(memberId,isReceive);
	}

}
