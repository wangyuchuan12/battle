package com.battle.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.UserFrendDao;
import com.battle.domain.UserFriend;

@Service
public class UserFrendService {

	@Autowired
	private UserFrendDao userFrendDao;

	public void add(UserFriend userFriend) {
		
		userFriend.setUpdateAt(new DateTime());
		userFriend.setCreateAt(new DateTime());
		userFriend.setId(UUID.randomUUID().toString());
		
		userFrendDao.save(userFriend)
;		
	}

	public UserFriend findOneByUserIdAndFriendUserId(String userId, String recommendUserId) {
		
		return userFrendDao.findOneByUserIdAndFriendUserId(userId,recommendUserId);
	}

	public List<UserFriend> findAllByUserId(String userId) {
		
		return userFrendDao.findAllByUserId(userId);
	}
}
