package com.battle.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleNoticeDao;
import com.battle.domain.BattleNotice;

@Service
public class BattleNoticeService {

	@Autowired
	private BattleNoticeDao battleNoticeDao;

	public Page<BattleNotice> findAllByUserIdAndTypeAndRoomIdAndIsRead(String userId , Integer type, String roomId, int isRead,
			Pageable pageable) {
		
		return battleNoticeDao.findAllByUserIdAndTypeAndRoomIdAndIsRead(userId,type,roomId,isRead,pageable);
	}

	public void update(BattleNotice battleNotice) {
		
		battleNotice.setUpdateAt(new DateTime());
		
		battleNoticeDao.save(battleNotice);
		
	}

	public void add(BattleNotice battleNotice) {
		
		battleNotice.setId(UUID.randomUUID().toString());
		battleNotice.setCreateAt(new DateTime());
		battleNotice.setUpdateAt(new DateTime());
		
		battleNoticeDao.save(battleNotice);
		
	}
}
