package com.wyc.common.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wyc.common.domain.AccountAmountTakeoutRecord;
import com.wyc.common.repositories.AccountAmountTakeoutRecordRepository;

@Service
public class AccountAmountTakeoutRecordService {

	@Autowired
	private AccountAmountTakeoutRecordRepository accountAmountTakeoutRecordRepository;

	public List<AccountAmountTakeoutRecord> findAllByAccountIdOrderByCreateAtDesc(String accountId,Pageable pageable) {
		
		return accountAmountTakeoutRecordRepository.findAllByAccountIdOrderByCreateAtDesc(accountId,pageable);
	}

	public void add(AccountAmountTakeoutRecord accountAmountTakeoutRecord) {
		
		accountAmountTakeoutRecord.setId(UUID.randomUUID().toString());
		accountAmountTakeoutRecord.setCreateAt(new DateTime());
		accountAmountTakeoutRecord.setUpdateAt(new DateTime());
		
		accountAmountTakeoutRecordRepository.save(accountAmountTakeoutRecord);
		
	}
}
