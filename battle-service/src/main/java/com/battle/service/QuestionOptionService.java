package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.QuestionOptionDao;
import com.battle.domain.QuestionOption;

@Service
public class QuestionOptionService {
	
	@Autowired
	private QuestionOptionDao questionOptionDao;

	public List<QuestionOption> findAllByQuestionId(String questionId) {
		
		return questionOptionDao.findAllByQuestionId(questionId);
	}

	public QuestionOption findOne(String id) {
		
		return questionOptionDao.findOne(id);
	}

	public void add(QuestionOption questionOption) {
		
		questionOption.setId(UUID.randomUUID().toString());
		questionOption.setCreateAt(new DateTime());
		questionOption.setUpdateAt(new DateTime());
		
		questionOptionDao.save(questionOption);
		
	}

	public void update(QuestionOption questionOption) {
		
		questionOption.setUpdateAt(new DateTime());
		
		questionOptionDao.save(questionOption);
		
	}
}
