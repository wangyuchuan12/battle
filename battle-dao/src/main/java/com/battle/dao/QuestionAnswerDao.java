package com.battle.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.QuestionAnswer;

public interface QuestionAnswerDao extends CrudRepository<QuestionAnswer, String>{

	@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	List<QuestionAnswer> findAllByTargetIdAndType(String targetId, Integer type);
	
	@Cacheable("userCache")
	public QuestionAnswer findOne(String id);

}
