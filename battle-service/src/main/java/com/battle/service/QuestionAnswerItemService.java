package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.QuestionAnswerItemDao;
import com.battle.domain.QuestionAnswerItem;

@Service
public class QuestionAnswerItemService {

	@Autowired
	private QuestionAnswerItemDao questionAnswerItemDao;

	public void add(QuestionAnswerItem questionAnswerItem) {
		
		questionAnswerItem.setId(UUID.randomUUID().toString());
		questionAnswerItem.setCreateAt(new DateTime());
		questionAnswerItem.setUpdateAt(new DateTime());
		questionAnswerItemDao.save(questionAnswerItem);
		
	}
}
