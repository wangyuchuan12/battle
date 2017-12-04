package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDanTaskUserDao;
import com.battle.domain.BattleDanTaskUser;

@Service
public class BattleDanTaskUserService {

	@Autowired
	private BattleDanTaskUserDao battleDanTaskUserDao;

	public List<BattleDanTaskUser> findAllByDanIdAndUserIdOrderByIndexAsc(String danId,String userId) {
	
		return battleDanTaskUserDao.findAllByDanIdAndUserIdOrderByIndexAsc(danId,userId);
	}
}
