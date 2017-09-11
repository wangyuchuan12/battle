package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.QuestionDao;
import com.battle.domain.Question;

@Service
public class QuestionService {

	@Autowired
	private QuestionDao questionDao;

	public List<Question> findAllByIdIn(List<String> questionIds) {
		return questionDao.findAllByIdIn(questionIds);
	}

	public Question findOne(String id) {
		return questionDao.findOne(id);
	}
}
