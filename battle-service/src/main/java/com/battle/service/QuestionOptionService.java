package com.battle.service;

import java.util.List;

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
}
