package com.battle.dao;

import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.battle.domain.BattlePeriodMember;

public interface BattlePeriodMemberDao extends CrudRepository<BattlePeriodMember, String>{

	BattlePeriodMember findOneByBattleIdAndBattleUserIdAndPeriodIdAndRoomIdAndIsDel(String battleId, String battleUserId,
			String periodId,String roomId,Integer isDel);

	//@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndIsDel(String battleId, String periodId, String roomId,List<Integer> statuses,Integer isDel,Pageable pageable);

	//@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	BattlePeriodMember findOneByRoomIdAndBattleUserIdAndIsDel(String roomId,String battleUserId,Integer isDel);
	
	//@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	BattlePeriodMember findOneByRoomIdAndUserIdAndIsDel(String roomId,String battleUserId,Integer isDel);
	
	@Query(value="select count(*)+1 from com.battle.domain.BattlePeriodMember bm where bm.roomId=:roomId and bm.score>:score ")
	Long rank(@Param("roomId")String roomId,@Param("score")Integer score);

	//@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndRoomIdOrderByCreateAtAsc(String battleId, String periodId, String roomId);

	//@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndRoomId(String battleId, String periodId, String roomId,
			Pageable pageable);
	
	//@Cacheable(value="userCache")
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	BattlePeriodMember findOne(String id);

	//@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	@Query("from com.battle.domain.BattlePeriodMember bpm where bpm.battleId=:battleId and bpm.periodId=:periodId and bpm.roomId=:roomId and bpm.status in (:statuses) and bpm.isDel=:isDel and EXISTS(select id from com.battle.domain.BattleRoomGroupMember brgm where brgm.groupId=:groupId and brgm.userId=bpm.userId) ")
	List<BattlePeriodMember> findAllByBattleIdAndPeriodIdAndRoomIdAndStatusInAndGroupIdAndIsDel(@Param("battleId")String battleId,
			@Param("periodId")String periodId, @Param("roomId")String roomId,@Param("statuses")List<Integer> statuses, @Param("groupId")String groupId, @Param("isDel") int isDel, Pageable pageable);

}
