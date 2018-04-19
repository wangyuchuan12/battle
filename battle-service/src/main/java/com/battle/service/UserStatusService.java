package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.battle.dao.UserStatusDao;
import com.battle.domain.UserStatus;

@Service
public class UserStatusService {

	@Autowired
	private UserStatusDao userStatusDao;


	public UserStatus findOne(String id) {
		
		return userStatusDao.findOne(id);
	}


	public void add(UserStatus userStatus) {
		
		userStatus.setId(UUID.randomUUID().toString());
		
		userStatus.setCreateAt(new DateTime());
		userStatus.setUpdateAt(new DateTime());
		
		userStatusDao.save(userStatus);
		
	}


	public void update(UserStatus userStatus) {
		
		userStatus.setUpdateAt(new DateTime());
		userStatusDao.save(userStatus);
		
	}


	public UserStatus findOneByUserId(String userId) {
		
		return userStatusDao.findOneByUserId(userId);
	}


	public Page<UserStatus> findAllByIsLine(int isLine, Pageable pageable) {
		
		return userStatusDao.findAllByIsLine(isLine,pageable);
	}
}
