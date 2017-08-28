package com.wyc.common.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.domain.Account;
import com.wyc.common.repositories.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public Account add(Account account) {
		account.setId(UUID.randomUUID().toString());
		account.setUpdateAt(new DateTime());
		account.setCreateAt(new DateTime());
		return accountRepository.save(account);
	}

	public Account findOne(String id) {
		return accountRepository.findOne(id);
	}

	public Account fineOneSync(String accountId) {
		return accountRepository.findOneSync(accountId);
	}

	public void update(Account account) {
		account.setUpdateAt(new DateTime());
		accountRepository.save(account);
		
	}
	
	@Transactional
	public void initDrawUserCanTakeOutCount(int count) {
		accountRepository.initDrawUserCanTakeOutCount(count);
		
	}
}
