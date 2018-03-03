package com.battle.service.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wyc.common.service.AccountService;

@Service
public class AccountTask {
	
	private AccountService accountService;

	@Scheduled(cron = "0 37 11 * * ?")
	public void giftInit(){
		
		System.out.println("..................giftInit");
		accountService.updateAllAboutGift(0, 3);
	}
}
