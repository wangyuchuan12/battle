package com.battle.service;

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

	public BattleMemberPaperAnswer findOneByBattlePeriodMemberId(String memberId) {
		
		return battleMemberPaperAnswerDao.findOneByBattlePeriodMemberId(memberId);
	}

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
}
