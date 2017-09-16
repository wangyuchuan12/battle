package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleMemberQuestionAnswerDao;
import com.battle.domain.BattleMemberQuestionAnswer;

@Service
public class BattleMemberQuestionAnswerService {

	@Autowired
	private BattleMemberQuestionAnswerDao memberQuestionAnswerDao;

	public void add(BattleMemberQuestionAnswer battleMemberQuestionAnswer) {
		
		battleMemberQuestionAnswer.setId(UUID.randomUUID().toString());
		battleMemberQuestionAnswer.setCreateAt(new DateTime());
		battleMemberQuestionAnswer.setUpdateAt(new DateTime());
		
		memberQuestionAnswerDao.save(battleMemberQuestionAnswer);
		
	}

	public List<BattleMemberQuestionAnswer> findAllByBattleMemberPaperAnswerId(String battleMemberPaperAnswerId) {
		return memberQuestionAnswerDao.findAllByBattleMemberPaperAnswerId(battleMemberPaperAnswerId);
	}
}
