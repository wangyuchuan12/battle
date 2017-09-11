package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.QuestionAnswer;

public interface QuestionAnswerDao extends CrudRepository<QuestionAnswer, String>{

	QuestionAnswer findOneByTargetIdAndType(String targetId, Integer type);

}
