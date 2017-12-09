package com.battle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDanRewardDao;
import com.battle.domain.BattleDanReward;

@Service
public class BattleDanRewardService {

	@Autowired
	private BattleDanRewardDao battleDanRewardDao;

	public List<BattleDanReward> findAllByDanId(String danId, Pageable pageable) {
		
		return battleDanRewardDao.findAllByDanId(danId,pageable);
	}
}
