package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.QuestionAnswer;

public interface QuestionAnswerDao extends CrudRepository<QuestionAnswer, String>{

	List<QuestionAnswer> findAllByTargetIdAndType(String targetId, Integer type);

}
