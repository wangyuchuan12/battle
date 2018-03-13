package com.battle.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleRoomStepIndex;

public interface BattleRoomStepIndexDao extends CrudRepository<BattleRoomStepIndex, String>{

	//@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	List<BattleRoomStepIndex> findAllByRoomIdOrderByStepIndexAsc(String roomId);

	//@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	List<BattleRoomStepIndex> findAllByRoomIdAndStepIndexGreaterThanAndStepIndexLessThanEqualOrderByStepIndexAsc(
			String roomId,Integer startIndex, Integer endIndex);

}
