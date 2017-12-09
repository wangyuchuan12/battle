package com.battle.dao;

import com.battle.domain.BattleDanReward;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface BattleDanRewardDao extends CrudRepository<BattleDanReward, String>{

	List<BattleDanReward> findAllByDanId(String danId, Pageable pageable);

}
