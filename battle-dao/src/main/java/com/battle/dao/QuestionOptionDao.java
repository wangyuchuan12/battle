package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.QuestionOption;

public interface QuestionOptionDao extends CrudRepository<QuestionOption, String>{

	List<QuestionOption> findAllByQuestionId(String questionId);

}
