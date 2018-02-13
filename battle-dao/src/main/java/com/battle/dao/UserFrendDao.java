package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.UserFriend;

public interface UserFrendDao extends CrudRepository<UserFriend, String>{

	UserFriend findOneByUserIdAndFriendUserId(String userId, String recommendUserId);

	List<UserFriend> findAllByUserId(String userId);

}
