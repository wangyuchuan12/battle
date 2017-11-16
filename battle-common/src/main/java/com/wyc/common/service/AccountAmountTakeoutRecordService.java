package com.wyc.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.repositories.AccountAmountTakeoutRecordRepository;

@Service
public class AccountAmountTakeoutRecordService {

	@Autowired
	private AccountAmountTakeoutRecordRepository accountAmountTakeoutRecordRepository;
}
