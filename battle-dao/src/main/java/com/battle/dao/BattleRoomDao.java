package com.battle.dao;
import java.util.List;
import javax.persistence.LockModeType;
import org.joda.time.DateTime;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.battle.domain.BattleRoom;

public interface BattleRoomDao extends CrudRepository<BattleRoom, String>{

	List<BattleRoom> findAllByBattleIdAndPeriodIdAndOwnerAndIsPk(String battleId, String periodId, String battleUserId,Integer isPk);
	
	List<BattleRoom> findAllByBattleIdAndPeriodIdAndOwner(String battleId, String periodId, String battleUserId);

	Page<BattleRoom> findAllByIsDisplayAndStatusInAndIsDel(Integer isPublic,List<Integer> statuses,Integer isDel ,Pageable pageable);


	Page<BattleRoom> findAllByBattleIdAndStatusAndIsSearchAble(String battleId, Integer status, int isSearchAble,
			Pageable pageable);

	@Query("select br from com.battle.domain.BattleRoom br,com.battle.domain.BattlePeriodMember bpm where  br.id=bpm.roomId and bpm.userId=:userId and (bpm.status=1 or bpm.status=2) and br.isDel=0 and bpm.isDel=0 order by bpm.takepartAt desc")
	Page<BattleRoom> findAllByUserId(@Param("userId") String userId,Pageable pageable);
	
	@Query("select br from com.battle.domain.BattleRoom br,com.battle.domain.BattlePeriodMember bpm where  br.id=bpm.roomId and bpm.userId=:userId and (bpm.status=1 or bpm.status=2) and br.isDel=0 and bpm.isDel=0 and br.battleId=:battleId order by bpm.takepartAt desc")
	Page<BattleRoom> findAllByBattleIdAndUserId(@Param("battleId") String battleId,@Param("userId") String userId,Pageable pageable);

	Page<BattleRoom> findAll(Pageable pageable);
	
	@Cacheable(value="userCache") 
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	BattleRoom findOne(String id);

	List<BattleRoom> findAllByBattleIdAndPeriodIdAndStatusAndIsPassThrough(String battleId, String periodId,
			Integer status, int isPassThrough, Pageable pageable);

	List<BattleRoom> findAllByIsDanRoomAndStatus(int isDanRoom, Integer status,Pageable pageable);

	List<BattleRoom> findAllByIsDanRoomAndBattleIdAndPeriodIdAndStatusInAndStartTimeGreaterThan(int isDanRoom, String battleId,String periodId,List<Integer> statuses, DateTime now,Pageable pageable);
	
	List<BattleRoom> findAllByIsDanRoomAndBattleIdAndPeriodIdAndStatusIn(int isDanRoom, String battleId,String periodId,List<Integer> statuses,Pageable pageable);

	List<BattleRoom> findAllByDekornIdAndStatusIn(String dekornId, List<Integer> statuses, Pageable pageable);

	List<BattleRoom> findAllByIsDanRoomAndStatusIn(int isDanRoom, List<Integer> statuses, Pageable pageable);

}
