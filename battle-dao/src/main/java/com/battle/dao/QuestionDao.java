package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.Question;

public interface QuestionDao extends CrudRepository<Question, String>{

	List<Question> findAllByIdIn(List<String> questionIds);

}
