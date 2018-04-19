package com.wyc.common.repositories;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.common.domain.Account;

public interface AccountRepository extends CrudRepository<Account, String>{
	
	@Query(value="from com.wyc.common.domain.Account where id=:id")
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Account fineOneSync(@Param("id")String id);
	
	@Modifying
	@Query(value="update com.wyc.common.domain.Account du set du.canTakeOutCount=:count")
	void initDrawUserCanTakeOutCount(@Param("count") int count);

	
	@Modifying
	@Query(value="update com.wyc.common.domain.Account du set du.receiveGiftCount=:receiveGiftCount")
	void updateAllAboutReceiveGiftCount(@Param("receiveGiftCount")Integer receiveGiftCount);

	
	@Modifying
	@Query(value="update com.wyc.common.domain.Account account set account.loveLife = :loveLife where account.loveLife<:loveLife")
	void setLoveLife(@Param("loveLife")int loveLife);

}
