package com.wyc.common.repositories;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.common.domain.Account;

public interface AccountRepository extends CrudRepository<Account, String>{
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("from com.wyc.common.domain.Account account where account.id=:id")
	Account findOneSync(@Param("id") String id);
	
	@Modifying
	@Query(value="update com.wyc.common.domain.Account du set du.canTakeOutCount=:count")
	void initDrawUserCanTakeOutCount(@Param("count") int count);

}
