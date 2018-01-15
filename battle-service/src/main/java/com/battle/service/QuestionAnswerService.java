package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.QuestionAnswerDao;
import com.battle.domain.QuestionAnswer;

@Service
public class QuestionAnswerService {

	@Autowired
	private QuestionAnswerDao questionAnswerDao;

	public List<QuestionAnswer> findAllByTargetIdAndType(String targetId, Integer type) {
		
		return questionAnswerDao.findAllByTargetIdAndType(targetId,type);
	}

	public void add(QuestionAnswer questionAnswer) {
		
		questionAnswer.setId(UUID.randomUUID().toString());
		questionAnswer.setUpdateAt(new DateTime());
		questionAnswer.setCreateAt(new DateTime());
		
		questionAnswerDao.save(questionAnswer);
		
	}

	public void update(QuestionAnswer questionAnswer) {
		
		questionAnswer.setUpdateAt(new DateTime());
		
		questionAnswerDao.save(questionAnswer);
	}

	public QuestionAnswer findOne(String id) {
		
		return questionAnswerDao.findOne(id);
	}
}
