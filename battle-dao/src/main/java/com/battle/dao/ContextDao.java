package com.battle.dao;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.Context;



public interface ContextDao extends CrudRepository<Context, String>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Context findOneByCode(String code);

}
