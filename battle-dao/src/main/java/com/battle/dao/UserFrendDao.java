package com.battle.dao;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.UserFriend;

public interface UserFrendDao extends CrudRepository<UserFriend, String>{

	UserFriend findOneByUserIdAndFriendUserId(String userId, String recommendUserId);

}
