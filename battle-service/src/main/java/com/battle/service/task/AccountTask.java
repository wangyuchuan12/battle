package com.battle.service.task;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.battle.service.BattleGiftService;
import com.wyc.common.service.AccountService;

@Service
public class AccountTask {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BattleGiftService battleGiftService;

	@Scheduled(cron = "0 59 23 * * ?")
	@Transactional
	public void giftInit(){
		accountService.updateAllAboutReceiveGiftCount(0);
		
		battleGiftService.setIsReceive(0);
	}
}
