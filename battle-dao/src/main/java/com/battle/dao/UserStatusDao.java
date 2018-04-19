package com.battle.dao;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.UserStatus;

public interface UserStatusDao extends CrudRepository<UserStatus, String>{

	@Lock(value=LockModeType.PESSIMISTIC_WRITE)
	UserStatus findOne(String id);

	UserStatus findOneByUserId(String userId);

	Page<UserStatus> findAllByIsLine(int isLine, Pageable pageable);

}
