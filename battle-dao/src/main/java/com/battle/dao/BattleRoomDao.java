package com.battle.dao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.battle.domain.BattleRoom;

public interface BattleRoomDao extends CrudRepository<BattleRoom, String>{

	BattleRoom findOneByBattleIdAndPeriodIdAndOwner(String battleId, String periodId, String battleUserId);

	Page<BattleRoom> findAllByIsPublicAndStatusOrderByCreationTimeAsc(Integer isPublic,Integer status,Pageable pageable);


	Page<BattleRoom> findAllByBattleIdAndStatusAndIsPublic(String battleId, Integer status, int isPublic,
			Pageable pageable);

	@Query("from com.battle.domain.BattleRoom br where  br.id in (select bpm.roomId from com.battle.domain.BattlePeriodMember bpm where bpm.userId=:userId) order by br.updateAt desc")
	Page<BattleRoom> findAllByUserId(@Param("userId") String userId,Pageable pageable);

}
