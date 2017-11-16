package com.wyc.common.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.wyc.common.domain.AccountAmountTakeoutRecord;

public interface AccountAmountTakeoutRecordRepository extends CrudRepository<AccountAmountTakeoutRecord, String>{

	List<AccountAmountTakeoutRecord> findAllByAccountIdOrderByCreateAtDesc(String accountId,Pageable pageable);

}
